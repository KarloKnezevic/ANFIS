package hr.fer.zemris.nenr.lab2.tNorm;

/**
 * Hamacher product T-norm.
 * @author Karlo Knezevic, karlo.knezevic@fer.hr
 *
 */
public final class HamacherProduct implements ITNorm {

	@Override
	public double computeNorm(double a, double b) {
		if (a > 0 || b > 0) {
			return (a * b) / (a + b - a * b);
		}
		return 0;
	}

	@Override
	public double computeDerivative(double a, double b, int varIndex) {
		double denominator = a + b - a * b;
		if (denominator == 0) {
			return 0; // or throw an exception, depending on desired behavior
		}
		if (varIndex == 0) { // derivative with respect to a
			return (b * b) / (denominator * denominator);
		} else if (varIndex == 1) { // derivative with respect to b
			return (a * a) / (denominator * denominator);
		}
		return 0;
	}
}
