package application;

public class MIDIConstants {
    public static final int TARGET_FRAMERATE = 144;


    public static final int MICROSECONDS_PER_SECOND = 1000000;
    // ShortMessage Midi Note On Message https://www.recordingblogs.com/wiki/midi-note-on-message

    public static final int MIDDLE_C = 60;

    // MetaMessages https://www.recordingblogs.com/wiki/midi-meta-messages
    public static final int SET_TEMPO = 0x51;
    public static final int END_OF_TRACK = 0x2f;
}
