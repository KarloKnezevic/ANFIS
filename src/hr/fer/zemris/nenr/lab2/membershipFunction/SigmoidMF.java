package hr.fer.zemris.nenr.lab2.membershipFunction;

import java.util.Random;

/**
 * Sigmoid membership function.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class SigmoidMF implements IMembershipFunction {

	private double a;
	private double b;

	public SigmoidMF(Random rand) {
		a = rand.nextDouble() - 0.5;
		b = rand.nextDouble() - 0.5;
	}

	@Override
	public double compute(double x) {
		return 1.0 / (1 + Math.exp(b * (x - a)));
	}

	@Override
	public double[] getParameters() {
		return new double[] { a, b };
	}

	@Override
	public void setParameters(double[] params) {
		this.a = params[0];
		this.b = params[1];
	}

	@Override
	public int getNumberOfParameters() {
		return 2;
	}

	@Override
	public double computeDerivative(double x, int paramIndex) {
		double f = compute(x);
		if (paramIndex == 0) { // derivative with respect to a
			return f * (1 - f) * b;
		} else if (paramIndex == 1) { // derivative with respect to b
			return f * (1 - f) * -(x - a);
		}
		return 0;
	}
}