package application;
	

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import sun.audio.*;
import javafx.application.Application;
import javafx.beans.binding.ListExpression;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;


public class Main extends Application 
{
	
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
					System.out.println("healdj");
					pane.getChildren().add(keys[x].getWRect());
				}
			}
			//Note note =new Note(c.getGraphicsContext2D(),2,52,200);
//			note.draw(up.isblack(note.getKey()));
			Group root = new Group();
			root.getChildren().add(c);
			root.getChildren().add(pane);
			Scene scene = new Scene(root);
//			primaryStage.setX(SCREEN_BOUNDS.getMinX());
//			primaryStage.setY(SCREEN_BOUNDS.getMinY());
//			primaryStage.setWidth(SCREEN_BOUNDS.getWidth());
//			primaryStage.setHeight(SCREEN_BOUNDS.getHeight());
			
		System.out.println(keys[2].getRect().getOnKeyPressed());
		primaryStage.setScene(scene);
		for(int x =0;x<keys.length;x++)
		{
			primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED,keys[x].getRect().getOnKeyPressed());
			primaryStage.getScene().addEventHandler(KeyEvent.KEY_RELEASED,keys[x].getRect().getOnKeyReleased());
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
