package org.usfirst.frc.team7146.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.d0048.Utils;
import io.github.d0048.vision.VisualTarget;

public class VisionSubsystem extends Subsystem {

	private static final java.util.logging.Logger logger = Logger.getLogger(VisionSubsystem.class.getName());
	public CameraServer mCameraServer = CameraServer.getInstance();
	public UsbCamera mUsbCamera = mCameraServer.startAutomaticCapture();
	CvSink cvSink = mCameraServer.getVideo();
	CvSource cvSrcOut, cvSrcMask;
	// MjpegServer mjpegServer = mCameraServer.addServer("Cubejpeg");
	public boolean VisionDebug = false;

	public VisionSubsystem() {
		this("VisionSubSystem");
		this.mUsbCamera.setFPS(10);
		this.mUsbCamera.setResolution(160, 120);
		cvSrcOut = mCameraServer.putVideo("src out", 160, 120);
		cvSrcMask = mCameraServer.putVideo("src mask", 160, 120);
		this.cvSrcOut.setFPS(1);
		// this.mjpegServer.setSource(this.cvSrcOut);
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
		int max_area = -1;
		MatOfPoint max = null;
		for (MatOfPoint cnt : cnts) {
			if (Imgproc.contourArea(cnt) >= max_area) {
				Utils.release(max);
				max = cnt;
			}
		}
		return max;
	}

	Scalar lowerY = new Scalar(20, 90, 90);
	Scalar upperY = new Scalar(35, 250, 250);

	/**
	 * 
	 * @return null if nothing found
	 */
	double minSize = 10;

	public VisualTarget findCube() throws Exception {
		// TODO
		List<Mat> toRelsease = new ArrayList<Mat>();

		Mat cubeMat = new Mat();
		toRelsease.add(cubeMat);
		if (0 == cvSink.grabFrame(cubeMat)) {
			logger.warning("Error grabbing fram from camera");
		}

		Mat cubeMatHSV = new Mat();
		toRelsease.add(cubeMatHSV);
		Imgproc.cvtColor(cubeMat, cubeMatHSV, Imgproc.COLOR_BGR2HSV);

		Mat cubeMatBlur = new Mat();
		toRelsease.add(cubeMatBlur);
		Imgproc.GaussianBlur(cubeMatHSV, cubeMatBlur, new Size(3, 3), 0);

		Mat cubeMatFilter = new Mat();
		toRelsease.add(cubeMatFilter);
		Imgproc.bilateralFilter(cubeMatBlur, cubeMatFilter, 9, 9, 75);

		Mat cubeMask = cubeMatHSV.clone();
		toRelsease.add(cubeMask);
		Core.inRange(cubeMask, lowerY, upperY, cubeMask);

		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(cubeMask.clone(), contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		MatOfPoint max = this.LargestCnt(contours);
		if (max == null) {
			logger.info("Nothing found");
			return null;
		}
		toRelsease.add(max);

		MatOfPoint2f max2f = new MatOfPoint2f();
		max.convertTo(max2f, CvType.CV_32F);
		toRelsease.add(max2f);

		if (Imgproc.contourArea(max2f) > minSize) {
			Imgproc.drawContours(cubeMat, contours, -1, new Scalar(100, 100, 256), 4);
		}
		RotatedRect cube = Imgproc.minAreaRect(max2f);
		VisualTarget ret = new VisualTarget(cube.center, cube.size);
		if (VisionDebug) {
			logger.info("Detected:" + cube.toString());
		}

		if (cube.size.area() > minSize) {
			SmartDashboard.putNumber("Cube X:", cube.center.x);
			SmartDashboard.putNumber("Cube Y:", cube.center.y);
			SmartDashboard.putNumber("Cube Size", cube.size.area());
		}
		this.cvSrcMask.putFrame(cubeMask);
		this.cvSrcOut.putFrame(cubeMat);

		for (Mat m : toRelsease) {
			try {
				m.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret.size.area() > minSize ? ret : null;
	}

	public void dispFram(Mat m) {
		// this.cvSrcOut.notifyError(cvSink.getError());
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
