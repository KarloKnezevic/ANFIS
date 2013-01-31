package hr.fer.zemris.nenr.lab2.membershipFunction;

import java.util.Random;

public class MF1 {
	
	public double a;
	
	public double b;
	
	//gradient a
	public double α;
	
	//gradient b
	public double β;
	
	public MF1(Random rand) {
		
		a = rand.nextDouble() - 0.5;
		b = rand.nextDouble() - 0.5;
	}

	public double compute(double x) {
		
		return 1.0 / (1 + Math.exp(b*(x-a)));
		
	}
}