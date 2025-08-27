package com.fuzzy.system.membership;

/**
 * A sealed interface for membership functions. This allows for a fixed set of
 * membership function types, promoting a more robust and predictable system.
 * New membership functions can be added by adding them to the 'permits' clause.
 */
public sealed interface MembershipFunction permits TriangularMembershipFunction, TrapezoidalMembershipFunction, GaussianMembershipFunction {

    /**
     * Calculates the degree of membership for a given input value.
     *
     * @param x the input value
     * @return the degree of membership, a value between 0 and 1
     */
    double getMembership(double x);

    /**
     * Returns the center of the membership function. This is used for
     * defuzzification methods like Center of Gravity.
     *
     * @return the center of the membership function
     */
    double getCenter();

    /**
     * Gets the parameters of the membership function.
     *
     * @return an array of parameters
     */
    double[] getParameters();

    /**
     * Sets the parameters of the membership function.
     *
     * @param params an array of parameters
     */
    void setParameters(double[] params);
}
