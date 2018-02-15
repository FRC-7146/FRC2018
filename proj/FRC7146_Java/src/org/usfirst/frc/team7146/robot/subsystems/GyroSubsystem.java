package org.usfirst.frc.team7146.robot.subsystems;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class GyroSubsystem extends Subsystem {
	private static final Logger logger = Logger.getLogger(GyroSubsystem.class.getName());
	public static final double DISP_2_METER = 1500;

	public Gyro mGyro = Robot.m_oi.mGyro;
	public Accelerometer mAccel = Robot.m_oi.mAccelerometer;
	public TalonSRX mTalon1 = Robot.m_oi.mTalon1;

	public GyroSubsystem() {
		super("GyroSubsystem");
		this.calibrate();
	}

	public void calibrate() {
		logger.info("Calibrating gyro/acc");
		Robot.m_oi.mGyro.reset();
		// Robot.m_oi.mGyro.calibrate();
		logger.info("Calibration done");
	}

	public double getPosition() {
		return mTalon1.getSensorCollection().getQuadraturePosition();
	}

	public ErrorCode setPosition(int newPosition) {
		return mTalon1.getSensorCollection().setQuadraturePosition(newPosition, 19);
	}

	public double getAngle() {
		double angle = this.mGyro.getAngle();/*
												 * while (!(angle > -180 && angle < 180)) { if (angle > 180) { angle -=
												 * 360; } else if (angle < -180) { angle += 180; } }
												 */
		return angle;
	}

	public double getAbsoluteAngle() {
		double angle = this.mGyro.getAngle();
		while (!(angle > -180 && angle < 180)) {
			if (angle > 180) {
				angle -= 360;
			} else if (angle < -180) {
				angle += 180;
			}
		}
		return angle + 180;

	}

	@Override
	protected void initDefaultCommand() {

	}

}
