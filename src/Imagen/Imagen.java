package Imagen;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Imagen {

	/**
	 * @param args
	 */
	private String formato; // Formato de la imagen //(?)String?
	private Histograma histograma; // Histograma
	private int[] tam = new int[2]; // tam[0]=columnas, tam[1]=filas
	private int[] minmax = new int[]{99999,0}; // Rango de valores de grises. minmax[0]=valor minimo, minmax[1]=valor maximo
	private int brillo; // int?
	private int contraste; // int?
	private double entropia; // double?
	private int[] pos = new int[2]; //pos[0]=x, pos[1]=y
	private int[] nivelGris;
	private int[] ROI = new int[]{0,0,0,0}; //iniciox,inicioy,finx,finy
	private int[] arrayGrises = new int[256]; //Array que almacena el numero de pixeles que existen de cada tono de gris.
	
	private BufferedImage imageActual;
    
	public Imagen(){
		abrirImagen();
		tam[0] = imageActual.getWidth();
		tam[1] = imageActual.getHeight();
		nivelGris = new int[tam[0]*tam[1]];
	}
	
	public Imagen(BufferedImage img){
		setImageActual(img);
		tam[0] = imageActual.getWidth();
		tam[1] = imageActual.getHeight();
		nivelGris = new int[tam[0]*tam[1]];
	}
	
    //Método que devuelve una imagen abierta desde archivo
    //Retorna un objeto BufferedImagen
    public BufferedImage abrirImagen(){
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp=null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector=new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("TIFF & GIF & BMP & PNG", "tiff", "gif", "bmp", "png");
        selector.setFileFilter(filtroImagen);
        //Abrimos el cuadro de diálog
        int flag=selector.showOpenDialog(null);
        //Comprobamos que pulse en aceptar
        if(flag==JFileChooser.APPROVE_OPTION){
            try {
                //Devuelve el fichero seleccionado
                File imagenSeleccionada=selector.getSelectedFile();
                String[] aux = imagenSeleccionada.getName().split("[.]");
                this.formato = aux[aux.length-1];
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
            }
                  
        }
        //Asignamos la imagen cargada a la propiedad imageActual
        setImageActual(bmp);
        //Retornamos el valor
        return bmp;
    }
    
    public BufferedImage escalaGrises(){
        //Llenamos el array para el histograma de ceros
    	for(int i = 0; i < 256; i++) {
    		arrayGrises[i] = 0;
    	}
    	
    	//Variables que almacenarán los píxeles
        int gris;
        Color colorAux;
                 
        //Recorremos la imagen píxel a píxel
        for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(this.getImageActual().getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                gris=(int)(colorAux.getRed()*0.299+colorAux.getGreen()*0.587+colorAux.getBlue()*0.114);
                //Sumamos uno a ese valor de gris en el array para el histograma
                arrayGrises[gris] += 1;
                nivelGris[j*getImageActual().getWidth()+i] = gris;
                if(gris < minmax[0]) minmax[0] = gris;
                if(gris > minmax[1]) minmax[1] = gris;
                //Cambiamos a formato sRGB
                //Asignamos el nuevo valor al BufferedImage
                
                Color valor = new Color(gris, gris, gris);
                getImageActual().setRGB(i, j, valor.getRGB());
            }
        }
        //Retornamos la imagen
        return getImageActual();
    }

	public BufferedImage getImageActual() {
		return imageActual;
	}

	public void setImageActual(BufferedImage imageActual) {
		this.imageActual = imageActual;
	}

	public int[] getROI() {
		return ROI;
	}

	public void setROI(int[] rOI) {
		ROI = rOI;
	}

	public int[] getPos() {
		return pos;
	}
	
	public int[] getArrayGrises() {
		return arrayGrises;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}

	public void actualizarPos(int i, int j) {
		pos[0] = i; pos [1] = j;
	}

	public int genNumPixels() {
		return tam[0]*tam[1];
	}

	public int getGris(int i, int j) {
		if((j>=0 && i >=0) && (j<=tam[1] && i <=tam[0]))return nivelGris[j*tam[0]+i];
		else return 0;
	}

}
