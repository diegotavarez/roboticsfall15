package main;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;

public class PathColorRecognizerThread extends Thread {

	private EV3ColorSensor colorSensor;

	public PathColorRecognizerThread(final EV3ColorSensor colorSensor) {
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
			//YELLOW
			case 3:
				Button.LEDPattern(3);
				break;
			//BLUE
			case 2:	
//				Button.LEDPattern(0);
//				System.exit(0);
				
				Teste.pilotThread.turn();
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
