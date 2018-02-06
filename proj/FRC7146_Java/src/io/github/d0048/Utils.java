package io.github.d0048;


public class Utils {

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

}
