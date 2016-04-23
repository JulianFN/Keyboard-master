package application;


import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Note 
{
	private double time;
	private double length;
	private int    key;
	private Rectangle note;
	private Timeline timeline;
	public Note(Pane s,double t,int l,double e)
	{
		key=l;
		time=t;
		length=e;
		this.draw(((l+9)%12>5&&(((l+9)%12)%2==0)||((l+9)%12)%2==0 && ((l+9)%12)!=5));
        note.setFill(Color.VIOLET);
        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();
        
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(0),
                    new KeyValue(x, 0),
                    new KeyValue(y, 0)
            ),
            new KeyFrame(Duration.seconds(3),
                    new KeyValue(x, 780),
                    new KeyValue(y, note.getHeight() +600)
            )
        );
        timeline.setCycleCount(1);
        timeline.setOnFinished(new EventHandler<ActionEvent>()
        		{
        			public void handle(ActionEvent event)
        			{
        				s.getChildren().remove(note);
        			}
        		});
        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                note.setFill(Color.CORAL);
                //note.setTranslateX(x.doubleValue());
                note.setTranslateY(y.doubleValue());
                //note.translateXProperty();
                note.translateYProperty();     
           }
        };
        s.getChildren().add(note);
      note.setOnKeyPressed( new EventHandler<KeyEvent>()
        		{
        			public void handle(KeyEvent event)
        			{
        				System.out.println(timeline.getCurrentTime().toSeconds()+ " " +timeline.getCurrentRate());
        				if(isPlayed())
        				{
        					System.out.println("Points");
        				}
        			}
        		});
        timer.start();
        timeline.play();
	}
	public void draw(boolean black)
	{
		if(black)
		{
			note = new Rectangle(key*9+5,0,8,60);
		}
		else
		{
			note = new Rectangle(key*9,0,17.9,60);
		}
		note.setFill(Color.RED);
	}
	public boolean isPlayed()
	{
		return ((timeline.getCurrentTime().toSeconds()*(timeline.getCurrentRate()*200) + 60)>=450)&&(timeline.getCurrentTime().toSeconds()*(timeline.getCurrentRate()*200))<=510;
	}
	public int getKey()
	{
		return key;
	}
	public Rectangle getNote()
	{
		return note;
	}
	
}
