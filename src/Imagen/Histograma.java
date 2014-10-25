package Imagen;

import java.io.File;
import java.io.IOException;

import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class Histograma {

	/**
	 * @param args
	 */
	
	//Set de valores con los que se crear� el histograma
	private HistogramDataset dataset = new HistogramDataset();
	//T�tulo de la gr�fica y de los ejes X e Y
	private String plotTitle = "Histograma"; 
    private String xaxis = "Tonos Gris";
    private String yaxis = "Pixeles";
    //Orientaci�n de la gr�fica
    private PlotOrientation orientation = PlotOrientation.VERTICAL;
    //Booleanos random
    boolean show = false; 
    boolean toolTips = false;
    boolean urls = false; 
    //Objeto que almacenar� la gr�fica
    private JFreeChart chart;
    
    //Constructor
	public Histograma(int[] arrayGrises, int min, int max) throws IOException {
		//Tipo del histograma e introducci�n del array a dicho histograma
		dataset.setType(HistogramType.FREQUENCY);
		double[] arrayGrisesD = new double[arrayGrises.length];
		for(int i = 0; i < arrayGrises.length; i++){
			arrayGrisesD[i] = (double)arrayGrises[i];
		}
		dataset.addSeries("Histograma",arrayGrisesD,256,0,arrayGrisesD.length);
		//Llamada a la funci�n que crear� la gr�fica
		setChart(ChartFactory.createHistogram( plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips, urls));
	}
	
	public File saveChartToJPG(final JFreeChart chart, final int width, final int height) {
        //Strings que almacenar�n el nombre y la direcci�n completa de la imagen
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
        	//Se llama a la funci�n para pasar a imagen JPG, no devuelve nada, solo crea el fichero
			ChartUtilities.saveChartAsJPEG(new File(result), chart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //Devolvemos el fichero
        return new File(result);
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
}
