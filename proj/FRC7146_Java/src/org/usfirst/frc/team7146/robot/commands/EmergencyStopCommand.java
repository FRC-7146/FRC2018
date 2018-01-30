package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class EmergencyStopCommand extends Command {

	private static final java.util.logging.Logger logger = Logger.getLogger(EmergencyStopCommand.class.getName());

	public EmergencyStopCommand() {
		super("EmergencyStopCommand");
		requires(Robot.m_ChasisTrainSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	double LEFT_FACTOR_BAK = RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR,
			RIGHT_FACTOR_BAK = RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR;

	@Override
	protected void execute() {
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = 0;
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = 0;
		logger.info("Emergency stop!");
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
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = this.LEFT_FACTOR_BAK;
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = this.RIGHT_FACTOR_BAK;

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
