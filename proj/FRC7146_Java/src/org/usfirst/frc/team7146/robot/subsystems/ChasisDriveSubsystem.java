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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import io.github.d0048.NumPID;

public class ChasisDriveSubsystem extends Subsystem {
	private static final Logger logger = Logger.getLogger(ChasisDriveSubsystem.class.getName());
	public static boolean CHASIS_DEBUG = false;

	public static enum CHASIS_MODE {
		TANK, ARCADE
	};

	CHASIS_MODE mode;

	SpeedController mLeftMotor = Robot.m_oi.mLeftMotor;
	SpeedController mRightMotor = Robot.m_oi.mRightMotor;
	DifferentialDrive mDifferentialDrive = Robot.m_oi.mDifferentialDrive;

	Gyro mGyro = Robot.m_oi.mGyro;// TODO: TEST
	AnalogAccelerometer mAccelerometer = Robot.m_oi.mAccelerometer;

	// PID Speed Control
	PIDController mPIDArcadeSpdCtler, mPIDArcadeAngCtler;// Arcade drive mode
	// public double requestedSpd = 0, requestedAng = 0;
	public double execSpd = 0, execAng = 0;

	PIDController mPIDTankLeftCtler, mPIDTankRightCtler;// Tank drive mode
	// public double requestedLeftSpd = 0, requestedRightSpd = 0;
	public double execLeftSpd = 0, execRightSpd = 0;

	/**
	 * Either (arcade, spd_pid, ang_pid) Or (Tank, left_pid, right_pid) !!!Ang pid
	 * not enabled
	 * 
	 * @param mode
	 * @param numPID1
	 * @param numPID2
	 */
	public ChasisDriveSubsystem(CHASIS_MODE mode, NumPID numPID1, NumPID numPID2) {
		super();
		this.mode = mode;
		// if (mode == CHASIS_MODE.ARCADE) {// Arcade mode pid
		this.mPIDArcadeSpdCtler = new PIDController(numPID1.P, numPID1.I, numPID1.D, mAccelerometer, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				execSpd = output;
			}
		});
		this.mPIDArcadeSpdCtler.setAbsoluteTolerance(0.5);
		this.mPIDArcadeSpdCtler.setInputRange(-1, 1);
		this.mPIDArcadeSpdCtler.setOutputRange(-1, 1);
		// this.mPIDArcadeSpdCtler.enable(); TODO: set

		/*
		 * this.mPIDArcadeAngCtler = new PIDController(numPID2.P, numPID2.I, numPID2.D,
		 * mGyro, new PIDOutput() {
		 * 
		 * @Override public void pidWrite(double output) { execAng = output; } });
		 * this.mPIDArcadeAngCtler.setAbsoluteTolerance(0.5);
		 * this.mPIDArcadeAngCtler.setInputRange(-1, 1);
		 * this.mPIDArcadeAngCtler.setOutputRange(0, 360);
		 * this.mPIDArcadeAngCtler.setContinuous(); this.mPIDArcadeAngCtler.enable();
		 */

		// } else
		if (mode == CHASIS_MODE.TANK) {// Tank mode pid
			this.mPIDTankLeftCtler = new PIDController(numPID1.P, numPID1.I, numPID1.D, mAccelerometer, mLeftMotor);// tank
																													// left
			this.mPIDTankLeftCtler.setAbsoluteTolerance(0.1);
			this.mPIDTankLeftCtler.setInputRange(-1, 1);
			this.mPIDTankLeftCtler.setOutputRange(0, 360);
			this.mPIDTankLeftCtler.setContinuous();
			this.mPIDTankLeftCtler.enable();

			this.mPIDTankRightCtler = new PIDController(numPID2.P, numPID2.I, numPID2.D, mAccelerometer, mRightMotor);// tank
																														// right
			this.mPIDTankRightCtler.setAbsoluteTolerance(0.1);
			this.mPIDTankRightCtler.setInputRange(-1, 1);
			this.mPIDTankRightCtler.setOutputRange(0, 360);
			this.mPIDTankRightCtler.setContinuous();
			this.mPIDTankRightCtler.enable();
		}
	}

	public void initDefaultCommand() {
		if (this.mode == CHASIS_MODE.TANK)
			setDefaultCommand(new TeleopTankDriveCommand());
		if (this.mode == CHASIS_MODE.ARCADE)
			setDefaultCommand(new TeleopArcadeDriveCommand());
	}

	public void mDriveTank(double l, double r) {
		// x: [-1,1];y: [-1,1]
		if (mode != CHASIS_MODE.TANK) {
			logger.warning("This chasis is not init to tank mode!");
			if (RobotMap.DEBUG) {
				logger.warning("Ignoring tank drive cmd");
				return;
			}
		}
		mPIDTankLeftCtler.setSetpoint(l);
		mPIDTankRightCtler.setSetpoint(r);
		mDifferentialDrive.tankDrive(this.execLeftSpd * RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR, // TODO
				this.execRightSpd * RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR);
	}

	public void mDriveArcade(double xSpeed, double zRot) {// TODO: TEST
		// x: [-1,1]
		if (mode != CHASIS_MODE.ARCADE) {
			logger.warning("This chasis is not init to arcade mode!");
			if (RobotMap.DEBUG) {
				logger.warning("Ignoring arcade drive cmd");
				return;
			}
		}
		mPIDArcadeSpdCtler.setSetpoint(xSpeed);
		// range for
		// rotation
		mDifferentialDrive.arcadeDrive((this.execSpd + xSpeed) * RobotMap.MOTOR.TELE_SPD_FACTOR, // TODO: set
				zRot * RobotMap.MOTOR.TELE_ANG_FACTOR);
		if (CHASIS_DEBUG) {
			logger.info("Angle" + mGyro.getAngle());
			logger.info("ArcadeDrive:" + this.execSpd * RobotMap.MOTOR.TELE_SPD_FACTOR + ", "
					+ zRot * RobotMap.MOTOR.TELE_ANG_FACTOR);
		}
	}

	public void stopDrive() {
		logger.info("Chassis Drive stops");
		mLeftMotor.stopMotor();
		mRightMotor.stopMotor();
	}

	@Override
	public void finalize() {
		mGyro.free();
	}

}
