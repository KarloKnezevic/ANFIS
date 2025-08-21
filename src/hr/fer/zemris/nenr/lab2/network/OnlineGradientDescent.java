package hr.fer.zemris.nenr.lab2.network;

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

	private ANFIS anfis;
	private double learningRate;

	/**
	 * Constructor.
	 * @param anfis The ANFIS network to train.
	 * @param learningRate The learning rate.
	 */
	public OnlineGradientDescent(ANFIS anfis, double learningRate) {
		this.anfis = anfis;
		this.learningRate = learningRate;
	}

	/**
	 * Performs one epoch of learning.
	 * @param dataset The dataset to learn from.
	 * @return The total error for the epoch.
	 */
	public double learn(List<Pair> dataset) {
		double error = 0;

		Collections.shuffle(dataset);

		for (int i = 0; i < dataset.size(); i++) {
			double x = dataset.get(i).x;
			double y = dataset.get(i).y;
			double expected = dataset.get(i).value;

			double actual = anfis.compute(x, y);
			double error_i = expected - actual;

			updateParameters(x, y, error_i);

			error += 0.5 * error_i * error_i;
		}

		return error;
	}

	private void updateParameters(double x, double y, double error) {
		Rule[] rules = anfis.getRules();
		double sumW = 0;
		for (Rule rule : rules) {
			sumW += rule.weight(x, y);
		}

		for (int i = 0; i < rules.length; i++) {
			double weight = rules[i].weight(x, y);
			double muA = rules[i].mfA.compute(x);
			double muB = rules[i].mfB.compute(y);

			double sigma = 0;
			double zi = rules[i].conclude(x, y);
			for (int j = 0; j < rules.length; j++) {
				if (i == j) continue;
				sigma += rules[j].weight(x, y) * (zi - rules[j].conclude(x, y));
			}

			// Antecedent parameters
			double common_part = error * sigma / Math.pow(sumW, 2);
			
			double dWdmuA = rules[i].norm.computeDerivative(muA, muB, 0);
			double dWdmuB = rules[i].norm.computeDerivative(muA, muB, 1);

			double[] paramsA = rules[i].mfA.getParameters();
			for (int p = 0; p < paramsA.length; p++) {
				double dmuAdp = rules[i].mfA.computeDerivative(x, p);
				paramsA[p] += learningRate * common_part * dWdmuA * dmuAdp;
			}
			rules[i].mfA.setParameters(paramsA);

			double[] paramsB = rules[i].mfB.getParameters();
			for (int p = 0; p < paramsB.length; p++) {
				double dmuBdp = rules[i].mfB.computeDerivative(y, p);
				paramsB[p] += learningRate * common_part * dWdmuB * dmuBdp;
			}
			rules[i].mfB.setParameters(paramsB);

			// Consequent parameters
			rules[i].conclusion.p += learningRate * error * weight / sumW * x;
			rules[i].conclusion.q += learningRate * error * weight / sumW * y;
			rules[i].conclusion.r += learningRate * error * weight / sumW;
		}
	}
}