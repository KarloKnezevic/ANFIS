package com.fuzzy.system.membership;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MembershipFunctionTest {

    @Test
    void testTriangularMembershipFunction() {
        TriangularMembershipFunction tmf = new TriangularMembershipFunction(10, 20, 30);
        assertEquals(0.0, tmf.getMembership(5));
        assertEquals(0.0, tmf.getMembership(10));
        assertEquals(0.5, tmf.getMembership(15));
        assertEquals(1.0, tmf.getMembership(20));
        assertEquals(0.5, tmf.getMembership(25));
        assertEquals(0.0, tmf.getMembership(30));
        assertEquals(0.0, tmf.getMembership(35));
        assertEquals(20, tmf.getCenter());
    }

    @Test
    void testTrapezoidalMembershipFunction() {
        TrapezoidalMembershipFunction tmf = new TrapezoidalMembershipFunction(10, 20, 30, 40);
        assertEquals(0.0, tmf.getMembership(5));
        assertEquals(0.0, tmf.getMembership(10));
        assertEquals(0.5, tmf.getMembership(15));
        assertEquals(1.0, tmf.getMembership(20));
        assertEquals(1.0, tmf.getMembership(25));
        assertEquals(1.0, tmf.getMembership(30));
        assertEquals(0.5, tmf.getMembership(35));
        assertEquals(0.0, tmf.getMembership(40));
        assertEquals(0.0, tmf.getMembership(45));
        assertEquals(25, tmf.getCenter());
    }

    @Test
    void testGaussianMembershipFunction() {
        GaussianMembershipFunction gmf = new GaussianMembershipFunction(50, 10);
        assertEquals(1.0, gmf.getMembership(50));
        assertEquals(0.606, gmf.getMembership(40), 0.001);
        assertEquals(0.606, gmf.getMembership(60), 0.001);
        assertEquals(50, gmf.getCenter());
    }
}
