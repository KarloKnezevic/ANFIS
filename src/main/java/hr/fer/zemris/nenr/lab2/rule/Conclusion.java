package hr.fer.zemris.nenr.lab2.rule;

/**
 * The conclusion part of a TSK fuzzy rule.
 * The conclusion is a linear function of the inputs: z = p*x + q*y + r.
 * @param p The p parameter.
 * @param q The q parameter.
 * @param r The r parameter.
 */
public record Conclusion(double p, double q, double r) {

	/**
	 * Computes the output of the conclusion.
	 * @param x The first input.
	 * @param y The second input.
	 * @return The output of the conclusion.
	 */
	public double compute(double x, double y) {
		return x * p + y * q + r;
	}
}