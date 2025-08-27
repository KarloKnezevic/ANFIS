package com.fuzzy.data;

import java.util.List;

/**
 * Represents a single data point in a dataset.
 * It consists of a list of input values and an expected output value.
 *
 * @param inputs the input values
 * @param output the output value
 */
public record DataPoint(List<Double> inputs, double output) {
}
