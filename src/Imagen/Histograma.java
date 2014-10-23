package Imagen;

import javax.swing.JInternalFrame;
import org.jfree.data.statistics.HistogramDataset;

public class Histograma extends JInternalFrame {

	/**
	 * @param args
	 */
	
	//Set de valores con los que se creará el histograma
	HistogramDataset dataset = new HistogramDataset();
	
	public Histograma(double[] histogramArray) {
		super( "Histograma", false, true, false, false );
		dataset.addSeries("Histograma",histogramArray,histogramArray.length);
		
	}
	
}
