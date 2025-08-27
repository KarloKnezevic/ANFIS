package com.fuzzy.visualization;

import com.fuzzy.system.model.FuzzyRule;
import com.fuzzy.system.model.FuzzySet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RuleVisualizer {

    public static void visualize(List<FuzzyRule> rules, String filePath) throws IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (FuzzyRule rule : rules) {
            for (FuzzySet antecedent : rule.antecedents()) {
                XYSeries series = new XYSeries(antecedent.name());
                // Sample the membership function to create the chart
                for (double x = 0; x < 100; x += 0.5) {
                    series.add(x, antecedent.membershipFunction().getMembership(x));
                }
                dataset.addSeries(series);
            }
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Fuzzy Rules Visualization",
                "Input Value",
                "Membership Degree",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }
}
