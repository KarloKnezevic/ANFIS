package com.fuzzy.system.model;

import com.fuzzy.system.membership.MembershipFunction;

/**
 * Represents a fuzzy set, which is a component of a linguistic variable.
 * A fuzzy set has a name (e.g., "hot") and an associated membership function.
 *
 * @param name the name of the fuzzy set
 * @param membershipFunction the membership function that defines the set
 */
public record FuzzySet(String name, MembershipFunction membershipFunction) {
}
