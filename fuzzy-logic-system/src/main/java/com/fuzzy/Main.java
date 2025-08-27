package com.fuzzy;

import com.fuzzy.config.ConfigLoader;
import com.fuzzy.config.Configuration;
import com.fuzzy.data.Dataset;
import com.fuzzy.system.FuzzySystemDescriptor;
import com.fuzzy.system.evaluator.FuzzySystemFitnessEvaluator;
import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.visualization.ErrorVisualizer;
import com.fuzzy.visualization.RuleVisualizer;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.membership.TriangularMembershipFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Fuzzy Logic System starting...");

        // 1. Load Configuration
        Configuration config = ConfigLoader.loadConfig("config.properties");
        logger.info("Configuration loaded: {}", config);

        // 2. Load Dataset
        Dataset dataset = Dataset.fromResource("example.txt");
        logger.info("Dataset loaded with {} data points.", dataset.size());

        // 3. Define the Fuzzy System Architecture
        FuzzySystemDescriptor descriptor = createFuzzySystemDescriptor();
        logger.info("Fuzzy System Descriptor created.");

        // 4. Create the Fitness Evaluator
        FuzzySystemFitnessEvaluator evaluator = new FuzzySystemFitnessEvaluator(descriptor, dataset);
        logger.info("Initial system fitness (error): {}", evaluator.evaluate());

        // 5. Visualize the rules and a dummy error plot
        try {
            RuleVisualizer.visualize(descriptor.rules(), "rules.png");
            logger.info("Fuzzy rules visualization saved to rules.png");

            List<Double> errors = List.of(10.0, 8.0, 6.5, 5.0, 4.0, 3.0, 2.5, 2.0, 1.5, 1.0);
            ErrorVisualizer.visualize(errors, "errors.png");
            logger.info("Epoch error visualization saved to errors.png");
        } catch (java.io.IOException e) {
            logger.error("Failed to save visualizations", e);
        }

        logger.info("Fuzzy Logic System finished.");
    }

    private static FuzzySystemDescriptor createFuzzySystemDescriptor() {
        // This is a dummy implementation for demonstration purposes.
        // A real implementation would define meaningful variables and rules.
        Map<String, LinguisticVariable> inputVariables = new HashMap<>();

        FuzzySet inputSet = new FuzzySet("dummy_in", new TriangularMembershipFunction(0, 1, 2));
        Map<String, FuzzySet> inputSets = new HashMap<>();
        inputSets.put(inputSet.name(), inputSet);
        LinguisticVariable inputVar = new LinguisticVariable("input1", inputSets);
        inputVariables.put(inputVar.name(), inputVar);

        FuzzySet outputSet = new FuzzySet("dummy_out", new TriangularMembershipFunction(0, 1, 2));
        Map<String, FuzzySet> outputSets = new HashMap<>();
        outputSets.put(outputSet.name(), outputSet);
        LinguisticVariable outputVar = new LinguisticVariable("output", outputSets);

        FuzzyRule rule = new FuzzyRule(List.of(inputSet), outputSet);
        List<FuzzyRule> rules = List.of(rule);

        return new FuzzySystemDescriptor(inputVariables, outputVar, rules);
    }
}
