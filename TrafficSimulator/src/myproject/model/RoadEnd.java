package myproject.model;

/**
 * Interface for objects that can be placed at the end of roads
 */
public interface RoadEnd {
	public boolean accept(Car c);
}
