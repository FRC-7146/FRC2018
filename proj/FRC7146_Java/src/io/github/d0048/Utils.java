package io.github.d0048;

import java.util.List;
import java.util.logging.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Point;

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

	public static void release(Object o) {
		if (o != null) {
			((Mat) o).release();
		}
	}

	public static void release(Object[] os) {
		for (Object o : os) {
			release(o);
		}
	}

	public static void release(List<Object> os) {
		release(os.toArray());

	}

}
