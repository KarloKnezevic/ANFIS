package com.fuzzy.system.operators;

@FunctionalInterface
public interface SNorm {
    double compute(double a, double b);

    /**
     * A common s-norm implementation (maximum).
     */
    SNorm MAX = Math::max;

    /**
     * Another common s-norm (probabilistic sum).
     */
    SNorm SUM = (a, b) -> a + b - a * b;
}
