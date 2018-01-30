package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopArcadeDriveCommand extends Command {

	private static final java.util.logging.Logger logger = Logger.getLogger(TeleopArcadeDriveCommand.class.getName());

	public TeleopArcadeDriveCommand() {
		super("TeleopArcadeDriveCommand");
		requires(Robot.m_ChasisTrainSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double speed = Robot.m_oi.mJoystick1.getRawAxis(3);// Right x
		double ang =speed<0?Robot.m_oi.mJoystick1.getTwist():-Robot.m_oi.mJoystick1.getTwist();
		Robot.m_ChasisTrainSubsystem.mDriveArcade(speed, 
				ang);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_ChasisTrainSubsystem.stopDrive();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
