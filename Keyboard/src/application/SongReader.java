package application;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import javafx.scene.canvas.GraphicsContext;

public class SongReader 
{
		public static final int NOTE_ON = 0x90;
	    public static final int NOTE_OFF = 0x80;
	    private Queue<Note> notes = new LinkedList();
	    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	    public SongReader(GraphicsContext gc) throws Exception {
	        Sequence sequence = MidiSystem.getSequence(new File("C:/Project/Keyboard/music/HotelCalifornia.mid"));

	        int trackNumber = 0;
	        for (Track track :  sequence.getTracks()) {
	            trackNumber++;
	            System.out.println("Track " + trackNumber + ": size = " + track.size());
	            System.out.println();
	            for (int i=0; i < track.size(); i++) { 
	                MidiEvent event = track.get(i);
	                System.out.print("@" + event.getTick() + " ");
	                MidiMessage message = event.getMessage();
	                if (message instanceof ShortMessage) {
	                    ShortMessage sm = (ShortMessage) message;
	                    System.out.print("Channel: " + sm.getChannel() + " ");
	                    if (sm.getCommand() == NOTE_ON) {
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    } else if (sm.getCommand() == NOTE_OFF) {
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    } else {
	                        System.out.println("Command:" + sm.getCommand());
	                    }
	                } else {
	                    System.out.println("Other message: " + message.getClass());
	                }
	            }

	            System.out.println();
	        }

	    }
}
