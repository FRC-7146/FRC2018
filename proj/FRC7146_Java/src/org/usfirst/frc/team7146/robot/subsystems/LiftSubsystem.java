package org.usfirst.frc.team7146.robot.subsystems;

import org.usfirst.frc.team7146.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.d0048.NumPID;

public class LiftSubsystem extends PIDSubsystem {

	DigitalInput mUpSw = Robot.m_oi.mLimitSwitchUp;
	DigitalInput mMidSw = Robot.m_oi.mLimitSwitchDw;
	DigitalInput mDwSw = Robot.m_oi.mLimitSwitchDw;

	public LiftSubsystem(String name, NumPID pid) {
		super(name, pid.P, pid.I, pid.D, 0);
		// TODO Auto-generated constructor stub
	}

	public boolean isUp() {
		return mUpSw.get();
	}

	public boolean isMiddle() {
		return mMidSw.get();

	}

	public boolean isDown() {
		return mDwSw.get();
	}

	public void writeStatus() {
		SmartDashboard.putBoolean("Switch Up", mUpSw.get());
		SmartDashboard.putBoolean("Switch Middle", mMidSw.get());
		SmartDashboard.putBoolean("Switch Down", mDwSw.get());
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
