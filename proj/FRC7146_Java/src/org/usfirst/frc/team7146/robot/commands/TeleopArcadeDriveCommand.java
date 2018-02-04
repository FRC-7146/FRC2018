package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopArcadeDriveCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(TeleopArcadeDriveCommand.class.getName());
	public static Command instance;

	public TeleopArcadeDriveCommand() {
		super("TeleopArcadeDriveCommand", 100);
		requires(Robot.m_ChasisDriveSubsystem);

		logger.info("Instance created");
		TeleopArcadeDriveCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this)) {
			logger.info("drive override");
			return;
		}
		double speed = Robot.m_oi.mJoystick1.getRawAxis(3);// Right x
		double ang = Robot.m_oi.mJoystick1.getTwist();
		Robot.m_ChasisDriveSubsystem.requestedAng=ang*100;
		Robot.m_ChasisDriveSubsystem.mDriveArcade(speed, Robot.m_ChasisDriveSubsystem.execAng);

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_ChasisDriveSubsystem.stopDrive();
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
