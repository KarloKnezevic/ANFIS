package hr.fer.zemris.nenr.lab2.rule;

import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.tNorm.ITNorm;

/**
 * Represents a fuzzy rule in the ANFIS network.
 * The rule is of the form: IF x is A AND y is B THEN z = f(x, y)
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public class Rule {
	
	public IMembershipFunction mfA;
	public IMembershipFunction mfB;
	public ITNorm norm;
	public Conclusion conclusion;
	
	public Rule(IMembershipFunction mfA, IMembershipFunction mfB, ITNorm norm,
			Conclusion conclusion) {
		this.mfA = mfA;
		this.mfB = mfB;
		this.norm = norm;
		this.conclusion = conclusion;
	}
	
	/**
	 * Computes the conclusion of the rule.
	 * @param x The first input.
	 * @param y The second input.
	 * @return The conclusion of the rule.
	 */
	public double conclude(double x, double y) {
		return conclusion.compute(x, y);
	}
	
	/**
	 * Computes the weight of the rule.
	 * @param x The first input.
	 * @param y The second input.
	 * @return The weight of the rule.
	 */
	public double weight(double x, double y) {
		return norm.computeNorm(mfA.compute(x), mfB.compute(y));
	}

}