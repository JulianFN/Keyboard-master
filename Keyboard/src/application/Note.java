package application;


import javafx.util.Duration;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Note 
{
	public static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	private double time;
	private double length;
	private int    key;
	private GraphicsContext graphics;
	private DoubleProperty  x;
	private DoubleProperty  y;
	public Note(GraphicsContext gc,double t,int l,double e)
	{
		key=l;
		graphics=gc;
		time=t;
		length=e;
		this.draw(false);
		 Timeline timeline = new Timeline(
		            new KeyFrame(Duration.seconds(0),
		                    new KeyValue(x, 0),
		                    new KeyValue(y, 0)
		            ),
		            new KeyFrame(Duration.seconds(3),
		                    new KeyValue(x, 600),
		                    new KeyValue(y, 600)
		            )
		        );
		 AnimationTimer timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) 
	            {
	                gc.setFill(Color.CORNSILK);
	                gc.fillRect(x.doubleValue(), y.doubleValue(), 200, 200);	                
	            }
		 };
		 timer.start();
	       timeline.play();
	}
	public void draw(boolean black)
	{
		graphics.setFill(Color.RED);
		if(black)
		{
			graphics.fillRect(key*9+5,500,8,60);
		}
		else
		{
			graphics.fillRect(key*9,560,17.9,60);
		}
		System.out.println("x "+x+"y "+y);
	}
	public boolean isPlayed()
	{
		return false;
	}
	public void changeColor(boolean black)
	{
		graphics.setFill(Color.ORANGE);
		if(black)
		{
			graphics.fillRect(key*9+5,500,8,60);
		}
		else
		{
			graphics.fillRect((double) key*37,SCREEN_BOUNDS.getHeight()/1.072,SCREEN_BOUNDS.getWidth()/52,SCREEN_BOUNDS.getHeight()/10);
		}
	}
	public int getKey()
	{
		return key;
	}
	
}
