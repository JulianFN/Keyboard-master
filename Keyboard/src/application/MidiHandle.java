package application;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class MidiHandle
{
	private MidiDevice keyboard;
	private List<Transmitter> transmitters;
	private Receiver r;
	
	public MidiHandle()
	{
		MidiDevice.Info[] info = MidiSystem.getMidiDeviceInfo();
		for(int i =0;i<info.length;i++)
		{
			try
			{
			keyboard = MidiSystem.getMidiDevice(info[i]);
			System.out.println(info[i]);

            //get all transmitters
			transmitters = keyboard.getTransmitters();
            //and for each transmitter
            for(int j = 0; j<transmitters.size();j++) 
            {
            	//create a new receiver
            	//transmitters.get(j).setReceiver(
                    //using my own MidiInputReceiver
                  //   new Input(keyboard.getDeviceInfo().toString()));
            }
            
            //trans.setReceiver(new Input(keyboard.getDeviceInfo().toString()));

            //open each device
            keyboard.open();
            Transmitter trans = keyboard.getTransmitter();
            trans.setReceiver(new Input(keyboard.getDeviceInfo().toString()));

            //if code gets this far without throwing an exception
            //print a success message
            System.out.println(keyboard.getDeviceInfo()+" Was Opened");
			}
			catch (MidiUnavailableException e)
			{
				System.out.println("not Found");
				keyboard=null;
			}
		}
		
	}
//	public List<Transmitter> getTransmitters()
//	{
//		return transmitters;
//	}
	public MidiDevice getKeyboard()
	{
		return keyboard;
	}
	
}
