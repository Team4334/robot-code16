package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.utils.PidController;

public class DriveConstants {
	public static final double JOY_DEADZONE = 0.2;
	public static final double TICKS_PER_INCH = 5.582359629;//(1284-855) / (3 * 12);
	//(1284-855/(3 * 12))
	public static final double TICKS_PER_FEET = TICKS_PER_INCH * 12;
	public static final double DRIVE_WIDTH = 22.5; //inches 
	
	
	public static final int THREAD_SLEEP_MS = 20;
	
	public static final int SATISFY_TIME_MS = 600;
	public static final int SATISFY_TIME_TURN = 600;
	
	
	
	//2 inches
	public static final double DRIVE_STRAIGHT_ERROR_THRESH = 0.1;
	
	//2 degrees 
	public static final double TURN_ERROR_THRESH = 0.3;
	
	public static final double MAX_AUTO_SPEED = 0.99;
	public static final double MAX_AUTO_TURN_SPEED = 0.75;
	public static final double AUTO_REST_THRESH = 0.01;
	//PID Constants

	
	//drive straight
	public static final double DRIVE_KP = 0.0210;
	public static final double DRIVE_KI = 0.00012;
	public static final double DRIVE_KD = 0.02;
	public static final double DRIVE_INT_LIM = 0.10 / DRIVE_KI;
	
	public static final double DRIVE_SLAVE_KP = 0.005;
	public static final double DRIVE_SLAVE_KI = 0;
	public static final double DRIVE_SLAVE_KD = 0.080;
	public static final double DRIVE_SLAVE_INT_LIM = 200;
	
	//gyro turn 
	public static final double TURN_KP = 0.06;//0.05;
	public static final double TURN_KI = 0.004;//0.002;
	public static final double TURN_KD = 0.4;//0.02;
	public static final double TURN_INT_LIM = 0.25 / TURN_KI;
	
	public static final double STRAIGHT_KP = 0.04;
	public static final double STRAIGHT_KI = 0;
	public static final double STRAIGHT_KD = 0.1;
	public static final double STRAIGHT_INT_LIM = 0;
	
}
