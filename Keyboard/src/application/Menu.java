package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Menu extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		File path = new File("../../music"); //(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
    	//path = new File(path.getParentFile().getAbsolutePath()+"/music");
		System.out.println(path.listFiles().toString());
		String[] files  = path.list();
		TextField[] s = new TextField[files.length];
		for(int i = 0;i<files.length;i++)
		{
			s[i].setText(files[i].toString());
		}
		Group ro = new Group();
		Pane p = new Pane();
		Button x = new Button("Change");
		x.setOnAction((event)->
		{
			Keyboard k = new Keyboard(primaryStage,"Super Mario Bros");
		});
		for(TextField we:s)
		{
			p.getChildren().add(we);
		}
		p.getChildren().add(x);
		ro.getChildren().add(p);
		Scene t = new Scene(ro);
		primaryStage.setScene(t);
		primaryStage.show();
	}
	public static void main(String[] args)
	{
		launch(args);
	}
}
