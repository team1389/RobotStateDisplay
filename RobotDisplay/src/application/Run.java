package application;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


import com.team1389.trajectory.RigidTransform2d;

import draw.Alliance;
import draw.SimulationField;
import draw.EstimatedRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTable.*;


public class Run extends BasicGame{
	public static int scale = 1;
	public static final int width = (int) (716 * scale);
	public static final int height = (int) (376 * scale);

	public static void main(String[] args) throws SlickException{
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("127.0.0.1");
		NetworkTable.initialize();

		Run r = new Run("Position Estimator");
		AppGameContainer cont = new AppGameContainer(r);
		cont.setTargetFrameRate(70);
		cont.setFullscreen(true);
		cont.setDisplayMode(width, height, false);
		cont.setAlwaysRender(true);
		cont.start();

	}

	public Run(String title){
		super(title);
	}

	NetworkTable estimation;
	EstimatedRobot robot;
	SimulationField field;

	@Override
	public void init(GameContainer arg0) throws SlickException {
		estimation = NetworkTable.getTable("estimator");
		field = new SimulationField(width, height, Alliance.RED);
		robot = new EstimatedRobot(field, Alliance.RED); 
	}

	@Override
	public void render(GameContainer cont, Graphics g) throws SlickException {
		field.render(g);
		robot.render(cont, g);
	}

	@Override
	public void update(GameContainer arg0, int delta) throws SlickException {
		boolean placed = estimation.getBoolean(EstimatorTableKeys.GEAR_PLACED.key, false);
		if(placed){
			estimation.putBoolean(EstimatorTableKeys.GEAR_PLACED.key, false);
		}
		if(estimation.getNumber(EstimatorTableKeys.ACCELERATION_MAGNITUDE.key, -1) == 0){
			double x = robot.getX();
			double y = robot.getY();
			robot.setPosition(estimation.getNumber(EstimatorTableKeys.X_POSITION.key, 0),
					estimation.getNumber(EstimatorTableKeys.Y_POSITION.key, 0), 
					estimation.getNumber(EstimatorTableKeys.ANGLE_DEGREES.key, 0),
					placed);	
			robot.setAbsolutePosition(x, y);
		}
		robot.setPosition(estimation.getNumber(EstimatorTableKeys.X_POSITION.key, 0),
				estimation.getNumber(EstimatorTableKeys.Y_POSITION.key, 0), 
				estimation.getNumber(EstimatorTableKeys.ANGLE_DEGREES.key, 0),
				placed);		

	}

	private enum EstimatorTableKeys {
		X_POSITION("x"), Y_POSITION("y"), ANGLE_DEGREES("angle"), GEAR("gear"), GEAR_PLACED("placed"), ACCELERATION_MAGNITUDE("accel");
		protected final String key;
		private EstimatorTableKeys(String key) {
			this.key = key;
		}
	}



}
