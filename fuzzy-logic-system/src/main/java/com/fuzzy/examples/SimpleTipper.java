package com.fuzzy.examples;

import com.fuzzy.system.FuzzySystem;
import com.fuzzy.system.FuzzySystemDescriptor;
import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.membership.TriangularMembershipFunction;
import com.fuzzy.system.operators.Defuzzifier;
import com.fuzzy.system.operators.TNorm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleTipper {

    public static void main(String[] args) {
        // Define linguistic variables
        LinguisticVariable service = createServiceVariable();
        LinguisticVariable foodQuality = createFoodQualityVariable();
        LinguisticVariable tip = createTipVariable();

        // Define rules
        List<FuzzyRule> rules = createRules(service, foodQuality, tip);

        // Create descriptor
        Map<String, LinguisticVariable> inputVariables = new HashMap<>();
        inputVariables.put("service", service);
        inputVariables.put("foodQuality", foodQuality);
        FuzzySystemDescriptor descriptor = new FuzzySystemDescriptor(inputVariables, tip, rules);

        // Create fuzzy system
        FuzzySystem fuzzySystem = new FuzzySystem(descriptor, TNorm.MIN, Defuzzifier.CENTER_OF_GRAVITY);

        // Provide inputs
        Map<String, Double> inputs = new HashMap<>();
        inputs.put("service", 7.5);
        inputs.put("foodQuality", 8.5);

        // Compute the result
        double result = fuzzySystem.compute(inputs);
        System.out.printf("Service quality: %.1f, Food quality: %.1f -> Tip: %.2f%%\n",
                inputs.get("service"), inputs.get("foodQuality"), result);
    }

    private static LinguisticVariable createServiceVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("poor", new FuzzySet("poor", new TriangularMembershipFunction(0, 0, 5)));
        sets.put("good", new FuzzySet("good", new TriangularMembershipFunction(0, 5, 10)));
        sets.put("excellent", new FuzzySet("excellent", new TriangularMembershipFunction(5, 10, 10)));
        return new LinguisticVariable("service", sets);
    }

    private static LinguisticVariable createFoodQualityVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("rancid", new FuzzySet("rancid", new TriangularMembershipFunction(0, 0, 5)));
        sets.put("delicious", new FuzzySet("delicious", new TriangularMembershipFunction(5, 10, 10)));
        return new LinguisticVariable("foodQuality", sets);
    }

    private static LinguisticVariable createTipVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("low", new FuzzySet("low", new TriangularMembershipFunction(0, 5, 10)));
        sets.put("medium", new FuzzySet("medium", new TriangularMembershipFunction(10, 15, 20)));
        sets.put("high", new FuzzySet("high", new TriangularMembershipFunction(20, 25, 30)));
        return new LinguisticVariable("tip", sets);
    }

    private static List<FuzzyRule> createRules(LinguisticVariable service, LinguisticVariable foodQuality, LinguisticVariable tip) {
        FuzzySet servicePoor = service.getFuzzySet("poor").get();
        FuzzySet serviceGood = service.getFuzzySet("good").get();
        FuzzySet serviceExcellent = service.getFuzzySet("excellent").get();

        FuzzySet foodRancid = foodQuality.getFuzzySet("rancid").get();
        FuzzySet foodDelicious = foodQuality.getFuzzySet("delicious").get();

        FuzzySet tipLow = tip.getFuzzySet("low").get();
        FuzzySet tipMedium = tip.getFuzzySet("medium").get();
        FuzzySet tipHigh = tip.getFuzzySet("high").get();

        return List.of(
                new FuzzyRule(List.of(servicePoor, foodRancid), tipLow),
                new FuzzyRule(List.of(serviceGood, foodRancid), tipLow),
                new FuzzyRule(List.of(serviceExcellent, foodRancid), tipMedium),
                new FuzzyRule(List.of(servicePoor, foodDelicious), tipMedium),
                new FuzzyRule(List.of(serviceGood, foodDelicious), tipMedium),
                new FuzzyRule(List.of(serviceExcellent, foodDelicious), tipHigh)
        );
    }
}
