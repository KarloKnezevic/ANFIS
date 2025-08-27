package com.fuzzy.system.model;

import java.util.List;

/**
 * Represents a Takagi-Sugeno-Kang (TSK) fuzzy rule.
 * The consequence of a TSK rule is a linear function of the inputs,
 * e.g., y = p*x1 + q*x2 + ... + r.
 *
 * @param antecedents           the list of fuzzy sets in the antecedent part of the rule
 * @param consequenceParameters the array of coefficients for the linear function
 *                              (e.g., [p, q, ..., r])
 */
public record TskFuzzyRule(List<FuzzySet> antecedents, double[] consequenceParameters) {
}
