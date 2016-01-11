package myproject.model;

/**
 * A car remembers its position from the beginning of its road.
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class Car implements Agent {
	
	//ATTRIBUTES
	private Road road;
	//private boolean backAndForth = false;
	private double position = 0;
	private double velocity = (int) Math.ceil(Math.random()*(MP.maxCarVelocity-MP.minCarVelocity))+MP.minCarVelocity;
	private double carLength = (int) Math.ceil(Math.random() * (MP.maxCarLength-MP.minCarLength) ) + MP.minCarLength;
	//private double stoppingDistance = carLength;
	//private double brake = 1;
	//private String desiredDirection;
	private java.awt.Color color = new java.awt.Color(
			(int)Math.ceil(Math.random()*155)+100,
			(int)Math.ceil(Math.random()*155)+100,
			(int)Math.ceil(Math.random()*155)+100	);
	private String name;
	
	//STATES
	boolean initialized = false;
	boolean waiting = false;
	boolean decelerating;
	boolean accelerating;
	boolean transitioning = false;

	Car() { } //Created only by this package
	Car(String name) { // Created only by this package
		this.name = name;
	} 
	
//	public void update() {
//		
//	}

	public void run(double time) {
		if (road == null) return;
		//if (waiting) return;
		Car leadingCar = road.carInFrontOf(this);
		double distance = velocity * 0.0489; //Assumes 30Hz
		if ( (position + distance)>(road.roadLength()-carLength) ) {
			if (leadingCar!=null) { throw new IllegalStateException(); }
			Connection c = road.connection();
			if (c == null) { System.out.println(this+" has reached a dead-end on "+this.road); this.road = null; return;}
			if (c.accept(this)) {
				if (c instanceof Light) {
					Road nextRoad = ((Light) c).direct(this);
					if (nextRoad == null) return;
					else {
						road.remove(this);
						road = nextRoad;
						position = 0;
						//System.out.println(this+" was directed onto "+this.road);
					}
				} else if (c instanceof Junkyard) {
					if (c.accept(this)) {
						road.remove(this);
						road = null;
						//c.remove(this);
					}
				}
			} else { return; }
			return;
		} else if ( (leadingCar!=null) ) {
			if (position+carLength+distance >= leadingCar.position()) {
				return;
			}
		}
		position += distance;
	}
	public boolean initializeOnRoad(Road r) {
		if (!initialized) {
			road = r;
			initialized = true;
			return true;
		} else { throw new IllegalStateException(this+" has already been initialized on "+road); }
	}
	public double position() {
		return position;
	}
	public java.awt.Color color() {
		return color;
	}
	public double carLength() {
		return carLength;
	}
	public Road road() {
		return road;
	}
	public String toString() {
		return "Car#"+name;
	}
}
