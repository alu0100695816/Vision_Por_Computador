import Imagen.*;

import java.awt.AlphaComposite;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Shape;

import javax.swing.*;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.event.*;

import javax.swing.JComponent;

import com.sun.javafx.geom.Rectangle;

public class PanelFlowLayout extends JFrame implements MouseListener {

	private JMenuBar barra1;
    private JMenu menu;
    private JMenuItem abrir, item2, item3, gris;
    private Imagen img = new Imagen();
    private JLabel jLabel1, jLabel2;
    private JFrame frame = new JFrame("img");
    private JFrame frame2 = new JFrame("img");
    private Graphics2D graph;
    private Boolean roi = false;
    private Rectangle2D.Double rect; 
    private BufferedImage aux = null;
    

    public PanelFlowLayout(){
       setLayout(new FlowLayout());
       barra1= new JMenuBar();
       setJMenuBar(barra1);
       jLabel1 = new JLabel();
       jLabel2 = new JLabel();
       jLabel1.addMouseListener(this);
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   frame.add(jLabel1);
	   frame2.add(jLabel2);
	   frame.setResizable(false);
	   frame.setAlwaysOnTop (true);
	   
       
       this.setExtendedState(MAXIMIZED_BOTH);
       this.setTitle("Pikaphoto");

       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       
       class MenuDemo implements ActionListener{
    	   public MenuDemo() {
    		   menu= new JMenu("Archivo");
    		   barra1.add(menu);

    		   abrir= new JMenuItem("Abrir imagen");
    		   menu.add(abrir);
    		   abrir.addActionListener(this);
   
    		   item2= new JMenuItem("Guardar Imagen");
    		   menu.add(item2);

    		   item3= new JMenuItem("Cerrar");
    		   menu.add(item3);
       
    		   menu= new JMenu("Editar");
    		   barra1.add(menu);

    		   gris= new JMenuItem("Escala Grises");
    		   menu.add(gris);
    		   gris.addActionListener(this);
   
    		   item2= new JMenuItem("Cortar");
    		   menu.add(item2);

    		   item3= new JMenuItem("Pegar");
    		   menu.add(item3);
    
    	   }

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == abrir){
				jLabel1.setIcon(new ImageIcon(img.abrirImagen()));
            
				frame.pack();
				frame.setVisible(true);
			}
			else if(e.getSource() == gris){
				jLabel1.setIcon(new ImageIcon(img.escalaGrises()));
			}
		}
       }

       MenuDemo menu = new MenuDemo();
 
    }
	
	public static void main(String[] args) {
		
		PanelFlowLayout marco = new PanelFlowLayout();
	    marco.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(roi == true){
			img.setImageActual(aux);
			//graph.clearRect((int)img.getROI()[0],(int)img.getROI()[1],(int)(img.getROI()[2]-img.getROI()[0]),(int)(img.getROI()[3]-img.getROI()[1])); 
		}
		aux = deepCopy(img.getImageActual());
		e.getLocationOnScreen().getX();
		roi = false;
		img.getROI()[0] = e.getPoint().getX();
		img.getROI()[1] = e.getPoint().getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		img.getROI()[2] = e.getPoint().getX();
		img.getROI()[3] = e.getPoint().getY();
		graph = img.getImageActual().createGraphics();
		rect = new Rectangle2D.Double((int)img.getROI()[0],(int)img.getROI()[1],(int)(img.getROI()[2]-img.getROI()[0]),(int)(img.getROI()[3]-img.getROI()[1])); 
		graph.draw(rect);
        roi = true;
        jLabel1.setIcon(new ImageIcon(img.getImageActual()));

        //ImageIO.write(img, "jpg", new File("images/template.jpg"));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
