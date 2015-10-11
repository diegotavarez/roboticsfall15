package main;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;


public class Teste{

	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int YELLOW = 3;
	public static final int MAGENTA = 4;
	public static final int ORANGE = 5;
	public static final int WHITE = 6;
	public static final int BLACK = 7;
	public static final int PINK = 8;
	public static final int GRAY = 9;
	public static final int LIGHT_GRAY = 10;
	public static final int DARK_GRAY = 11;
	public static final int CYAN = 12;
	public static final int BROWN = 13;
	public static final int NONE = -1;
	private static final int COL_AMBIENT = 0;
	private static final int COL_COLOR = 0;
	private static final int COL_REFLECT = 0;
	private static final String SWITCH_DELAY = null;

	public static void main(String[] args) throws InterruptedException{

		final EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
		SensorMode mode = colorSensor.getColorIDMode();
		final ColorRecognizerThread colorRecognizerThread = new ColorRecognizerThread(colorSensor);

		colorRecognizerThread.start();

		Button.waitForAnyPress();
		
		if(Button.ESCAPE.isDown()){
			colorRecognizerThread.stop();			
		}

	}



	public boolean setFloodlight(int color)
	{
		int mode;
		switch (color)
		{
		case Color.BLUE:
			mode = COL_AMBIENT;
			break;
		case Color.WHITE:
			mode = COL_COLOR;
			break;
		case Color.RED:
			mode = COL_REFLECT;
			break;
		default:
			// TODO: Should we ignore a wrong color or throw an exception?
					throw new IllegalArgumentException("Invalid color specified");
		}
		switchMode(mode, SWITCH_DELAY);
		// TODO Auto-generated method stub
		return true;
	}



	private void switchMode(int mode, String switchDelay) {
		// TODO Auto-generated method stub
		System.out.println("teste: " + mode);

	}
}

class ColorRecognizerThread extends Thread {

	private EV3ColorSensor colorSensor;

	public ColorRecognizerThread(final EV3ColorSensor colorSensor) {
		this.colorSensor = colorSensor;
	}

	@Override
	public void run() {
		while(true){
			final int colorId = colorSensor.getColorID();
			switch (colorId){
			//RED
			case 0:
				Button.LEDPattern(2);
				break;
				//GREEN
			case 1:
				Button.LEDPattern(1);
				break;
				//BLUE
			case 2:
				Button.LEDPattern(4);
				threadSleep(2000);
				Button.LEDPattern(0);
				System.exit(0);
				//YELLOW
			case 3:
				Button.LEDPattern(3);
				break;
			default:
				Button.LEDPattern(0);
			}
		}
	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}