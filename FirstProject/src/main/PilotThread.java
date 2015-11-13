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
			Teste.movements.push("TRAVEL|10");
		}
		
	}
	
	public static void turnRight(){
		robot.travel(10);
		Teste.movements.push("TRAVEL|10");

		robot.rotate(120);
		Teste.movements.push("ROTATE|120");

		robot.travel(10);
		Teste.movements.push("TRAVEL|10");

	}
	
	public static void turnLeft(){
		robot.travel(10);
		Teste.movements.push("TRAVEL|10");

		robot.rotate(-120);
		Teste.movements.push("ROTATE|-120");

		robot.rotate(10);
		Teste.movements.push("TRAVEL|10");

	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void goBack()
	{
		for (int i = 0 ; i < Teste.movements.size(); i ++)
		{
			String movement = Teste.movements.pop().toString();
			String[] data = movement.split("|");
			
			if (data[0].equals("TRAVEL")){
				robot.travel((-1) * Integer.parseInt(data[1]));
			} else if (data[0].equals("ROTATE"))
			{
				robot.rotate((-1) * Integer.parseInt(data[1]));
			}
		}
	}
	

}
