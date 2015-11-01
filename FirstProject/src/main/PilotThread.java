package main;

import lejos.robotics.navigation.DifferentialPilot;

public class PilotThread extends Thread {

	private static DifferentialPilot robot;

	public PilotThread(final DifferentialPilot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		while(PathColorRecognizerThread.colorId != -1){
			robot.travel(10);
		}
		
	}
	
	public static void turnRight(){
		robot.travel(10);
		robot.rotate(120);
		robot.travel(10);
	}
	
	public static void turnLeft(){
		robot.travel(10);
		robot.rotate(-120);
		robot.rotate(10);
	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
