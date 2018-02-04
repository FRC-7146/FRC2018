/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import java.util.HashMap;
import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.commands.CmdBase;
import org.usfirst.frc.team7146.robot.commands.EmergencyRecoverCommand;
import org.usfirst.frc.team7146.robot.commands.EmergencyStopCommand;
import org.usfirst.frc.team7146.robot.commands.StraightDriveCommand;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private static final Logger logger = Logger.getLogger(OI.class.getName());

	public SpeedController mLeftMotor = new Spark(RobotMap.MOTOR.LEFT_MOTOR_GROUP);
	public SpeedController mRightMotor = new Spark(RobotMap.MOTOR.RIGHT_MOTOR_GROUP);
	public DifferentialDrive mDifferentialDrive = new DifferentialDrive(mLeftMotor, mRightMotor);

	public Joystick mJoystick1 = new Joystick(0);
	public Button mXboxBtnA = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_A),
			mXboxBtnB = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_B),
			mXboxBtnX = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_X),
			mXboxBtnY = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_Y),
			mBtn5 = new JoystickButton(mJoystick1, 5), mBtn6 = new JoystickButton(mJoystick1, 6),
			mBtn7 = new JoystickButton(mJoystick1, 7), mBtn8 = new JoystickButton(mJoystick1, 8);

	public ADXRS450_Gyro mGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);// TODO: TEST
	public AnalogAccelerometer mAccelerometer = new AnalogAccelerometer(5);// TODO: TEST, ACC

	public HashMap<String, CmdBase> mCommands = new HashMap<String, CmdBase>();

	public OI() {
	}

	public void mapOI() {
		/* Btn binding */
		logger.info("OI map");
		EmergencyStopCommand mEmergencyStopCommand = new EmergencyStopCommand();
		mXboxBtnB.whileHeld(mEmergencyStopCommand);
		EmergencyRecoverCommand mEmergencyRecoverCommand = new EmergencyRecoverCommand();
		mXboxBtnB.whenReleased(mEmergencyRecoverCommand);
		StraightDriveCommand mStraightDriveCommand = new StraightDriveCommand(0.9);
		mXboxBtnY.whileActive(mStraightDriveCommand);
	}
}