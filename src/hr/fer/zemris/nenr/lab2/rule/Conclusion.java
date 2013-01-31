package hr.fer.zemris.nenr.lab2.rule;

import java.util.Random;

public class Conclusion {
	
	public double p;
	
	public double q;
	
	public double r;
	
	//gradient p
	public double π;
	
	//gradient q
	public double ω;
	
	//gradient r
	public double ρ;
	
	public Conclusion(Random rand) {
		p = rand.nextDouble() - 0.5;
		q = rand.nextDouble() - 0.5;
		r = rand.nextDouble() - 0.5;
	}
	
	public double compute(double x, double y) {
		return x*p + y*q + r;
	}

}