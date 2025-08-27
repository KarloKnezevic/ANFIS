package com.fuzzy.system.evaluator;

import com.fuzzy.data.DataPoint;
import com.fuzzy.data.Dataset;
import com.fuzzy.system.FuzzySystem;
import com.fuzzy.system.FuzzySystemDescriptor;
import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.membership.TriangularMembershipFunction;
import com.fuzzy.system.operators.Defuzzifier;
import com.fuzzy.system.operators.TNorm;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class FuzzySystemFitnessEvaluatorTest {

    @Test
    void testEvaluate() {
        // Using the same system as in FuzzySystemTest
        LinguisticVariable service = createServiceVariable();
        LinguisticVariable tip = createTipVariable();
        Map<String, LinguisticVariable> inputVariables = new HashMap<>();
        inputVariables.put("service", service);

        FuzzySet servicePoor = service.getFuzzySet("poor").get();
        FuzzySet serviceGood = service.getFuzzySet("good").get();
        FuzzySet tipLow = tip.getFuzzySet("low").get();
        FuzzySet tipHigh = tip.getFuzzySet("high").get();

        List<FuzzyRule> rules = List.of(
                new FuzzyRule(List.of(servicePoor), tipLow),
                new FuzzyRule(List.of(serviceGood), tipHigh)
        );

        FuzzySystemDescriptor descriptor = new FuzzySystemDescriptor(inputVariables, tip, rules);
        FuzzySystem system = new FuzzySystem(descriptor, TNorm.MIN, Defuzzifier.CENTER_OF_GRAVITY);

        // Create a dataset
        List<DataPoint> dataPoints = List.of(
                new DataPoint(List.of(3.0), 17.0), // Perfect match
                new DataPoint(List.of(7.0), 25.0)  // Should be high tip
        );
        Dataset dataset = Dataset.fromDataPoints(dataPoints);

        FuzzySystemFitnessEvaluator evaluator = new FuzzySystemFitnessEvaluator(descriptor, dataset);
        double error = evaluator.evaluate(system);

        // For service=7.0, poor=0, good=1. Only rule 2 fires. Output = 25.
        // Error for point 1: 17 - 17 = 0
        // Error for point 2: 25 - 25 = 0
        // Total error should be 0
        assertEquals(0.0, error, 0.001);
    }

    private static LinguisticVariable createServiceVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("poor", new FuzzySet("poor", new TriangularMembershipFunction(0, 0, 5)));
        sets.put("good", new FuzzySet("good", new TriangularMembershipFunction(0, 5, 10)));
        return new LinguisticVariable("service", sets);
    }

    private static LinguisticVariable createTipVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("low", new FuzzySet("low", new TriangularMembershipFunction(0, 5, 10)));
        sets.put("high", new FuzzySet("high", new TriangularMembershipFunction(20, 25, 30)));
        return new LinguisticVariable("tip", sets);
    }
}
