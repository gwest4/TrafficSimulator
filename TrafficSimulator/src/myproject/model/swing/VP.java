package myproject.model.swing;

/**
 * Static class for visualization parameters.
 */
class VP {
	private VP() {}
	/** Width of model elements, in meters */
	static double elementWidth = 14;
	/** Gap between model elements, in meters */
	static double gap = 1;
	/** Width of the displayed graphics window, in pixels */
	static int displayWidth = 1920;
	/** Height of the displayed graphics window, in pixels */
	static int displayHeight = 1080;
	/** Delay introduced into each graphics update, in milliseconds */
	static int displayDelay = 20; //30 Hz = 20, 60 Hz = 10
	/** Scalefactor relating model space to pixels, in pixels/feet */
	static double scaleFactor = 0.95;
}
