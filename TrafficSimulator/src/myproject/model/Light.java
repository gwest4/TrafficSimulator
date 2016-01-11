package myproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A light has a boolean state.
 */
public class Light implements Agent, Connection {
	
	private Road verticalIn, verticalOut, horizontalIn, horizontalOut;
	private List<Car> cars = new ArrayList<Car>();
	private String name;
	private boolean[] state = {true, false}; // [horizontal, vertical]
	private int frequency = (int) (Math.ceil(
			Math.random()*(MP.maxLightFrequency-MP.minLightFrequency) ) + MP.minLightFrequency);
	
	Light() { this.name = "0"; state[0]=Math.random()<0.5; state[1]=!state[0]; } // Created only by this package
	Light(String name) { this.name = name; state[0]=Math.random()<0.5; state[1]=!state[0]; } // Created only by this package
	
	private boolean state(Car c) {
		if (c.road() == horizontalIn) {
			return state[0];
		} else if (c.road() == verticalIn) {
			return state[1];
		} else { throw new IllegalArgumentException("Car not on road feeding into "+this); }
	}
	
	public boolean state() {
		return state[0];
	}
	
	public void connectRoad(Road road, boolean horizontal, boolean in) {
		if (horizontal) { 
			if (in) { horizontalIn = road; }
			else { horizontalOut = road; }
		} else {
			if (in) { verticalIn = road; }
			else { verticalOut = road; }
		}
	}
	
	public void run(double time) {
		if (time%frequency==0) {
			state[0] = !state[0];
			state[1] = !state[1];
		}
	}

	public boolean accept(Car c) {
		if (state(c)) {
			cars.add(c);
			return true;
		}
		return false;
	}
	
	public boolean remove(Car c) {
		if (cars.remove(c)) return true;
		return false;
	}

	public Road direct(Car c) {
		if (c.road() == verticalIn) {
			if (verticalOut.accept(c)) { this.remove(c); return verticalOut; }
			else { /*System.out.println(verticalOut+" cannot accept "+c+" at the moment");*/ }
		} else if (c.road() == horizontalIn) {
			if (horizontalOut.accept(c)) { this.remove(c); return horizontalOut; }
			else { /*System.out.println(horizontalOut+" cannot accept "+c+" at the moment");*/ }
		} else if (c.road() == horizontalOut || c.road()== verticalOut){ /*throw new IllegalStateException("Car observed going the wrong way!");*/ 
			System.out.println(c+" entering light "+this+" from wrong road ("+c.road()+")!");
		}
		return null;
	}

	public String toString() {
		return "Light#"+name;
	}
}

