package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;

import edu.wpi.first.wpilibj.Joystick;

public class AbsoluteAngleTurnCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(AbsoluteAngleTurnCommand.class.getName());
	public static CmdBase instance;
	public static boolean AbsAngleTurnCommandDebug = true;

	private double absAng = 0;
	private Joystick mJoystick = Robot.m_oi.mJoystick1;
	private ChasisDriveSubsystem mChassis = Robot.m_ChasisDriveSubsystem;

	public AbsoluteAngleTurnCommand(double absAng) {
		super("AbsoluteAngleTurnCommand", 99);
		requires(Robot.m_ChasisDriveSubsystem);
		requires(Robot.m_GyroSubsystem);

		logger.info("Instance created");
		AbsoluteAngleTurnCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);

		this.absAng = absAng;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
		mChassis.mArcadeRequestAbsolute(0, absAng);
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
		AbsoluteAngleTurnCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
