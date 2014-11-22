package uk.ac.uea.mathsthing.gui;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Initializes a graph to be drawn to show the user the results of a mathematical function.
 * 
 * @author Jake Ruston
 * @version 0.1
 */
public class Graph extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/** The frame for the chart. */
	private JFXPanel chartPanel;
    
	/**
	 * Constructor to create a graph.
	 * @param frame The root window this chart will be displayed in.
	 */
    public Graph(final JFrame frame) {
    	
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
              
            	final NumberAxis xAxis = new NumberAxis();
                final NumberAxis yAxis = new NumberAxis();
            	final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis, yAxis);
            	
            	chartPanel = new JFXPanel();
            	chartPanel.setSize(frame.getWidth()-30, frame.getHeight()-140);
            	int centerX = (frame.getWidth() - chartPanel.getWidth()) / 2;
                chartPanel.setLocation(centerX, 90);
                chartPanel.setVisible(false);
                Scene scene = new Scene(lineChart);
                chartPanel.setScene(scene);
                frame.add(chartPanel);
            }
       });
        
    }
    
    /**
     * Re-draws the chart based on a new dataset.
     * @param title The title of the chart - usually the formula.
     * @param xAxis The name of the x axis for the chart.
     * @param yAxis The name of the y axis for the chart.
     * @param results The results to display - the value of x and the value of y as a HashMap.
     */
    public void updateChart(final String title, final String xAxis, final String yAxis, final LinkedHashMap<Double, BigDecimal> results) {
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
              
            	chartPanel.setVisible(true);
            	LineChart<Number,Number> chart = createChart(title, xAxis, yAxis, results);
            	Scene scene = new Scene(chart);
                chartPanel.setScene(scene);
            }
       });
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
    	
    	// This needs rewriting to work with the new chart.
    	
    	/*// Attempt to save the chart as a 1024 x 768 PNG image.
    	try {
			ChartUtilities.saveChartAsPNG(file, chartPanel.getChart(), 1024, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }
    
    /**
     * Method to create a chart so it can be displayed in the window.
     * @param title The title of the chart.
     * @param xAxis The name of the x axis for the chart.
     * @param yAxis The name of the y axis for the chart.
     * @param results The results as a HashMap - containing values of x and values of y.
     * @return The JFreeChart object, the chart to draw.
     */
    private LineChart<Number,Number> createChart(String title, String xAxis, String yAxis, LinkedHashMap<Double, BigDecimal> results) {
    	
    	final NumberAxis x = new NumberAxis();
        final NumberAxis y = new NumberAxis();
        x.setLabel(xAxis);
        y.setLabel(yAxis);
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(x,y);
        lineChart.setTitle(title);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(true);
        lineChart.setLegendVisible(false);
        
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        
        for (Map.Entry<Double, BigDecimal> entry : results.entrySet()) {
            series.getData().add(new XYChart.Data<Number,Number>(entry.getKey(), entry.getValue()));
        }
        
        lineChart.getData().add(series);
        
        return lineChart;
    }
}