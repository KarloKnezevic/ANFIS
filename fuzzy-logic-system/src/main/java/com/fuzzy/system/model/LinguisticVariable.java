package com.fuzzy.system.model;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a linguistic variable, which is a variable whose values are words
 * or sentences in a natural or artificial language. For example, "temperature"
 * is a linguistic variable if its values are "low", "medium", "high".
 *
 * @param name the name of the linguistic variable
 * @param fuzzySets a map of fuzzy set names to FuzzySet objects
 */
public record LinguisticVariable(String name, Map<String, FuzzySet> fuzzySets) {

    /**
     * Gets a fuzzy set by its name.
     *
     * @param name the name of the fuzzy set
     * @return an Optional containing the fuzzy set if found, otherwise empty
     */
    public Optional<FuzzySet> getFuzzySet(String name) {
        return Optional.ofNullable(fuzzySets.get(name));
    }
}
