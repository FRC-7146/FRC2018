package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;
import org.usfirst.frc.team7146.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopArcadeDriveCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(TeleopArcadeDriveCommand.class.getName());
	public static CmdBase instance;
	public static boolean TeleArcadeDriveDebug = false;

	private Joystick mJoystick = Robot.m_oi.mJoystick1;
	private ChasisDriveSubsystem mChasis = Robot.m_ChasisDriveSubsystem;
	private LiftSubsystem mLift = Robot.m_LiftSubsystem;

	public TeleopArcadeDriveCommand() {
		super("TeleopArcadeDriveCommand", 100);
		// DO NOT INTERUPT
		requires(Robot.m_ChasisDriveSubsystem);

		logger.info("Instance created");
		TeleopArcadeDriveCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
	}

	@Override
	protected void initialize() {
	}

	boolean isReturning = false;

	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this)) {
			logger.info("drive override");
			return;
		}
		double speed = Robot.m_oi.mJoystick1.getRawAxis(3);// Right x
		double ang = Robot.m_oi.mJoystick1.getTwist();
		double pov = mJoystick.getPOV();
		SmartDashboard.putNumber("POV", pov);

		// if (ChasisDriveSubsystem.LOCK_OVERRIDE) {// not operating

		// if (!DriverStation.getInstance().isOperatorControl()) {// otherwise will be
		// handle by operator
		// ChasisDriveSubsystem.LOCK_OVERRIDE = false;
		// }

		// } else {// operating

		// if (!DriverStation.getInstance().isOperatorControl()) {// otherwise will be
		// handle by operator
		// ChasisDriveSubsystem.LOCK_OVERRIDE = true;
		// }

		if (ChasisDriveSubsystem.LOCK_OVERRIDE) {
			mChasis.mArcadeForceDrive(speed, ang);
			isReturning = true;
		}
		// }

		if (isReturning) {
			mChasis.resetAngleState();
			isReturning = false;
		}

		if (pov != -1) {
			mChasis.mArcadeRequestAbsolute(0, pov);
		}

		if (Robot.m_oi.mXboxBtnY.get()) {// up
			if (mLift.isUp()) {
				mLift.stop();
			} else {
				mLift.up();
			}
		} else if (Robot.m_oi.mXboxBtnX.get()) {// stop
			mLift.stop();
		} else if (Robot.m_oi.mXboxBtnA.get()) {// down
			/*
			 * if (mLift.isDown()) { mLift.stop(); } else { mLift.down(); }
			 */
			mLift.down();
		} else {
			if (mLift.isDown() || mLift.isMiddle() || mLift.isUp()) {
				mLift.stop();
			}
		}

		if (Robot.m_oi.mXboxBtnRb.get()) {
			Robot.m_oi.collectorWheelMotors.set(-0.9);
		} else if (Robot.m_oi.mXboxBtnRt.get()) {
			if (Robot.m_oi.collectorWheelMotors.get() != 0) {
				Robot.m_oi.collectorWheelMotors.stopMotor();
			} else {
				Robot.m_oi.collectorWheelMotors.set(0.9);
			}
		}

		if (DriverStation.getInstance().isOperatorControl()) {
			if (Robot.m_oi.mXboxBtnLt.get()) {
				ChasisDriveSubsystem.LOCK_OVERRIDE = false;
			} else {
				ChasisDriveSubsystem.LOCK_OVERRIDE = true;
				mChasis.resetAngleState();
			}
		} else {
			if (Robot.m_oi.mXboxBtnLt.get()) {
				ChasisDriveSubsystem.LOCK_OVERRIDE = true;
				mChasis.resetAngleState();
			} else {
				ChasisDriveSubsystem.LOCK_OVERRIDE = false;
			}
		}

		if (Robot.m_oi.mXboxBtnLb.get()) {
			mChasis.mArcadeRequest(10, 0);
		}

		if (TeleArcadeDriveDebug) {
			logger.info("Requested: " + speed + ", " + ang + ", " + pov);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		super.end();
		mChasis.stopDrive();
		logger.info("Instance destroyed");
		TeleopArcadeDriveCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
