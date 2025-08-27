package com.fuzzy.system;

import com.fuzzy.data.DataPoint;
import com.fuzzy.data.Dataset;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.model.TskFuzzyRule;
import com.fuzzy.system.membership.GaussianMembershipFunction;
import com.fuzzy.system.operators.TNorm;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class AnfisTest {

    @Test
    void testCompute() {
        Anfis anfis = createAnfis();
        Map<String, Double> inputs = new HashMap<>();
        inputs.put("x1", 3.0);
        inputs.put("x2", 7.0);

        double result = anfis.compute(inputs);
        assertEquals(5.3221, result, 0.0001);
    }

    @Test
    void testTrain() {
        Anfis anfis = createAnfis();
        Map<String, Double> inputs = new HashMap<>();
        inputs.put("x1", 3.0);
        inputs.put("x2", 7.0);

        double resultBefore = anfis.compute(inputs);
        assertEquals(5.3221, resultBefore, 0.0001);

        Dataset dataset = createTrainingDataset();
        anfis.train(dataset, 10, 0.01);

        double resultAfter = anfis.compute(inputs);
        assertNotEquals(resultBefore, resultAfter);
    }

    private Anfis createAnfis() {
        LinguisticVariable x1 = createVariable("x1", 0, 10);
        LinguisticVariable x2 = createVariable("x2", 0, 10);
        Map<String, LinguisticVariable> inputVariables = new HashMap<>();
        inputVariables.put("x1", x1);
        inputVariables.put("x2", x2);
        List<TskFuzzyRule> rules = createRules(x1, x2);
        return new Anfis(inputVariables, rules, TNorm.PRODUCT);
    }

    private LinguisticVariable createVariable(String name, double mean1, double mean2) {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("low", new FuzzySet("low", new GaussianMembershipFunction(mean1, 2)));
        sets.put("high", new FuzzySet("high", new GaussianMembershipFunction(mean2, 2)));
        return new LinguisticVariable(name, sets);
    }

    private List<TskFuzzyRule> createRules(LinguisticVariable x1, LinguisticVariable x2) {
        FuzzySet x1Low = x1.getFuzzySet("low").get();
        FuzzySet x1High = x1.getFuzzySet("high").get();
        FuzzySet x2Low = x2.getFuzzySet("low").get();
        FuzzySet x2High = x2.getFuzzySet("high").get();

        return List.of(
            new TskFuzzyRule(List.of(x1Low, x2Low), new double[]{0.1, 0.2, 0.3}),
            new TskFuzzyRule(List.of(x1Low, x2High), new double[]{0.4, 0.5, 0.6}),
            new TskFuzzyRule(List.of(x1High, x2Low), new double[]{0.7, 0.8, 0.9}),
            new TskFuzzyRule(List.of(x1High, x2High), new double[]{1.0, 1.1, 1.2})
        );
    }

    private Dataset createTrainingDataset() {
        List<DataPoint> dataPoints = List.of(
            new DataPoint(List.of(1.0, 2.0), 2.0),
            new DataPoint(List.of(1.0, 8.0), 6.0),
            new DataPoint(List.of(9.0, 2.0), 8.0),
            new DataPoint(List.of(9.0, 8.0), 12.0)
        );
        return Dataset.fromDataPoints(dataPoints);
    }
}
