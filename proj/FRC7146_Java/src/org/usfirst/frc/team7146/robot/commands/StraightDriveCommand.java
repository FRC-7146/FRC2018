package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import io.github.d0048.Utils;

public class StraightDriveCommand extends CmdBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(StraightDriveCommand.class.getName());
	public static CmdBase instance;
	public static boolean StraightDriveDebug = true;

	private double spd = 0, ang = 0, tol = 3;

	public StraightDriveCommand(double spd) {
		super("StraightDriveCommand", 99);
		requires(Robot.m_ChasisDriveSubsystem);
		requires(Robot.m_GyroSubsystem);

		logger.info("Instance created");
		StraightDriveCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);

		this.spd = spd;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		this.ang = Robot.m_GyroSubsystem.getAngle();
		if (StraightDriveDebug)
			logger.info("Angle marked");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
		/*
		 * double angle = Utils.AngleOffsetCal(Robot.m_GyroSubsystem.mGyro.getAngle(),
		 * this.ang); angle = Utils.Ang2tanh(angle, this.tol);
		 * Robot.m_ChasisDriveSubsystem.mDriveArcade(this.spd, angle); if
		 * (StraightDriveDebug) logger.info("Straight Drive: \nangle: " + angle);
		 */
		Robot.m_ChasisDriveSubsystem.mArcadeRequest(this.spd, 0);
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
		Robot.m_ChasisDriveSubsystem.mArcadeRequest(0, 0);
		logger.info("Instance destroyed");
		StraightDriveCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
