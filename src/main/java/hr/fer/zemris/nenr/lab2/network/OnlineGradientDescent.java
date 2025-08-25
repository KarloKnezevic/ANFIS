package hr.fer.zemris.nenr.lab2.network;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.rule.Conclusion;
import hr.fer.zemris.nenr.lab2.rule.Rule;
import hr.fer.zemris.nenr.lab2.util.Pair;

import java.util.Collections;
import java.util.List;

/**
 * Online (stochastic) gradient descent learning algorithm for ANFIS.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class OnlineGradientDescent {

	private final double learningRate;

	public OnlineGradientDescent(double learningRate) {
		this.learningRate = learningRate;
	}

	public ANFIS learn(ANFIS anfis, List<Pair> dataset) {
		Collections.shuffle(dataset);

		ANFIS currentAnfis = anfis;
		for (Pair sample : dataset) {
			currentAnfis = updateParameters(currentAnfis, sample.x(), sample.y(), sample.value());
		}
		return currentAnfis;
	}

	private ANFIS updateParameters(ANFIS anfis, double x, double y, double expected) {
		Rule[] oldRules = anfis.rules();
		Rule[] newRules = new Rule[oldRules.length];

		double actual = anfis.compute(x, y);
		double error = expected - actual;

		double sumW = 0;
		for (Rule rule : oldRules) {
			sumW += rule.weight(x, y);
		}

		if (sumW == 0) {
			return anfis;
		}

		for (int i = 0; i < oldRules.length; i++) {
			Rule oldRule = oldRules[i];
			double weight = oldRule.weight(x, y);
			double muA = oldRule.mfA().compute(x);
			double muB = oldRule.mfB().compute(y);

			double sigma = 0;
			double zi = oldRule.conclude(x, y);
			for (int j = 0; j < oldRules.length; j++) {
				if (i == j) continue;
				sigma += oldRules[j].weight(x, y) * (zi - oldRules[j].conclude(x, y));
			}

			double common_part = error * sigma / Math.pow(sumW, 2);

			IMembershipFunction oldMfA = oldRule.mfA();
			double[] paramsA = oldMfA.getParameters();
			double dWdmuA = oldRule.norm().computeDerivative(muA, muB, 0);
			for (int p = 0; p < paramsA.length; p++) {
				double dmuAdp = oldMfA.computeDerivative(x, p);
				paramsA[p] += learningRate * common_part * dWdmuA * dmuAdp;
			}
			IMembershipFunction newMfA = oldMfA.withParameters(paramsA);

			IMembershipFunction oldMfB = oldRule.mfB();
			double[] paramsB = oldMfB.getParameters();
			double dWdmuB = oldRule.norm().computeDerivative(muA, muB, 1);
			for (int p = 0; p < paramsB.length; p++) {
				double dmuBdp = oldMfB.computeDerivative(y, p);
				paramsB[p] += learningRate * common_part * dWdmuB * dmuBdp;
			}
			IMembershipFunction newMfB = oldMfB.withParameters(paramsB);

			Conclusion oldConclusion = oldRule.conclusion();
			double p_new = oldConclusion.p() + learningRate * error * weight / sumW * x;
			double q_new = oldConclusion.q() + learningRate * error * weight / sumW * y;
			double r_new = oldConclusion.r() + learningRate * error * weight / sumW;
			Conclusion newConclusion = new Conclusion(p_new, q_new, r_new);

			newRules[i] = new Rule(newMfA, newMfB, oldRule.norm(), newConclusion);
		}
		return new ANFIS(newRules);
	}
}