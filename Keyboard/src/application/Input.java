package application;

import javafx.scene.paint.Color;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class Input implements Receiver 
{
	private String name;
	private int key;
	public Input(String name)
	{
		this.name=name;
	}
	@Override
	public void send(MidiMessage message, long timeStamp) 
	{
		if(message instanceof ShortMessage)
		{
			key = (((ShortMessage) message).getData1()-21);
			ShortMessage m = (ShortMessage) message;
			if(
					//m.getCommand()==144 && 
					m.getData2()>0)
			{
				System.out.println("cool "+ m.getData2());
				System.out.println("not cool "+m.getData1());
				
				KeyBoard.keys[key].changeColor(Color.LIGHTGREY);
				KeyBoard.keys[key].setPlaying(true);	
			}
			else
			{
				this.close();
			}
		}
	}

	@Override
	public void close() 
	{
		KeyBoard.keys[key].removeColor(Color.LIGHTGREY);
		KeyBoard.keys[key].setPlaying(false);
		//System.out.println("FL:DJSLF:JSLD:FJK");
	}
}
