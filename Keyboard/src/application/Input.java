package application;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import javafx.scene.paint.Color;

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
			key = ((ShortMessage) message).getData2()-21;
			KeyBoard.keys[key].changeColor(Color.LIGHTGREY);
			KeyBoard.keys[key].setPlaying(true);
			
			
		}
	}

	@Override
	public void close() 
	{
		KeyBoard.keys[key].removeColor(Color.LIGHTGREY);
		KeyBoard.keys[key].setPlaying(false);
	}
}
