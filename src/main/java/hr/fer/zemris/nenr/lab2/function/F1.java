package hr.fer.zemris.nenr.lab2.function;

public class F1 implements IFunction {

	@Override
	public double compute(double x, double y) {

		return
				((x-1)*(x-1) + (y+2)*(y+2) - 5*x*y + 3) * 
				Math.cos((double)x/5)*Math.cos((double)x/5);

	}

}
