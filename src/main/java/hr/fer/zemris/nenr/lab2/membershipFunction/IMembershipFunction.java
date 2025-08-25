package hr.fer.zemris.nenr.lab2.membershipFunction;

/**
 * Interface for a membership function.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public sealed interface IMembershipFunction permits SigmoidMF {

	/**
	 * Computes the membership value for a given input.
	 * @param x The input value.
	 * @return The membership value.
	 */
	double compute(double x);

	/**
	 * Gets the parameters of the membership function.
	 * @return An array of parameters.
	 */
	double[] getParameters();

	/**
	 * Creates a new membership function with updated parameters.
	 * @param params The new parameters.
	 * @return A new membership function with the updated parameters.
	 */
	IMembershipFunction withParameters(double[] params);

	/**
	 * Gets the number of parameters.
	 * @return The number of parameters.
	 */
	int getNumberOfParameters();

	/**
	 * Computes the derivative of the membership function with respect to a parameter.
	 * @param x The input value.
	 * @param paramIndex The index of the parameter.
	 * @return The derivative.
	 */
	double computeDerivative(double x, int paramIndex);

}
