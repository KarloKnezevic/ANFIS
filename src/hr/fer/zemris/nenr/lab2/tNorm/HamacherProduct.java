package hr.fer.zemris.nenr.lab2.tNorm;

public class HamacherProduct  {

	public double computeNorm(double μA, double μB) {
		
		if (μA > 0 || μB > 0)
			return (μA*μB) / (μA + μB - μA*μB);
		
		return 0;
	}
}
