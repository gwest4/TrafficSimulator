package myproject.model;

import java.util.ArrayList;
import java.util.List;

public class Junkyard implements Connection {
	private List<Car> cars = new ArrayList<Car>();
	
	public boolean accept(Car c) {
		cars.add(c);
		return true;
	}
	
	public boolean remove(Car c) {
		if (cars.remove(c)) return true;
		return false;
	}
}
