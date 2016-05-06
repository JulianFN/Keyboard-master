package application;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
		if(note<5)
			color = note%2==1;
		else
			color = note%2==0&&note!=5;
		this.draw();
		System.out.println(octave+" "+note+" "+key+" "+color);
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
		int x = calculateWhite();
		if(color)
		{
			keyRect = new Rectangle(15.38*x+13.13,500,4.5,60);
			
			keyRect.setFill(Color.BLACK);
			return true;
		}
		else
		{
			keyRect = new Rectangle(15.38*x,560,15.38,60);
			keyRect.setFill(Color.WHITE);
			if(key ==0||key==87)
			{
				whiteRect = new Rectangle(15.38*x,500,15.38,60);
				whiteRect.setFill(Color.WHITE);
			}
			else if( note ==4||note ==11)
			{
				whiteRect = new Rectangle(15.38*x+2.25, 500,13.13,60);
				whiteRect.setFill(Color.WHITE);
				return true;
			}
			else if(note ==2 || note ==7||note==9)
			{
				whiteRect = new Rectangle(15.38*x+2.5, 500,10,60);
				whiteRect.setFill(Color.WHITE);
				return true;
			}
			else
			{
				whiteRect = new Rectangle(15.38*x,500,13.13,60);
				whiteRect.setFill(Color.WHITE);
			}
			
		}
		return color;
	}
	
	public int getKey()
	{
		return key;
	}
	public void changeColor(Color c)
	{
		 if(color)
		 {
			 keyRect.setFill(c);
		 }
		 else
		 {
			keyRect.setFill(c);
			whiteRect.setFill(c);
		 } 
	}
	private int calculateWhite()
	{
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
	public void orginalColor()
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
