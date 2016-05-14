package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SongReader {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;
	private long microSeconds;
	private float tickPerMic;
	private Sequencer sequencer;
	private NoteQueue notes;
	private ArrayList<long[]> temp = new ArrayList<long[]>();
	public static final String[] NOTE_NAMES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

	public SongReader(String file, Pane pan, Key[] keys,Stage f,Pane menu) throws Exception {
		File path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"../music/"+file);
		String s = path.getParentFile().getAbsolutePath();
		System.out.println(path.getAbsolutePath());
		Sequence sequence = MidiSystem.getSequence(path);
		microSeconds = sequence.getMicrosecondLength();
		tickPerMic = (sequence.getTickLength() / (float) (microSeconds));
		sequencer = MidiSystem.getSequencer();
		InputStream fi = new BufferedInputStream(new FileInputStream(path));
		// System.out.println(sequencer.getTempoInBPM());
		sequencer.open();
		sequencer.setSequence(fi);
		int trackNumber = -1;
		
		Color[] color = { Color.BLUE, Color.CORAL, Color.CYAN, Color.VIOLET, Color.FUCHSIA, Color.GREEN, Color.RED,
				Color.ORCHID, Color.TURQUOISE, Color.ORANGERED, Color.DARKORCHID, Color.DEEPPINK, Color.DARKGOLDENROD,
				Color.LIGHTSLATEGREY, Color.CADETBLUE, Color.FIREBRICK, Color.MIDNIGHTBLUE, Color.LIGHTSTEELBLUE };
		notes = new NoteQueue(sequence.getTickLength(), microSeconds, pan, sequencer,f,menu);
		
		for (Track track : sequence.getTracks()) 
		{
			trackNumber++;
			System.out.println("Track " + trackNumber + ": size = " + track.size());
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				System.out.print("@" + event.getTick() + " ");
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) 
				{
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) 
					{
						int key = sm.getData1();
						int octave = (key / 12) - 1;
						int note = key % 12;
						String noteName = NOTE_NAMES[note];
						int velocity = sm.getData2();
						System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
						boolean found = false;
						int j = 0;
						if (velocity == 0 || sm.getCommand() == NOTE_OFF)
							while (j < temp.size() && !found) 
							{
								if (temp.get(j)[0] == key) 
								{
									if(key-21<0)
										notes.add(new Note(temp.get(j)[1], key - 9, event.getTick() - temp.get(j)[1],
												tickPerMic, color[trackNumber], keys[0]));
									else if(key-21>87)
										notes.add(new Note(temp.get(j)[1], key - 9, event.getTick() - temp.get(j)[1],
												tickPerMic, color[trackNumber], keys[87]));
									else
										notes.add(new Note(temp.get(j)[1], key - 9, event.getTick() - temp.get(j)[1],
												tickPerMic, color[trackNumber], keys[key - 21]));
									temp.remove(j);
									found = true;
								}
								j++;
							}
						else
							temp.add(new long[] { key, event.getTick() });
					}
				}
			}
		}
		notes.start();
	}
	
	/**
	 * gets the sequencer
	 */
	public Sequencer getSequencer()
	{
		return sequencer;
	}
}
