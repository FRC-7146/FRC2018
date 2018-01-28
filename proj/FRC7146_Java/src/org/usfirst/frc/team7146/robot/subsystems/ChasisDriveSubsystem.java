/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7146.robot.subsystems;

import org.usfirst.frc.team7146.robot.RobotMap;
import org.usfirst.frc.team7146.robot.commands.TeleopTankDriveCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class ChasisDriveSubsystem extends PIDSubsystem {

	Spark mLeftMotor = new Spark(RobotMap.LEFT_MOTOR_GROUP);
	Spark mRightMotor = new Spark(RobotMap.RIGHT_MOTOR_GROUP);
	DifferentialDrive mDifferentialDrive = new DifferentialDrive(mLeftMotor, mRightMotor);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public ChasisDriveSubsystem(double p, double i, double d) {
		super(p, i, d);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new TeleopTankDriveCommand());
	}

	public void mDriveTank(Joystick j) {
		// x: [0,1];y: [0,1]
		mDifferentialDrive.tankDrive(j.getY() * RobotMap.TELE_LEFT_SPEED_FACTOR,
				j.getThrottle() * RobotMap.TELE_RIGHT_SPEED_FACTOR);
	}

	public void stopDrive() {
		mLeftMotor.stopMotor();
		mRightMotor.stopMotor();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Return PID offset
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Use PID output

	}
}
