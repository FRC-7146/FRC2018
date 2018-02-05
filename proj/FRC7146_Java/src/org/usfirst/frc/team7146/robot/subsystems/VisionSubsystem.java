package org.usfirst.frc.team7146.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.validation.SchemaFactoryLoader;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionSubsystem extends Subsystem {

	private static final java.util.logging.Logger logger = Logger.getLogger(VisionSubsystem.class.getName());
	public CameraServer mCameraServer = CameraServer.getInstance();
	public UsbCamera mUsbCamera = mCameraServer.startAutomaticCapture();
	CvSink cvSink = mCameraServer.getVideo();
	public boolean VisionDebug = false;

	public VisionSubsystem() {
		this("VisionSubSystem");
		this.mUsbCamera.setFPS(10);
		// this.mUsbCamera.setExposureAuto();
		// this.mUsbCamera.setWhiteBalanceAuto();
	}

	public VisionSubsystem(String name) {
		super(name);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO
	}

	MatOfPoint LargestCnt(List<MatOfPoint> cnts) {
		int max_area = 0;
		MatOfPoint max = null;
		for (MatOfPoint cnt : cnts) {
			if (Imgproc.contourArea(cnt) > max_area) {
				max = cnt;
			}
		}
		return max;
	}

	Scalar lowerY = new Scalar(30, 60, 60), upperY = new Scalar(40, 255, 255);

	/**
	 * 
	 * @return null if nothing found
	 */

	public VisualTarget findCube() {
		// TODO
		Mat cubeMat = new Mat();// reuse to save memory
		cvSink.grabFrame(cubeMat);

		Mat cubeMatHSV = new Mat();
		Imgproc.cvtColor(cubeMat, cubeMatHSV, Imgproc.COLOR_RGB2HSV);
		cubeMat = null;

		Mat cubeMatBlur = new Mat();
		Imgproc.GaussianBlur(cubeMatHSV, cubeMatBlur, new Size(3, 3), 0);
		cubeMatHSV = null;

		Mat cubeMatFilter = new Mat();
		Imgproc.bilateralFilter(cubeMatBlur, cubeMatFilter, 9, 9, 75);
		cubeMatBlur = null;

		Mat cubeMask = new Mat();
		Core.inRange(cubeMatFilter, lowerY, upperY, cubeMask);

		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(cubeMask.clone(), contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

		MatOfPoint max = this.LargestCnt(contours);

		return null;
	}

	/**
	 * 
	 * @return null if nothing found
	 */
	public VisualTarget findBusket() {
		// TODO
		return null;
	}

	/**
	 * 
	 * @return null if nothing found
	 */
	public VisualTarget findPWStation() {
		// TODO
		return null;
	}
}

class VisualTarget {
	// in view,deviation from center
	public double x = 0, y = 0, area = 0;
	public double lu = 0, ru = 0, ld = 0, rd = 0;

	public VisualTarget(double x, double y, double area, double lu, double ru, double ld, double rd) {
		super();
		this.x = x;
		this.y = y;
		this.area = area;
		this.lu = lu;
		this.ru = ru;
		this.ld = ld;
		this.rd = rd;
	}

}