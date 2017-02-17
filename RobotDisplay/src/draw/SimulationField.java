package draw;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class SimulationField {
	private static String mapPath = "res/pretty field.png";
	private Image fieldMap;

	private DriverStation driverStation;
	private Image visibility;

	public SimulationField(int width, int height, Alliance red) {
		driverStation = DriverStation.Center;
		visibility = driverStation.visibility.getScaledCopy(width, height);
		try {
			fieldMap = new Image(mapPath).getScaledCopy(width, height);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	

	public void render(Graphics g) {
		fieldMap.draw();
	}

	public void renderVisibility() {
		visibility.draw();
	}
	
	public enum DriverStation {
		Boiler(Resources.ds1vis), Center(Resources.ds2vis), Feeder(Resources.ds3vis);
		public final Image visibility;

		private DriverStation(String visibilityPath) {
			Image img = null;
			try {
				img = new Image(visibilityPath);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			visibility = img;
		}
	}

}
