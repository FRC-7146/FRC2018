package testCommands;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.usfirst.frc.team7146.robot.Robot;
import org.usfirst.frc.team7146.robot.commands.CmdBase;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AccelTestCMD extends CmdBase {
	private static final java.util.logging.Logger logger = Logger.getLogger(AccelTestCMD.class.getName());
	public static CmdBase instance;
	public Accelerometer mAccel = Robot.m_GyroSubsystem.mAccel;
	double ofs = 0;

	public AccelTestCMD() {
		super("AccelTestCMD", 99);
		requires(Robot.m_ChasisDriveSubsystem);
		requires(Robot.m_GyroSubsystem);

		logger.info("Instance created");
		AccelTestCMD.instance = this;
		Robot.m_oi.mCommands.put(this.getName(), this);
		int itr = 100;
		for (int i = itr; i > 0; i--) {
			this.ofs += mAccel.getX();
		}
		this.ofs /= itr;

		mAccel.getX();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	public BigDecimal v = new BigDecimal(0), x = new BigDecimal(0), dt = new BigDecimal(1 / 3);
	int itr = 10, i = itr;

	public void disp() {
		// if (!Robot.cmdCanRun(this))
		// return;
		/*
		 * v = v.add(dt.multiply(new BigDecimal((mAccel.getX())))); x =
		 * x.add(v.multiply(dt)); logger.info("Accel:\n" + "\na: " + (mAccel.getX() -
		 * ofs) + "\nv: " + v + "\nx: " + x); SmartDashboard.putNumber("AccX",
		 * (mAccel.getX() - ofs)); SmartDashboard.putNumber("v", this.v.doubleValue());
		 * SmartDashboard.putNumber("x", this.x.doubleValue());
		 */
		if (i-- < 0) {
			i = itr;
			SmartDashboard.putNumber("Encoder position", Robot.m_oi.mTalon1.getSelectedSensorPosition(0));
			SmartDashboard.putNumber("Encode velocity", Robot.m_oi.mTalon1.getSelectedSensorVelocity(0));
			logger.info("Encode velocity: " + Robot.m_oi.mTalon1.getSelectedSensorVelocity(0));
			logger.info("Encoder position: " + Robot.m_oi.mTalon1.getSelectedSensorPosition(0));
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
		Robot.m_ChasisDriveSubsystem.stopDrive();
		logger.info("Instance destroyed");
		AccelTestCMD.instance = null;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
