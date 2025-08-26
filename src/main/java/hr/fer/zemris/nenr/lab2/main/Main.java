package hr.fer.zemris.nenr.lab2.main;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.membershipFunction.SigmoidMF;
import hr.fer.zemris.nenr.lab2.network.ANFIS;
import hr.fer.zemris.nenr.lab2.network.OfflineGradientDescent;
import hr.fer.zemris.nenr.lab2.tNorm.HamacherProduct;
import hr.fer.zemris.nenr.lab2.tNorm.ITNorm;
import hr.fer.zemris.nenr.lab2.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

		// Load configuration
		Properties props = new Properties();
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
			props.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}

		int numRules = Integer.parseInt(props.getProperty("anfis.rules"));
		double learningRate = Double.parseDouble(props.getProperty("anfis.learningRate"));
		int epochs = Integer.parseInt(props.getProperty("anfis.epochs"));

		// Create ANFIS
		Random rand = new Random();
		ITNorm tNorm = new HamacherProduct();
		ANFIS anfis = ANFIS.create(numRules, SigmoidMF.factory(), SigmoidMF.factory(), tNorm, rand);

		// Create learning algorithm
		OfflineGradientDescent trainer = new OfflineGradientDescent(learningRate);

		// Train the network
		System.out.println("Training started.");
		for (int i = 0; i < epochs; i++) {
			anfis = trainer.learn(anfis, dataset);
			if ((i + 1) % 100 == 0) {
				double error = 0;
				for (Pair p : dataset) {
					error += Math.pow(p.value() - anfis.compute(p.x(), p.y()), 2);
				}
				System.out.printf("\rEpoch %d/%d, MSE: %.6f", i + 1, epochs, error / dataset.size());
			}
		}
		System.out.println("\nTraining finished.");

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
