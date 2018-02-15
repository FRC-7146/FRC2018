package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team7146.robot.subsystems.LiftSubsystem.POSITION;

public class Lift2MidCommand extends CmdBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(Lift2MidCommand.class.getName());
	public static CmdBase instance;
	public static boolean AngleTurnCommandDebug = false;

	public POSITION dst = POSITION.DOWN;
	LiftSubsystem mLift = Robot.m_LiftSubsystem;

	public Lift2MidCommand() {
		this(POSITION.MID);
	}

	public Lift2MidCommand(POSITION p) {
		super("AngleTurnCommand", 99);
		//requires(Robot.m_LiftSubsystem);

		logger.info("Instance created");
		Lift2MidCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);

		dst = p;
	}

	@Override
	protected void initialize() {
	}

	boolean uped = false, downed = false;

	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
		switch (dst) {
		case UP:
			if (!mLift.isUp())
				mLift.up();
			break;
		case MID:
			if (mLift.isMiddle()) {
				mLift.down();
				uped = true;
			} else {
				mLift.up();
				downed = true;
			}
			break;
		case DOWN:
			if (!mLift.isDown())
				mLift.down();
			break;

		default:
			logger.warning("This code should not be reached");
			break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		switch (dst) {
		case UP:
			return mLift.isUp();
		case MID:
			return uped && downed;
		case DOWN:
			return mLift.isDown();
		default:
			logger.warning("This code should not be reached");
			break;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();
		mLift.stop();
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
