package com.fuzzy.system.operators;

@FunctionalInterface
public interface TNorm {
    double compute(double a, double b);

    /**
     * A common t-norm implementation (minimum).
     */
    TNorm MIN = Math::min;

    /**
     * Another common t-norm (product).
     */
    TNorm PRODUCT = (a, b) -> a * b;
}
