package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

/**
 * 
 * @author Julian Nieto
 * Description: Actually keys for {@link KeyBoard}
 */
public class Key 
{
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	private boolean black;
	private int key;
	private int note;
	private AudioClip audio;
	private Rectangle keyRect;
	private Rectangle whiteRect;
	private LinkedList<Color> color;
	private boolean triggered=false;
	public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	/**
	 * Constructs all of the keys
	 * @param i
	 */
	public Key(int i)
	{
		color = new LinkedList<Color>();
		key=i;
		note=(i+9)%12;
		if(note<5)
			black = note%2==1;
		else
			black = note%2==0&&note!=5;
		this.draw();
		
		//KeyEvents
		keyRect.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			 public void handle(KeyEvent event)
			 {
				 char key = event.getText().charAt(0);
				 if(event.getCode()!= KeyCode.ESCAPE&&key==(char)(i+38))
				 {
					 if(!triggered)
					 {
						 //audio.play(.2)
						 triggered=true;
						 if(black)
							 changeColor(Color.DARKGRAY);
						 else
							 changeColor(Color.LIGHTGRAY);
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
					 if(black)
					 {
						 color.remove(Color.DARKGRAY);
						  keyRect.setFill(color.peek());
					 }
					 else
					 {
						color.remove(Color.LIGHTGRAY);
					    keyRect.setFill(color.peek());
						whiteRect.setFill(color.peek());
				   	 }
					 triggered=false;
				}
			 }
		});
	}
	
	/**
	 * Draws All of the keys
	 * @return
	 */
	public void draw()
	{
		int x = calculateWhite(); // Calculates amount of whites determine spot
		double height =(SCREEN_BOUNDS.getHeight()/8);
		double offset = SCREEN_BOUNDS.getWidth()/52-SCREEN_BOUNDS.getWidth()/208;
		//if Black
		if(black)
		{
			keyRect = new Rectangle((SCREEN_BOUNDS.getWidth()/52)*x+offset,SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4),(SCREEN_BOUNDS.getWidth()/104),height);
			color.push(Color.BLACK);
			keyRect.setFill(Color.BLACK);
		}
		
		//White
		else
		{
			color.push(Color.WHITE);
			keyRect = new Rectangle((SCREEN_BOUNDS.getWidth()/52)*x,SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/8),SCREEN_BOUNDS.getWidth()/52,height);
			keyRect.setFill(Color.WHITE);
			if(key ==0||key==87)
			{
				whiteRect = new Rectangle((SCREEN_BOUNDS.getWidth()/52)*x,SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4),SCREEN_BOUNDS.getWidth()/52,height);
				whiteRect.setFill(Color.WHITE);
			}
			else if( note ==4||note ==11)
			{
				whiteRect = new Rectangle((SCREEN_BOUNDS.getWidth()/52)*x+SCREEN_BOUNDS.getWidth()/208, SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4),offset,height);
				whiteRect.setFill(Color.WHITE);
			}
			else if(note ==2 || note ==7||note==9)
			{
				whiteRect = new Rectangle((SCREEN_BOUNDS.getWidth()/52)*x+SCREEN_BOUNDS.getWidth()/208, SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4),SCREEN_BOUNDS.getWidth()/104,height);
				whiteRect.setFill(Color.WHITE);
			}
			else
			{
				whiteRect = new Rectangle((SCREEN_BOUNDS.getWidth()/52)*x,SCREEN_BOUNDS.getHeight()-(SCREEN_BOUNDS.getHeight()/4),offset,height);
				whiteRect.setFill(Color.WHITE);
			}
//			whiteRect = new Rectangle(0.0,10.10);
		}
	}
	
	/**
	 * gets the Key
	 * @return
	 */
	public int getKey()
	{
		return key;
	}
	/**
	 * Changes Color to c
	 * @param c Color
	 */
	public void changeColor(Color c)
	{
		 color.addFirst(c);
		 if(black)
		 {
			 keyRect.setFill(color.peek());
		 }
		 else
		 {
			keyRect.setFill(color.peek());
			whiteRect.setFill(color.peek());
		 } 
	}
	
	/**
	 * Calculates amount of Whites before it
	 * @return number of whites
	 */
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
	public void removeColor(Color c)
	{
		color.remove(c);
		if(black)
		 {
			  keyRect.setFill(color.peek());
		 }
		 else
		 {
		 	keyRect.setFill(color.peek());
			whiteRect.setFill(color.peek());
	   	 }
	}
	
	/**
	 * gets the Rectangle
	 * @return
	 */
	public Rectangle getRect()
	{
		return keyRect;
	}
	
	public boolean isPlaying()
	{
		return triggered;
	}
	/**
	 * Returns extra Rectangles for Whites
	 * @return
	 */
	public Rectangle getWRect()
	{
		return whiteRect;
	}
	
	public LinkedList<Color> getColor()
	{
		return color;
	}
	public boolean getBlack()
	{
		return black;
	}
	public String getNoteName()
	{
		return NOTE_NAMES[note];
	}
}
