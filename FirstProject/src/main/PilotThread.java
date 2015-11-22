package main;

import lejos.robotics.navigation.DifferentialPilot;

public class PilotThread extends Thread {
	
	public static boolean travel = true;
	public static int travel_distance = 10;

	public static DifferentialPilot robot;

	public PilotThread(final DifferentialPilot robot) {
		this.robot = robot;
	}

	@Override
	public void run() {
		while(true){
			if(travel && ObjectColorRecognizerThread.objectColor != -1)
			robot.travel(travel_distance);
		}
	}
	
	public static void travel(){
		robot.travel(10);
	}
	
	public static void turnRight(){
		travel = false;
		
		robot.travel(25);
		
		robot.rotate(110);
		
		robot.travel(25);
		
		//travel = true;
		
		goBackFromRight();
		
	//	try {
	//		Thread.sleep(1500);
	//	} catch (InterruptedException e) {
	//		e.printStackTrace();
	//	}
	}
	
	public static void turnLeft(){
		
		travel = false;
		
		robot.travel(25);
		
		robot.rotate(-110);
		
		robot.travel(25);
		
		//travel = true;
		
		goBackFromLeft();

	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void goBackFromRight()
	{
		robot.travel(-25);
		robot.rotate(-110);
		travel_distance = -10;
		PathColorRecognizerThread.delivered = true;
		travel = true;
	}
	
	public static void goBackFromLeft()
	{
		robot.travel(-25);
		robot.rotate(110);
		travel_distance = -10;
		PathColorRecognizerThread.delivered = true;
		travel = true;
	}
	
	public static void goBack()
	{
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		robot = Teste.initializePilot("false");
		
		for (int i = 0 ; i < Teste.movements.size(); i ++)
		{
			String movement = Teste.movements.pop().toString();
			String[] data = movement.split("|");
			
			if (data[0].equals("TRAVEL")){
				robot.travel(Integer.parseInt(data[1]));
			} else if (data[0].equals("ROTATE"))
			{
				turnLeft();
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
	

}
