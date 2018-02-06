package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;

public class AngleTurnCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(AngleTurnCommand.class.getName());
	public static CmdBase instance;
	public static boolean AngleTurnCommandDebug = true;

	private double ang = 0;

	public AngleTurnCommand(double ang) {
		super("AngleTurnCommand", 99);
		requires(Robot.m_ChasisDriveSubsystem);
		requires(Robot.m_GyroSubsystem);

		logger.info("Instance created");
		AngleTurnCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);

		this.ang = ang;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.m_ChasisDriveSubsystem.mArcadeRequest(0, ang);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
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
		Robot.m_ChasisDriveSubsystem.resetAngleState();
		logger.info("Instance destroyed");
		AngleTurnCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
