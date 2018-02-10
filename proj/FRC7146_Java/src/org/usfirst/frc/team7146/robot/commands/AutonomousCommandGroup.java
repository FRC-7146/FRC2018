package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.MatchInfo;

public class AutonomousCommandGroup extends CmdGroupBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(AutonomousCommandGroup.class.getName());

	public static char mStartPoint = 'M';
	public static int DIST_LF_MID = 10, DIST_RT_MID = 10;// TODO: Measure
	public static int DIST_BK_SW = 10;

	public AutonomousCommandGroup(int priority) {// TODO: Timeout
		this("AutonomouseCrommandGroup", priority);
		leave();
		if (MatchInfo.scale1 == 'L') {
			addSequential(new AbsoluteAngleTurnCommand(0, 90));
		} else {
			addSequential(new AbsoluteAngleTurnCommand(0, -90));
		}
		// TODO: Put cube
		addSequential(new AutoGrabCubeCommand(0.1), 5);

	}

	public void leave() {
		if (mStartPoint == 'M') {
			switch (MatchInfo.scale1) {
			case 'L':
				addSequential(new AbsoluteAngleTurnCommand(0, -90));
				addSequential(new AbsoluteAngleTurnCommand(DIST_LF_MID, -90));
				addSequential(new AbsoluteAngleTurnCommand(0, -0));
				break;
			case 'R':
				addSequential(new AbsoluteAngleTurnCommand(0, 90));
				addSequential(new AbsoluteAngleTurnCommand(DIST_RT_MID, -90));
				addSequential(new AbsoluteAngleTurnCommand(0, -0));
				break;

			default:
				logger.warning("This code should not be reached");
				break;
			}
		}

		else if (MatchInfo.scale1 == mStartPoint) {// Nothing to do
		} else {
			switch (MatchInfo.scale1) {
			case 'L':
				addSequential(new AbsoluteAngleTurnCommand(0, -90));
				addSequential(new AbsoluteAngleTurnCommand(DIST_LF_MID, -90));
				addSequential(new AbsoluteAngleTurnCommand(DIST_RT_MID, -90));
				addSequential(new AbsoluteAngleTurnCommand(0, -0));
				break;
			case 'R':
				addSequential(new AbsoluteAngleTurnCommand(0, 90));
				addSequential(new AbsoluteAngleTurnCommand(DIST_LF_MID, 90));
				addSequential(new AbsoluteAngleTurnCommand(DIST_RT_MID, 90));
				addSequential(new AbsoluteAngleTurnCommand(0, -0));
				break;

			default:
				logger.warning("This code should not be reached");
				break;
			}
		}

		new AbsoluteAngleTurnCommand(DIST_BK_SW, 0);
		addSequential(new AbsoluteAngleTurnCommand(0, 90));

	}

	public AutonomousCommandGroup(String name, int priority) {
		super(name, priority);
	}

}
