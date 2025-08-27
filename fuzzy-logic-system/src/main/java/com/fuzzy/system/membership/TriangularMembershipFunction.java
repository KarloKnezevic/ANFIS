package com.fuzzy.system.membership;

public final class TriangularMembershipFunction implements MembershipFunction {

    private double a, b, c;

    public TriangularMembershipFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getMembership(double x) {
        if (x <= a || x >= c) {
            return 0.0;
        } else if (x == b) {
            return 1.0;
        } else if (x > a && x < b) {
            return (x - a) / (b - a);
        } else { // x > b && x < c
            return (c - x) / (c - b);
        }
    }

    @Override
    public double getCenter() {
        return b;
    }

    @Override
    public double[] getParameters() {
        return new double[]{a, b, c};
    }

    @Override
    public void setParameters(double[] params) {
        if (params.length != 3) {
            throw new IllegalArgumentException("TriangularMembershipFunction requires 3 parameters.");
        }
        this.a = params[0];
        this.b = params[1];
        this.c = params[2];
    }
}
