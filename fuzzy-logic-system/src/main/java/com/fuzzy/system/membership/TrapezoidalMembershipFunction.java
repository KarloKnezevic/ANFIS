package com.fuzzy.system.membership;

public final class TrapezoidalMembershipFunction implements MembershipFunction {

    private final double a, b, c, d;

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
}
