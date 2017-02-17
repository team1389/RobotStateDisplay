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
import draw.SimulationRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTable.*;


public class Run extends BasicGame{
	public static int scale = 1;
	public static final int width = (int) (716 * scale);
	public static final int height = (int) (376 * scale);
	
	public static void main(String[] args) throws SlickException{
		NetworkTable.setServerMode();
		NetworkTable.setIPAddress("localhost");
		NetworkTable.initialize();
		NetworkTable.globalDeleteAll();
		//NetworkTablesJNI.startDSClient(port);
		Run r = new Run("TEST");
		AppGameContainer cont = new AppGameContainer(r);
		cont.setTargetFrameRate(70);
		cont.setFullscreen(true);
		cont.setDisplayMode(width, height, false);
		cont.start();
		//NetworkTable robotInfo = NetworkTable.getTable("test");
		//robotInfo.getDouble("test");

	}
	
	boolean useBumpers = true;
	
	
	public Run(String title){
		super(title);
	}



	PositionEstimator estimator;
	SimulationRobot robot;
	SimulationField field;
	


	@Override
	public void init(GameContainer arg0) throws SlickException {
		//estimator = new PositionEstimator(Alliance.RED, temp, robot.getGearsDroppedOff(), drive.leftIn.getInches() , drive.rightIn.getInches(), drive.leftVel.mapToRange(0, 1).scale(4 * 2 * Math.PI), drive.rightVel.mapToRange(0, 1).scale(4 * 2 * Math.PI), new AngleIn<Position>(Position.class ,() -> robot.getRelativeHeadingDegrees()), 10, 23, .6);
		estimator = new PositionEstimator(Alliance.RED);
		field = new SimulationField(width, height, Alliance.RED);
		robot = new SimulationRobot(field, Alliance.RED, estimator); 


	}
	
	@Override
	public void render(GameContainer cont, Graphics g) throws SlickException {
		//field.render(g);
		//robot.render(cont, g);
	}

	@Override
	public void update(GameContainer arg0, int delta) throws SlickException {
		//robot.update(delta);
	}
	
	
	
}
