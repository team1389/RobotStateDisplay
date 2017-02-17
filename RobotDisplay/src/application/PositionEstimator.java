package application;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.hardware.value_types.Value;
import com.team1389.trajectory.RigidTransform2d;
import com.team1389.trajectory.RobotState;
import com.team1389.trajectory.RobotStateEstimator;
import com.team1389.trajectory.Rotation2d;
import com.team1389.trajectory.Translation2d;

import draw.Alliance;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class PositionEstimator{

	Alliance color;
	RangeIn<Value> gearsDroppedOff;
	double lastGearValue = 0;
	
	
	boolean return0 = false;
	public PositionEstimator(Alliance color){
		this.color = color;
	}
	
	public RigidTransform2d get(){
		if(return0){
			return new RigidTransform2d(new Translation2d(Math.random() * 500, Math.random() * 500), Rotation2d.fromDegrees(10));
		}
		double x = NetworkTable.getTable("StateEstimator").getNumber("X");
		double y = NetworkTable.getTable("StateEstimator").getNumber("Y");
		double theta = NetworkTable.getTable("StateEstimator").getNumber("Degrees");
		
		//Check change in gears. Would be different for actual robot
		double temp = gearsDroppedOff.get();
		if(temp != lastGearValue){
			lastGearValue = temp;
			//pickUpGearReset(theta);
		}
		
		
		
		return new RigidTransform2d(new Translation2d(x, y), Rotation2d.fromDegrees(theta));
	}
	
	private double xShift;
	private double yShift;
	public void pickUpGearReset(double thetaDegrees, double rawX, double rawY){
		if(color.equals(Alliance.RED)){
			if(thetaDegrees < -40){
				//this.resetToPosition(140, 250, thetaDegrees);
			}
			else if(thetaDegrees > 40){
				//this.resetToPosition(156, 120, thetaDegrees);
			}
			else{
				//this.resetToPosition(118, 185, thetaDegrees);
			}
		}
		
		//TODO: Blue alliance
		
	}
	
	//private double getX(double raw){
		
		
	//}
	
	public Alliance getAlliance(){
		return color;
	}

}
