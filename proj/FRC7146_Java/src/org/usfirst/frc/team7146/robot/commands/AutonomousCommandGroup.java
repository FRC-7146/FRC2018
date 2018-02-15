package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder;

import org.usfirst.frc.team7146.robot.MatchInfo;
import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;
import org.usfirst.frc.team7146.robot.subsystems.GyroSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommandGroup extends CmdGroupBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(AutonomousCommandGroup.class.getName());

	public static char mStartPoint = 'M';
	public static double DIST_LF_MID = 3.048 * GyroSubsystem.DISP_2_METER, // 3.048
			DIST_RT_MID = 2 * GyroSubsystem.DISP_2_METER;// 1.83

	public static double DIST_BK_SW = 4.267 * GyroSubsystem.DISP_2_METER;

	public AutonomousCommandGroup(int priority) {// TODO: Timeout
		this("AutonomouseCrommandGroup", priority);

	}

	public AutonomousCommandGroup(String name, int priority) {
		super(name, priority);
		// addSequential(new AbsoluteAngleTurnCommand(-1*GyroSubsystem.DISP_2_METER,
		// 0),2);

		addParallel(liftUp, 5);
		leave();
		if (MatchInfo.scale1 == 'L') {
			addSequential(new AbsoluteAngleTurnCommand(0, 90), 2);
			addSequential(new AbsoluteAngleTurnCommand(1 * GyroSubsystem.DISP_2_METER, 90), 1);
		} else {
			addSequential(new AbsoluteAngleTurnCommand(0, -90), 2);
			addSequential(new AbsoluteAngleTurnCommand(1 * GyroSubsystem.DISP_2_METER, -90), 1);
		}

		addParallel(collectorOut, 0.5);
		// TODO: Put cube
		addParallel(collectorIn, 0.5);
		// addSequential(new AutoGrabCubeCommand(0.1), 5);
	}

	public void leave() {
		if (mStartPoint == 'M') {
			switch (MatchInfo.scale1) {
			case 'L':
				addSequential(new AbsoluteAngleTurnCommand(0, -90), 2);
				addSequential(new AbsoluteAngleTurnCommand(DIST_LF_MID, -90), 3);
				addSequential(new AbsoluteAngleTurnCommand(0, -0), 2);
				break;
			case 'R':
				addSequential(new AbsoluteAngleTurnCommand(0, 90), 2);
				addSequential(new AbsoluteAngleTurnCommand(DIST_RT_MID, -90), 3);
				addSequential(new AbsoluteAngleTurnCommand(0, -0), 2);
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
				addSequential(new AbsoluteAngleTurnCommand(0, -90), 2);
				addSequential(new AbsoluteAngleTurnCommand(DIST_LF_MID, -90), 3);
				addSequential(new AbsoluteAngleTurnCommand(DIST_RT_MID, -90), 3);
				addSequential(new AbsoluteAngleTurnCommand(0, -0), 2);
				break;
			case 'R':
				addSequential(new AbsoluteAngleTurnCommand(0, 90), 2);
				addSequential(new AbsoluteAngleTurnCommand(DIST_LF_MID, 90), 3);
				addSequential(new AbsoluteAngleTurnCommand(DIST_RT_MID, 90), 3);
				addSequential(new AbsoluteAngleTurnCommand(0, -0), 2);
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
			Robot.m_oi.collectorWheelMotors.arcadeDrive(0.9, 0);
			;
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
			Robot.m_oi.collectorWheelMotors.arcadeDrive(-0.9, 0);
			;
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
