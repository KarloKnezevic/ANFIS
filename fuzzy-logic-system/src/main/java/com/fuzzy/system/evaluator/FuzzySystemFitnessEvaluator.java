package com.fuzzy.system.evaluator;

import com.fuzzy.data.DataPoint;
import com.fuzzy.data.Dataset;
import com.fuzzy.system.FuzzySystem;
import com.fuzzy.system.FuzzySystemDescriptor;
import com.fuzzy.system.operators.Defuzzifier;
import com.fuzzy.system.operators.TNorm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Evaluates the fitness of a fuzzy system based on a given dataset.
 * The fitness is calculated as the mean squared error.
 */
public class FuzzySystemFitnessEvaluator {

    private final FuzzySystemDescriptor descriptor;
    private final Dataset dataset;

    /**
     * Constructs a new fitness evaluator.
     *
     * @param descriptor the descriptor of the fuzzy system to evaluate
     * @param dataset    the dataset to evaluate the system against
     */
    public FuzzySystemFitnessEvaluator(FuzzySystemDescriptor descriptor, Dataset dataset) {
        this.descriptor = descriptor;
        this.dataset = dataset;
    }

    /**
     * Evaluates the given fuzzy system and returns the mean squared error.
     *
     * @param system the fuzzy system to evaluate
     * @return the mean squared error
     */
    public double evaluate(FuzzySystem system) {
        double totalError = 0;
        List<String> inputVariableNames = descriptor.inputVariables().keySet().stream().toList();

        for (DataPoint dataPoint : dataset.getDataPoints()) {
            Map<String, Double> inputs = new HashMap<>();
            AtomicInteger index = new AtomicInteger(0);
            dataPoint.inputs().forEach(inputValue -> {
                if (index.get() < inputVariableNames.size()) {
                    inputs.put(inputVariableNames.get(index.get()), inputValue);
                    index.getAndIncrement();
                }
            });

            double output = system.compute(inputs);
            double error = output - dataPoint.output();
            totalError += error * error;
        }
        return totalError / dataset.size();
    }

    // A convenience method to create a FuzzySystem and evaluate it directly
    public double evaluate() {
        // For simplicity, using default operators. These could be configurable.
        FuzzySystem system = new FuzzySystem(descriptor, TNorm.MIN, Defuzzifier.CENTER_OF_GRAVITY);
        return evaluate(system);
    }
}
