package com.fuzzy.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dataset {

    private final List<DataPoint> dataPoints;

    private Dataset(List<DataPoint> dataPoints) {
        this.dataPoints = List.copyOf(dataPoints);
    }

    public static Dataset fromDataPoints(List<DataPoint> dataPoints) {
        return new Dataset(dataPoints);
    }

    public static Dataset fromResource(String resourceName) {
        List<DataPoint> points = new ArrayList<>();
        InputStream is = Dataset.class.getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new IllegalArgumentException("Resource not found: " + resourceName);
        }

        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.trim().split("\\s+");
                List<Double> inputs = Arrays.stream(parts, 0, parts.length - 1)
                                            .map(Double::parseDouble)
                                            .collect(Collectors.toList());
                double output = Double.parseDouble(parts[parts.length - 1]);
                points.add(new DataPoint(inputs, output));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read dataset from resource: " + resourceName, e);
        }
        return new Dataset(points);
    }

    public int size() {
        return dataPoints.size();
    }

    public DataPoint get(int index) {
        return dataPoints.get(index);
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
}
