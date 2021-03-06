package Imagen;

import java.io.File;
import java.io.IOException;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class Histograma {

	/**
	 * @param args
	 */
	
	//Set de valores con los que se crearï¿œ el histograma
	private XYSeries dataset = new XYSeries("Píxeles");
	
	//Tï¿œtulo de la grï¿œfica y de los ejes X e Y
	private String plotTitle; 
    private String xaxis = "Tonos Gris";
    private String yaxis = "Pixeles";
    //Orientaciï¿œn de la grï¿œfica
    private PlotOrientation orientation = PlotOrientation.VERTICAL;
    //Booleanos random
    boolean show = false; 
    boolean toolTips = false;
    boolean urls = false; 
    //Objeto que almacenarï¿œ la grï¿œfica
    private JFreeChart chart;
    
    //Constructor
	public Histograma(String title, int[] arrayGrises, int min, int max) throws IOException {
		//LLamamos a la función para rellenar el dataset
		createDataset(arrayGrises);
		//Metemos el dataset en un dataset collection que será pasado como parametro al crear el chart
		XYSeriesCollection finalDataset = new XYSeriesCollection(dataset);
		plotTitle = title;
		//Creamos el chart
		chart=ChartFactory.createXYBarChart(plotTitle,xaxis,false,yaxis,finalDataset,orientation,true,true,false);
	}
	
	public Histograma(String title, double[] arrayGrises, int min, int max) throws IOException {
		//LLamamos a la funci�n para rellenar el dataset
		createDataset(arrayGrises);
		//Metemos el dataset en un dataset collection que ser� pasado como parametro al crear el chart
		XYSeriesCollection finalDataset = new XYSeriesCollection(dataset);
		plotTitle = title;
		//Creamos el chart
		chart=ChartFactory.createXYBarChart(plotTitle,xaxis,false,yaxis,finalDataset,orientation,true,true,false);
	}
	
	//Funci�n para rellenar el dataset dependiendo del array
	public void createDataset(double[] arrayGrises) {
		for(int i = 0; i < arrayGrises.length; i++) {
			dataset.add(i, arrayGrises[i]);
		}
	}
	
	public void createDataset(int[] arrayGrises) {
		for(int i = 0; i < arrayGrises.length; i++) {
			dataset.add(i, arrayGrises[i]);
		}
	}
	
	public File saveChartToJPG(final JFreeChart chart, final int width, final int height) {
        //Strings que almacenarï¿œn el nombre y la direcciï¿œn completa de la imagen
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
        	//Se llama a la funciï¿œn para pasar a imagen JPG, no devuelve nada, solo crea el fichero
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
