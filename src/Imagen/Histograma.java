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
	//Título de la gráfica y de los ejes X e Y
	String plotTitle = "Histograma"; 
    String xaxis = "Tonos Gris";
    String yaxis = "Pixeles";
    //Orientación de la gráfica
    PlotOrientation orientation = PlotOrientation.VERTICAL;
    //Booleanos random
    boolean show = false; 
    boolean toolTips = false;
    boolean urls = false; 
    //Objeto que almacenará la gráfica
    public JFreeChart chart;
    
    //Constructor
	public Histograma(double[] histogramArray) throws IOException {
		//Tipo del histograma e introducción del array a dicho histograma
		dataset.setType(HistogramType.FREQUENCY);
		dataset.addSeries("Histograma",histogramArray,256);
		//Llamada a la función que creará la gráfica
		chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips, urls);
	}
	
	public File saveChartToJPG(final JFreeChart chart, final int width, final int height) {
        //Strings que almacenarán el nombre y la dirección completa de la imagen
		String result = null;
        String fileName = null;
        
        //Se le asigna un nombre
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
        	//Se llama a la función para pasar a imagen JPG, no devuelve nada, solo crea el fichero
			ChartUtilities.saveChartAsJPEG(new File(result), chart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //Devolvemos el fichero
        return new File(result);
	}
}
