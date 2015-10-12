package main;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import lejos.utility.PilotProps;


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

		//Button.waitForAnyPress();
		
		if(Button.ESCAPE.isDown()){
			colorRecognizerThread.stop();
		}
		
		DifferentialPilot robot = initializePilot();
		while(!Button.DOWN.isDown())
		{
			robot.travel(30);
			Delay.msDelay(2000);	
		}
	}

	private boolean setFloodlight(int color)
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
	
	private static DifferentialPilot initializePilot() {
		/* Steps to initialize the pilot*/
		PilotProps pp = new PilotProps();
		//pp.loadPersistentValues();
		float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "4.0"));
		float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "18.0"));
		RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
		RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));
		boolean reverse = Boolean.parseBoolean(pp.getProperty(PilotProps.KEY_REVERSE,"true"));
		DifferentialPilot robot = new DifferentialPilot(wheelDiameter,trackWidth,leftMotor,rightMotor,reverse);
		robot.setAcceleration(4000);
		robot.setTravelSpeed(40); // cm/sec
		robot.setRotateSpeed(90); // deg/sec
		return robot;
	}
}

class ColorRecognizerThread extends Thread {

	private EV3ColorSensor colorSensor;

	public ColorRecognizerThread(final EV3ColorSensor colorSensor) {
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
				break;
			//YELLOW
			case 3:
				Button.LEDPattern(3);
				break;
			//WHITE
			case 6:
				Button.LEDPattern(7);
				threadSleep(2000);
				Button.LEDPattern(8);
				threadSleep(2000);
				Button.LEDPattern(9);
				threadSleep(2000);	
				Button.LEDPattern(0);
				System.exit(0);
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