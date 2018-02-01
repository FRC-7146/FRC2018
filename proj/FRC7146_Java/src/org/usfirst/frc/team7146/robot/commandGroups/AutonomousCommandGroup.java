package org.usfirst.frc.team7146.robot.commandGroups;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandGroup extends CommandGroup {
	private static final java.util.logging.Logger logger = Logger.getLogger(AutonomousCommandGroup.class.getName());
	public static Command instance;

	public AutonomousCommandGroup() {
		super("AutonomousCMD");
		requires(Robot.m_ChasisDriveSubsystem);
		// TODO: Add sequential/parallel cmd
		logger.info("Instance created");
		AutonomousCommandGroup.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
	}

	@Override
	protected void end() {
		logger.info("Instance destroyed");
		AutonomousCommandGroup.instance = null;
		Robot.m_oi.mCommands.remove(this.getName());
	}
}
