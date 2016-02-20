package org.usfirst.frc.team4334.drive;

import java.util.TimerTask;

import org.usfirst.frc.team4334.control.Loopable;
import org.usfirst.frc.team4334.robot.Ports;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopDrive implements Loopable {
	private DriveBase drive;
	private static Joystick joy = new Joystick(Ports.JOYSTICK_1);

	public TeleopDrive(DriveBase d) {
		drive = d;
	}

	public void teleopDrive() {
		teleopDrive(DriveConstants.JOY_DEADZONE);
	}

	public void teleopDrive(double deadzone) {
		// boolean flipped = Math.abs(joy.getRawAxis(3)) > 0.5 ? true : false;
		double x1 = Utils.deadzone(joy.getRawAxis(0),
				DriveConstants.JOY_DEADZONE);
		double y1 = -Utils.deadzone(joy.getRawAxis(1),
				DriveConstants.JOY_DEADZONE);
		double x2 = Utils.deadzone(joy.getRawAxis(4),
				DriveConstants.JOY_DEADZONE);
		double y2 = -Utils.deadzone(joy.getRawAxis(5),
				DriveConstants.JOY_DEADZONE);

		// forward on stick b, turn on stick a
		
		
		y1 = mapToNDeg(y1, 2);
		x2 = mapToNDeg(x2, 2);

		drive.setDrive((double) y1 + (double) x2, (double) y1 - (double) x2);
		// if (flipped) {
		// drive.setDrive((double) y2 + (double) x1, (double) y2
		// - (double) x1);
		// }

	}

	private double mapToNDeg(double in, int n) {
		if (in != 0) {
			if (n % 2 == 0) {
				return (Math.pow(in, n) * (in / Math.abs(in)));
			} else {
				return Math.pow(in, n);
			}
		}
		return 0;
	}

	@Override
	public void update() {
		teleopDrive();
	}

}
