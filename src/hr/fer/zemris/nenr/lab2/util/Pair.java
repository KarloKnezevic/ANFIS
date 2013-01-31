package hr.fer.zemris.nenr.lab2.util;

public class Pair {
	
	public double x;
	
	public double y;
	
	public double value;
	
	public Pair() {}
	
	public Pair(double x, double y, double value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + value;
	}

}
