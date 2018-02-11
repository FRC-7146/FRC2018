package org.usfirst.frc.team7146.robot.subsystems;

import org.usfirst.frc.team7146.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.d0048.NumPID;

public class LiftSubsystem extends Subsystem {
	public static enum POSITION {
		UP, MID, DOWN
	};

	DigitalInput mUpSw = Robot.m_oi.mLimitSwitchUp;
	DigitalInput mMidSw = Robot.m_oi.mLimitSwitchDw;
	DigitalInput mDwSw = Robot.m_oi.mLimitSwitchDw;
	SpeedController mMotor = Robot.m_oi.liftMotor;

	public LiftSubsystem() {
		super("LiftSubsystem");
		// TODO Auto-generated constructor stub
	}

	public void up() {
		mMotor.set(0.98);
	}

	public void down() {
		mMotor.set(-0.98);
	}

	public void stop() {
		mMotor.stopMotor();
	}

	public boolean isUp() {
		return !mUpSw.get();
	}

	public boolean isMiddle() {
		return !mMidSw.get();

	}

	public boolean isDown() {
		return !mDwSw.get();
	}

	public void writeStatus() {
		SmartDashboard.putBoolean("Switch Up", mUpSw.get());
		SmartDashboard.putBoolean("Switch Middle", mMidSw.get());
		SmartDashboard.putBoolean("Switch Down", mDwSw.get());
		SmartDashboard.putNumber("Switch Speed", mMotor.get());
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
