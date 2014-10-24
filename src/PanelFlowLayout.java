import Imagen.*;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.event.*;

import javax.swing.JComponent;


public class PanelFlowLayout extends JFrame implements MouseListener {

	private JMenuBar barra1;
    private JMenu menu;
    private JMenuItem abrir, item2, gris, ROI, histogram;
    private JDesktopPane panel;
    private JFrame frame = new JFrame();
    

    public PanelFlowLayout(){
       setLayout(new FlowLayout());
       barra1= new JMenuBar();
       setJMenuBar(barra1);
       panel = new JDesktopPane();
       panel.addMouseListener(this);
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   panel.setVisible(true);
	   panel.setOpaque(true);
	   frame.setResizable(false);
	   frame.setAlwaysOnTop (true);
	   setContentPane(panel);
       
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
       
    		   menu= new JMenu("Editar");
    		   barra1.add(menu);

    		   gris= new JMenuItem("Escala Grises");
    		   menu.add(gris);
    		   gris.addActionListener(this);
   
    		   ROI= new JMenuItem("Separar Región de interés");
    		   menu.add(ROI);
    		   ROI.addActionListener(this);

    		   histogram= new JMenuItem("Histograma");
    		   menu.add(histogram);
    		   histogram.addActionListener(this);
    	   }

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == abrir){
				Imagen im = new Imagen();
				FrameInterno fi = new FrameInterno(im);
				panel.setVisible(true);
				panel.add(fi);
			}
			else if(e.getSource() == gris){
				((FrameInterno)(panel.getSelectedFrame())).getImg().escalaGrises();
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			else if(e.getSource() == ROI){
				Imagen im = new Imagen(subImage(((FrameInterno)(panel.getSelectedFrame())).getImg().getImageActual(),((FrameInterno)(panel.getSelectedFrame())).getImg().getROI()[0], ((FrameInterno)(panel.getSelectedFrame())).getImg().getROI()[1], ((FrameInterno)(panel.getSelectedFrame())).getImg().getROI()[2]-((FrameInterno)(panel.getSelectedFrame())).getImg().getROI()[0], ((FrameInterno)(panel.getSelectedFrame())).getImg().getROI()[3]-((FrameInterno)(panel.getSelectedFrame())).getImg().getROI()[1]));
				FrameInterno fi = new FrameInterno(im);
				panel.setVisible(true);
				panel.add(fi);
				fi.actualize();
			}
			else if(e.getSource() == histogram){
				Histograma myHistogram = new Histograma(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramArray());
				myHistogram.pack();
		        myHistogram.setVisible(true);
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
		for(int i = 0; i < panel.getAllFrames().length; i++)
			((FrameInterno)(panel.getAllFrames()[i])).actualize();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	static BufferedImage subImage(BufferedImage bi, int x, int y, int w, int h) {
		BufferedImage biCut = bi.getSubimage( x, y,w, h);
		BufferedImage newbiCut =  new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB );
		Graphics g = newbiCut.getGraphics();
		g.drawImage(biCut, 0, 0, null);

		return newbiCut;
	}

}
