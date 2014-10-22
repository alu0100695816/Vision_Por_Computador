package Imagen;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import com.sun.prism.Image;

public class Imagen {

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
	
	private BufferedImage imageActual;
    
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
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
            }
                  
        }
        //Asignamos la imagen cargada a la propiedad imageActual
        imageActual=bmp;
        //Retornamos el valor
        return bmp;
    }
    
    public BufferedImage escalaGrises(){
        //Variables que almacenarán los píxeles
        int mediaPixel,colorSRGB;
        Color colorAux;
                 
        //Recorremos la imagen píxel a píxel
        for( int i = 0; i < imageActual.getWidth(); i++ ){
            for( int j = 0; j < imageActual.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(this.imageActual.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel=(int)((colorAux.getRed()+colorAux.getGreen()+colorAux.getBlue())/3);
                //Cambiamos a formato sRGB
                colorSRGB=(mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(i, j,colorSRGB);
            }
        }
        //Retornamos la imagen
        return imageActual;
    }

}
