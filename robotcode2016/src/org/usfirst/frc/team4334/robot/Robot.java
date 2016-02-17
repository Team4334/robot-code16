package org.usfirst.frc.team4334.robot;

import java.util.LinkedList;

import org.usfirst.frc.team4334.control.MultiLooper;
import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.drive.TeleopDrive;
import org.usfirst.frc.team4334.subsystems.FlywheelController;
import org.usfirst.frc.team4334.subsystems.IntakeController;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.filters.Filter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//camera
	CameraServer usbCamServ;
	
	//drive 
    DriveBase driveBase;
	TeleopDrive teleopDrive;
	
	//intake
	IntakeController intake;
	
	//fly wheel
	FlywheelController flyControl;

	//joysticks
    Joystick joyDrive = new Joystick(Ports.JOYSTICK_1);
    Joystick joyOper = new Joystick(Ports.JOYSTICK_2);
    
    //
	Victor sketchyFlywheel = new Victor(Ports.SHOOTER);
	
	
	
	
	public static RobotStates gameState = RobotStates.DISABLED;
	
	public static enum RobotStates{
		DISABLED,
		TELEOP,
		AUTO
	}
	
	AnalogGyro gyro;


   	 
	public void robotInit(){
//        usbCamServ.setQuality(50);
//        usbCamServ.setSize(50);
//        usbCamServ.startAutomaticCapture("cam0");   
//        usbCamServ = CameraServer.getInstance();
		
        //create our drivebase 
    	LinkedList<SpeedController> left = new LinkedList<SpeedController>();
    	LinkedList<SpeedController> right = new LinkedList<SpeedController>();
    	left.add(new Victor(Ports.DRIVE_LEFT_1));
    	right.add(new Victor(Ports.DRIVE_RIGHT_1));
     	driveBase = new DriveBase(left, right);
     	
     	//create our intake controller 
    	intake = new IntakeController(Ports.INTAKE);
    	gyro = new AnalogGyro(Ports.GYRO);

    	gyro.calibrate();

    	driveControl = new DriveController(driveBase,
    			new Encoder(Ports.ENCODER_LEFT, Ports.ENCODER_LEFT + 1,true,EncodingType.k4X)
    			, new Encoder(Ports.ENCODER_RIGHT, Ports.ENCODER_RIGHT + 1,true,EncodingType.k4X),
    			gyro);
    	//Counter flyCount = new Counter(Ports.HALL_EFFECT);
    	//flyControl = new FlywheelController(flyCount,new Victor(Ports.SHOOTER));
    	
    	teleopDrive = new TeleopDrive(driveBase,joyDrive);
    }
    
    
    public void disabled(){
    	Robot.gameState = RobotStates.DISABLED;
    }
    
    
    public void test(){
    	
    }
    

	Thread autoThread;
    public void autonomousInit(){
    	autoThread = new Thread(new Auto(driveControl));
    	
    }
    
 	DriveController driveControl;
	MultiLooper autoLooper = new MultiLooper("auto ", 200);
    public void autonomousPeriodic() {
    	
    	
    	if(isAutonomous() && isEnabled()){
    		gyro.reset();
    		autoThread.start();
    		Robot.gameState = RobotStates.AUTO;

     		
        	while( isAutonomous() && isEnabled() ){
        		if(isDisabled()){
        			Robot.gameState = RobotStates.DISABLED;
        			autoLooper.stop();
        		}
        		Timer.delay(0.02);
        	} 		
    	}
 
    	Robot.gameState = RobotStates.DISABLED;
 		Timer.delay(0.02);
    }

    
    public void disabledPeriodic(){
    	Robot.gameState = RobotStates.DISABLED;
    	while(isDisabled()){
    		Timer.delay(0.02);
    	}
    }

    
    MultiLooper teleLooper = new MultiLooper("tele looper", 0.05);
 	boolean firstRun = true;
    public void teleopInit(){
    	if(firstRun){
    		teleLooper.addLoopable(new TeleopDrive(driveBase, joyDrive));
    	}
    	Robot.gameState = RobotStates.TELEOP;      
    	
    }
    
    
    public void teleopPeriodic() {

    }
}
