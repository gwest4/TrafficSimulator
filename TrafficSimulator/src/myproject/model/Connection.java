package myproject.model;

public interface Connection {
	public boolean accept(Car c);
	
	boolean remove(Car c);
}
