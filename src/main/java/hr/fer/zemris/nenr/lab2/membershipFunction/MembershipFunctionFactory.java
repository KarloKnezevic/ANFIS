package hr.fer.zemris.nenr.lab2.membershipFunction;

import java.util.Random;

@FunctionalInterface
public interface MembershipFunctionFactory {
    IMembershipFunction create(Random rand);
}
