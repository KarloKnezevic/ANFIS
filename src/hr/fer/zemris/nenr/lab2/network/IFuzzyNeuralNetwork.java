package hr.fer.zemris.nenr.lab2.network;

import hr.fer.zemris.nenr.lab2.util.Pair;

import java.util.List;

public interface IFuzzyNeuralNetwork {
	
	public void learnNetworkError(List<Pair> learningDataset, double error);
	
	public void learnNetworkEpoch(List<Pair> learningDataset, int epoch);
	
	public void writeLearnedParams2File();
	
	public void writeRelativeError2File(List<Pair> learningDataset);
	
	public void writeEpochError2File(List<Pair> learningDataset, int epoch);
	
	public void writeEpochErrorForEta2File(double η, List<Pair> learningDataset, int epoch);
	
	public void learnNetworkRules(List<Pair> learningDataset, int startRuleNum, int endRuleNum, int epochPerRule);

	public void validateLearned(List<Pair> learningDataset);

}
