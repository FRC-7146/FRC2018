package org.usfirst.frc.team7146.robot.subsystems;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class GyroSubsystem extends Subsystem {
	private static final Logger logger = Logger.getLogger(GyroSubsystem.class.getName());

	Gyro mGyro = Robot.m_oi.mGyro;

	public GyroSubsystem() {
		super("GyroSubsystem");
		this.calibrate();
	}

	public void calibrate() {
		logger.info("Calibrating gyro/acc");
		Robot.m_oi.mGyro.reset();
		Robot.m_oi.mGyro.calibrate();
		// TODO: acc set zero
		logger.info("Calibration done");
	}

	@Override
	protected void initDefaultCommand() {

	}

}
