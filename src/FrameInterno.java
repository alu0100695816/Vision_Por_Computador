import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import Imagen.Imagen;


public class FrameInterno extends JInternalFrame implements MouseListener {
	private Imagen img;
	private JLabel lab = new JLabel();
	private Graphics2D graph;
	private Rectangle2D.Double rect; 
	private int desvX = 5;
	private int desvY = 25;
	private BufferedImage aux;

	public FrameInterno(Imagen im) {
		img = im;
		lab.setIcon(new ImageIcon(img.getImageActual()));
		add(lab);
		pack();
	}

	public Imagen getImg() {
		return img;
	}

	public void setImg(Imagen img) {
		this.img = img;
		lab.setIcon(new ImageIcon(img.getImageActual()));
		add(lab);
		pack();
	}
	
	public void actualize(){
		lab.setIcon(new ImageIcon(img.getImageActual()));
	}
	
	protected void paintComponent(Graphics g) {
		
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		getImg().getROI()[0] = e.getPoint().getX()-desvX;
		getImg().getROI()[1] = e.getPoint().getY()-desvY;
		aux = deepCopy(img.getImageActual());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getImg().getROI()[2] = e.getPoint().getX()-desvX;
		getImg().getROI()[3] = e.getPoint().getY()-desvY;
		graph = getImg().getImageActual().createGraphics();
		rect = new Rectangle2D.Double((int)getImg().getROI()[0],(int)getImg().getROI()[1],(int)(getImg().getROI()[2]-getImg().getROI()[0]),(int)(getImg().getROI()[3]-getImg().getROI()[1])); 
		graph.draw(rect);
        
        actualize();
        img.setImageActual(aux);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
