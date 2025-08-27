package com.fuzzy.system;

import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.LinguisticVariable;
import java.util.List;
import java.util.Map;

/**
 * Describes the architecture of a fuzzy system.
 * It contains the input linguistic variables, the output linguistic variable,
 * and the set of fuzzy rules.
 *
 * @param inputVariables  a map of input variable names to LinguisticVariable objects
 * @param outputVariable the output linguistic variable
 * @param rules          the list of fuzzy rules
 */
public record FuzzySystemDescriptor(
    Map<String, LinguisticVariable> inputVariables,
    LinguisticVariable outputVariable,
    List<FuzzyRule> rules) {
}
