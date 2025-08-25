package hr.fer.zemris.nenr.lab2.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.nenr.lab2.function.IFunction;
import hr.fer.zemris.nenr.lab2.util.Pair;

public class Dataset {
	
	private int min;
	private int max;
	private IFunction function;
	
	private List<Pair> trainingSet;
	
	public Dataset(int min, int max, IFunction function) {
		
		this.min = min;
		this.max = max;
		this.function = function;
		
		createLearningDataset();
		
	}
	
	private void createLearningDataset() {
		
		trainingSet = new ArrayList<>();
		
		for (int i = min; i <= max; i++) {
			for (int j = min; j<= max; j++) {
				trainingSet.add(new Pair(
						i,j,function.compute(i, j)
						)
				);
			}
		}
		
	}
	
	public List<Pair> getValidationSet(Random rand, int setSize) {
		
		List<Pair> validationSet = new ArrayList<>();
		
		for (int i = 0; i < setSize; i++) {
			double x = (max-min)*rand.nextDouble() + min;
			double y = (max-min)*rand.nextDouble() + min;
			validationSet.add(new Pair(
					x,y,function.compute(x, y)
					)
			);
		}
		
		return validationSet;
		
	}

	public List<Pair> getTrainingSet() {
		return trainingSet;
	}
	
}