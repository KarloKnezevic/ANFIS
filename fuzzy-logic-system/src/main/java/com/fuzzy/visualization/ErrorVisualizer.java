package com.fuzzy.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ErrorVisualizer {

    public static void visualize(List<Double> errors, String filePath) throws IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Epoch Error");

        for (int i = 0; i < errors.size(); i++) {
            series.add(i, errors.get(i));
        }
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Epoch Error",
                "Epoch",
                "Error",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }
}
