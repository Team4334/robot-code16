package org.usfirst.frc.team4334.flywheel;

import java.util.TimerTask;

import org.usfirst.frc.team4334.control.Loopable;
import org.usfirst.frc.team4334.robot.Ports;
import org.usfirst.frc.team4334.utils.PidController;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlywheelController implements Loopable {

	PidController pid;
	private Fly fly;

	private double setPoint;
	private double predPow;
	private double error;

	// assuming encoder ticks once per degree and is mounted at flywheel
	// (probably will not be the case)

	public FlywheelController(Fly f) {
		fly = f;
		pid = new PidController(FlyConstants.kP, FlyConstants.kI,
				FlyConstants.kD, FlyConstants.intLim, false);
	
	}

	public void setFlySpeedBatter() {
		setFlySpeed(FlyConstants.RPM_ON_BATTER, FlyConstants.POW_ON_BATTER);
	}

	public void setFlySpeedObj() {
		setFlySpeed(FlyConstants.RPM_OBST, FlyConstants.POW_OBST);
	}

	public void setFlySpeedAuto(){
	    setFlySpeed(FlyConstants.AUTO_RPM, FlyConstants.AUTO_POW);
	}
	
	public void setFlySpeedGenericCross(){
		setFlySpeed(FlyConstants.AUTO_GENERIC_RPM, FlyConstants.AUTO_POW);
	}
	
	public void setFlySpeed(double rpm) {
		this.setPoint = rpm;
		this.predPow = 0;
		pid.reset();
	}

	public void setFlySpeed(double rpm, double pred) {
		this.setPoint = rpm;
		this.predPow = pred;
		pid.reset();
	}

	public double getError() {
		return this.setPoint - fly.getRpm();
	}

	public boolean onTarget() {
		return Math.abs(getError()) < FlyConstants.ERROR_THRESH;
	}

	private boolean pid_enabled = true;
	private boolean lastOn = false;
	private long lastFlash = System.currentTimeMillis();

	public void calculate() {
		pid.sendValuesToDashboard("flywheel_val");
		this.error = getError();
		double output = this.predPow + pid.calculate(this.error);
		// System.out.println(output);
		if (output < 0) {
			output = 0;
		}
		if (error > 500) {
			//light.set(Relay.Value.kReverse);
		} else {
			if (lastFlash + 100 < System.currentTimeMillis()) {
				if (lastOn) {
					//light.set(Relay.Value.kReverse);
				} else {
					//light.set(Relay.Value.kOn);
				}
				lastOn = !lastOn;
				lastFlash = System.currentTimeMillis();
			}
		}
		fly.setFlyPow(output);
		SmartDashboard.putNumber("fly_error ", this.error);
		SmartDashboard.putNumber("fly_out", output);
		SmartDashboard.putNumber("set_rpm", this.setPoint);

		try {
			Thread.sleep(FlyConstants.THREAD_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		FlyConstants.debugRpms();
		calculate();
	}

}
