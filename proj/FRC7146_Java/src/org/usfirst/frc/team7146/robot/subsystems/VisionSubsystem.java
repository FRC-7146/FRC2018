package org.usfirst.frc.team7146.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import io.github.d0048.vision.VisualTarget;

public class VisionSubsystem extends Subsystem {

	private static final java.util.logging.Logger logger = Logger.getLogger(VisionSubsystem.class.getName());
	public CameraServer mCameraServer = CameraServer.getInstance();
	public UsbCamera mUsbCamera = mCameraServer.startAutomaticCapture();
	CvSink cvSink = mCameraServer.getVideo();
	CvSource cvSrcOut;
	public boolean VisionDebug = false;

	public VisionSubsystem() {
		this("VisionSubSystem");
		this.mUsbCamera.setFPS(10);
		this.mUsbCamera.setResolution(640, 480);
		this.cvSrcOut = CameraServer.getInstance().putVideo("Rectangle", 640, 480);
		// this.mUsbCamera.setExposureAuto();
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
		this.dispFram(cubeMat);
		/*
		 * Mat cubeMatHSV = new Mat(); Imgproc.cvtColor(cubeMat, cubeMatHSV,
		 * Imgproc.COLOR_RGB2HSV); cubeMat = null;
		 * 
		 * Mat cubeMatBlur = new Mat(); Imgproc.GaussianBlur(cubeMatHSV, cubeMatBlur,
		 * new Size(3, 3), 0); cubeMatHSV = null;
		 * 
		 * Mat cubeMatFilter = new Mat(); Imgproc.bilateralFilter(cubeMatBlur,
		 * cubeMatFilter, 9, 9, 75); cubeMatBlur = null;
		 * 
		 * Mat cubeMask = new Mat(); Core.inRange(cubeMatFilter, lowerY, upperY,
		 * cubeMask);
		 * 
		 * List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		 * Imgproc.findContours(cubeMask.clone(), contours, new Mat(),
		 * Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		 * 
		 * MatOfPoint max = this.LargestCnt(contours); this.dispFram(max);
		 */
		return null;
	}

	public void dispFram(Mat m) {
		this.cvSrcOut.notifyError(cvSink.getError());
		this.cvSrcOut.putFrame(m);
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
