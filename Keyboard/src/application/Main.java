package application;
	

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import sun.audio.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.ListExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application 
{
	  public static final double W = 800; // canvas dimensions.
	    public static final double H = 1000;
	    private Rectangle Rect = new Rectangle(100, 40, 15, 150);
	    public static final double D = 20;  // diameter.
	private Canvas c = new Canvas(800,600);
//	Updater up = new Updater(c.getGraphicsContext2D());
	private Key[] keys = new Key[88];
	private ArrayList<AudioClip> noise = new ArrayList<AudioClip>();
	private boolean[] playing = new boolean[89];
	
	public void start(Stage primaryStage) 
	{
		try 
		{
			Pane pane = new Pane();
			c.getGraphicsContext2D().drawImage(new Image("/application/BackOfGround.jpg",800, 600,true,false),0,0);
			for(int x =0;x<keys.length;x++)
			{
				keys[x] = new Key(x);
				pane.getChildren().add(keys[x].getRect());
				if(!keys[x].getColor())
				{
					pane.getChildren().add(keys[x].getWRect());
				}
			}
			
			//Note note =new Note(c.getGraphicsContext2D(),2,52,200);
//			note.draw(up.isblack(note.getKey()));
	        
			Group root = new Group();
			root.getChildren().add(c);
			//root.getChildren().add(panel);
			root.getChildren().add(pane);
			//panel.toBack();
	        pane.toFront();
			Scene scene = new Scene(root);
			
//			primaryStage.setX(SCREEN_BOUNDS.getMinX());
//			primaryStage.setY(SCREEN_BOUNDS.getMinY());
//			primaryStage.setWidth(SCREEN_BOUNDS.getWidth());
//			primaryStage.setHeight(SCREEN_BOUNDS.getHeight());
			
		primaryStage.setScene(scene);
		for(int z =0;z<keys.length;z++)
		{
			primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED,keys[z].getRect().getOnKeyPressed());
			primaryStage.getScene().addEventHandler(KeyEvent.KEY_RELEASED,keys[z].getRect().getOnKeyReleased());
		}
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
