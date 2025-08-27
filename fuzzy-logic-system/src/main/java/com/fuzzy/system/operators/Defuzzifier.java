package com.fuzzy.system.operators;

import com.fuzzy.system.model.FuzzyRule;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface Defuzzifier {
    /**
     * Defuzzifies the output of the fuzzy system.
     *
     * @param firedRules A map of fuzzy rules to their firing strengths.
     * @return the crisp output value.
     */
    double defuzzify(Map<FuzzyRule, Double> firedRules);

    /**
     * A common defuzzification method: Center of Gravity (COG).
     * This is a simplified version and might need adjustment based on the
     * specific representation of the fuzzy set outputs.
     * For this example, we assume the output of a rule is a single crisp value
     * for simplicity, which is a common approach in Mamdani-style systems
     * where the output fuzzy sets are simplified to singletons.
     */
    Defuzzifier CENTER_OF_GRAVITY = (firedRules) -> {
        double numerator = 0;
        double denominator = 0;
        for (Map.Entry<FuzzyRule, Double> entry : firedRules.entrySet()) {
            double firingStrength = entry.getValue();
            // Assuming the consequence is a simple fuzzy set with a representative value (e.g., its center)
            // This is a simplification. A full COG would integrate over the shape of the output fuzzy set.
            // For now, let's assume the rule's consequence has a representative crisp value.
            // This part of the logic will need to be connected to the FuzzyRule's structure.
            // Let's assume a method getConsequenceRepresentativeValue() exists on FuzzyRule for now.
            double representativeValue = entry.getKey().getConsequenceRepresentativeValue();
            numerator += firingStrength * representativeValue;
            denominator += firingStrength;
        }
        return denominator == 0 ? 0 : numerator / denominator;
    };
}
