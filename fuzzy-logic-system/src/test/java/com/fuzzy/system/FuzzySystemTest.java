package com.fuzzy.system;

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

class FuzzySystemTest {

    @Test
    void testCompute() {
        // Simple tipper example from examples package
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

        Map<String, Double> inputs = new HashMap<>();
        inputs.put("service", 3.0);

        double result = system.compute(inputs);
        // At service=3, poor is 0.4, good is 0.6.
        // Rule 1 fires with strength 0.4, output is 5 (center of low).
        // Rule 2 fires with strength 0.6, output is 25 (center of high).
        // Defuzzified value = (0.4*5 + 0.6*25) / (0.4 + 0.6) = (2 + 15) / 1 = 17
        assertEquals(17.0, result, 0.001);
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
