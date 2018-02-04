/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import java.util.logging.Logger;

import io.github.d0048.NumPID;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	private static final java.util.logging.Logger logger = Logger.getLogger(RobotMap.class.getName());
	public static boolean DEBUG = true;

	public static class MOTOR {
		/*
		 * DifferentialDrive Motor Group Port Number, now using two set of spark
		 * 
		 * TODO: Change to spark mixed spx
		 */
		public static final int LEFT_MOTOR_GROUP = 0;
		public static final int RIGHT_MOTOR_GROUP = 1;
		public static final NumPID TANK_NUM_PID = new NumPID(2, 0, 0), ARCADE_SPD_NUM_PID = TANK_NUM_PID,
				ARCADE_ANG_NUM_PID = new NumPID(2, 0, 0), LIFT_NUM_PID = new NumPID(2, 0, 0);
		/*
		 * Motor Speed Factor
		 * 
		 * Do not raise more than 1
		 */
		public static double TELE_LEFT_SPEED_FACTOR = 0.7;
		public static double TELE_RIGHT_SPEED_FACTOR = 0.7;
		public static double TELE_SPD_FACTOR = 0.7;
		public static double TELE_ANG_FACTOR = 0.5;
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

	public enum STATUS {
		STAT_AUTO, STAT_TELEOP, STAT_HALT, STAT_ERR
	};

	public static STATUS mStatus;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

}
