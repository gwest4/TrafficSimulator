package myproject.model;

/**
 * Static class for model parameters.
 */
public class MP {
	private MP() {}
	/** Number of rows */
	public static int rows = 4;
	/** Number of columns */
	public static int columns = 7;
	/** Number of steps in simulation */
	public static int steps = 2500;
	/** Length of cars, in meters */
	public static double maxCarLength = 60;
	public static double minCarLength = 14;
	/** Length of roads, in meters */
	public static double roadLength = 200;
	/** Car velocity, in miles/hour */
	public static double maxCarVelocity = 80;
	public static double minCarVelocity = 30;
	/** Light change frequency, in seconds */
	public static double minLightFrequency = 50;
	public static double maxLightFrequency = 150;
	/** Dealership car-selling frequency */
	public static double minDealershipFrequency = 50;
	public static double maxDealershipFrequency = 150;
}

