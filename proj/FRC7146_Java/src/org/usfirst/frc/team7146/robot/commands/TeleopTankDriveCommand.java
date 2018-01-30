/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class TeleopTankDriveCommand extends Command {
	private static final java.util.logging.Logger logger = Logger.getLogger(TeleopTankDriveCommand.class.getName());
	public TeleopTankDriveCommand() {
		super("TeleopTankDriveCommand");
		requires(Robot.m_ChasisTrainSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.m_ChasisTrainSubsystem.mDriveTank(Robot.m_oi.mJoystick1);
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
