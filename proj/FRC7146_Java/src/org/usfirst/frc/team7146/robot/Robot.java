/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.RobotMap.STATUS;
import org.usfirst.frc.team7146.robot.commands.CmdBase;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;
import org.usfirst.frc.team7146.robot.subsystems.GyroSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	private static final java.util.logging.Logger logger = Logger.getLogger(Robot.class.getName());
	public static ChasisDriveSubsystem m_ChasisDriveSubsystem;
	public static GyroSubsystem m_GyroSubsystem;
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_ChasisDriveSubsystem = new ChasisDriveSubsystem(ChasisDriveSubsystem.CHASIS_MODE.ARCADE,
				RobotMap.MOTOR.ARCADE_SPD_NUM_PID, RobotMap.MOTOR.ARCADE_ANG_NUM_PID);
		m_GyroSubsystem = new GyroSubsystem();
		SmartDashboard.putData("Auto mode", m_chooser);
		m_oi.mapOI();// execute at the end to make sure all other subsystems initialized
		// chooser.addObject("My Auto", new MyAutoCommand());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		RobotMap.mStatus = STATUS.STAT_HALT;
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		RobotMap.mStatus = STATUS.STAT_AUTO;
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */
		// schedule the autonomous command (example)
	}

	@Override
	public void autonomousPeriodic() {
		if (!RobotMap.mStatus.equals(STATUS.STAT_ERR))
			Scheduler.getInstance().run();
		else {
			logger.warning("Err in Periodic");
			if (!RobotMap.DEBUG) {// Stop running for debug
				logger.warning("Ignoring");
				Scheduler.getInstance().run();
			}
		}
	}

	@Override
	public void teleopInit() {
		RobotMap.mStatus = STATUS.STAT_TELEOP;
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		if (!RobotMap.mStatus.equals(STATUS.STAT_ERR))
			Scheduler.getInstance().run();
		else {
			logger.warning("Err in Teleop");
			if (!RobotMap.DEBUG) {// Stop running for debug
				logger.warning("Ignoring");
				Scheduler.getInstance().run();
			}
		}
	}

	@Override
	public void testPeriodic() {
		if (!RobotMap.mStatus.equals(STATUS.STAT_ERR))
			Scheduler.getInstance().run();
		else {
			logger.warning("Err in Test");
			if (!RobotMap.DEBUG) {// Stop running for debug
				logger.warning("Ignoring");
				Scheduler.getInstance().run();
			}
		}
	}

	public static boolean cmdCanRun(CmdBase cmd) {
		for (String k : m_oi.mCommands.keySet()) {
			if (m_oi.mCommands.get(k).priority < cmd.priority && m_oi.mCommands.get(k).isRunning()) {
				return false;
			}
		}
		return true;
	}
}
