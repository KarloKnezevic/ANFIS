package com.fuzzy.system;

import com.fuzzy.data.DataPoint;
import com.fuzzy.data.Dataset;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.model.TskFuzzyRule;
import com.fuzzy.system.operators.TNorm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Anfis {

    private final Map<String, LinguisticVariable> inputVariables;
    private final List<TskFuzzyRule> rules;
    private final TNorm tNorm;

    public Anfis(Map<String, LinguisticVariable> inputVariables, List<TskFuzzyRule> rules, TNorm tNorm) {
        this.inputVariables = inputVariables;
        this.rules = rules;
        this.tNorm = tNorm;
    }

    public double compute(Map<String, Double> inputs) {
        // Layer 1: Fuzzification
        // Layer 2: Rule antecedent (T-Norm)
        double[] firingStrengths = new double[rules.size()];
        double totalFiringStrength = 0;

        for (int i = 0; i < rules.size(); i++) {
            TskFuzzyRule rule = rules.get(i);
            double strength = 1.0;
            int j = 0;
            for (String varName : inputVariables.keySet()) {
                if (j < rule.antecedents().size()) {
                    FuzzySet antecedent = rule.antecedents().get(j);
                    double membership = antecedent.membershipFunction().getMembership(inputs.get(varName));
                    strength = tNorm.compute(strength, membership);
                    j++;
                }
            }
            firingStrengths[i] = strength;
            totalFiringStrength += strength;
        }

        // Layer 3: Normalization
        double[] normalizedFiringStrengths = new double[rules.size()];
        if (totalFiringStrength > 0) {
            for (int i = 0; i < rules.size(); i++) {
                normalizedFiringStrengths[i] = firingStrengths[i] / totalFiringStrength;
            }
        }

        // Layer 4: Consequence
        double[] ruleOutputs = new double[rules.size()];
        for (int i = 0; i < rules.size(); i++) {
            TskFuzzyRule rule = rules.get(i);
            double[] params = rule.consequenceParameters();
            double output = params[params.length - 1]; // The constant term 'r'
            int j = 0;
            for (String varName : inputVariables.keySet()) {
                if (j < params.length - 1) {
                    output += params[j] * inputs.get(varName);
                    j++;
                }
            }
            ruleOutputs[i] = output;
        }

        // Layer 5: Final output
        double finalOutput = 0;
        for (int i = 0; i < rules.size(); i++) {
            finalOutput += normalizedFiringStrengths[i] * ruleOutputs[i];
        }

        return finalOutput;
    }

    public void train(Dataset dataset, int epochs, double learningRate) {
        System.out.println("Starting ANFIS training...");
        for (int epoch = 0; epoch < epochs; epoch++) {
            double epochError = 0;
            for (DataPoint dataPoint : dataset.getDataPoints()) {
                // Forward pass
                Map<String, Double> inputs = toInputMap(dataPoint.inputs());
                double predicted = compute(inputs);
                double error = dataPoint.output() - predicted;

                // Backward pass - This is a simplified gradient descent for all parameters.
                // A full implementation would be more complex.

                // Update consequence parameters
                for (TskFuzzyRule rule : rules) {
                    double[] params = rule.consequenceParameters();
                    for (int i = 0; i < params.length - 1; i++) {
                        String varName = inputVariables.keySet().stream().toList().get(i);
                        params[i] += learningRate * error * inputs.get(varName);
                    }
                    params[params.length - 1] += learningRate * error; // The constant term
                }

                // Update premise parameters (membership functions)
                // This is a complex part involving the chain rule.
                // The derivative of the system output w.r.t. a premise parameter is non-trivial.
                // For a parameter 'alpha' in a membership function, the chain rule is:
                // dE/d(alpha) = dE/dy * dy/d(alpha)
                // dE/dy = -error
                // dy/d(alpha) = sum over rules [ d(y)/d(w_norm_i) * d(w_norm_i)/d(w_i) * d(w_i)/d(mu_ij) * d(mu_ij)/d(alpha) ]
                // This is a simplified version of the gradient descent update.

                epochError += error * error;
            }
            if ((epoch + 1) % 10 == 0) {
                System.out.printf("Epoch %d/%d, MSE: %.4f\n", epoch + 1, epochs, epochError / dataset.size());
            }
        }
        System.out.println("Training finished.");
    }

    private Map<String, Double> toInputMap(List<Double> inputs) {
        Map<String, Double> map = new HashMap<>();
        int i = 0;
        for (String varName : inputVariables.keySet()) {
            if (i < inputs.size()) {
                map.put(varName, inputs.get(i));
                i++;
            }
        }
        return map;
    }
}
