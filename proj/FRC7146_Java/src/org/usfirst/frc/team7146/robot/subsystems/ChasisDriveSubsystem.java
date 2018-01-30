/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot.subsystems;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;
import org.usfirst.frc.team7146.robot.commands.TeleopArcadeDriveCommand;
import org.usfirst.frc.team7146.robot.commands.TeleopTankDriveCommand;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class ChasisDriveSubsystem extends PIDSubsystem {
	private static final Logger logger = Logger.getLogger(ChasisDriveSubsystem.class.getName());

	Spark mLeftMotor = new Spark(RobotMap.MOTOR.LEFT_MOTOR_GROUP);
	Spark mRightMotor = new Spark(RobotMap.MOTOR.RIGHT_MOTOR_GROUP);
	DifferentialDrive mDifferentialDrive = new DifferentialDrive(mLeftMotor, mRightMotor);
	Gyro mGyro = Robot.m_oi.mGyro;// TODO: TEST
	// AnalogAccelerometer mAccelerometer = new AnalogAccelerometer(0);// TODO: TEST

	public ChasisDriveSubsystem(double p, double i, double d) {
		super(p, i, d);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);

		logger.info("Calibrating gyro");
		mGyro.reset();
		mGyro.calibrate();
	}

	public void initDefaultCommand() {
		// setDefaultCommand(new TeleopTankDriveCommand());
		setDefaultCommand(new TeleopArcadeDriveCommand());
	}

	public void mDriveTank(Joystick j) {
		// x: [-1,1];y: [-1,1]
		mDifferentialDrive.tankDrive(j.getY() * RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR,
				j.getThrottle() * RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR);
	}

	public void mDriveArcade(double xSpeed, double zRot) {// TODO: TEST
		// x: [-1,1]
		mDifferentialDrive.arcadeDrive(xSpeed * RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR,
				zRot * RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR);
	}

	public void stopDrive() {
		logger.info("Chassis Drive stops");
		mLeftMotor.stopMotor();
		mRightMotor.stopMotor();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Return PID offset
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Use PID output

	}

	@Override
	public void finalize() {
		mGyro.free();
	}
}
