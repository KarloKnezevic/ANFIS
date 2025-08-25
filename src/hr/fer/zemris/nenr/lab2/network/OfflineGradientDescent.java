package hr.fer.zemris.nenr.lab2.network;

import hr.fer.zemris.nenr.lab2.rule.Rule;
import hr.fer.zemris.nenr.lab2.util.Pair;

import java.util.Collections;
import java.util.List;

/**
 * Offline (batch) gradient descent learning algorithm for ANFIS.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class OfflineGradientDescent {

	private ANFIS anfis;
	private double learningRate;

	private double[][] mfA_gradients;
	private double[][] mfB_gradients;
	private double[][] conclusion_gradients;

	/**
	 * Constructor.
	 * @param anfis The ANFIS network to train.
	 * @param learningRate The learning rate.
	 */
	public OfflineGradientDescent(ANFIS anfis, double learningRate) {
		this.anfis = anfis;
		this.learningRate = learningRate;

		int numRules = anfis.getRules().length;
		int numMfAParams = anfis.getRules()[0].mfA.getNumberOfParameters();
		int numMfBParams = anfis.getRules()[0].mfB.getNumberOfParameters();

		this.mfA_gradients = new double[numRules][numMfAParams];
		this.mfB_gradients = new double[numRules][numMfBParams];
		this.conclusion_gradients = new double[numRules][3];
	}

	/**
	 * Performs one epoch of learning.
	 * @param dataset The dataset to learn from.
	 * @return The total error for the epoch.
	 */
	public double learn(List<Pair> dataset) {
		double error = 0;

		Collections.shuffle(dataset);
		initializeGradients();

		for (int i = 0; i < dataset.size(); i++) {
			double x = dataset.get(i).x;
			double y = dataset.get(i).y;
			double expected = dataset.get(i).value;

			double actual = anfis.compute(x, y);
			double error_i = expected - actual;

			updateGradients(x, y, error_i);

			error += 0.5 * error_i * error_i;
		}

		updateParameters();

		return error;
	}

	private void initializeGradients() {
		for (int i = 0; i < mfA_gradients.length; i++) {
			for (int j = 0; j < mfA_gradients[i].length; j++) {
				mfA_gradients[i][j] = 0;
			}
		}
		for (int i = 0; i < mfB_gradients.length; i++) {
			for (int j = 0; j < mfB_gradients[i].length; j++) {
				mfB_gradients[i][j] = 0;
			}
		}
		for (int i = 0; i < conclusion_gradients.length; i++) {
			for (int j = 0; j < conclusion_gradients[i].length; j++) {
				conclusion_gradients[i][j] = 0;
			}
		}
	}

	private void updateGradients(double x, double y, double error) {
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

			for (int p = 0; p < rules[i].mfA.getNumberOfParameters(); p++) {
				double dmuAdp = rules[i].mfA.computeDerivative(x, p);
				mfA_gradients[i][p] += common_part * dWdmuA * dmuAdp;
			}
			
			for (int p = 0; p < rules[i].mfB.getNumberOfParameters(); p++) {
				double dmuBdp = rules[i].mfB.computeDerivative(y, p);
				mfB_gradients[i][p] += common_part * dWdmuB * dmuBdp;
			}

			// Consequent parameters
			conclusion_gradients[i][0] += error * weight / sumW * x;
			conclusion_gradients[i][1] += error * weight / sumW * y;
			conclusion_gradients[i][2] += error * weight / sumW;
		}
	}

	private void updateParameters() {
		Rule[] rules = anfis.getRules();
		for (int i = 0; i < rules.length; i++) {
			double[] paramsA = rules[i].mfA.getParameters();
			for (int j = 0; j < paramsA.length; j++) {
				paramsA[j] += learningRate * mfA_gradients[i][j];
			}
			rules[i].mfA.setParameters(paramsA);

			double[] paramsB = rules[i].mfB.getParameters();
			for (int j = 0; j < paramsB.length; j++) {
				paramsB[j] += learningRate * mfB_gradients[i][j];
			}
			rules[i].mfB.setParameters(paramsB);

			rules[i].conclusion.p += learningRate * conclusion_gradients[i][0];
			rules[i].conclusion.q += learningRate * conclusion_gradients[i][1];
			rules[i].conclusion.r += learningRate * conclusion_gradients[i][2];
		}
	}
}