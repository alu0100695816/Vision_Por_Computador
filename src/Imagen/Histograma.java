package Imagen;

import javax.swing.JInternalFrame;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class Histograma extends JInternalFrame {

	/**
	 * @param args
	 */
	
	//Set de valores con los que se creará el histograma
	HistogramDataset dataset = new HistogramDataset();
	String plotTitle = "Hist"; 
    String xaxis = "number";
    String yaxis = "value"; 
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    boolean show = false; 
    boolean toolTips = false;
    boolean urls = false; 
    
	public Histograma(double[] histogramArray) {
		super( "Histograma", false, true, false, false );
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		dataset.addSeries("Histograma",histogramArray,histogramArray.length);
		JFreeChart chart = createChart(dataset, "Histograma", plotTitle, xaxis, yaxis, orientation, show, toolTips, urls);
		ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
	}
	
	private JFreeChart createChart(HistogramDataset dataset, String plotTitle, String xaxis, String yaxis, PlotOrientation orientation, boolean show, boolean toolTips, boolean urls ) {
        
		JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);

        return chart;
        
    }
}
