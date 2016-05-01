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

public class SongReader 
{
		public static final int NOTE_ON = 0x90;
	    public static final int NOTE_OFF = 0x80;
	    private long microSeconds;
	    private float tickPerMic;
	    Sequencer sequencer;
	    ArrayList<long[]> temp = new ArrayList<long[]>();
	    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	    
	    public SongReader(String File,Pane pan) throws Exception 
	    {
	    	File path = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	    	String s = path.getParentFile().getAbsolutePath();
	    	Sequence  sequence = MidiSystem.getSequence(new File(s+"/music/"+File));
	    	microSeconds =  sequence.getMicrosecondLength();
	    	tickPerMic =  (sequence.getTickLength()/(float) (microSeconds));
	        sequencer = MidiSystem.getSequencer();
	        InputStream fi = new BufferedInputStream(new FileInputStream(new File(s+"/music/"+File)));
	       // System.out.println(sequencer.getTempoInBPM());
	        sequencer.open();
	        sequencer.setSequence(fi);
	        int trackNumber = -1;
	        Color[] color = {Color.BLUE,Color.CORAL,Color.CYAN,Color.VIOLET,Color.FUCHSIA,Color.GREEN,Color.RED,Color.ORCHID,Color.TURQUOISE,Color.ORANGERED,Color.DARKORCHID
	        		,Color.DEEPPINK,Color.DARKGOLDENROD,Color.LIGHTSLATEGREY,Color.CADETBLUE,Color.FIREBRICK,Color.MIDNIGHTBLUE,Color.LIGHTSTEELBLUE};
	        NoteQueue notes = new NoteQueue(sequence.getTickLength(), microSeconds, pan,sequencer);
	        for (Track track :  sequence.getTracks()) 
	        {
	            trackNumber++;
	            System.out.println("Track " + trackNumber + ": size = " + track.size());
	            //System.out.println();
	            for (int i=0; i < track.size(); i++) 
	            { 
	                MidiEvent event = track.get(i);
	                System.out.print("@" + event.getTick() + " ");
	                MidiMessage message = event.getMessage();
	                if (message instanceof ShortMessage) 
	                {
	                    ShortMessage sm = (ShortMessage) message;
	                   // System.out.print("Channel: " + sm.getChannel() + " ");
	                    if (sm.getCommand() == NOTE_ON)  
	                    {
	                      int key = sm.getData1();
	                      int octave = (key / 12)-1;
	                      int note = key % 12;
	                      String noteName = NOTE_NAMES[note];
	                      int velocity = sm.getData2();
	                      System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                      boolean found=false;
	                      int j =0;
	                      if(velocity==0)
	                    	  while(j<temp.size()&&!found)
	                    	  {
	                    		  if(temp.get(j)[0]==key)
	                    		  {
	                    			  //System.out.println(temp.get(j)[1]);
	                    			  //System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    			  System.out.println(event.getTick()-temp.get(j)[1]);
	                    			  notes.add(new Note(pan,event.getTick(),key-9,event.getTick()-temp.get(j)[1],tickPerMic,color[trackNumber]));
	                    			  temp.remove(j);
	                    			  found=true;
	                    		  }
	                    		  j++;
	                    	  }
	                      else
	                    	  temp.add(new long[]{key, event.getTick()});
	                       
	                      
	                    } 
	                    else if (sm.getCommand() == NOTE_OFF) 
	                    {
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        boolean found=false;
	                        int j =0;
	                       // System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                        while(j<temp.size()&&!found)
	                        {
	                        	if(temp.get(j)[0]==key)
	                        	{
//	                        		System.out.println(sequence.getMicrosecondLength());
//	                        		System.out.println(sequence.getTickLength()/sequence.getMicrosecondLength());
	                        		//System.out.println(tickPerMic);
	                        		notes.add(new Note(pan,event.getTick(),key-9,event.getTick()-temp.get(j)[1],tickPerMic,color[trackNumber]));
	                        		temp.remove(j);
	                        		found=true;
	                        	}
	                        	j++;
	                        }
	                        
	                    } 
	                    else 
	                    {
	                       // System.out.println("Command:" + sm.getCommand());
	                    }
	                } 
	                else 
	                {
	                    //System.out.println("Other message: " + message.getClass());
	                }
	            }
	            
	        }
	        notes.start();
	       
	    }
//	    public NoteQueue getQueue()
//		{
//	    	return notes;
//		}
//	    public static void main(String[] args)
//	    {
//	    	
//	    	try 
//	    	{
//				SongReader fak = new SongReader("Raiders of the Lost Ark.mid");
//			}
//	    	catch (Exception e) 
//	    	{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }


}
