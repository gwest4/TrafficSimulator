package myproject.main;

import myproject.main.Control;
import myproject.ui.UI;

/**
 * A static class to demonstrate the visualization aspect of
 * simulation.
 */
public class Main {
	public static void main(String[] args) {
		UI ui = null;

		if (args.length > 0) {
			if ("GUI".equalsIgnoreCase(args[0])) {
				ui = new myproject.ui.PopupUI();
			} else if ("TEXT".equalsIgnoreCase(args[0])) {
				ui = new myproject.ui.TextUI();
			} else {
				System.out.println("Argument must be GUI or TEXT");
				System.exit(1);
			}
		} else {
			if (Math.random() <= 0.5) {
				ui = new myproject.ui.TextUI();
			} else {
				ui = new myproject.ui.PopupUI();
			}
		}
		Control control = new Control(ui);
		control.run();
	}
}

