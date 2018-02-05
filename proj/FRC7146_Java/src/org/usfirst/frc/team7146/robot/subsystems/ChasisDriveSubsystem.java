/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot.subsystems;

import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;
import org.usfirst.frc.team7146.robot.commands.ChasisStateUpdateCommand;
import org.usfirst.frc.team7146.robot.commands.TeleopArcadeDriveCommand;
import org.usfirst.frc.team7146.robot.commands.TeleopTankDriveCommand;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import io.github.d0048.NumPID;
import io.github.d0048.Utils;

public class ChasisDriveSubsystem extends Subsystem {
	private static final Logger logger = Logger.getLogger(ChasisDriveSubsystem.class.getName());
	public static boolean CHASIS_DEBUG = false;

	public static enum CHASIS_MODE {
		TANK, ARCADE
	};

	public CHASIS_MODE mode;

	SpeedController mLeftMotor = Robot.m_oi.mLeftMotor;
	SpeedController mRightMotor = Robot.m_oi.mRightMotor;
	DifferentialDrive mDifferentialDrive = Robot.m_oi.mDifferentialDrive;

	Gyro mGyro = Robot.m_oi.mGyro;
	Accelerometer mAccelerometer = Robot.m_oi.mAccelerometer;

	// PID Speed Control
	/**
	 * requested: src|requested: dst|actual: current
	 */
	PIDController mPIDArcadeSpdCtler, mPIDArcadeAngCtler;// Arcade drive mode
	public double requestedSpd = 0, requestedAng = 0;
	public double execSpd = 0, execAng = 0;
	public double actualSpd = 0, actualAng = 0;

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
		logger.info("CHasis mode: " + (mode.equals(CHASIS_MODE.ARCADE) ? "Arcade" : "Tank"));
		if (mode == CHASIS_MODE.ARCADE) {// Arcade mode pid

			this.mPIDArcadeSpdCtler = new PIDController(numPID1.P, numPID1.I, numPID1.D, new PIDSource() {

				PIDSourceType pidSourceType;

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					this.pidSourceType = pidSource;
				}

				@Override
				public double pidGet() {
					return actualSpd;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					return this.pidSourceType;
				}
			},

					new PIDOutput() {
						@Override
						public void pidWrite(double output) {
							execSpd = output;
						}
					});
			this.mPIDArcadeSpdCtler.setAbsoluteTolerance(0.5);
			this.mPIDArcadeSpdCtler.setInputRange(-1, 1);
			this.mPIDArcadeSpdCtler.setOutputRange(-1, 1);
			// this.mPIDArcadeSpdCtler.enable(); TODO: set
			// Arcade angle
			// pid--------------------------------------------------------------
			this.mPIDArcadeAngCtler = new PIDController(numPID2.P, numPID2.I, numPID2.D, new PIDSource() {

				PIDSourceType pidSourceType;

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					this.pidSourceType = pidSource;
				}

				@Override
				public double pidGet() {
					return actualAng;
				}

				@Override
				public PIDSourceType getPIDSourceType() {
					return this.pidSourceType;
				}
			}, new PIDOutput() {

				@Override
				public void pidWrite(double output) {
					execAng = output;
				}
			});
			this.mPIDArcadeAngCtler.setAbsoluteTolerance(0.5);
			this.mPIDArcadeAngCtler.setInputRange(0, 360);
			this.mPIDArcadeAngCtler.setOutputRange(-1, 1);
			this.mPIDArcadeAngCtler.setContinuous();
			this.mPIDArcadeAngCtler.enable();
			// Arcade angle
			// pid--------------------------------------------------------------
		}
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(new ChasisStateUpdateCommand());
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
		mDifferentialDrive.tankDrive(l * RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR,
				r * RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR);
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
		mPIDArcadeAngCtler.setSetpoint(this.requestedAng);
		mDifferentialDrive.arcadeDrive(xSpeed * RobotMap.MOTOR.TELE_SPD_FACTOR, // TODO: set
				this.execAng * RobotMap.MOTOR.TELE_ANG_FACTOR);
		if (CHASIS_DEBUG) {
			logger.info("Angle: " + mGyro.getAngle());
			logger.info("ArcadeDrive:" + xSpeed * RobotMap.MOTOR.TELE_SPD_FACTOR + ", "
					+ this.execAng * RobotMap.MOTOR.TELE_ANG_FACTOR);
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
