package main;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import lejos.utility.PilotProps;

public class Robot {
	static RegulatedMotor leftMotor;
	static RegulatedMotor rightMotor;
	int errorRate = 0;
	
	int sampleSize;
	float test = -1;
	float[] sample;
	float[]sides = new float[8];
	int maxIndex = 0;
	EV3ColorSensor sensor;
	SampleProvider ambientMode;
	SampleProvider colorIdMode;
	SampleProvider reflectedLight;
	
	public static void main(String[] args) {
		Robot foo = new Robot();
	}

	public Robot() {
		Button.waitForAnyPress();
		DifferentialPilot robot = initializePilot();
		Brick brick = BrickFinder.getDefault();
		Port s4 = brick.getPort("S4");
		sensor = new EV3ColorSensor(s4);
		
		for (int i = 0; i < sides.length ; i++)
		{
			sides[i] = 0.0f;
		}
		
		while(!Button.ESCAPE.isDown() && test < (sides[maxIndex] - errorRate)){	
			
			//procura melhor direcao e vai
			
			test = 0.0f;
			initializeColorSensor();
			calibrateBrightSensor(reflectedLight, sample);
			calculateBrightBySide(sensor, robot, ambientMode, sampleSize, sides);
			findBrightestDirection(robot, maxIndex);
			goStraight(robot);
			robot.reset();
			//sensor.close();
			
			for(int j = 0; j < 50; j ++){
				reflectedLight.fetchSample(sample, 0);
				test += sample[0];
			}
			
			errorRate = (int) ((sides[maxIndex]/100) * 5);
		}
		
		//sensor.close();
	}

	private void goStraight(DifferentialPilot robot) {
		//go straight
		Delay.msDelay(1000);	
		robot.travel(60);
	}

	private void initializeColorSensor() {
		/* Steps to initialize a sensor */
//		Brick brick = BrickFinder.getDefault();
//		Port s4 = brick.getPort("S4");
//		sensor = new EV3ColorSensor(s4);
		
		ambientMode = sensor.getAmbientMode();
		colorIdMode = sensor.getColorIDMode();
	
		reflectedLight = new autoAdjustFilter(ambientMode);
		sampleSize = reflectedLight.sampleSize();
		sample = new float[sampleSize];
		sides = new float[8];
		maxIndex = 0;
		
	}

	private DifferentialPilot initializePilot() {
		/* Steps to initialize the pilot*/
		PilotProps pp = new PilotProps();
		//pp.loadPersistentValues();
		float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "4.0"));
		float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "18.0"));
		leftMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_LEFTMOTOR, "B"));
		rightMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));
		boolean reverse = Boolean.parseBoolean(pp.getProperty(PilotProps.KEY_REVERSE,"false"));
		DifferentialPilot robot = new DifferentialPilot(wheelDiameter,trackWidth,leftMotor,rightMotor,reverse);
		robot.setAcceleration(4000);
		robot.setTravelSpeed(40); // cm/sec
		robot.setRotateSpeed(90); // deg/sec
		return robot;
	}

	private void calibrateBrightSensor(SampleProvider reflectedLight, float[] sample) {
		//calibrating
		for(int j = 0; j < 50; j ++){
			reflectedLight.fetchSample(sample, 0);
		}
	}

	private void calculateBrightBySide(EV3ColorSensor sensor, DifferentialPilot robot, SampleProvider ambientMode,
			int sampleSize, float[] sides) {
		
		
		float[] sample = new float[sampleSize];
		for (int i = 0; i < sides.length ; i ++){
			Delay.msDelay(1500);
			for(int j = 0; j < 50; j ++){
				reflectedLight.fetchSample(sample, 0);
				sides[i] += sample[0];
			}
			System.out.println("\n--------------------------\nside " + i + ": " + sides[i] + " ");
			robot.rotate(57);
		}
		maxIndex = maxIndex(sides);
		System.out.println("\n-----------MAX INDEX---------------\n" + maxIndex + "\n\n");
	}

	private void findBrightestDirection(DifferentialPilot robot, int maxIndex) {
		Delay.msDelay(250);
		//rotate to the side with the maximum value of brightness.
		for (int i = 0; i < maxIndex; i ++){
			robot.rotate(57);
		}
	}

	/**
	 * This filter dynamicaly adjust the samples value to a range of 0-1. The
	 * purpose of this filter is to autocalibrate a light Sensor to return values
	 * between 0 and 1 no matter what the light conditions. Once the light sensor
	 * has "seen" both white and black it is calibrated and ready for use.
	 * 
	 * The filter could be used in a line following robot. The robot could rotate
	 * to calibrate the sensor.
	 * 
	 * @author Aswin
	 * 
	 */
	public class autoAdjustFilter extends AbstractFilter {
		/* These arrays hold the smallest and biggest values that have been "seen: */
		private float[] minimum;
		private float[] maximum;

		public autoAdjustFilter(SampleProvider source) {
			super(source);
			/* Now the source and sampleSize are known. The arrays can be initialized */
			minimum = new float[sampleSize];
			maximum = new float[sampleSize];
			reset();
		}

		public void reset() {
			/* Set the arrays to their initial value */
			for (int i = 0; i < sampleSize; i++) {
				minimum[i] = Float.POSITIVE_INFINITY;
				maximum[i] = Float.NEGATIVE_INFINITY;
			}
		}

		/*
		 * To create a filter one overwrites the fetchSample method. A sample must
		 * first be fetched from the source (a sensor or other filter). Then it is
		 * processed according to the function of the filter
		 */
		public void fetchSample(float[] sample, int offset) {
			super.fetchSample(sample, offset);
			for (int i = 0; i < sampleSize; i++) {
				if (minimum[i] > sample[offset + i])
					minimum[i] = sample[offset + i];
				if (maximum[i] < sample[offset + i])
					maximum[i] = sample[offset + i];
				sample[offset + i] = (sample[offset + i] - minimum[i]) / (maximum[i] - minimum[i]);
			}
		}

	}

	private static int maxIndex(float[] list) {
		int maxIndex = 0;
		for (int i = 1; i < list.length; i++){
			float newnumber = list[i];
			if ((newnumber > list[maxIndex]) && (newnumber != Float.NaN)){
				maxIndex = i;
			}
		}
		return maxIndex;
	}

}
