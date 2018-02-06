package org.usfirst.frc.team7146.robot;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import io.github.d0048.Utils;

public class MatchInfo {
	private static final Logger logger = Logger.getLogger(MatchInfo.class.getName());
	public static boolean matchInfoDebug = false;

	public static final String NAH = "";
	public static char scale1 = 'L';
	public static char balance = 'L';
	public static char scale2 = 'L';
	public static boolean success = false;

	public MatchInfo() {
	}

	public static boolean infoInit() {
		String msg = DriverStation.getInstance().getGameSpecificMessage();
		if (matchInfoDebug) {
			logger.info("Recieve: " + msg);
		}
		try {
			if (!msg.equals(NAH)) {
				MatchInfo.scale1 = msg.charAt(0);
				MatchInfo.balance = msg.charAt(1);
				MatchInfo.scale2 = msg.charAt(2);
				logger.info("match info reterived: " + scale1 + "|" + balance + "|" + scale2);
				return success = true;
			} else {
				if (matchInfoDebug) {
					logger.info("useless message:" + msg);
				}
				return false;
			}
		} catch (Exception e) {
			logger.throwing("InfoInit", "None Strandard message recieved", e);
			logger.warning("error resolving msg: " + e.getMessage());
			return false;
		}
	}

}
