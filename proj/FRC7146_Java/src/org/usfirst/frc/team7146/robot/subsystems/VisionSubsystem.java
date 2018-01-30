package org.usfirst.frc.team7146.robot.subsystems;


import java.util.logging.Logger;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionSubsystem extends Subsystem {

	private static final java.util.logging.Logger logger = Logger.getLogger(VisionSubsystem.class.getName());
	private CameraServer mCameraServer = CameraServer.getInstance();

	public VisionSubsystem() {
		this("VisionSubSystem");
	}

	public VisionSubsystem(String name) {
		super(name);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO
	}

}
