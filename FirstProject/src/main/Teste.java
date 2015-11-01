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
	
	public static ObjectColorRecognizerThread objectRecognizerThread;
	public static PathColorRecognizerThread colorRecognizerThread;
	public static PilotThread pilotThread;

	public static void main(String[] args) throws InterruptedException{
		final EV3ColorSensor pathColorSensor = new EV3ColorSensor(SensorPort.S3);
		final EV3ColorSensor objectColorSensor = new EV3ColorSensor(SensorPort.S4);

		//Path Color recognition thread
		SensorMode pathColorMode = pathColorSensor.getColorIDMode();
		colorRecognizerThread = new PathColorRecognizerThread(pathColorSensor);
		colorRecognizerThread.start();
		
		//Object Color recognition thread
		SensorMode objectColorMode = objectColorSensor.getColorIDMode();
		objectRecognizerThread = new ObjectColorRecognizerThread(objectColorSensor);
		objectRecognizerThread.start();
		
		//Pilot thread
		DifferentialPilot robot = initializePilot();
		pilotThread = new PilotThread(robot);
		pilotThread.start();
		
		while(!Button.ESCAPE.isDown())
		{ 
			//Empty	
		}
		LCD.drawString("Finished",0, 7); 
		LCD.refresh();
		System.exit(0);
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