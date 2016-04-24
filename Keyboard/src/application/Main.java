package application;
	

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application 
{
	private Rectangle Rect = new Rectangle(100, 40, 15, 150);
	public static final double D = 20;  // diameter.
	private Canvas c = new Canvas(800,600);
	private Key[] keys = new Key[88];
	
	public void start(Stage primaryStage) 
	{
		try 
		{
			Pane notes = new Pane();
			Pane pane = new Pane();
			SongReader s = new SongReader("Megolovania.mid",notes);
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
			
			//Note note =new Note(notes,2,13,200);
	        
			Group root = new Group();
			root.getChildren().add(c);
			root.getChildren().add(notes);
			root.getChildren().add(pane);
			Scene scene = new Scene(root);
			
//			primaryStage.setX(SCREEN_BOUNDS.getMinX());
//			primaryStage.setY(SCREEN_BOUNDS.getMinY());
//			primaryStage.setWidth(SCREEN_BOUNDS.getWidth());
//			primaryStage.setHeight(SCREEN_BOUNDS.getHeight());
			
		primaryStage.setScene(scene);
		//primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,);
		
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
