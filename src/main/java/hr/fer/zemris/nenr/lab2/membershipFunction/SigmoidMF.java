package hr.fer.zemris.nenr.lab2.membershipFunction;

import java.util.Random;

/**
 * Sigmoid membership function.
 * @param a The 'a' parameter of the sigmoid function.
 * @param b The 'b' parameter of the sigmoid function.
 */
public record SigmoidMF(double a, double b) implements IMembershipFunction {

	public static MembershipFunctionFactory factory() {
		return rand -> new SigmoidMF(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5);
	}

	@Override
	public double compute(double x) {
		return 1.0 / (1 + Math.exp(b() * (x - a())));
	}

	@Override
	public double[] getParameters() {
		return new double[] { a(), b() };
	}

	@Override
	public IMembershipFunction withParameters(double[] params) {
		return new SigmoidMF(params[0], params[1]);
	}

	@Override
	public int getNumberOfParameters() {
		return 2;
	}

	@Override
	public double computeDerivative(double x, int paramIndex) {
		double f = compute(x);
		if (paramIndex == 0) { // derivative with respect to a
			return f * (1 - f) * b();
		} else if (paramIndex == 1) { // derivative with respect to b
			return f * (1 - f) * -(x - a());
		}
		return 0;
	}
}