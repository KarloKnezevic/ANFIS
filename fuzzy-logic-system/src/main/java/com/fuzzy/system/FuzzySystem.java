package com.fuzzy.system;

import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.FuzzySet;
import com.fuzzy.system.operators.Defuzzifier;
import com.fuzzy.system.operators.TNorm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the core of the fuzzy inference system.
 * This class takes a fuzzy system descriptor and operators to compute a crisp output from crisp inputs.
 */
public class FuzzySystem {

    private final FuzzySystemDescriptor descriptor;
    private final TNorm tNorm;
    private final Defuzzifier defuzzifier;

    /**
     * Constructs a new fuzzy system.
     *
     * @param descriptor  the descriptor that defines the system's architecture
     * @param tNorm       the t-norm operator to use for AND operations
     * @param defuzzifier the defuzzifier to use for producing a crisp output
     */
    public FuzzySystem(FuzzySystemDescriptor descriptor, TNorm tNorm, Defuzzifier defuzzifier) {
        this.descriptor = descriptor;
        this.tNorm = tNorm;
        this.defuzzifier = defuzzifier;
    }

    /**
     * Computes the crisp output value for a given set of inputs.
     *
     * @param inputs a map of input variable names to their crisp values
     * @return the computed crisp output value
     */
    public double compute(Map<String, Double> inputs) {
        // Fuzzification and rule firing
        Map<FuzzyRule, Double> firedRules = new HashMap<>();
        for (FuzzyRule rule : descriptor.rules()) {
            double firingStrength = 1.0;
            List<FuzzySet> antecedents = rule.antecedents();

            // This assumes a fixed order of inputs corresponding to antecedents.
            // A more robust implementation would map inputs to linguistic variables by name.
            int i = 0;
            for (String inputName : descriptor.inputVariables().keySet()) {
                if (i < antecedents.size()) {
                    double inputValue = inputs.get(inputName);
                    double membership = antecedents.get(i).membershipFunction().getMembership(inputValue);
                    firingStrength = tNorm.compute(firingStrength, membership);
                    i++;
                }
            }

            if (firingStrength > 0) {
                firedRules.put(rule, firingStrength);
            }
        }

        // Defuzzification
        return defuzzifier.defuzzify(firedRules);
    }
}
