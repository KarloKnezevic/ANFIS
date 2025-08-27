package com.fuzzy.examples;

import com.fuzzy.data.DataPoint;
import com.fuzzy.data.Dataset;
import com.fuzzy.system.Anfis;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.model.TskFuzzyRule;
import com.fuzzy.system.membership.GaussianMembershipFunction;
import com.fuzzy.system.operators.TNorm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnfisExample {

    public static void main(String[] args) {
        // Define linguistic variables
        LinguisticVariable x1 = createVariable("x1", 0, 10);
        LinguisticVariable x2 = createVariable("x2", 0, 10);

        Map<String, LinguisticVariable> inputVariables = new HashMap<>();
        inputVariables.put("x1", x1);
        inputVariables.put("x2", x2);

        // Define TSK rules
        List<TskFuzzyRule> rules = createRules(x1, x2);

        // Create ANFIS network
        Anfis anfis = new Anfis(inputVariables, rules, TNorm.PRODUCT);

        // Provide inputs
        Map<String, Double> inputs = new HashMap<>();
        inputs.put("x1", 3.0);
        inputs.put("x2", 7.0);

        // Compute the result before training
        double resultBefore = anfis.compute(inputs);
        System.out.printf("Before training: Inputs: x1=%.1f, x2=%.1f -> Output: %.4f\n",
                inputs.get("x1"), inputs.get("x2"), resultBefore);

        // Create a dummy dataset for training
        Dataset dataset = createTrainingDataset();

        // Train the ANFIS network
        anfis.train(dataset, 100, 0.01);

        // Compute the result after training
        double resultAfter = anfis.compute(inputs);
        System.out.printf("After training:  Inputs: x1=%.1f, x2=%.1f -> Output: %.4f\n",
                inputs.get("x1"), inputs.get("x2"), resultAfter);
    }

    private static LinguisticVariable createVariable(String name, double mean1, double mean2) {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("low", new FuzzySet("low", new GaussianMembershipFunction(mean1, 2)));
        sets.put("high", new FuzzySet("high", new GaussianMembershipFunction(mean2, 2)));
        return new LinguisticVariable(name, sets);
    }

    private static List<TskFuzzyRule> createRules(LinguisticVariable x1, LinguisticVariable x2) {
        FuzzySet x1Low = x1.getFuzzySet("low").get();
        FuzzySet x1High = x1.getFuzzySet("high").get();
        FuzzySet x2Low = x2.getFuzzySet("low").get();
        FuzzySet x2High = x2.getFuzzySet("high").get();

        // Rule 1: IF x1 is low AND x2 is low THEN y = 0.1*x1 + 0.2*x2 + 0.3
        TskFuzzyRule rule1 = new TskFuzzyRule(
                List.of(x1Low, x2Low),
                new double[]{0.1, 0.2, 0.3}
        );

        // Rule 2: IF x1 is low AND x2 is high THEN y = 0.4*x1 + 0.5*x2 + 0.6
        TskFuzzyRule rule2 = new TskFuzzyRule(
                List.of(x1Low, x2High),
                new double[]{0.4, 0.5, 0.6}
        );

        // Rule 3: IF x1 is high AND x2 is low THEN y = 0.7*x1 + 0.8*x2 + 0.9
        TskFuzzyRule rule3 = new TskFuzzyRule(
                List.of(x1High, x2Low),
                new double[]{0.7, 0.8, 0.9}
        );

        // Rule 4: IF x1 is high AND x2 is high THEN y = 1.0*x1 + 1.1*x2 + 1.2
        TskFuzzyRule rule4 = new TskFuzzyRule(
                List.of(x1High, x2High),
                new double[]{1.0, 1.1, 1.2}
        );

        return List.of(rule1, rule2, rule3, rule4);
    }

    private static Dataset createTrainingDataset() {
        List<DataPoint> dataPoints = List.of(
            new DataPoint(List.of(1.0, 2.0), 2.0),
            new DataPoint(List.of(1.0, 8.0), 6.0),
            new DataPoint(List.of(9.0, 2.0), 8.0),
            new DataPoint(List.of(9.0, 8.0), 12.0)
        );
        return Dataset.fromDataPoints(dataPoints);
    }
}
