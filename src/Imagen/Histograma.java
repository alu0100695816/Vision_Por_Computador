package Imagen;

import java.io.File;
import java.io.IOException;

import javax.swing.JInternalFrame;

import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class Histograma {

	/**
	 * @param args
	 */
	
	//Set de valores con los que se creará el histograma
	HistogramDataset dataset = new HistogramDataset();
	String plotTitle = "Histograma"; 
    String xaxis = "number";
    String yaxis = "value"; 
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    boolean show = false; 
    boolean toolTips = false;
    boolean urls = false; 
    public JFreeChart chart;
    
	public Histograma(double[] histogramArray) throws IOException {
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		dataset.addSeries("Histograma",histogramArray,histogramArray.length);
		chart = createChart(dataset, plotTitle, xaxis, yaxis, orientation, show, toolTips, urls);
	}
	
	private JFreeChart createChart(HistogramDataset dataset, String plotTitle, String xaxis, String yaxis, PlotOrientation orientation, boolean show, boolean toolTips, boolean urls ) {
        
		JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis,
                dataset, orientation, show, toolTips, urls);

        return chart;
        
    }
	
	public File saveChartToJPG(final JFreeChart chart, final int width, final int height) {
        String result = null;
        String fileName = null;
        
        if (chart != null) {
        	final String chartTitle = chart.getTitle().getText();
            if (chartTitle != null) {
                fileName = chartTitle;
            } else {
                fileName = "chart";
            }
        }
		result = fileName+".jpg";
        try {
			ChartUtilities.saveChartAsJPEG(new File(result), chart, width, height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return new File(result);
	}
}
