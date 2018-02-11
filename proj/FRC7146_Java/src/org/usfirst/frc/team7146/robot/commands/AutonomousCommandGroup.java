package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder;

import org.usfirst.frc.team7146.robot.MatchInfo;
import org.usfirst.frc.team7146.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommandGroup extends CmdGroupBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(AutonomousCommandGroup.class.getName());

	public static char mStartPoint = 'M';
	public static int DIST_LF_MID = 10, DIST_RT_MID = 10;// TODO: Measure
	public static int DIST_BK_SW = 10;

	public AutonomousCommandGroup(int priority) {// TODO: Timeout
		this("AutonomouseCrommandGroup", priority);

	}

	public AutonomousCommandGroup(String name, int priority) {
		super(name, priority);
		addParallel(liftUp, 2);
		leave();
		if (MatchInfo.scale1 == 'L') {
			addSequential(new AbsoluteAngleTurnCommand(0, 90));
		} else {
			addSequential(new AbsoluteAngleTurnCommand(0, -90));
		}
		addParallel(collectorOut, 0.5);
		// TODO: Put cube
		addParallel(collectorIn, 0.5);
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

	Command liftUp = new Command() {
		@Override
		protected void initialize() {
			if (!Robot.m_LiftSubsystem.isUp()) {
				Robot.m_LiftSubsystem.up();
			} else {
				Robot.m_LiftSubsystem.stop();
			}
		}

		@Override
		protected void end() {
			Robot.m_LiftSubsystem.stop();
		}

		@Override
		protected void interrupted() {
			end();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}
	};
	Command liftDown = new Command() {
		@Override
		protected void initialize() {
			// if (!Robot.m_LiftSubsystem.isUp()) {
			Robot.m_LiftSubsystem.down();
			// } else {
			// Robot.m_LiftSubsystem.stop();
			// }
		}

		@Override
		protected void end() {
			Robot.m_LiftSubsystem.stop();
		}

		@Override
		protected void interrupted() {
			end();
		}

		@Override
		protected boolean isFinished() {
			return Robot.m_LiftSubsystem.isDown();
		}
	};
	Command collectorIn = new Command() {
		@Override
		protected void initialize() {
			Robot.m_oi.collectorWheelMotors.set(0.9);
		}

		@Override
		protected void end() {
			Robot.m_oi.collectorWheelMotors.stopMotor();
		}

		@Override
		protected void interrupted() {
			end();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}
	};
	Command collectorOut = new Command() {

		@Override
		protected void initialize() {
			Robot.m_oi.collectorWheelMotors.set(-0.9);
		}

		@Override
		protected void end() {
			Robot.m_oi.collectorWheelMotors.stopMotor();
		}

		@Override
		protected void interrupted() {
			end();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}
	};
}
