/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot.subsystems;

import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;

import org.omg.PortableServer.THREAD_POLICY_ID;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	PIDController mPIDArcadeAngCtler;// Arcade drive mode
	public double requestedSpd = 0, requestedAng = 0;
	public double execAng = 0;

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
		this.mDifferentialDrive.setSafetyEnabled(false);
		logger.info("CHasis mode: " + (mode.equals(CHASIS_MODE.ARCADE) ? "Arcade" : "Tank"));
		if (mode == CHASIS_MODE.ARCADE) {// Arcade mode pid
			// Arcade angle
			// pid--------------------------------------------------------------
			this.mPIDArcadeAngCtler = new PIDController(numPID2.P, numPID2.I, numPID2.D, new PIDSource() {

				PIDSourceType pidSourceType = PIDSourceType.kDisplacement;

				@Override
				public void setPIDSourceType(PIDSourceType pidSource) {
					this.pidSourceType = pidSource;
				}

				@Override
				public double pidGet() {
					return Robot.m_GyroSubsystem.getAngle();
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
			this.mPIDArcadeAngCtler.setAbsoluteTolerance(15);
			this.mPIDArcadeAngCtler.setInputRange(-180, 180);
			this.mPIDArcadeAngCtler.setOutputRange(-1, 1);
			this.mPIDArcadeAngCtler.setContinuous(true);
			this.mPIDArcadeAngCtler.enable();
			// Arcade angle
			// pid--------------------------------------------------------------
		}
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(new TeleopArcadeDriveCommand());
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
		if (CHASIS_DEBUG) {
			logger.info("ArcadeDrive:" + xSpeed + ", " + zRot);
			if (Math.abs(zRot) > 1 || Math.abs(xSpeed) > 1) {
				logger.warning("Threadhold reached, cancelled");
				return;
			}
		}
		if (Robot.EMERGENCY_HALT) {
			logger.warning("Emergency stop detected thus cancel");
			return;
		}
		mDifferentialDrive.arcadeDrive(xSpeed * RobotMap.MOTOR.TELE_SPD_FACTOR, // TODO: set
				zRot * RobotMap.MOTOR.TELE_ANG_FACTOR);
		writeStatus();
	}

	public void mArcadeRequest(double spd, double rot) {
		this.requestedSpd = spd;
		if (this.requestedAng + rot > 180) {
			this.requestedAng = (this.requestedAng + rot - 360);
		} else if (this.requestedAng + rot < -180) {
			this.requestedAng = (this.requestedAng + rot + 360);
		} else {

			requestedAng += rot;
		}
		mPIDArcadeAngCtler.setSetpoint(this.requestedAng);
		SmartDashboard.putNumber("Requested Angle: ", requestedAng);
		SmartDashboard.putNumber("Requested speed", requestedSpd);
		if (CHASIS_DEBUG) {
			logger.info("Got an request of" + spd + ", " + rot);
		}
		writeStatus();
	}

	public void mArcadeForceDrive(double spd, double rot) {
		writeStatus();
		resetAngleState();
		writeStatus();
		this.mDriveArcade(spd, rot);
		writeStatus();
		resetAngleState();
		writeStatus();
	}

	public void mArcadeDispatch() {
		this.mDriveArcade(this.requestedSpd, this.execAng);
		writeStatus();
	}

	public void writeStatus() {
		SmartDashboard.putNumber("Requested Angle: ", requestedAng);
		SmartDashboard.putNumber("Requested speed", requestedSpd);
		SmartDashboard.putNumber("Exec Angle: ", execAng);
		SmartDashboard.putNumber("Measured angle", Robot.m_GyroSubsystem.getAngle());
		SmartDashboard.putNumber("Gyro ang", this.mGyro.getAngle());
	}

	public void resetAngleState() {
		this.requestedAng = Robot.m_GyroSubsystem.getAngle();
		this.mPIDArcadeAngCtler.setSetpoint(Robot.m_GyroSubsystem.getAngle());
		writeStatus();
		if (CHASIS_DEBUG) {
			logger.info("Chasis angle reset");
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
