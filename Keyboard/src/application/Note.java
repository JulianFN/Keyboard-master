package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 * 
 * @author Julian Nieto
 * Description: Note class draws and animates the Notes
 */
public class Note 
{
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	private long time;
	private long length;
	private int key;
	private Rectangle note;
	private Timeline timeline;
	private float ticks;
	private Color paint;
	private Key changeColor;
	private boolean in = true;
	private DoubleProperty y;
	// public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E",
	// "F", "F#", "G", "G#", "A", "A#", "B"};

	public Note(float f, int l, long e, float tiks, Color color, Key Corrs) 
	{
		paint = color;
		key = l - 12;
		time = (long) f;
		length = e;
		ticks = tiks;
		changeColor = Corrs;
		// System.out.println("Sup "+length+ " " +ticks);
	}

	public void play(Pane x,TextField tex) {
		boolean color;
		int n = (key + 9) % 12;
		// System.out.println(ticks);
		if (n < 5)
			color = n % 2 == 1;
		else
			color = n % 2 == 0 && n != 5;
		this.draw(color);
		DoubleProperty xdouble = new SimpleDoubleProperty();
		y = new SimpleDoubleProperty();
		note.setFill(paint);
		double time =(SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4))/2.777777777;
		timeline = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(xdouble, 0), new KeyValue(y, 0)),
				new KeyFrame(Duration.seconds(((note.getHeight()) + SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4)) / time), new KeyValue(xdouble, 0),
						new KeyValue(y, note.getHeight() + SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4))));
		timeline.setCycleCount(1);
		timeline.setOnFinished(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				x.getChildren().remove(note);
				changeColor.removeColor(paint);
			}
		});
		AnimationTimer timer = new AnimationTimer() 
		{
			@Override
			public void handle(long now) 
			{
				note.setFill(paint);
				note.setTranslateY(y.doubleValue());

				note.translateYProperty();
				if(in && timeline.getCurrentTime().toSeconds()>(SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4))/time)
				{
					in=false;
					if(!changeColor.isPlaying())
						changeColor.changeColor(paint);
				}
				if(timeline.getCurrentTime().toSeconds()>(SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4))/time &&
						timeline.getCurrentTime().toSeconds()<timeline.getTotalDuration().toSeconds() && changeColor.isPlaying())
				{
					
					tex.setText(""+(Integer.parseInt(tex.getText())+1));
				}
					
			}
		};
		x.getChildren().add(note);
//		note.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			public void handle(KeyEvent event) {
//				// //System.out.println(timeline.getCurrentTime().toSeconds()+ "
//				// " +timeline.getCurrentRate());
//				// if(isPlayed())
//				// {
//				// System.out.println("Points");
//				// }
//			}
//		});
		timer.start();
		timeline.play();
	}

	public void draw(boolean black) 
	{
		double height =(SCREEN_BOUNDS.getHeight()/8);
		double offset = SCREEN_BOUNDS.getWidth()/52-SCREEN_BOUNDS.getWidth()/208;
		if (black) 
		{

			note = new Rectangle(calculateWhite() * (SCREEN_BOUNDS.getWidth()/52)+ 1.13*offset, -(180 * ((length / ticks) / 1000000)),SCREEN_BOUNDS.getWidth()/208,
					180 * ((length / ticks) / 1000000));
		}
		else 
		{
			note = new Rectangle(calculateWhite() * (SCREEN_BOUNDS.getWidth()/52) +SCREEN_BOUNDS.getWidth()/208, -(180 * ((length / ticks) / 1000000)), SCREEN_BOUNDS.getWidth()/104,
					180 * ((length / ticks) / 1000000));
		}
		note.setArcHeight(10);
		note.setArcWidth(10);
		note.setFill(paint);
	}

	public int getKey() 
	{
		return key;
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
	public DoubleProperty getY()
	{
		return y;
	}
	public void setIn(boolean f)
	{
		in =f;
	}
	private int calculateWhite() 
	{
		int note;
		boolean c;
		int x = 0;
		for (int k = 1; k <= key; k++) {
			note = (k + 9) % 12;
			if (note < 5)
				c = note % 2 == 1;
			else
				c = note % 2 == 0 && note != 5;
			if (!c)
				x++;
		}
		return x;
	}
	public Color getPaint()
	{
		return paint;
	}
	
	// public double getDuration()
	// {
	// return (note.getHeight()+550)/180;
	// }
	public int compareTo(Note s) {
		if (this.time > s.getTime())
			return 1;
		else if (this.time < s.getTime())
			return -1;
		else
			return 0;
	}
}
