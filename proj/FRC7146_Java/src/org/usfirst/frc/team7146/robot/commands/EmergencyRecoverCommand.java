package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class EmergencyRecoverCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(EmergencyRecoverCommand.class.getName());
	public static CmdBase instance;

	public EmergencyRecoverCommand() {
		super("EmergencyRecoverCommand", -1000);
		requires(Robot.m_ChasisDriveSubsystem);

		logger.info("Instance created");
		EmergencyRecoverCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (EmergencyStopCommand.instance != null) {
			logger.info("Recover");
			RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = ((EmergencyStopCommand) EmergencyStopCommand.instance).LEFT_FACTOR_BAK;
			RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR = ((EmergencyStopCommand) EmergencyStopCommand.instance).RIGHT_FACTOR_BAK;
			EmergencyRecoverCommand.instance.cancel();
			Robot.EMERGENCY_HALT = false;
			this.cancel();
		} else {
			logger.info("Failed to Recover: Can not find stop instance");
			this.cancel();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();
		Robot.m_ChasisDriveSubsystem.stopDrive();
		logger.info("Instance destroyed");
		// EmergencyStopCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run

}
