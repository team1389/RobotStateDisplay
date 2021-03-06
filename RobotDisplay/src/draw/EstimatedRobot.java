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
	private double yTranslate = 0;
	private double xTranslate = 0;

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

	public void setPosition(double x, double y, double theta, boolean placed) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		/*Gear currentGearState = getGear(placed);
		if(currentGearState.equals(Gear.PLACING)){
			placingGearReset();
		}*/
		if(placed) placingGearReset();


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
			if(theta < -25){
				setCurrentPosition(140, 250);
			}
			else if(theta > 25){
				setCurrentPosition(156, 125);
			}
			else{
				setCurrentPosition(118, 185);
			}
		}
		else{
			if(theta < 165){
				setCurrentPosition(562, 132);
			}
			else if(theta > 205){
				setCurrentPosition(569, 242);
			}
			else{
				setCurrentPosition(596, 185);
			}
		}


	}

	public void setCurrentPosition(double newX, double newY){
		this.xTranslate = newX - x;
		this.yTranslate = newY - y;
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
		robot.drawCentered((float)(getX() * Run.scale), (float)(getY() * Run.scale));
	}
	
	public double getX(){
		return x + xTranslate;
	}
	
	public double getY(){
		return y + yTranslate;
	}




}
