package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class EmergencyStopCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(EmergencyStopCommand.class.getName());
	public static CmdBase instance;

	public EmergencyStopCommand() {
		super("EmergencyStopCommand", -1000);
		requires(Robot.m_ChasisDriveSubsystem);

		logger.info("Instance created");
		EmergencyStopCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	public double LEFT_FACTOR_BAK = RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR,
			RIGHT_FACTOR_BAK = RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR, SPD_FACTOR_BAK = RobotMap.MOTOR.TELE_SPD_FACTOR,
			ANG_FACTOR_BAK = RobotMap.MOTOR.TELE_ANG_FACTOR;

	@Override
	protected void execute() {
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = 0;
		RobotMap.MOTOR.TELE_RIGHT_SPEED_FACTOR = 0;
		RobotMap.MOTOR.TELE_SPD_FACTOR = 0;
		RobotMap.MOTOR.TELE_ANG_FACTOR = 0;
		Robot.EMERGENCY_HALT = true;
		Robot.m_ChasisDriveSubsystem.stopDrive();
		System.exit(-1);
		logger.info("Emergency stop!");
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = this.LEFT_FACTOR_BAK;
		RobotMap.MOTOR.TELE_LEFT_SPEED_FACTOR = this.RIGHT_FACTOR_BAK;
		RobotMap.MOTOR.TELE_SPD_FACTOR = this.SPD_FACTOR_BAK;
		RobotMap.MOTOR.TELE_ANG_FACTOR = this.ANG_FACTOR_BAK;
		Robot.EMERGENCY_HALT = false;
		logger.info("Instance destroyed");
		EmergencyStopCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		// end();
	}

}
