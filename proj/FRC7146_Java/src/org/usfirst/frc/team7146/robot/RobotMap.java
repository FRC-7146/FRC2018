/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import java.util.logging.Logger;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	private static final java.util.logging.Logger logger = Logger.getLogger(RobotMap.class.getName());

	public static class MOTOR {
		/*
		 * DifferentialDrive Motor Group Port Number, now using two set of spark
		 * 
		 * TODO: Change to spark mixed spx
		 */
		public static final int LEFT_MOTOR_GROUP = 0;
		public static final int RIGHT_MOTOR_GROUP = 1;
		public static final double[] MOTOR_PID = { 2., 0., 0. };
		/*
		 * Teleop TankDrive Speed Factor
		 * 
		 * Do not raise more than 1
		 */
		public static double TELE_LEFT_SPEED_FACTOR = 0.1;
		public static double TELE_RIGHT_SPEED_FACTOR = 0.1;
	}

	public static class JOYSTICK {
		// TODO: Reference only
		public static final int NUM_XBOX_X = 1;
		public static final int NUM_XBOX_A = 2;
		public static final int NUM_XBOX_B = 3;
		public static final int NUM_XBOX_Y = 4;
		public static final int NUM_XBOX_LB = 5;
		public static final int NUM_XBOX_RB = 6;
		public static final int NUM_XBOX_LT = 7;
		public static final int NUM_XBOX_RT = 8;
		public static final int NUM_XBOX_LEFT_STICK_BTN = 9;
		public static final int NUM_XBOX_RIGHT_STICK_BTN = 10;
	}

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

}
