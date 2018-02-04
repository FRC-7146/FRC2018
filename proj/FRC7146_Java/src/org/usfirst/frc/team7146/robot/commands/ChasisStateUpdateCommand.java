package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.RobotMap;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class ChasisStateUpdateCommand extends CmdBase {

	private static final java.util.logging.Logger logger = Logger.getLogger(ChasisStateUpdateCommand.class.getName());
	public static Command instance;

	public ChasisDriveSubsystem mChasis = Robot.m_ChasisDriveSubsystem;
	public Gyro mGyro = Robot.m_GyroSubsystem.mGyro;

	public ChasisStateUpdateCommand() {
		super("ChasisStateUpdateCommand", 99);
		requires(Robot.m_ChasisDriveSubsystem);
		requires(Robot.m_GyroSubsystem);

		logger.info("Instance created");
		ChasisStateUpdateCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
		update();
		mChasis.mDriveArcade(0, mChasis.execAng);

	}

	protected void update() {
		this.mChasis.actualAng = mGyro.getAngle();
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
		StraightDriveCommand.instance = null;
		logger.info("Instance destroyed");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
}
