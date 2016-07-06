package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.scene.image.Image;

public class LittleScene 
{
	private Categorey[] categoreys;
	private String 		name;
	private Image		photo;
	private int			amount;
	public LittleScene(Categorey[] cat, String name,double price,Image pic,int amount)
	{
		categoreys=cat;
		this.name=name;
		photo=pic;
		this.amount=amount;
	}
	private boolean addOne()
	{
		File v = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+" ../music");
		String[] files = v.list();
		for(String x:files)
		{
			if(x.toLowerCase().equals(name.toLowerCase()))
			{
				return false;
			}
		}
		PrintWriter file;
		 try 
		 {
			file = new PrintWriter("name.txt");
		 } 
		 catch (FileNotFoundException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return true;
	}
	
	
}
