package main;
import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensorTest {
	public static void main(String[] args) throws InterruptedException
	{
		LCD.drawString("Color Sensor Demo", 0, 0);
		
		EV3ColorSensor s = new EV3ColorSensor(SensorPort.S4);
		
		ArrayList<String> a = s.getAvailableModes();
		System.out.println("Available Modes for EV3 Color Sensor Are...\n");
		for(int i = 0; i < a.size(); i ++)
		{
			s.setCurrentMode(a.get(i));
			System.out.println(s.getCurrentMode() + " : " + a.get(i));
		}
		System.out.println();
		
		float[] sample = new float[3];
		
		System.out.println("Testiong ColorID Mode");
		s.setCurrentMode("ColorID");
		s.setFloodlight(true);
		for(int i = 0; i < 10; i ++)
		{
			s.fetchSample(sample, 0);
			System.out.println(sample[0]);
			Thread.sleep(1000);
		}
		
		System.out.println("Testiong Red Mode");
		s.setCurrentMode("Red");
		for(int i = 0; i < 10; i ++)
		{
			s.fetchSample(sample, 0);
			System.out.println(sample[0]);
			Thread.sleep(1000);
		}
		
		System.out.println("Testiong RGB Mode");
		s.setCurrentMode("RGB");
		for(int i = 0; i < 10; i ++)
		{
			s.fetchSample(sample, 0);
			System.out.println(sample[0] + ", " + sample[1] + ", " + sample[2]);
			Thread.sleep(1000);
		}
		
		System.out.println("Testiong Ambient Mode");
		s.setCurrentMode("Ambient");
		for(int i = 0; i < 10; i ++)
		{
			s.fetchSample(sample, 0);
			System.out.println(sample[0]);
			Thread.sleep(1000);
		}
		
		System.out.println("Testing Floodlight");
		s.setFloodlight(false);
		System.out.println("NONE");
		s.setFloodlight(Color.NONE);
		System.out.println("BLUE");
		s.setFloodlight(Color.MAGENTA);
		Thread.sleep(1000);
		System.out.println("RED");
		s.setFloodlight(Color.RED);
		Thread.sleep(1000);
		System.out.println("WHITE");
		s.setFloodlight(Color.WHITE);
		Thread.sleep(1000);
		System.out.println("NONE");
		s.setFloodlight(Color.NONE);
		System.out.println("BLUE");
		s.setFloodlight(Color.BLUE);
		Thread.sleep(1000);
		System.out.println("RED");
		s.setFloodlight(Color.RED);
		Thread.sleep(1000);
		System.out.println("WHITE");
		s.setFloodlight(Color.WHITE);
		Thread.sleep(1000);
	}

}
