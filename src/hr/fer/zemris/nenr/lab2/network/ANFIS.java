package hr.fer.zemris.nenr.lab2.network;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.rule.Conclusion;
import hr.fer.zemris.nenr.lab2.rule.Rule;
import hr.fer.zemris.nenr.lab2.tNorm.ITNorm;

import java.util.Arrays;
import java.util.Random;

/**
 * A generic Adaptive Neuro-Fuzzy Inference System (ANFIS).
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class ANFIS {

	private Rule[] rules;

	/**
	 * Constructor.
	 * @param rulesCount The number of rules.
	 * @param mfA The membership function for the first input.
	 * @param mfB The membership function for the second input.
	 * @param tNorm The T-norm to use.
	 * @param rand A random number generator.
	 */
	public ANFIS(int rulesCount, IMembershipFunction mfA, IMembershipFunction mfB, ITNorm tNorm, Random rand) {
		createRules(rulesCount, mfA, mfB, tNorm, rand);
	}

	private void createRules(int rulesCount, IMembershipFunction mfA, IMembershipFunction mfB, ITNorm tNorm, Random rand) {
		this.rules = new Rule[rulesCount];
		for (int i = 0; i < rulesCount; i++) {
			rules[i] = new Rule(mfA, mfB, tNorm, new Conclusion(rand));
		}
	}

	/**
	 * Computes the output of the ANFIS network for a given input.
	 * @param x The first input.
	 * @param y The second input.
	 * @return The output of the network.
	 */
	public double compute(double x, double y) {
		double[] w = new double[rules.length];
		Arrays.fill(w, 0);
		double weightSum = 0;

		//LAYER 1 AND LAYER 2
		for (int i = 0; i < rules.length; i++) {
			w[i] = rules[i].weight(x, y);
			weightSum += w[i];
		}

		//LAYER 3, 4 AND LAYER 5
		double f = 0;
		for (int i = 0; i < rules.length; i++) {
			f += w[i]*rules[i].conclude(x, y)/weightSum;
		}

		return f;
	}

	/**
	 * Gets the rules of the ANFIS network.
	 * @return The rules of the ANFIS network.
	 */
	public Rule[] getRules() {
		return rules;
	}
}
