package myproject.model;

public class Dealership implements Agent {
	Dealership() { }
	Road road;
	private int frequency = (int) (Math.ceil(
			Math.random()*(MP.maxDealershipFrequency-MP.minDealershipFrequency) ) + MP.minDealershipFrequency);	
	private boolean state;
	
	public boolean getState() {
		return state;
	}
	
	public boolean connectRoad(Road r) {
		if (road == null) {
			road = r;
			return true;
		} else { throw new IllegalStateException("Road has already been connected to this dealership"); }
	}

	public void run(double time) {
		if (time%frequency==0) {
			Car car = new Car();
			if (road.accept(car)) {
				car.initializeOnRoad(road);
				Model.addAgent(car);
			}
		}
	}
}
