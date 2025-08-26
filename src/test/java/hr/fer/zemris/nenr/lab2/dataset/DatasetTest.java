package hr.fer.zemris.nenr.lab2.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.nenr.lab2.function.F1;

class DatasetTest {

    @Test
    void testDatasetCreation() {
        Dataset dataset = new Dataset(-4, 4, new F1());
        assertEquals(81, dataset.getTrainingSet().size());
    }
}
