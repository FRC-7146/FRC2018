package org.usfirst.frc.team7146.robot.commandGroups;

import org.apache.logging.log4j.LogManager;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {
	private static final org.apache.logging.log4j.Logger logger = LogManager
			.getLogger(AutonomousCommand.class.getName());

	public AutonomousCommand() {
		super("AutonomousCMD");
		// TODO: Add sequential/parallel cmd
	}
}
