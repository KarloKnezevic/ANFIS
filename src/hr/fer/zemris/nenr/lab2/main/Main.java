package hr.fer.zemris.nenr.lab2.main;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.membershipFunction.SigmoidMF;
import hr.fer.zemris.nenr.lab2.network.ANFIS;
import hr.fer.zemris.nenr.lab2.network.OfflineGradientDescent;
import hr.fer.zemris.nenr.lab2.tNorm.HamacherProduct;
import hr.fer.zemris.nenr.lab2.tNorm.ITNorm;
import hr.fer.zemris.nenr.lab2.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		// Create dataset
		List<Pair> dataset = new ArrayList<>();
		for (int i = -4; i <= 4; i++) {
			for (int j = -4; j <= 4; j++) {
				dataset.add(new Pair(i, j, f(i, j)));
			}
		}

		// Create ANFIS
		int numRules = 10;
		Random rand = new Random();
		IMembershipFunction mfA = new SigmoidMF(rand);
		IMembershipFunction mfB = new SigmoidMF(rand);
		ITNorm tNorm = new HamacherProduct();
		ANFIS anfis = new ANFIS(numRules, mfA, mfB, tNorm, rand);

		// Create learning algorithm
		double learningRate = 0.001;
		OfflineGradientDescent trainer = new OfflineGradientDescent(anfis, learningRate);

		// Train the network
		int epochs = 10000;
		for (int i = 0; i < epochs; i++) {
			double error = trainer.learn(dataset);
			if ((i + 1) % 1000 == 0) {
				System.out.println("Epoch " + (i + 1) + ", error: " + error);
			}
		}

		// Test the network
		System.out.println("\nTesting the trained network:");
		for (int i = -4; i <= 4; i++) {
			for (int j = -4; j <= 4; j++) {
				double expected = f(i, j);
				double actual = anfis.compute(i, j);
				System.out.println("Input: (" + i + ", " + j + "), Expected: " + expected + ", Actual: " + actual + ", Error: " + Math.abs(expected - actual));
			}
		}
	}

	private static double f(double x, double y) {
		return ((x - 1)*(x - 1) + (y + 2)*(y + 2) - 5*x*y + 3) * Math.cos(x/5.0);
	}
}
