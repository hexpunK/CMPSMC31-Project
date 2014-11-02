package uk.ac.uea.mathsthing.gui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Initializes a graph to be drawn to show the user the results of a mathematical function.
 * 
 * @author Jake Ruston
 * @version 0.1
 */
public class Graph extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/** The frame for the chart. */
	private ChartPanel chartPanel;
    
	/**
	 * Constructor to create a graph.
	 * @param frame The root window this chart will be displayed in.
	 */
    public Graph(final JFrame frame) {
    	
        JFreeChart chart = createChart("", new HashMap<Double, BigDecimal>());
    	chartPanel = new ChartPanel(chart);
        
    	// Set the location of the chart and hide it until the user enters a formula.
        chartPanel.setSize(frame.getWidth()-30, frame.getHeight()-140);
        int centerX = (frame.getWidth() - chartPanel.getWidth()) / 2;
        chartPanel.setLocation(centerX, 90);
        chartPanel.setVisible(false);
        frame.add(chartPanel);
    }
    
    /**
     * Re-draws the chart based on a new dataset.
     * @param title The title of the chart - usually the formula.
     * @param results The results to display - the value of x and the value of y as a HashMap.
     */
    public void updateChart(String title, HashMap<Double, BigDecimal> results) {
    	
    	chartPanel.setVisible(true);
    	JFreeChart chart = createChart(title, results);
    	chartPanel.setChart(chart);
    }
    
    /**
     * Changes the visibility of the chart. Used to hide the chart when the reset button is clicked.
     * @param visibility True to make the chart visible, false to hide it.
     */
    public void changeVisibility(boolean visibility) {
    	chartPanel.setVisible(visibility);
    }
    
    /**
     * Method to save the chart to the designated file.
     * @param file The file to save the chart to.
     */
    public void saveChartToFile(File file) {
    	
    	// Attempt to save the chart as a 1024 x 768 PNG image.
    	try {
			ChartUtilities.saveChartAsPNG(file, chartPanel.getChart(), 1024, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Method to create a chart so it can be displayed in the window.
     * @param title The title of the chart.
     * @param results The results as a HashMap - containing values of x and values of y.
     * @return The JFreeChart object, the chart to draw.
     */
    private JFreeChart createChart(String title, HashMap<Double, BigDecimal> results) {
    	
    	XYDataset dataset = createDataset(results);
    	
    	// Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "x",
            "y",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

        chart.setBackgroundPaint(Color.white);
        
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
    }
    
    /**
     * Converts the HashMap of results into a dataset for the chart.
     * @param results The results to display as values of x and values of y
     * @return The dataset for the chart.
     */
    private static XYDataset createDataset(HashMap<Double, BigDecimal> results) {
        
        final XYSeries series = new XYSeries("x");
        
        // Loop through each of the values, adding it to the chart series.
        for (Map.Entry<Double, BigDecimal> entry : results.entrySet()) {
            series.add(entry.getKey(), entry.getValue());
        }

        // Prepare the dataset to be displayed as an XY line graph.
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
                
        return dataset;
        
    }

}