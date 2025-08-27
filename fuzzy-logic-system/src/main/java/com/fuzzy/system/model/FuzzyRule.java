package com.fuzzy.system.model;

import java.util.List;

/**
 * Represents a fuzzy rule in the form:
 * IF (antecedent_1 AND antecedent_2 AND ... AND antecedent_n) THEN (consequence)
 *
 * @param antecedents the list of fuzzy sets in the antecedent part of the rule
 * @param consequence the fuzzy set in the consequence part of the rule
 */
public record FuzzyRule(List<FuzzySet> antecedents, FuzzySet consequence) {

    /**
     * Gets a representative crisp value from the consequence fuzzy set.
     * This is typically the center of the membership function of the consequence.
     *
     * @return a representative crisp value for the consequence.
     */
    public double getConsequenceRepresentativeValue() {
        return consequence().membershipFunction().getCenter();
    }
}
