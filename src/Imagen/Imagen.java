package Imagen;


import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import GUI.FrameInterno;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Imagen {

	/**
	 * @param args
	 */
	private String formato; // Formato de la imagen //(?)String?
	private Histograma histograma; // Histograma
	private Histograma histogramaAc;
	private Histograma histogramaAcNorm;
	private BufferedImage histogramaImg = null;
	private BufferedImage histogramaAcImg = null;
	private BufferedImage histogramaAcNormImg = null;
	private int[] tam = new int[2]; // tam[0]=columnas, tam[1]=filas
	private int[] minmax = new int[]{99999,0}; // Rango de valores de grises. minmax[0]=valor minimo, minmax[1]=valor maximo
	private double brillo;
	private double contraste;
	private double entropia;
	private double media;
	private double desvTip;
	private int[] pos = new int[2]; //pos[0]=x, pos[1]=y
	private int[] nivelGris;
	private int[] ROI = new int[]{0,0,0,0}; //iniciox,inicioy,finx,finy
	private int[] arrayGrises = new int[256]; //Array que almacena el numero de pixeles que existen de cada tono de gris.
	private int[] arrayGrisesAcumulativo = new int[256];
	private double[] arrayGrisesAcumulativoNorm = new double[256];
	
	private BufferedImage imageActual;
    
	public Imagen(){
		abrirImagen();
		getTam()[0] = imageActual.getWidth();
		getTam()[1] = imageActual.getHeight();
		nivelGris = new int[getTam()[0]*getTam()[1]];
		escalaGrises();
	}
	
	public Imagen(BufferedImage img){
		setImageActual(img);
		getTam()[0] = imageActual.getWidth();
		getTam()[1] = imageActual.getHeight();
		nivelGris = new int[getTam()[0]*getTam()[1]];
		escalaGrises();
	}
	
    public Imagen(BufferedImage img, int i) {
    	setImageActual(img);
		getTam()[0] = imageActual.getWidth();
		getTam()[1] = imageActual.getHeight();
		nivelGris = new int[getTam()[0]*getTam()[1]];
	}

	//Método que devuelve una imagen abierta desde archivo
    //Retorna un objeto BufferedImagen
    public BufferedImage abrirImagen(){
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp=null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector=new JFileChooser();
        String Cdir = System.getProperty("user.dir");
        File dir = new File(Cdir + File.separator + "img");
        selector.setCurrentDirectory(dir);
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
                this.setFormato(aux[aux.length-1]);
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
                arrayGrises[gris] += 1;
                nivelGris[j*getImageActual().getWidth()+i] = gris;
                if(gris < getMinmax()[0]) getMinmax()[0] = gris;
                if(gris > getMinmax()[1]) getMinmax()[1] = gris;
                //Cambiamos a formato sRGB
                //Asignamos el nuevo valor al BufferedImage
                
                Color valor = new Color(gris, gris, gris);
                getImageActual().setRGB(i, j, valor.getRGB());
            }
        }
        fillArrayGrisesAcumulativo();
        initBrillo();
        initContraste();
        initEntropia();
        //Retornamos la imagen
        return getImageActual();
    }
    
    public void rellenarArrayGrises(){
    	for(int i = 0; i < 256; i++) {
    		arrayGrises[i] = 0;
    	}
    	int gris;
        Color colorAux;
    	for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(this.getImageActual().getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                gris=colorAux.getRed();
                arrayGrises[gris] += 1;
                nivelGris[j*getImageActual().getWidth()+i] = gris;
                if(gris < getMinmax()[0]) getMinmax()[0] = gris;
                if(gris > getMinmax()[1]) getMinmax()[1] = gris;
                //Cambiamos a formato sRGB
                //Asignamos el nuevo valor al BufferedImage
                
                Color valor = new Color(gris, gris, gris);
                getImageActual().setRGB(i, j, valor.getRGB());
            }
        }
        fillArrayGrisesAcumulativo();
        initBrillo();
        initContraste();
        initEntropia();
    }

    public void initEntropia() {
    	double suma = 0;
    	double tam = getNumPixels();
		for(int i = 0; i < 256; i++){
			if(arrayGrises[i] != 0) suma += (arrayGrises[i]/tam) * (Math.log(arrayGrises[i]/tam)/Math.log(2));
		}
		suma = Math.floor(suma * 100) / 100;
		this.setEntropia(-suma);
    }

	public void initBrillo() {
    	double suma = 0;
		for(int i = 0; i < 256; i++){
			suma += arrayGrises[i] * i;
		}
		this.brillo = suma/getNumPixels();
		this.brillo = Math.floor(this.brillo * 100) / 100;
	}

	public void initContraste() {
		double suma = 0;
		for(int i = 0; i < 256; i++){
			suma += arrayGrises[i] * (i-this.brillo)*(i-this.brillo);
		}
		this.contraste = Math.sqrt(suma/getNumPixels());
		this.contraste = Math.floor(this.contraste * 100) / 100;
	}

	public void actBrilloContraste() throws IOException {
		double A = contraste/desvTip;
		double B = brillo - A*media;
    	for (int x = 0; x < getImageActual().getWidth(); x++) {
    		for (int y = 0; y < getImageActual().getHeight(); y++) {
    			nivelGris[y*getImageActual().getWidth()+x] = (int) Math.round((A*nivelGris[y*getImageActual().getWidth()+x]+B));
    			if(nivelGris[y*getImageActual().getWidth()+x] > 255) nivelGris[y*getImageActual().getWidth()+x] = 255; if(nivelGris[y*getImageActual().getWidth()+x] < 0) nivelGris[y*getImageActual().getWidth()+x] = 0;
    			Color valor = new Color(getGris(x,y), getGris(x,y), getGris(x,y));
                getImageActual().setRGB(x, y, valor.getRGB());
    		}
    	}
    }
	
	/*public void fillBothArrays() {
		//Llenamos el array para el histograma de ceros
    	for(int i = 0; i < 256; i++) {
    		arrayGrises[i] = 0;
    	}
		
		int gris;
        Color colorAux;
        
        for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
            	colorAux=new Color(this.getImageActual().getRGB(i, j));
            	gris=(int)(colorAux.getRed());
            	arrayGrises[gris] += 1;
            }
        }
        fillArrayGrisesAcumulativo();
	}*/
    
    public void fillArrayGrisesAcumulativo() {
    	for(int i = 0; i < arrayGrisesAcumulativo.length; i++) {
    		arrayGrisesAcumulativo[i] = arrayGrises[i];
    	}
    	for(int i = 1; i < arrayGrisesAcumulativo.length; i++) {
    		arrayGrisesAcumulativo[i] += arrayGrisesAcumulativo[i-1];
    	}
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
	public int[] getArrayGrisesAcumulativo() {
		return arrayGrisesAcumulativo;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}

	public void actualizarPos(int i, int j) {
		pos[0] = i; pos [1] = j;
	}

	public int getNumPixels() {
		return getTam()[0]*getTam()[1];
	}

	public int getGris(int i, int j) {
		if((j>=0 && i >=0) && (j<=getTam()[1] && i <=getTam()[0]))return nivelGris[j*getTam()[0]+i];
		else return 0;
	}
	
	public void generarHistograma() throws IOException{
		//fillBothArrays();
		//Lo construimos pasandole el arrayGrises de la imagen actual
		this.histograma = new Histograma("Histograma", getArrayGrises(), 0,getNumPixels());
		//Creamos el fichero JPG de la gr�fica
		File histogr = this.histograma.saveChartToJPG(this.histograma.getChart(), 320, 240);
		this.setHistogramaImg(ImageIO.read(histogr));
	}
	
	public void generarHistogramaAc() throws IOException{
		//fillBothArrays();
		//Lo construimos pasandole el arrayGrises de la imagen actual
		this.histogramaAc = new Histograma("Histograma Acumulativo", getArrayGrisesAcumulativo(), 0,getNumPixels());
		//Creamos el fichero JPG de la gr�fica
		File histogr = this.histogramaAc.saveChartToJPG(this.histogramaAc.getChart(), 320, 240);
		this.setHistogramaAcImg(ImageIO.read(histogr));
	}

	public void generarHistogramaAcNorm() throws IOException{
		//fillBothArrays();
		//Lo construimos pasandole el arrayGrises de la imagen actual
		generarHistogramaAc();
		normalize();
		this.histogramaAcNorm = new Histograma("Histograma Acumulativo Normalizado", getArrayGrisesAcumulativoNorm(), 0,getNumPixels());
		//Creamos el fichero JPG de la gr�fica
		File histogr = this.histogramaAcNorm.saveChartToJPG(this.histogramaAcNorm.getChart(), 320, 240);
		this.setHistogramaAcNormImg(ImageIO.read(histogr));
	}

	private double[] getArrayGrisesAcumulativoNorm() {
		// TODO Auto-generated method stub
		return arrayGrisesAcumulativoNorm;
	}

	public BufferedImage getHistogramaImg() {
		return histogramaImg;
	}
	
	public BufferedImage getHistogramaAcImg() {
		return histogramaAcImg;
	}

	public void setHistogramaImg(BufferedImage histogramaImg) {
		this.histogramaImg = histogramaImg;
	}
	
	public void setHistogramaAcImg(BufferedImage histogramaImg) {
		this.histogramaAcImg = histogramaImg;
	}

	public double getBrillo() {
		return brillo;
	}

	public double getContraste() {
		return contraste;
	}

	public void setBrilloContraste(double brillo, double contraste) throws IOException {
		calcMedia();
		calcVarianza();
		this.brillo = brillo;
		this.brillo = Math.floor(this.brillo * 100) / 100;
		this.contraste = contraste;
		this.contraste = Math.floor(this.contraste * 100) / 100;
		actBrilloContraste();
	}
	
	public void calcMedia(){
		media = brillo;
	}
	
	public void calcVarianza(){
		desvTip = contraste;
	}
	
	public void ecualizarHistograma() throws IOException{
		int Vin;
        Color colorAux;
        int[] arrayAux = new int[256];
		int size = getImageActual().getHeight()*getImageActual().getWidth();
		for( int i = 0; i < arrayAux.length; i++ ){
            Vin = i;
            int Vout = (int)(Math.round((256.0/size)*arrayGrisesAcumulativo[Vin])-1);
            if(Vout < 0) Vout = 0;
            arrayAux[Vin] = Vout;
		}
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
            	colorAux=new Color(this.getImageActual().getRGB(i, j));
            	Vin=(int)(colorAux.getRed());
            	int Vout = arrayAux[Vin];
            	Color valor = new Color(Vout, Vout, Vout);
            	getImageActual().setRGB(i, j, valor.getRGB());
            }
    	}
		escalaGrises();
		generarHistograma();
		generarHistogramaAc();
	}
	
	public BigDecimal calcPendiente(int x1, int y1, int x2, int y2) {
		int dividendo = y2-y1;
		int divisor = x2-x1;
		if(divisor == 0) {
			return new BigDecimal(0);
		}
		else {
			return new BigDecimal(dividendo).divide(new BigDecimal(divisor), 3, RoundingMode.HALF_UP);
		}
	}
	
	public BigDecimal calcConstante(BigDecimal pendiente,int x1, int y1) {
		BigDecimal res1 = pendiente.multiply(new BigDecimal(x1));
		return new BigDecimal(y1).subtract(res1);
	}
	
	public void transPorTramos(int[][] arrayCoord) {
		
		int[] arrayAux = new int[256];
		BigDecimal a = calcPendiente(arrayCoord[0][0], arrayCoord[0][1], arrayCoord[1][0],arrayCoord[1][1]);
		BigDecimal b = calcConstante(a, arrayCoord[0][0], arrayCoord[0][1]);
		
		if(arrayCoord.length == 2) {
			for(int i = 0; i < 256; i++) {
				arrayAux[i] = (((a.multiply(new BigDecimal(i))).add(b)).setScale(0, RoundingMode.HALF_UP)).intValueExact();
			}
		}
		else if(arrayCoord.length > 2) {
			for(int i = 0; i < arrayCoord[1][0]+1; i++) {
				arrayAux[i] = (((a.multiply(new BigDecimal(i))).add(b)).setScale(0, RoundingMode.HALF_UP)).intValueExact();
			}
			for(int i = 1; i < arrayCoord.length-1; i++) {
				a = calcPendiente(arrayCoord[i][0], arrayCoord[i][1], arrayCoord[i+1][0],arrayCoord[i+1][1]);
				b = calcConstante(a, arrayCoord[i][0], arrayCoord[i][1]);
				for(int j = arrayCoord[i][0]+1; j < arrayCoord[i+1][0]+1; j++) {
					arrayAux[j] = (((a.multiply(new BigDecimal(j))).add(b)).setScale(0, RoundingMode.HALF_UP)).intValueExact();
				}
			}
		}
		
		for(int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
            	Color colorAux=new Color(this.getImageActual().getRGB(i, j));
            	int Vin=(int)(colorAux.getRed());
            	int Vout = arrayAux[Vin];
            	Color valor = new Color(Vout, Vout, Vout);
            	getImageActual().setRGB(i, j, valor.getRGB());
            }
    	}
		escalaGrises();
	} //Function

	public void especificacionHistograma(Imagen imAux) throws IOException {
		generarHistogramaAcNorm();
		double value, aux, aux2;
		int minIndex = 0;
		int Vin, Vout;
		Color colorAux, valor;
		int[] matriz = new int[256];
		for(int x = 0; x < arrayGrisesAcumulativoNorm.length; x++){
			value = arrayGrisesAcumulativoNorm[x];
			for(int i = 0; i < imAux.getArrayGrisesAcumulativoNorm().length; i++){
				aux = imAux.getArrayGrisesAcumulativoNorm()[i];
				if(value == aux){
					minIndex = i;
					break;
				}
				else if(value < aux){ 
					if(i == 0){
						minIndex = 0;
					}
					else{
						aux2 = imAux.getArrayGrisesAcumulativoNorm()[i-1];
						if(Math.abs(value-aux) > Math.abs(value-aux2)) minIndex = i-1;
						else minIndex = i;
					}
					break;
				}
				if(i == 255) minIndex = 255;
			}
			matriz[x] = minIndex;
		}
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
	        for( int j = 0; j < getImageActual().getHeight(); j++ ){
	            colorAux=new Color(this.getImageActual().getRGB(i, j));
	            Vin=colorAux.getRed();
	            Vout = matriz[Vin];
	            valor = new Color(Vout, Vout, Vout);
	            getImageActual().setRGB(i, j, valor.getRGB());
	         }
		}
		escalaGrises();
		generarHistograma();
		generarHistogramaAc();
		normalize();
		generarHistogramaAcNorm();
		
	}

	public void normalize(){
		double sizeD = getImageActual().getHeight()*getImageActual().getWidth();
		for (int x = 0; x < arrayGrisesAcumulativo.length; x++) {
			arrayGrisesAcumulativoNorm[x] = arrayGrisesAcumulativo[x]/sizeD;
		}
	}
	
	public void denormalize(){
		double sizeD = getImageActual().getHeight()*getImageActual().getWidth();
		for (int x = 0; x < arrayGrisesAcumulativo.length; x++) {
			arrayGrisesAcumulativo[x] = (int) (arrayGrisesAcumulativoNorm[x]*sizeD);
		}
	}

	public void guardar() {
		JFileChooser selector=new JFileChooser();
        String Cdir = System.getProperty("user.dir");
        File dir = new File(Cdir + File.separator + "img");
        selector.setCurrentDirectory(dir);
        selector.setDialogTitle("Directorio donde guardar la imagen");
        selector.setFileFilter( new FolderFilter() );
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int flag=selector.showSaveDialog(null);
        if(flag==JFileChooser.APPROVE_OPTION){
         
        	try {
        		File f = selector.getSelectedFile();
        	    String test = f.getAbsolutePath();
        	    ImageIO.write(getImageActual(), getFormato(), selector.getSelectedFile());
        	} catch (IOException e) {
        		
        	}
        }
	}
	private static class FolderFilter extends javax.swing.filechooser.FileFilter {
	      @Override
	      public boolean accept( File file ) {
	        return file.isDirectory();
	      }

	      @Override
	      public String getDescription() {
	        return "Directorios solo";
	      }
	    }
	public void correccionGamma(double gamma) {
		Color colorAux, valor;
		int Vin, Vout; 
		double a, b;
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
            	colorAux=new Color(this.getImageActual().getRGB(i, j));
            	Vin=colorAux.getRed();
            	a = Vin/255.;
            	b = Math.pow(a, gamma);
            	Vout = (int) (b * 255);
            	valor = new Color(Vout, Vout, Vout);
            	getImageActual().setRGB(i, j, valor.getRGB());
             }
    	 }
	}

	public BufferedImage diferencia(Imagen imAux) throws IOException {
		Color color1, color2, color3;
		int gris1, gris2, gris3;
		Imagen imgDif = new Imagen(deepCopy(this.getImageActual()));
		for(int i = 0; i < getImageActual().getWidth(); i++){
			for(int j = 0; j < getImageActual().getHeight(); j++){
				color1=new Color(this.getImageActual().getRGB(i, j));
				gris1=color1.getRed();
				color2=new Color(imAux.getImageActual().getRGB(i, j));
				gris2=color2.getRed();
				gris3 = Math.abs(gris2-gris1);
				//if (gris3 > 1){ System.out.println(gris3); color3 = new Color(255, 0, 0);}
				//else color3 = color1;
				color3 = new Color(gris3, gris3, gris3);
				imgDif.getImageActual().setRGB(i, j, color3.getRGB());
			}
		}
		imgDif.escalaGrises();
		imgDif.generarHistograma();
		imgDif.generarHistogramaAc();
		return imgDif.getImageActual();
	}
	
	public BufferedImage diferenciaRojo(Imagen imAux, int umbral) {
		Color color1, color2, color3;
		int gris1, gris2, gris3;
		Imagen imgDif = new Imagen(deepCopy(this.getImageActual()));
		for(int i = 0; i < getImageActual().getWidth(); i++){
			for(int j = 0; j < getImageActual().getHeight(); j++){
				color1=new Color(this.getImageActual().getRGB(i, j));
				gris1=color1.getRed();
				color2=new Color(imAux.getImageActual().getRGB(i, j));
				gris2=color2.getRed();
				gris3 = Math.abs(gris2-gris1);
				if (gris3 > umbral){ System.out.println(gris3); color3 = new Color(255, 0, 0);}
				else color3 = color1;
				imgDif.getImageActual().setRGB(i, j, color3.getRGB());
			}
		}
		return imgDif.getImageActual();
	}
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public BufferedImage getHistogramaAcNormImg() {
		return histogramaAcNormImg;
	}

	public void setHistogramaAcNormImg(BufferedImage histogramaAcNormImg) {
		this.histogramaAcNormImg = histogramaAcNormImg;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int[] getMinmax() {
		return minmax;
	}

	public void setMinmax(int[] minmax) {
		this.minmax = minmax;
	}

	public double getEntropia() {
		return entropia;
	}

	public void setEntropia(double entropia) {
		this.entropia = entropia;
	}

	public int[] getTam() {
		return tam;
	}

	public void setTam(int[] tam) {
		this.tam = tam;
	}
	
	// -------------OPERACIONES GEOMETRICAS-----------------//
	
	public void espejoVertical(){
		int[] espejo = new int[tam[0]*tam[1]];
		Color valor;
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
                espejo[(getImageActual().getHeight()-1-j)*getTam()[0]+i]=getGris(i,j);
            }
        }
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
            	valor = new Color(espejo[j*getTam()[0]+i],espejo[j*getTam()[0]+i],espejo[j*getTam()[0]+i]);
            	getImageActual().setRGB(i, j, valor.getRGB());
            }
        }
		escalaGrises();
	}
	
	public void espejoHorizontal(){
		int[] espejo = new int[tam[0]*tam[1]];
		Color valor;
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
                espejo[j*getTam()[0]+(getImageActual().getWidth()-1-i)]=getGris(i,j);
            }
        }
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
            	valor = new Color(espejo[j*getTam()[0]+i],espejo[j*getTam()[0]+i],espejo[j*getTam()[0]+i]);
            	getImageActual().setRGB(i, j, valor.getRGB());
            }
        }
		escalaGrises();
	}
	
	public void traspuesta(){
		int[] espejo = new int[tam[0]*tam[1]];
		Color valor;
		BufferedImage im = new BufferedImage(tam[1], tam[0], BufferedImage.TYPE_INT_RGB);
		for( int i = 0; i < getImageActual().getWidth(); i++ ){
            for( int j = 0; j < getImageActual().getHeight(); j++ ){
                espejo[i*getTam()[1]+j]=getGris(i,j);
            }
        }
		for( int i = 0; i < getImageActual().getHeight(); i++ ){
            for( int j = 0; j < getImageActual().getWidth(); j++ ){
            	valor = new Color(espejo[j*getTam()[1]+i],espejo[j*getTam()[1]+i],espejo[j*getTam()[1]+i]);
            	im.setRGB(i, j, valor.getRGB());
            }
        }
		int[] tama = {tam[1],tam[0]};
		this.setImageActual(im);
		this.setTam(tama);
		escalaGrises();
	}
}
