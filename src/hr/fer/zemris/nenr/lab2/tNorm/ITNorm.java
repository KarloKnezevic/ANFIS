package hr.fer.zemris.nenr.lab2.tNorm;

/**
 * Interface for a T-norm.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public interface ITNorm {

	/**
	 * Computes the T-norm of two values.
	 * @param a The first value.
	 * @param b The second value.
	 * @return The result of the T-norm.
	 */
	double computeNorm(double a, double b);

	/**
	 * Computes the derivative of the T-norm with respect to one of its inputs.
	 * @param a The first value.
	 * @param b The second value.
	 * @param varIndex The index of the variable to differentiate with respect to (0 for a, 1 for b).
	 * @return The derivative.
	 */
	double computeDerivative(double a, double b, int varIndex);

}
