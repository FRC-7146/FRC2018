package io.github.d0048.vision;

import org.opencv.core.Core;
import org.opencv.core.Point;

import edu.wpi.cscore.CvSource;
import io.github.d0048.Utils;

public class VisualTarget {
	// in view,deviation from center

	Point center = new Point(0, 0);

	public VisualTarget(Point lu, Point rd) {
		this.center = Utils.mid(lu, rd);
	}

}
