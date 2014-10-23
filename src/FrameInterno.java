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
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import Imagen.Imagen;


public class FrameInterno extends JInternalFrame implements MouseListener, InternalFrameListener {
	private Imagen img;
	private JLabel lab = new JLabel();
	private Graphics2D graph;
	private Rectangle2D rect; 
	private int desvX = 5;
	private int desvY = 25;
	private BufferedImage imgAux;

	public FrameInterno(Imagen im) {
		super( "img", false, true, false, false ); 
		img = im;
		if(img.getImageActual() != null)lab.setIcon(new ImageIcon(img.getImageActual()));
		add(lab);
		setVisible(true);
		pack();
		moveToFront();
		addMouseListener(this);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
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
		pack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		FrameInterno fi = (FrameInterno) e.getSource();
		JDesktopPane dp = (JDesktopPane) fi.getParent();
		for(int i = 0; i < dp.getAllFrames().length; i++)
			((FrameInterno)(dp.getAllFrames()[i])).actualize();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		FrameInterno fi = (FrameInterno) e.getSource();
		JDesktopPane dp = (JDesktopPane) fi.getParent();
		for(int i = 0; i < dp.getAllFrames().length; i++)
			((FrameInterno)(dp.getAllFrames()[i])).actualize();
		getImg().getROI()[0] = (int) (e.getPoint().getX()-desvX);
		getImg().getROI()[1] = (int) (e.getPoint().getY()-desvY);
		imgAux = deepCopy(img.getImageActual());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getImg().getROI()[2] = (int) (e.getPoint().getX()-desvX);
		getImg().getROI()[3] = (int) (e.getPoint().getY()-desvY);
		graph = getImg().getImageActual().createGraphics();
		rect = new Rectangle2D.Double((int)getImg().getROI()[0],(int)getImg().getROI()[1],(int)(getImg().getROI()[2]-getImg().getROI()[0]),(int)(getImg().getROI()[3]-getImg().getROI()[1])); 
		graph.draw(rect);
        
        actualize();
        img.setImageActual(imgAux);
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
	
	public void copiarImg(BufferedImage bi){
		this.img.setImageActual(deepCopy(bi));
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
