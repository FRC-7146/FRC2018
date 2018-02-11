/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot;

import java.util.HashMap;
import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.commands.AngleTurnCommand;
import org.usfirst.frc.team7146.robot.commands.AutoGrabCubeCommand;
import org.usfirst.frc.team7146.robot.commands.CmdBase;
import org.usfirst.frc.team7146.robot.commands.EmergencyRecoverCommand;
import org.usfirst.frc.team7146.robot.commands.EmergencyStopCommand;
import org.usfirst.frc.team7146.robot.commands.Lift2MidCommand;
import org.usfirst.frc.team7146.robot.commands.StraightDriveCommand;
import org.usfirst.frc.team7146.robot.subsystems.ChasisDriveSubsystem;
import org.usfirst.frc.team7146.robot.subsystems.LiftSubsystem.POSITION;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private static final Logger logger = Logger.getLogger(OI.class.getName());

	public SpeedController mLeftMotor = new Spark(RobotMap.MOTOR.LEFT_MOTOR_GROUP);
	public SpeedController mRightMotor = new Spark(RobotMap.MOTOR.RIGHT_MOTOR_GROUP);
	public DifferentialDrive mDifferentialDrive = new DifferentialDrive(mLeftMotor, mRightMotor);

	public SpeedController collectorWheelMotors = new Spark(RobotMap.MOTOR.COLLECTOR_WHEEL_PORT);
	public SpeedController liftMotor = new Spark(RobotMap.MOTOR.LIFT_PORT);

	public Joystick mJoystick1 = new Joystick(0);
	public Button mXboxBtnA = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_A),
			mXboxBtnB = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_B),
			mXboxBtnX = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_X),
			mXboxBtnY = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_Y),
			mXboxBtnLb = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_LB),
			mXboxBtnRb = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_RB),
			mXboxBtnLt = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_LT),
			mXboxBtnRt = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_RT),
			mXboxBtnLftStk = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_LEFT_STICK_BTN),
			mXboxBtnRghtStk = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_RIGHT_STICK_BTN),
			mXboxBtnBack = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_BACK),
			mXboxBtnStart = new JoystickButton(mJoystick1, RobotMap.JOYSTICK.NUM_XBOX_START);

	public ADXRS450_Gyro mGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	// public Accelerometer mAccelerometer = new ADXL345_I2C(I2C.Port.kOnboard,
	// Accelerometer.Range.k4G, 0x3A);
	public Accelerometer mAccelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G);// TODO: TEST, ACC
	public TalonSRX mTalon1 = new TalonSRX(0);
	public DigitalInput mLimitSwitchUp = new DigitalInput(RobotMap.SENSOR.NUM_LSW_UP);
	public DigitalInput mLimitSwitchMID = new DigitalInput(RobotMap.SENSOR.NUM_LSW_MID);
	public DigitalInput mLimitSwitchDw = new DigitalInput(RobotMap.SENSOR.NUM_LSW_DW);

	public HashMap<String, CmdBase> mCommands = new HashMap<String, CmdBase>();

	public OI() {
		// mTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
		// 0, 10);
		// mTalon1.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
		mTalon1.set(ControlMode.PercentOutput, 0.0); // Disable output coz not needed
	}

	public void mapOI() {
		/* Btn binding */
		logger.info("OI map");
		EmergencyStopCommand mEmergencyStopCommand = new EmergencyStopCommand();
		mXboxBtnB.whileHeld(mEmergencyStopCommand);
		mXboxBtnStart.whileHeld(new AutoGrabCubeCommand(0.6));

	}
}