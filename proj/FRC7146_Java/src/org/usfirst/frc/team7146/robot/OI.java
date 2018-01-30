/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.commands.EmergencyStopCommand;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private static final Logger logger = Logger.getLogger(OI.class.getName());

	public Joystick mJoystick1 = new Joystick(0);
	public Button mXboxBtnA = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_A),
			mXboxBtnB = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_B),
			mXboxBtnX = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_X),
			mXboxBtnY = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_Y),
			mBtn5 = new JoystickButton(mJoystick1, 5), mBtn6 = new JoystickButton(mJoystick1, 6),
			mBtn7 = new JoystickButton(mJoystick1, 7), mBtn8 = new JoystickButton(mJoystick1, 8);
	public Gyro mGyro = new AnalogGyro(0);// TODO: TEST
	// TODO: ACCELERATEMETER

	public OI() {
	}

	public void mapOI() {
		EmergencyStopCommand mEmergencyStopCommand = new EmergencyStopCommand();
		mXboxBtnB.whileHeld(mEmergencyStopCommand);

		/* Possible btn binding */
		// mBtn.whenPressed(new ExampleCommand());
		// mBtn.whileHeld(new ExampleCommand());
		// mBtn.whenReleased(new ExampleCommand());

	}
}