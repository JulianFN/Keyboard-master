package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.sound.midi.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;

import static application.MIDIConstants.*;
import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;

public class SongReader {

    private final long microSeconds;
    private float tickPerMic;
    private double tickToMic;
    private final Sequencer sequencer;
    private final NoteQueue notes;
    private int temp_mirco_per_beat = 500000; // https://www.recordingblogs.com/wiki/header-chunk-of-a-midi-file default is 500,000 micro per beat


    public SongReader(String file, Pane pan, Key[] keys, Stage f, Pane menu) throws Exception {
        File path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "/music/" + file);
        String s = path.getParentFile().getAbsolutePath();
        System.out.println(path.getAbsolutePath());
        Sequence sequence = MidiSystem.getSequence(path);
        microSeconds = sequence.getMicrosecondLength();
        sequencer = MidiSystem.getSequencer();
        InputStream fi = new BufferedInputStream(new FileInputStream(path));
        sequencer.open();
        sequencer.setSequence(fi);
        int trackNumber = -1;
        System.out.println("MIDI resolution: " + sequence.getResolution() + " MIDI Division type: " + sequence.getDivisionType());
        if (sequence.getDivisionType() == Sequence.PPQ) {
            tickToMic = ((double) temp_mirco_per_beat / (double) sequence.getResolution());
        } else {
            tickToMic = MICROSECONDS_PER_SECOND / (sequence.getResolution() * sequence.getDivisionType());
        }
        System.out.println(tickToMic);

        Color[] color = {Color.BLUE, Color.CORAL, Color.CYAN, Color.VIOLET, Color.FUCHSIA, Color.GREEN, Color.RED,
                Color.ORCHID, Color.TURQUOISE, Color.ORANGERED, Color.DARKORCHID, Color.DEEPPINK, Color.DARKGOLDENROD,
                Color.LIGHTSLATEGREY, Color.CADETBLUE, Color.FIREBRICK, Color.MIDNIGHTBLUE, Color.LIGHTSTEELBLUE};
        notes = new NoteQueue(sequence.getTickLength(), microSeconds, pan, sequencer, f, menu);

        ArrayList<long[]> temp = new ArrayList<long[]>();

        System.out.println("Num Tracks: " + sequence.getTracks().length);
        for (Track track : sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage sm) {
                    if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {

                        int key = sm.getData1();
                        int firstKeyToMIDI = MIDDLE_C - 39; // 39 is middle C on the keys array
                        int velocity = sm.getData2();
                        boolean found = false;
                        int j = 0;
                        int keyIndex = Math.min(Math.max(key - firstKeyToMIDI, 0), keys.length - 1);

                        if (velocity == 0 || sm.getCommand() == NOTE_OFF) {
//                            System.out.println("Note done @" + event.getTick() + " key: " + keyIndex + " " + keys[keyIndex].getNoteName());
                            while (j < temp.size() && !found) {
                                if (temp.get(j)[0] == key) {
                                    notes.add(new Note(temp.get(j)[1], event.getTick() - temp.get(j)[1], tickToMic, color[trackNumber], keys[keyIndex]));
                                    temp.remove(j);
                                    found = true;
                                }
                                j++;
                            }
                        } else {
//                            System.out.println("Note start @" + event.getTick() + " key: " + keyIndex + " " + keys[keyIndex].getNoteName());
                            temp.add(new long[]{key, event.getTick()});
                        }
                    }
                } else if (message instanceof MetaMessage mm) {
                    int metaMessageType = mm.getType();
                    if (metaMessageType != END_OF_TRACK) {

                    }
                    if (metaMessageType == SET_TEMPO) {
                        byte[] data = mm.getData();
                        temp_mirco_per_beat = new BigInteger(data).intValue();

                        System.out.println("Message Type: " + Integer.toHexString(metaMessageType) + " Data: " + this.toHex(data) + " Int: " + temp_mirco_per_beat);
                        if (sequence.getDivisionType() == Sequence.PPQ) {
                            tickToMic = (float) temp_mirco_per_beat / sequence.getResolution();
                        } else {
                            tickToMic = MICROSECONDS_PER_SECOND / (sequence.getResolution() * sequence.getDivisionType());
                        }
                    }
                }

            }
        }
        notes.start();
    }

    public String toHex(byte[] bytes) {
        System.out.println(bytes);
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    /**
     * gets the sequencer
     */
    public Sequencer getSequencer() {
        return sequencer;
    }
}
