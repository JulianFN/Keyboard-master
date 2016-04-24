package application;

import java.time.Duration;
import java.util.Comparator;
import java.util.LinkedList;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;


public class NoteQueue 
{
	private long time;
	LinkedList<Note> notes = new LinkedList<Note>();
	private float ticks;
	private long seconds;
	private Pane pane;
	AnimationTimer timeline;
	Sequencer sequencer;
	public NoteQueue(float tickPerMic, long s,Pane pan,Sequencer seq)
	{
		sequencer=seq;
		pane=pan;
		ticks=tickPerMic;
		seconds=s;
	}
	public void add(Note s)
	{
		
		notes.addLast(s);
		
	}
	public void start()
	{
		notes.sort(new NoteTimeComparator());
		//notes.pollFirst().play(pane);
		//Timeline time = new Timeline(new KeyFrame(Duration.ofSeconds(1/ticks),new KeyValue(play(time.getCurrentTime().toSeconds()))));
		time  = System.nanoTime();
		timeline  = new AnimationTimer()
				{
					@Override
					public void handle(long time)
					{
						boolean on=false;
						play((time));
						if(!on)
							startS(on,time);
					}
				};
		timeline.start();
	}
	public boolean play(long t)
	{
		//System.out.println("time"+ (t-time));
		//System.out.println("note"+notes.peekFirst().getTime());
		if(Math.abs((t-time)-(notes.peekFirst().getTime()))<=250000000L)
		{
			
			notes.pollFirst().play(pane);
			//System.out.println("good");
			return true;
		}
		if(t-time >=seconds)
		{
			timeline.stop();
		}
		return false;
	}
	public void startS(boolean j,long t)
	{
		if(t-time>=2135000000L)
		{
			try 
			{
				sequencer.open();
			} 
			catch (MidiUnavailableException e) 
			{
				e.printStackTrace();
			}
			sequencer.start();
			j=true;
		}
	}
	public Note getFirst()
	{
		return notes.peekFirst();
	}
	class NoteTimeComparator implements Comparator<Note>
	{

		@Override
		public int compare(Note a, Note b) 
		{
			return a.compareTo(b);
		}
		
	}
}
