package Imagen;

public class Histograma {

	/**
	 * @param args
	 */
	String formato; // Formato de la imagen //(?)String?
	Histograma histograma; // Histograma
	int[] tam = new int[2]; // tam[0]=filas, tam[1]=columnas
	int[] minmax = new int[2]; // Rango de valores de grises. minmax[0]=valor minimo, minmax[1]=valor maximo
	int brillo; // int?
	int contraste; // int?
	double entropia; // double?
	int[] pos = new int[2]; //pos[0]=x, pos[1]=y
	int nivelGris;
	
	public static void cargarImagen(){
		
	}

}
