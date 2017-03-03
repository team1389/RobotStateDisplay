package draw;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.value_types.Position;

import application.Run;


public class EstimatedRobot {
	public static final int ROBOT_WIDTH = 24;
	public static final int ROBOT_HEIGHT = 26;
	static final int BUMPER_OFFSET = 3;

	static final boolean useBumpers = true;

	int robotWidth = (int) ((ROBOT_WIDTH + (useBumpers ? 2 * BUMPER_OFFSET : 0)) * Run.scale);
	int robotHeight = (int) ((ROBOT_WIDTH + (useBumpers ? 2 * BUMPER_OFFSET : 0)) * Run.scale);

	Image robot;
	protected Alliance alliance;

	SimulationField field;

	double x;
	double y;
	double theta;
	
	public EstimatedRobot(SimulationField field) {
		this(field, Alliance.RED);
	}

	public EstimatedRobot(SimulationField field, Alliance alliance) {
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

	


	public void setPosition(double x, double y, double theta, int gear) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		Gear currentGearState = getGear(gear);
		if(currentGearState.equals(currentGearState.PLACING)){
			placingGearReset();
		}
		

	}
	
	private Gear getGear(int gear) {
		try{
			return Gear.values()[gear];
		}
		catch(Exception e){
			//index is out of bounds of array
			return Gear.STOWED;
		}
	}

	private enum Gear{
		INTAKING, CARRYING, STOWED, PLACING, ALIGNING;
	}

	public void placingGearReset(){
		if(alliance.equals(Alliance.RED)){
			if(theta < -40){
				setAbsolutePosition(140, 250);
			}
			else if(theta > 40){
				setAbsolutePosition(156, 250);
			}
			else{
				setAbsolutePosition(118, 185);
			}
		}
		
		//TODO: Blue alliance
		
	}
	
	/**
	 * Scale assumed to be zero for input values
	 */
	public void setAbsolutePosition(double x, double y){
		this.x = x * Run.scale;
		this.y = y * Run.scale;
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