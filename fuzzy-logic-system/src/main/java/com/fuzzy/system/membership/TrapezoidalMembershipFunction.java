package com.fuzzy.system.membership;

public final class TrapezoidalMembershipFunction implements MembershipFunction {

    private double a, b, c, d;

    public TrapezoidalMembershipFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double getMembership(double x) {
        if (x <= a || x >= d) {
            return 0.0;
        } else if (x >= b && x <= c) {
            return 1.0;
        } else if (x > a && x < b) {
            return (x - a) / (b - a);
        } else { // x > c && x < d
            return (d - x) / (d - c);
        }
    }

    @Override
    public double getCenter() {
        return (b + c) / 2.0;
    }

    @Override
    public double[] getParameters() {
        return new double[]{a, b, c, d};
    }

    @Override
    public void setParameters(double[] params) {
        if (params.length != 4) {
            throw new IllegalArgumentException("TrapezoidalMembershipFunction requires 4 parameters.");
        }
        this.a = params[0];
        this.b = params[1];
        this.c = params[2];
        this.d = params[3];
    }
}
