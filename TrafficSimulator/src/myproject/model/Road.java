package myproject.model;

import java.util.LinkedList;

/**
 * A road holds cars.
 */
public class Road {
	private LinkedList<Car> cars = new LinkedList<Car>();
	private double roadLength = MP.roadLength;
	private boolean direction;
	private Connection connection;
	private String name;
	
	Road(boolean direction) { // Created only by this package
		this.direction = direction;
	}

	public Road(boolean direction, String name) {
		this.direction = direction;
		this.name = name;
	}

	public boolean accept(Car d) {
		if (d == null) { throw new IllegalArgumentException(); }
		if (d.carLength() > buffer()) {
			return false;
		}
		cars.add(d);
		return true;
	}
	
	public boolean remove(Car d) {
		if (d == null) { throw new IllegalArgumentException(); }
		if (!cars.remove(d)) { throw new IllegalArgumentException("Car "+d+" not found on road "+this); }
		return true;
	}
	
	public LinkedList<Car> cars() {
		return cars;
	}
	
	public Car carInFrontOf(Car c) {
		if (cars.peek() == c) {
			return null;
		} else {
			return (cars.get(cars.indexOf(c)-1));
		}
	}

	public double roadLength() {
		return roadLength;
	}
	
	public Connection connection() {
		return connection;
	}
	
	public boolean direction() {
		return direction;
	}
	
	void connect(Connection conn) {
		if (conn == null) { throw new IllegalArgumentException(); }
		if (connection != null) { throw new IllegalStateException("Road has already been connected"); }
		connection = conn;
	}
	
	/**
	 * Length of road from front edge to most recently accepted Car
	 */
	private double buffer() {
		if (cars.isEmpty()) { return roadLength; }
		Car lastCar = cars.peekLast();
		return lastCar.position();
	}
	
//	public double buffer(Car c) {
//		Car nextCar = getNextCar(c);
//		if (nextCar == null) {
//			return -1.0;
//		} else {
//			
//		}
//	}
	
	public String toString() {
		return "Road#"+name;
	}

}
