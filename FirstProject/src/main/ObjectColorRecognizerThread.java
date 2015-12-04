package main;

import java.io.File;

import javax.swing.text.DefaultEditorKit.BeepAction;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.Sound;

public class ObjectColorRecognizerThread extends Thread {
	
	public static int objectColor = -1;

	private EV3ColorSensor colorSensor;

	public ObjectColorRecognizerThread(final EV3ColorSensor colorSensor) {
		this.colorSensor = colorSensor;
	}

	@Override
	public void run() {
		/* Button.LEDPattern(0): turn off button lights
		 * Button.LEDPattern(1); static green light
		 * Button.LEDPattern(2); static red light
		 * Button.LEDPattern(3); static yellow light
		 * Button.LEDPattern(4); normal blinking green light
		 * Button.LEDPattern(5); normal blinking red light
		 * Button.LEDPattern(6); normal blinking yellow light
		 * Button.LEDPattern(7); fast blinking green light
		 * Button.LEDPattern(8); fast blinking red light
		 * Button.LEDPattern(9); fast blinking yellow light
		 * Button.LEDPattern(>9); same as 9 
		 */
		
		while(true){
			final int colorId = colorSensor.getColorID();
			switch (colorId){
	//		case -1:
				//break;
			//RED
			case 0:
				System.out.println("RED OBJECT");
				
				playSound(colorId);

				if(checkForSpace(0))
				{
				objectColor = 0;
				this.suspend();
				}
				break;
			//GREEN
			case 1:
				System.out.println("GREEN OBJECT");
				
				playSound(colorId);
				
				if(checkForSpace(1))
				{
				objectColor = 1;
				this.suspend();
				}
				
				break;
			//YELLOW
			case 3:
				System.out.println("YELLOW OBJECT");
				
				playSound(colorId);
				
				if(checkForSpace(3))
				{
				objectColor = 3;
				this.suspend();
				}
				break;
			//BLUE
			case 2:	
				//System.out.println("BLUE OBJECT");
				Button.LEDPattern(0);
				break;
			default:
				//Button.LEDPattern(0);
			}
		}
		
	}
	
	private int getPositionOfColor(int color){

			if(PathColorRecognizerThread.map[2][1].getColor() == color)
			{
				return 2;
			}
			else if (PathColorRecognizerThread.map[1][1].getColor() == color)
				return 1;
			else
				return 0;
	}
	
	private boolean checkForSpace(int color)
	{
		if(color == 3)
		{
			if(PathColorRecognizerThread.map[getPositionOfColor(color)][0].getOccupied() == 1 &&
					PathColorRecognizerThread.map[getPositionOfColor(color)][2].getOccupied() == 1)
				return false;
		}
		else if (color == 0)
		{
			if(PathColorRecognizerThread.map[getPositionOfColor(color)][0].getOccupied() == 1 &&
					PathColorRecognizerThread.map[getPositionOfColor(color)][2].getOccupied() == 1)
				return false;
		}
		else
			if(PathColorRecognizerThread.map[getPositionOfColor(color)][0].getOccupied() == 1 &&
			PathColorRecognizerThread.map[getPositionOfColor(color)][2].getOccupied() == 1)
				return false;
		return true;		
	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void playSound(int id){
		
		if(id==0){
			
			File myFile = new File("redobject.wav");
			Sound.playSample(myFile, 100);
			
		}else if(id == 1){
			
			File myFile = new File("greenobject.wav");
			Sound.playSample(myFile, 100);
			
		}else if(id == 3){
			
			File myFile = new File("yellowobject.wav");
			Sound.playSample(myFile, 100);
			
		}
		
	}

}
