package draw;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.value_types.Position;
import com.team1389.trajectory.RigidTransform2d;
import com.team1389.trajectory.RigidTransform2d.Delta;
import com.team1389.trajectory.RobotState;
import com.team1389.trajectory.Rotation2d;
import com.team1389.trajectory.Translation2d;

import application.PositionEstimator;
import edu.wpi.first.wpilibj.Timer;

public class SimulationRobot {
	public static final int ROBOT_WIDTH = 24;
	public static final int ROBOT_HEIGHT = 26;
	static final int BUMPER_OFFSET = 3;

	static final boolean useBumpers = true;

	double scale = 1;
	int robotWidth = (int) ((ROBOT_WIDTH + (useBumpers ? 2 * BUMPER_OFFSET : 0)) * scale);
	int robotHeight = (int) ((ROBOT_WIDTH + (useBumpers ? 2 * BUMPER_OFFSET : 0)) * scale);

	Image robot;
	protected boolean disabled;
	protected Alliance alliance;

	SimulationField field;

	double x;
	double y;
	double theta;
	
	public SimulationRobot(SimulationField field) {
		this(field, Alliance.RED);
	}

	public SimulationRobot(SimulationField field, Alliance alliance) {
		this.field = field;
		this.alliance = alliance;
		try {
			robot = generateRobotImage().getScaledCopy(robotWidth, robotHeight);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	

	private Image generateRobotImage() throws SlickException {
		return new Image(useBumpers
				? alliance == Alliance.BLUE ? Resources.blueAllianceRobotImage : Resources.redAllianceRobotImage
				: Resources.robotImage);
	}

	public void startMatch() {
		x = 0;
		y = 0;
	}

	


	public void setPosition(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	


	public Alliance getAlliance() {
		return alliance;
	}

	public AngleIn<Position> getGyro() {
		return new AngleIn<Position>(Position.class, () -> theta);
	}

	public void render(GameContainer container, Graphics g){
		robot.setRotation((float) theta + 90);
		robot.setCenterOfRotation(robotWidth / 2, robotHeight / 2);
		robot.drawCentered((float)x, (float)y);
	}
	

}
