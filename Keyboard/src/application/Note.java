package application;


import java.awt.Point;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Note 
{
	private long time;
	private long length;
	private int    key;
	private Rectangle note;
	private Timeline timeline;
	private  float ticks;
	private Color paint;
	private Key changeColor;
	private boolean in=true;
	//public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	public Note(float f,int l,long e,float tiks,Color color,Key Corrs)
	{
		paint = color;
		key=l-12;
		time=(long) f;
		length=e;
		ticks = tiks; 
		changeColor= Corrs;
		//System.out.println("Sup     "+length+ " " +ticks);
	}
	
	public void play(Pane x)
	{
		boolean color;
		int n=(key+9)%12;
		//System.out.println(ticks);
		if(n<5)
			color = n%2==1;
		else
			color = n%2==0&&n!=5;
		this.draw(color);
		//System.out.println("same"+(length/ticks));
        DoubleProperty xdouble  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();
        note.setFill(paint);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                    new KeyValue(xdouble, 0),
                    new KeyValue(y, 0)
            ),
            new KeyFrame(Duration.seconds(((note.getHeight())+500)/180),
                    new KeyValue(xdouble, 0),
                    new KeyValue(y, note.getHeight() +500)
            )
        );
        timeline.setCycleCount(1);
        timeline.setOnFinished(new EventHandler<ActionEvent>()
        		{
        			public void handle(ActionEvent event)
        			{
        				x.getChildren().remove(note);
        				//System.out.println("done");
        				changeColor.orginalColor();
        				//System.out.println(checker.size());
        				
        				
        			}
        		});
        //timeline.setAutoReverse(true);
        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
            	//System.out.println(y.doubleValue());
                note.setFill(paint);
                //note.setTranslateX(x.doubleValue());
                note.setTranslateY(y.doubleValue());
                //note.translateXProperty();
                note.translateYProperty();  
                if(in&&timeline.getCurrentTime().toSeconds()>((note.getHeight()/1000000)+500)/180)
                {
                	//System.out.println("out");
                	changeColor.changeColor(paint);
                	in = false;
                }
                //System.out.println(y.doubleValue());
           }
        };
        x.getChildren().add(note);
      note.setOnKeyPressed( new EventHandler<KeyEvent>()
        		{
        			public void handle(KeyEvent event)
        			{
//        				//System.out.println(timeline.getCurrentTime().toSeconds()+ " " +timeline.getCurrentRate());
//        				if(isPlayed())
//        				{
//        					System.out.println("Points");
//        				}
        			}
        		});
        timer.start();
        timeline.play();
	}
	public void draw(boolean black)
	{
		//System.out.println(length);
		//System.out.println(180*((le/ticks)/1000000));
		if(black)
		{
			
			note = new Rectangle(calculateWhite()*15.38+13,-(180*((length/ticks)/1000000)),4,180*((length/ticks)/1000000));
		}
		else
		{
			note = new Rectangle(calculateWhite()*15.38+3,-(180*((length/ticks)/1000000)),10,180*((length/ticks)/1000000));
		}
		//System.out.println(time +" j "+(note.getY()-(-note.getHeight())));
		//System.out.println(note.getY());
		//System.out.println(-180*((le/ticks)/1000000));
		note.setArcHeight(10);
		note.setArcWidth(10);
		note.setFill(paint);
	}
//	public boolean isPlayed()
//	{
//		return ((timeline.getCurrentTime().toSeconds()*(timeline.getCurrentRate()*200) + 60)>=450)&&(timeline.getCurrentTime().toSeconds()*(timeline.getCurrentRate()*200))<=510;
//	}
	public int getKey()
	{
		return key;
	}
	public void setIn(boolean f)
	{
		in=f;
	}
	public boolean getIn()
	{
		return in;
	}
	public Rectangle getNote()
	{
		return note;
	}
	public long getTime()
	{
		return time;
	}
	private Note search(ArrayList<Note> s)
	{
		for(Note n:s)
		{
			if((n.getNote().contains(new Point2D(this.getNote().getX(),this.getNote().getY()))))
				return n;
		}
		return null;
	}
	private int calculateWhite()
	{
		int note;
		boolean c;
		int x =0;
		for(int k =1;k<=key;k++)
		{
			note=(k+9)%12;
			if(note<5)
				c = note%2==1;
			else
				c = note%2==0&&note!=5;
			if(!c)
				x++;	
		}
		return x;
	}
//	public double getDuration()
//	{
//		return (note.getHeight()+550)/180;
//	}
	public int compareTo(Note s)
	{
		if(this.time>s.getTime())
			return 1;
		else if (this.time<s.getTime())
			return -1;
		else
			return 0;
	}
}
