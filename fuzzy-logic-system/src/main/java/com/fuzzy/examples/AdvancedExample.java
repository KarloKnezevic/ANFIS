package com.fuzzy.examples;

import com.fuzzy.system.FuzzySystem;
import com.fuzzy.system.FuzzySystemDescriptor;
import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.model.LinguisticVariable;
import com.fuzzy.system.membership.GaussianMembershipFunction;
import com.fuzzy.system.membership.TrapezoidalMembershipFunction;
import com.fuzzy.system.operators.Defuzzifier;
import com.fuzzy.system.operators.TNorm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedExample {

    public static void main(String[] args) {
        // Define linguistic variables with different membership functions
        LinguisticVariable temperature = createTemperatureVariable();
        LinguisticVariable humidity = createHumidityVariable();
        LinguisticVariable fanSpeed = createFanSpeedVariable();

        // Define rules
        List<FuzzyRule> rules = createRules(temperature, humidity, fanSpeed);

        // Create descriptor
        Map<String, LinguisticVariable> inputVariables = new HashMap<>();
        inputVariables.put("temperature", temperature);
        inputVariables.put("humidity", humidity);
        FuzzySystemDescriptor descriptor = new FuzzySystemDescriptor(inputVariables, fanSpeed, rules);

        // Create fuzzy system with different operators
        FuzzySystem fuzzySystem = new FuzzySystem(descriptor, TNorm.PRODUCT, Defuzzifier.CENTER_OF_GRAVITY);

        // Provide inputs
        Map<String, Double> inputs = new HashMap<>();
        inputs.put("temperature", 28.0);
        inputs.put("humidity", 65.0);

        // Compute the result
        double result = fuzzySystem.compute(inputs);
        System.out.printf("Temperature: %.1f, Humidity: %.1f -> Fan Speed: %.2f RPM\n",
                inputs.get("temperature"), inputs.get("humidity"), result);
    }

    private static LinguisticVariable createTemperatureVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("cold", new FuzzySet("cold", new GaussianMembershipFunction(0, 10)));
        sets.put("warm", new FuzzySet("warm", new TrapezoidalMembershipFunction(15, 20, 25, 30)));
        sets.put("hot", new FuzzySet("hot", new GaussianMembershipFunction(35, 10)));
        return new LinguisticVariable("temperature", sets);
    }

    private static LinguisticVariable createHumidityVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("dry", new FuzzySet("dry", new TrapezoidalMembershipFunction(0, 20, 30, 50)));
        sets.put("normal", new FuzzySet("normal", new GaussianMembershipFunction(50, 15)));
        sets.put("wet", new FuzzySet("wet", new TrapezoidalMembershipFunction(50, 70, 80, 100)));
        return new LinguisticVariable("humidity", sets);
    }

    private static LinguisticVariable createFanSpeedVariable() {
        Map<String, FuzzySet> sets = new HashMap<>();
        sets.put("slow", new FuzzySet("slow", new GaussianMembershipFunction(1000, 500)));
        sets.put("medium", new FuzzySet("medium", new TrapezoidalMembershipFunction(2000, 3000, 4000, 5000)));
        sets.put("fast", new FuzzySet("fast", new GaussianMembershipFunction(6000, 1000)));
        return new LinguisticVariable("fanSpeed", sets);
    }

    private static List<FuzzyRule> createRules(LinguisticVariable temperature, LinguisticVariable humidity, LinguisticVariable fanSpeed) {
        FuzzySet tempCold = temperature.getFuzzySet("cold").get();
        FuzzySet tempWarm = temperature.getFuzzySet("warm").get();
        FuzzySet tempHot = temperature.getFuzzySet("hot").get();

        FuzzySet humDry = humidity.getFuzzySet("dry").get();
        FuzzySet humNormal = humidity.getFuzzySet("normal").get();
        FuzzySet humWet = humidity.getFuzzySet("wet").get();

        FuzzySet fanSlow = fanSpeed.getFuzzySet("slow").get();
        FuzzySet fanMedium = fanSpeed.getFuzzySet("medium").get();
        FuzzySet fanFast = fanSpeed.getFuzzySet("fast").get();

        return List.of(
                new FuzzyRule(List.of(tempCold, humDry), fanSlow),
                new FuzzyRule(List.of(tempWarm, humDry), fanMedium),
                new FuzzyRule(List.of(tempHot, humDry), fanFast),
                new FuzzyRule(List.of(tempCold, humNormal), fanSlow),
                new FuzzyRule(List.of(tempWarm, humNormal), fanMedium),
                new FuzzyRule(List.of(tempHot, humNormal), fanFast),
                new FuzzyRule(List.of(tempCold, humWet), fanMedium),
                new FuzzyRule(List.of(tempWarm, humWet), fanFast),
                new FuzzyRule(List.of(tempHot, humWet), fanFast)
        );
    }
}
