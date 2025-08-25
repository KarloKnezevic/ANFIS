package hr.fer.zemris.nenr.lab2.network;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.rule.Conclusion;
import hr.fer.zemris.nenr.lab2.rule.Rule;
import hr.fer.zemris.nenr.lab2.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Offline (batch) gradient descent learning algorithm for ANFIS.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class OfflineGradientDescent {

	private final double learningRate;

	public OfflineGradientDescent(double learningRate) {
		this.learningRate = learningRate;
	}

	public ANFIS learn(ANFIS anfis, List<Pair> dataset) {
		Rule[] rules = anfis.rules();
		int numRules = rules.length;
		int numMfAParams = rules[0].mfA().getNumberOfParameters();
		int numMfBParams = rules[0].mfB().getNumberOfParameters();

		double[][] mfA_gradients = new double[numRules][numMfAParams];
		double[][] mfB_gradients = new double[numRules][numMfBParams];
		double[][] conclusion_gradients = new double[numRules][3];

		Collections.shuffle(dataset);

		dataset.stream().forEach(sample -> {
			double x = sample.x();
			double y = sample.y();
			double expected = sample.value();

			double actual = anfis.compute(x, y);
			double error = expected - actual;

			updateGradients(anfis, x, y, error, mfA_gradients, mfB_gradients, conclusion_gradients);
		});

		return updateParameters(anfis, mfA_gradients, mfB_gradients, conclusion_gradients);
	}

	private void updateGradients(ANFIS anfis, double x, double y, double error,
								 double[][] mfA_gradients, double[][] mfB_gradients, double[][] conclusion_gradients) {
		Rule[] rules = anfis.rules();
		double sumW = 0;
		for (Rule rule : rules) {
			sumW += rule.weight(x, y);
		}

		if (sumW == 0) {
			return;
		}

		for (int i = 0; i < rules.length; i++) {
			double weight = rules[i].weight(x, y);
			double muA = rules[i].mfA().compute(x);
			double muB = rules[i].mfB().compute(y);

			double sigma = 0;
			double zi = rules[i].conclude(x, y);
			for (int j = 0; j < rules.length; j++) {
				if (i == j) continue;
				sigma += rules[j].weight(x, y) * (zi - rules[j].conclude(x, y));
			}

			double common_part = error * sigma / Math.pow(sumW, 2);

			double dWdmuA = rules[i].norm().computeDerivative(muA, muB, 0);
			for (int p = 0; p < rules[i].mfA().getNumberOfParameters(); p++) {
				double dmuAdp = rules[i].mfA().computeDerivative(x, p);
				mfA_gradients[i][p] += common_part * dWdmuA * dmuAdp;
			}

			double dWdmuB = rules[i].norm().computeDerivative(muA, muB, 1);
			for (int p = 0; p < rules[i].mfB().getNumberOfParameters(); p++) {
				double dmuBdp = rules[i].mfB().computeDerivative(y, p);
				mfB_gradients[i][p] += common_part * dWdmuB * dmuBdp;
			}

			conclusion_gradients[i][0] += error * weight / sumW * x;
			conclusion_gradients[i][1] += error * weight / sumW * y;
			conclusion_gradients[i][2] += error * weight / sumW;
		}
	}

	private ANFIS updateParameters(ANFIS anfis,
								   double[][] mfA_gradients, double[][] mfB_gradients, double[][] conclusion_gradients) {
		Rule[] oldRules = anfis.rules();
		Rule[] newRules = new Rule[oldRules.length];

		for (int i = 0; i < oldRules.length; i++) {
			IMembershipFunction oldMfA = oldRules[i].mfA();
			double[] paramsA = oldMfA.getParameters();
			for (int j = 0; j < paramsA.length; j++) {
				paramsA[j] += learningRate * mfA_gradients[i][j];
			}
			IMembershipFunction newMfA = oldMfA.withParameters(paramsA);

			IMembershipFunction oldMfB = oldRules[i].mfB();
			double[] paramsB = oldMfB.getParameters();
			for (int j = 0; j < paramsB.length; j++) {
				paramsB[j] += learningRate * mfB_gradients[i][j];
			}
			IMembershipFunction newMfB = oldMfB.withParameters(paramsB);

			Conclusion oldConclusion = oldRules[i].conclusion();
			double p = oldConclusion.p() + learningRate * conclusion_gradients[i][0];
			double q = oldConclusion.q() + learningRate * conclusion_gradients[i][1];
			double r = oldConclusion.r() + learningRate * conclusion_gradients[i][2];
			Conclusion newConclusion = new Conclusion(p, q, r);

			newRules[i] = new Rule(newMfA, newMfB, oldRules[i].norm(), newConclusion);
		}

		return new ANFIS(newRules);
	}
}