package hr.fer.zemris.nenr.lab2.rule;

import java.util.Random;

/**
 * The conclusion part of a TSK fuzzy rule.
 * The conclusion is a linear function of the inputs: z = p*x + q*y + r.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class Conclusion {
	
	public double p;
	public double q;
	public double r;
	
	public Conclusion(Random rand) {
		p = rand.nextDouble() - 0.5;
		q = rand.nextDouble() - 0.5;
		r = rand.nextDouble() - 0.5;
	}
	
	/**
	 * Computes the output of the conclusion.
	 * @param x The first input.
	 * @param y The second input.
	 * @return The output of the conclusion.
	 */
	public double compute(double x, double y) {
		return x*p + y*q + r;
	}

}