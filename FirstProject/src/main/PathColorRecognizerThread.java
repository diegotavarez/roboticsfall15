package main;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;

public class PathColorRecognizerThread extends Thread {

	private static EV3ColorSensor colorSensor;
	public static int colorId = -1;
	public static boolean delivered = false;
	public static Location current_location = new Location(3,1); // 3,1 pq tem que ser 2,1 a primeira celula entao qd
																 // achar a primeira celula diminui 1 e fica 2,1
	public static int current_color = -1;
	public static Cell[][] map = new Cell[3][3];

	public PathColorRecognizerThread(final EV3ColorSensor colorSensor) {
		this.colorSensor = colorSensor;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				map[i][j] = new Cell(0, -1);
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
			colorId = colorSensor.getColorID();
			switch (colorId){
			//RED
			case 0:
				if(current_color != 0)
				{
					System.out.println("ENTREI NO RED" + "i = " + getCurrentLocation().getI());
					System.out.println("ENTREI NO RED" + "j = " + getCurrentLocation().getJ());
			
					if(PilotThread.travel_distance == 10)
					{
						updateCurrentLocation(true);
					}
					else
						updateCurrentLocation(false);

					map[getCurrentLocation().getI()][getCurrentLocation().getJ()] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ()].getOccupied(), 0);
					map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].getOccupied(), 0);
					map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].getOccupied(), 0);		
				}
				Button.LEDPattern(2);
			//	System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) + Integer.toString(colorId));
				if(ObjectColorRecognizerThread.objectColor==colorId && delivered == false){
					System.out.println(" ocupado = " + map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].occupied);
					 System.out.println("color = " + map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].color);
					if(map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].occupied == 0 &&
							map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].color == 0)
						Teste.pilotThread.turnRight();
					else if(map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].occupied == 0 &&
							map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].color == 0)
						Teste.pilotThread.turnLeft();
					else
						System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) 
								+ Integer.toString(colorId));
					//Teste.pilotThread.goBack();		
					
					//System.exit(0);
				}
				current_color = 0; // chamar dentro do turn, vai q

			//	System.out.println("SAI DO RED" + "i = " + getCurrentLocation().getI());
			//	System.out.println("SAI DO RED" + "j = " + getCurrentLocation().getJ());
				break;
			//GREEN
			case 1:
				if(current_color != 1)
				{
					System.out.println("ENTREI NO green" + "i = " + getCurrentLocation().getI());
					System.out.println("ENTREI NO green" + "j = " + getCurrentLocation().getJ());

					if(PilotThread.travel_distance == 10)
					{
						updateCurrentLocation(true);
					}
					else
						updateCurrentLocation(false);
					
					map[getCurrentLocation().getI()][getCurrentLocation().getJ()] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ()].getOccupied(), 1);
					map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].getOccupied(), 1);
					map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].getOccupied(), 1);

				}
				// updateLocation
				Button.LEDPattern(1);
			//	System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) + Integer.toString(colorId));
				
				if(ObjectColorRecognizerThread.objectColor==colorId && delivered == false){
					System.out.println(" ocupado = " + map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].occupied);
					 System.out.println("color = " + map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].color);
					if(map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].occupied == 0 &&
							map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].color == 1)
						Teste.pilotThread.turnRight();
					else if(map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].occupied == 0 &&
							map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].color == 1)
						Teste.pilotThread.turnLeft();
					else
						System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) 
								+ Integer.toString(colorId));
					
					//Teste.pilotThread.goBack();
					
			//		System.exit(0);

				}
				current_color = 1;
			//	System.out.println("sai do green" + "i = " + getCurrentLocation().getI());
			//	System.out.println("sai do green" + "j = " + getCurrentLocation().getJ());
				break;
			//YELLOW
			case 3:
				if(current_color != 3)
				{
					System.out.println("ENTREI NO yellow" + "i = " + getCurrentLocation().getI());
					System.out.println("ENTREI NO yellow" + "j = " + getCurrentLocation().getJ());
	
					if(PilotThread.travel_distance == 10)
					{
						updateCurrentLocation(true);
					}
					else
						updateCurrentLocation(false);
					
					map[getCurrentLocation().getI()][getCurrentLocation().getJ()] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ()].getOccupied(), 3);
					map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].getOccupied(), 3);
					map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1] = new Cell(map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].getOccupied(), 3);
				}
				// updateLocation
				Button.LEDPattern(3);
			//	System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) + Integer.toString(colorId));
				if(ObjectColorRecognizerThread.objectColor==colorId && delivered == false){
					System.out.println(" ocupado = " + map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].occupied);
					 System.out.println("color = " + map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].color);
					if(map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].occupied == 0
							&& map[getCurrentLocation().getI()][getCurrentLocation().getJ() + 1].color == 3)
						Teste.pilotThread.turnRight();
					else if(map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].occupied == 0
							&& map[getCurrentLocation().getI()][getCurrentLocation().getJ() - 1].color == 3)
						Teste.pilotThread.turnLeft();
					else
						System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) 
								+ Integer.toString(colorId));					
					//Teste.pilotThread.goBack();
					
			//		System.exit(0);

				}
				current_color = 3;
			//	System.out.println("sai do yellow" + "i = " + getCurrentLocation().getI());
			//	System.out.println("sai do yellow" + "j = " + getCurrentLocation().getJ());
				break;
			//BLUE
			case 2:
				// updateLocation
			//	System.out.println(Integer.toString(ObjectColorRecognizerThread.objectColor) + Integer.toString(colorId));

				if(delivered == true)
				{
					current_color = -1;
					current_location.setI(3);
					current_location.setJ(1);
					System.out.println("entrei no azul" + "i = " + getCurrentLocation().getI());
					System.out.println("entrei no azul" + "j = " + getCurrentLocation().getJ());
					PilotThread.robot.travel(-10);
					ObjectColorRecognizerThread.objectColor = -1;
					Teste.objectRecognizerThread.resume();
					PilotThread.travel_distance = 10;
					delivered = false;
				}
				// object area
				// call recognize color
//				Button.LEDPattern(0);
//				System.exit(0);
				
				
				//Teste.pilotThread.turnRight();
				break;
			default:
				Button.LEDPattern(0);
				//Teste.pilotThread.travel();
			}
		}
		
	}
	
	public static Location getCurrentLocation()
	{
		return current_location;
	}
	
	public void updateCurrentLocation(boolean direction)
	{
		if(direction)
			current_location.i--;
		else
			current_location.i++;
			
			
	}

	private void threadSleep(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
