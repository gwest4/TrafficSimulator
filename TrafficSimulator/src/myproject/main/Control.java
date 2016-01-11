package myproject.main;

import myproject.model.MP;
import myproject.model.Model;
import myproject.model.swing.SwingAnimatorBuilder;
import myproject.ui.UI;
import myproject.ui.UIError;
import myproject.ui.UIFormBuilder;
import myproject.ui.UIFormTest;
import myproject.ui.UIMenu;
import myproject.ui.UIMenuBuilder;

class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int NUMSTATES = 10;
	private UIMenu[] menus;
	private int state;
	
	private UIFormTest rowsTest;
	private UIFormTest columnsTest;
	private UIFormTest stepsTest;
	private UIFormTest roadLengthTest;
	private UIFormTest maxCarLengthTest;
	private UIFormTest minCarLengthTest;
	private UIFormTest maxCarVelocityTest;
	private UIFormTest minCarVelocityTest;
	private UIFormTest minLightFrequencyTest;
	private UIFormTest maxLightFrequencyTest;
	private UIFormTest minDealershipFrequencyTest;
	private UIFormTest maxDealershipFrequencyTest;

	private UI ui;

	Control(UI ui) {
		this.ui = ui;

		menus = new UIMenu[NUMSTATES];
		state = START;
		addSTART(START);
		addEXIT(EXIT);

		rowsTest = input -> {
			try {
				Integer setting = Integer.parseInt(input);
				return (setting >= 1) && (setting <= 10);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		columnsTest = input -> {
			try {
				Integer setting = Integer.parseInt(input);
				return (setting >= 1) && (setting <= 10);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		stepsTest = input -> {
			try {
				Integer setting = Integer.parseInt(input);
				return (setting >= 100) && (setting <= 10000);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		maxCarLengthTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 30) && (setting <= 100);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		minCarLengthTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 14) && (setting <= 30);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		roadLengthTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 150) && (setting <= 500);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		maxCarVelocityTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 25) && (setting <= 120);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		minCarVelocityTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 5) && (setting <= 25);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		maxLightFrequencyTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 100) && (setting <= 300);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		minLightFrequencyTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 30) && (setting <= 100);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		maxDealershipFrequencyTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 100) && (setting <= 300);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
		minDealershipFrequencyTest = input -> {
			try {
				Double setting = Double.parseDouble(input);
				return (setting >= 30) && (setting <= 100);
			} catch (IllegalArgumentException e) {
				return false;
			}
		};
	}

	void run() {
		try {
			while (state != EXITED) {
				ui.processMenu(menus[state]);
			}
		} catch (UIError e) {
			ui.displayError("UI closed");
		}
	}

	private void addSTART(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default",
				() -> ui.displayError("Please choose an action"));
		m.add("Run simulation",
				() -> {
					Model model = new Model(new SwingAnimatorBuilder(), MP.rows, MP.columns);
					model.run(MP.steps);
					model.dispose();
					System.exit(0);
				});
		m.add("Edit simulation variables", 
				() -> {
					
					UIMenuBuilder s = new UIMenuBuilder();
					s.add("Default",
							() -> ui.displayError("Please choose an action"));
					s.add("Change number of rows (E/W roads)", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Number of rows (1-10)", rowsTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.rows = Integer.parseInt(result[0]);
					});
					s.add("Change number of columns (N/S roads)", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Number of columns (1-10)", columnsTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.columns = Integer.parseInt(result[0]);
					});
					s.add("Change the duration of the simulation", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Duration of simulation (100-10000 steps)", stepsTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.steps = Integer.parseInt(result[0]);
					});
					s.add("Change road length", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Minimum car length (150-300 feet)", roadLengthTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.roadLength = Double.parseDouble(result[0]);
					});
					s.add("Change maximum car length", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Maximum car length (30-100 feet)", maxCarLengthTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.maxCarLength = Double.parseDouble(result[0]);
					});
					s.add("Change minimum car length", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Minimum car length (14-30 feet)", minCarLengthTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.minCarLength = Double.parseDouble(result[0]);
					});
					s.add("Change maximum car velocity", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Maximum car velocity (25-120 mph)", maxCarVelocityTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.maxCarVelocity = Double.parseDouble(result[0]);
					});
					s.add("Change minimum car velocity", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Minimum car velocity (5-25 mph)", minCarVelocityTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.minCarVelocity = Double.parseDouble(result[0]);
					});
					s.add("Change maximum light frequency", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Maximum light frequency (every 100-300 steps)", maxLightFrequencyTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.maxLightFrequency = Double.parseDouble(result[0]);
					});
					s.add("Change minimum light frequency", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Minimum light frequency (every 30-100 steps)", minLightFrequencyTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.minLightFrequency = Double.parseDouble(result[0]);
					});
					s.add("Change maximum dealership frequency", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Maximum dealership frequency (every 100-300 steps)", maxDealershipFrequencyTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.maxDealershipFrequency = Double.parseDouble(result[0]);
					});
					s.add("Change minimum dealership frequency", () -> {
						UIFormBuilder f = new UIFormBuilder();
						f.add("Minimum dealership frequency (every 30-100 steps)", minDealershipFrequencyTest);
						String[] result = ui.processForm(f.toUIForm(""));
						MP.minDealershipFrequency = Double.parseDouble(result[0]);
					});
					s.add("Back",
							() -> { menus[stateNum] = m.toUIMenu("Traffic Simulation"); });
					menus[stateNum] = s.toUIMenu("Edit Simulation Variables");
				}
			);
		m.add("Exit",
				() -> state = EXIT);

		menus[stateNum] = m.toUIMenu("Traffic Simulation");
	}
	private void addEXIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", () -> {});
		m.add("Yes",
				() -> state = EXITED);
		m.add("No",
				() -> state = START);

		menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	}
}
