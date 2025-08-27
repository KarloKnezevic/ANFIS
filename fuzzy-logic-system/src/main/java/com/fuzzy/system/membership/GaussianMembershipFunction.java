package com.fuzzy.system.membership;

public final class GaussianMembershipFunction implements MembershipFunction {

    private final double mean, sigma;

    public GaussianMembershipFunction(double mean, double sigma) {
        this.mean = mean;
        this.sigma = sigma;
    }

    @Override
    public double getMembership(double x) {
        return Math.exp(-0.5 * Math.pow((x - mean) / sigma, 2));
    }

    @Override
    public double getCenter() {
        return mean;
    }
}
