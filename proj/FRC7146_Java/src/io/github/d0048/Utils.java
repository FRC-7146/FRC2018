package io.github.d0048;

import java.util.logging.Logger;

import org.opencv.core.Point;
import org.usfirst.frc.team7146.robot.OI;

public class Utils {
	private static final Logger logger = Logger.getLogger(Utils.class.getName());
	public static double AngleOffsetCal(double src, double dst) {
		return dst - src;
	}

	public static double Ang2tanh(double ofs, double tol) {
		double angle = 0;
		if (Math.abs(ofs) > tol) {// TODO: Change to tanh
			angle = ofs / 15;
		}
		return angle;

	}

	public static Point mid(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

}
