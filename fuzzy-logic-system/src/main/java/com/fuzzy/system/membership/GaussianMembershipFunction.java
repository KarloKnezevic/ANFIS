package com.fuzzy.system.membership;

public final class GaussianMembershipFunction implements MembershipFunction {

    private double mean, sigma;

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

    @Override
    public double[] getParameters() {
        return new double[]{mean, sigma};
    }

    @Override
    public void setParameters(double[] params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("GaussianMembershipFunction requires 2 parameters.");
        }
        this.mean = params[0];
        this.sigma = params[1];
    }
}
