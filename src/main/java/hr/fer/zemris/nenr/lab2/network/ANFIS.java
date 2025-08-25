package hr.fer.zemris.nenr.lab2.network;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.rule.Conclusion;
import hr.fer.zemris.nenr.lab2.rule.Rule;
import hr.fer.zemris.nenr.lab2.tNorm.ITNorm;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * A generic Adaptive Neuro-Fuzzy Inference System (ANFIS).
 * @param rules The rules of the ANFIS network.
 */
public record ANFIS(Rule[] rules) {

	/**
	 * Creates a new ANFIS with random rules.
	 * @param rulesCount The number of rules.
	 * @param mfA The membership function for the first input.
	 * @param mfB The membership function for the second input.
	 * @param tNorm The T-norm to use.
	 * @param rand A random number generator.
	 * @return A new ANFIS instance.
	 */
	public static ANFIS create(int rulesCount, hr.fer.zemris.nenr.lab2.membershipFunction.MembershipFunctionFactory mfAFactory, hr.fer.zemris.nenr.lab2.membershipFunction.MembershipFunctionFactory mfBFactory, ITNorm tNorm, Random rand) {
		Rule[] rules = IntStream.range(0, rulesCount)
				.mapToObj(i -> {
					IMembershipFunction newMfA = mfAFactory.create(rand);
					IMembershipFunction newMfB = mfBFactory.create(rand);
					double p = rand.nextDouble() - 0.5;
					double q = rand.nextDouble() - 0.5;
					double r = rand.nextDouble() - 0.5;
					return new Rule(newMfA, newMfB, tNorm, new Conclusion(p, q, r));
				})
				.toArray(Rule[]::new);
		return new ANFIS(rules);
	}

	/**
	 * Computes the output of the ANFIS network for a given input.
	 * @param x The first input.
	 * @param y The second input.
	 * @return The output of the network.
	 */
	public double compute(double x, double y) {
		final double[] w = Arrays.stream(rules).mapToDouble(rule -> rule.weight(x, y)).toArray();
		final double weightSum = Arrays.stream(w).sum();

		if (weightSum == 0) {
			return 0;
		}

		return IntStream.range(0, rules.length)
				.mapToDouble(i -> w[i] * rules[i].conclude(x, y) / weightSum)
				.sum();
	}
}
