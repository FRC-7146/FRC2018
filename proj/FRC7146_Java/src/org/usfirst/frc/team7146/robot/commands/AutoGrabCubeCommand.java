package org.usfirst.frc.team7146.robot.commands;

import java.util.logging.Logger;

import org.opencv.core.Point;
import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;
import org.usfirst.frc.team7146.robot.subsystems.VisionSubsystem;

import io.github.d0048.vision.VisualTarget;

public class AutoGrabCubeCommand extends CmdBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(AutoGrabCubeCommand.class.getName());
	public static CmdBase instance;
	public static boolean AutoGrabCubeDebug = true;

	public VisionSubsystem mVision = Robot.m_VisionSubsystem;
	ChasisDriveSubsystem mChasis = Robot.m_ChasisDriveSubsystem;

	double spd = 0;

	public AutoGrabCubeCommand(double spd) {
		super("AutoGrabCubeCommand", 99);
		requires(Robot.m_ChasisDriveSubsystem);
		requires(Robot.m_GyroSubsystem);
		requires(Robot.m_VisionSubsystem);

		logger.info("Instance created");
		AutoGrabCubeCommand.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
		this.spd = spd;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	VisualTarget lastTarget = null;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (!Robot.cmdCanRun(this))
			return;
		try {
			VisualTarget target = mVision.findCube();
			if (target != null) {
				lastTarget = target;
				move2Target(target);
			} else {
				move2Target(lastTarget);
			}
		} catch (Exception e) {
			logger.throwing("Err calling find cube", "auto grab cube cmd", e);
			e.printStackTrace();
		}

	}

	public void move2Target(VisualTarget v) {
		if (v == null)
			return;
		Point c = v.center;
		if (c.y > 160 / 2) {
			mChasis.mArcadeRequest(spd, 0.2);
		} else {
			mChasis.mArcadeRequest(spd, -0.2);
		}
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
		mChasis.mArcadeRequest(0, 0);
		logger.info("Instance destroyed");
		AutoGrabCubeCommand.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
