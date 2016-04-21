package application;

import java.net.URL;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class Key 
{
	private boolean color;
	private int key;
	private int note;
	private AudioClip audio;
	private Rectangle keyRect;
	private Rectangle whiteRect;
	private boolean triggered=false;
	public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	public Key(int i)
	{
		
		URL resource = getClass().getResource("Sound"+1+".wav");
		//audio = new AudioClip(resource.toString());
		key=i;
		note=(i+9)%12;
		int octave = ((i+9) / 12)-1;
		if(note>5)
			color = note%2==0;
		else
			color = note%2==1&&note!=5;
		this.draw();
		System.out.println(octave+" "+NOTE_NAMES[note]);
		keyRect.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			 public void handle(KeyEvent event)
			 {
				 char key = event.getText().charAt(0);
				 if(key==(char)(i+38))
				 {
					 if(!triggered)
					 {
						 audio.play(.2);
						 triggered=true;
						 System.out.println("good");
					 }
					 if(color)
					 {
						 keyRect.setFill(Color.DARKGRAY);
					 }
					 else
					 {
						keyRect.setFill(Color.LIGHTGREY);
						whiteRect.setFill(Color.LIGHTGREY);
					 } 
				 }
			 }
		});
		keyRect.setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			 public void handle(KeyEvent event)
			 {
				 char key = event.getText().charAt(0);
				 if(key==(char)(i+38))
				 {
					 if(color)
					 {
						  keyRect.setFill(Color.BLACK);
					 }
					 else
					 {
					 	keyRect.setFill(Color.WHITE);
						whiteRect.setFill(Color.WHITE);
				   	 }
					 triggered=false;
				}
			 }
		});
	}
	
	public boolean draw()
	{
		if(color)
		{
			keyRect = new Rectangle(key*9+5,500,8,60);
			keyRect.setFill(Color.BLACK);
			return true;
		}
		else
		{
			keyRect = new Rectangle(key*9,560,17.9,60);
			keyRect.setFill(Color.WHITE);
			if( note ==4||note==11)
			{
				whiteRect = new Rectangle(key*9+4, 500,10,60);
				whiteRect.setFill(Color.WHITE);
				return true;
			}
			else
			if(note ==2 || note ==7||note==9)
			{
				whiteRect = new Rectangle(key*9+4, 500,10,60);
				whiteRect.setFill(Color.WHITE);
				return true;
			}
			else
			{
				whiteRect = new Rectangle(key*9, 500,14,60);
				whiteRect.setFill(Color.WHITE);
				return true;
			}
		}
	}
	
	public int getKey()
	{
		return key;
	}
	
	public Rectangle getRect()
	{
		return keyRect;
	}
	public Rectangle getWRect()
	{
		return whiteRect;
	}
	public boolean getColor()
	{
		return color;
	}
	public AudioClip getAudio()
	{
		return audio;
	}
	public String getNoteName()
	{
		return NOTE_NAMES[note];
	}
}
