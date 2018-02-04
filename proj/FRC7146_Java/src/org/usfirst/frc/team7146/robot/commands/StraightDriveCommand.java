package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class StraightDriveCommand extends CmdBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(StraightDriveCommand.class.getName());
	public static Command instance;
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
		this.ang = Robot.m_GyroSubsystem.mGyro.getAngle();
		if (StraightDriveDebug)
			logger.info("Angle marked");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
		double current = Robot.m_GyroSubsystem.mGyro.getAngle();
		double dst = this.ang;
		double ofs = dst - current;
		double angle = ofs / 360;
		if (Math.abs(ofs) > tol) {
			angle = ofs / 5;
		}
		Robot.m_ChasisDriveSubsystem.mDriveArcade(this.spd, angle);
		if (StraightDriveDebug)
			logger.info("Straight Drive: \n current: " + current + "\ndst: " + dst + "\nangle: " + angle);
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
		StraightDriveCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
