package testCommands;

import java.util.logging.Logger;

import org.omg.PortableServer.THREAD_POLICY_ID;
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
	public double v = 0, x = 0, dt = 0.9;

	public void disp() {
		// if (!Robot.cmdCanRun(this))
		// return;
		v += (mAccel.getX() - ofs) * dt;
		x += v * dt;
		logger.info("Accel:\n" + "\na: " + (mAccel.getX() - ofs) + "\nv: " + v + "\nx: " + x);
		SmartDashboard.putNumber("AccX", (mAccel.getX() - ofs));
		SmartDashboard.putNumber("v", this.v);
		SmartDashboard.putNumber("x", this.x);

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
