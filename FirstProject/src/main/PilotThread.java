package main;

import lejos.robotics.navigation.DifferentialPilot;

public class PilotThread extends Thread {

	private static DifferentialPilot robot;

	public PilotThread(final DifferentialPilot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		while(true){
			robot.travel(30);
		}
		
	}
	
	public static void turn(){
		robot.rotate(120);
	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
