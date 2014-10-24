import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import Imagen.Imagen;


public class FrameInterno extends JInternalFrame implements MouseListener, MouseMotionListener, InternalFrameListener {
	private Imagen img;
	private JLabel lab = new JLabel();
	public JLabel lab2 = new JLabel("x:   y:   ");
	private Graphics2D graph;
	private Rectangle2D rect; 
	private int desvX = 6;
	private int desvY = 28;
	private BufferedImage imgAux;

	public FrameInterno(Imagen im) {
		super( "img", false, true, false, false ); 
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());
		img = im;
		if(img.getImageActual() != null)lab.setIcon(new ImageIcon(img.getImageActual()));
		pan.add(lab,BorderLayout.CENTER);
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		pan.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(pan.getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		lab2.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(lab2);
		setContentPane(pan);
		setVisible(true);
		pan.setVisible(true);
		pack();
		moveToFront();
		addMouseListener(this);
		addMouseMotionListener(this);
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

	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		getImg().actualizarPos((int) (e.getPoint().getX()-desvX),(int) (e.getPoint().getY()-desvY));
		lab2.setText("x: " + this.getImg().getPos()[0] + " y: " + this.getImg().getPos()[1]);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		lab2.setText("x:  y:");

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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
