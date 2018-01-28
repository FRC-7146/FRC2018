/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public Joystick mJoystick1 = new Joystick(0);
	public Button mBtn1 = new JoystickButton(mJoystick1, 1), mBtn2 = new JoystickButton(mJoystick1, 2),
			mBtn3 = new JoystickButton(mJoystick1, 3), mBtn4 = new JoystickButton(mJoystick1, 4),
			mBtn5 = new JoystickButton(mJoystick1, 5), mBtn6 = new JoystickButton(mJoystick1, 6),
			mBtn7 = new JoystickButton(mJoystick1, 7), mBtn8 = new JoystickButton(mJoystick1, 8);

	public OI() {
		/* Possible btn binding */
		// mBtn.whenPressed(new ExampleCommand());
		// mBtn.whileHeld(new ExampleCommand());
		// mBtn.whenReleased(new ExampleCommand());
	}
}