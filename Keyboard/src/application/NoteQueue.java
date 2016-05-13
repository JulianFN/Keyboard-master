package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class NoteQueue 
{
	private LongProperty time;
	LinkedList<Note> notes = new LinkedList<Note>();
	private float ticks;
	private long seconds;
	private Pane pane;
	AnimationTimer timer;
	private Timeline timeLine;
	Sequencer sequencer;
	private Note note;
	private Stage finished;
	private Pane paneM;
	private TextField points = new TextField("0");
	
	public NoteQueue(float tickPerMic, long s,Pane pan,Sequencer seq,Stage f,Pane menu)
	{
		sequencer=seq;
		pane=pan;
		ticks=tickPerMic;
		seconds=s;
		finished = f;
		paneM = menu;
		points.setMaxWidth(100);
		points.setEditable(false);
		points.setMouseTransparent(true);
		points.setFocusTraversable(false);
		points.autosize();
		points.setLayoutX(Note.SCREEN_BOUNDS.getWidth()-100);
		points.setLayoutY(0);
	}
	public void add(Note s)
	{
		notes.addLast(s);	
	}
	public void start()
	{
		pane.getChildren().add(points);
		time = new SimpleLongProperty();
		notes.sort(new NoteTimeComparator());
		note = notes.peekFirst();
		//System.out.println(notes.toString());
		//notes.pollFirst().play(pane);
		timeLine= new Timeline(
				new KeyFrame(Duration.millis(0)
						,new KeyValue(time,0)
				)
				,new KeyFrame(Duration.millis(seconds/1000)
						,new KeyValue(time,ticks)
						)
				);
		timeLine.setCycleCount(1);
		timer  = new AnimationTimer()
				{
					@Override
					public void handle(long time)
					{
						boolean on=false;
						play(time);
						if(!on)
							startS(on,time);
					}
				};
//		timeLine.setOnFinished((event) ->
//		{
//			try {
//				wait(300000000);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Group temp = new Group();
//			temp.getChildren().add(paneM);
//			finished.setScene(new Scene(temp));
//		});
		//if()
		timer.start();
		timeLine.play();
	}
	public boolean play(long t)
	{
		//System.out.println("time"+ (t-time));
		//System.out.println("note"+notes.peekFirst().getTime());
		
		if(notes.peekFirst()!=null)
		{
			if(time.longValue()>notes.peekFirst().getTime())
			{
				notes.pollFirst().play(pane,points);
				return true;
			}
		}
		return false;
	}
	public void startS(boolean j,long t)
	{
		if(timeLine.getCurrentTime().toSeconds()>= (note.getTime()/1000000)+2.7777777)
		{
			try 
			{
				sequencer.open();
			} 
			catch (MidiUnavailableException e) 
			{
				e.printStackTrace();
			}
			//sequencer.
			sequencer.start();
			j=true;
		}
	}
	public Note getFirst()
	{
		return notes.peekFirst();
	}
//	public Sequencer getSequencer()
//	{
//		return sequencer;
//	}
	class NoteTimeComparator implements Comparator<Note>
	{

		@Override
		public int compare(Note a, Note b) 
		{
			return a.compareTo(b);
		}
		
	}
}
