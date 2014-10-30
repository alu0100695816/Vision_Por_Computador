package GUI;
import Imagen.*;

import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.*;


public class PanelFlowLayout extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar barra1;
    private JMenu menu;
    private JMenuItem abrir, gris, ROI, histogram, histogramAc, bc, eqAc, espHist, guardar, gamma, dif;
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
   
    		   guardar= new JMenuItem("Guardar Imagen");
    		   menu.add(guardar);
    		   guardar.addActionListener(this);
       
    		   menu= new JMenu("Editar");
    		   barra1.add(menu);

    		   gris= new JMenuItem("Escala Grises");
    		   menu.add(gris);
    		   gris.addActionListener(this);
   
    		   ROI= new JMenuItem("Separar Region de interes");
    		   menu.add(ROI);
    		   ROI.addActionListener(this);
    		   menu.addSeparator();

    		   histogram= new JMenuItem("Histograma");
    		   menu.add(histogram);
    		   histogram.addActionListener(this);
    		   
    		   histogramAc= new JMenuItem("Histograma Acumulativo");
    		   menu.add(histogramAc);
    		   histogramAc.addActionListener(this);
    		   
    		   eqAc= new JMenuItem("Ecualizar Histograma");
    		   menu.add(eqAc);
    		   eqAc.addActionListener(this);

    		   bc= new JMenuItem("Brillo/Contraste");
    		   menu.add(bc);
    		   bc.addActionListener(this);

    		   espHist= new JMenuItem("Especificacion del histograma");
    		   menu.add(espHist);
    		   espHist.addActionListener(this);
    		   
    		   gamma= new JMenuItem("Correccion gamma");
    		   menu.add(gamma);
    		   gamma.addActionListener(this);
    		   
    		   dif= new JMenuItem("Diferencia entre dos imagenes");
    		   menu.add(dif);
    		   dif.addActionListener(this);
    	   }

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == abrir){
				Imagen im = new Imagen();
				FrameInterno fi = new FrameInterno(im);
				panel.setVisible(true);
				panel.add(fi);
			}
			if(e.getSource() == guardar){
				try{
				((FrameInterno)(panel.getSelectedFrame())).getImg().guardar();
				}catch(Exception e2){
					JOptionPane.showMessageDialog(frame,
	        			    "Error. Recuerda poner un nombre a tu imagen al final de la ruta");
				}
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
				//Generamos el histograma
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().generarHistograma();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Se crea un objeto imagen de la gr�fica y se a�ade a un FrameInterno
				FrameInterno fi = new FrameInterno(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramaImg());
				panel.setVisible(true);
				panel.add(fi);
			}
			else if(e.getSource() == histogramAc) {
				//Generamos el histograma
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().generarHistogramaAc();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Se crea un objeto imagen de la gr�fica y se a�ade a un FrameInterno
				FrameInterno fi = new FrameInterno(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramaAcImg());
				panel.setVisible(true);
				panel.add(fi);
			}
			else if(e.getSource() == eqAc) {
				//Generamos el histograma
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().generarHistogramaAc();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Se crea un objeto imagen de la gr�fica y se a�ade a un FrameInterno
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().ecualizarHistograma();
					((FrameInterno)(panel.getSelectedFrame())).actualize();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FrameInterno fi1 = new FrameInterno(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramaImg());
				panel.setVisible(true);
				panel.add(fi1);
				FrameInterno fi2 = new FrameInterno(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramaAcImg());
				panel.setVisible(true);
				panel.add(fi2);
			}
			else if(e.getSource() == bc) {
				JFrame frame = new JFrame("Brillo y Contraste");
				JPanel pan = new JPanel();
				final JSlider brillo = new JSlider();
				brillo.setValue((int) ((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo());
				JLabel bri = new JLabel("Brillo: ");
				final JLabel numBri = new JLabel(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo()));
				JLabel con = new JLabel("Contraste: ");
				final JLabel numCon = new JLabel(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getContraste()));
				brillo.setMaximum(255);
				final JSlider contraste = new JSlider();
				contraste.setMaximum(127);
				contraste.setValue((int) ((FrameInterno)(panel.getSelectedFrame())).getImg().getContraste());
				
				brillo.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent e) {
				    	  ((FrameInterno)(panel.getSelectedFrame())).getImg().setBrilloContraste(brillo.getValue(),contraste.getValue());
				    	  numBri.setText(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo()));
				    	  ((FrameInterno)(panel.getSelectedFrame())).actualize();
				      }
				});
				 
				contraste.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent e) {
				    	  ((FrameInterno)(panel.getSelectedFrame())).getImg().setBrilloContraste(brillo.getValue(),contraste.getValue());
						  numCon.setText(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getContraste()));
				    	  ((FrameInterno)(panel.getSelectedFrame())).actualize();
				      }
				});
				
				pan.add(bri);
				pan.add(brillo);
				pan.add(numBri);
				pan.add(con);
				pan.add(contraste);
				pan.add(numCon);
				frame.setContentPane(pan);
				pan.setVisible(true);
				frame.setVisible(true);
				frame.pack();
			}
			else if(e.getSource() == espHist) {
				Imagen imAux = new Imagen();
				try {
					imAux.escalaGrises();
					imAux.normalize();
					imAux.generarHistogramaAcNorm();
					((FrameInterno)(panel.getSelectedFrame())).getImg().especificacionHistograma(imAux);
					((FrameInterno)(panel.getSelectedFrame())).actualize();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FrameInterno fi = new FrameInterno(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramaAcImg());
				panel.setVisible(true);
				panel.add(fi);
				
			}
			else if(e.getSource() == gamma) {
				double gamma = Float.parseFloat(JOptionPane.showInputDialog("Seleccione el indice gamma"));
				((FrameInterno)(panel.getSelectedFrame())).getImg().correccionGamma(gamma);	
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			else if(e.getSource() == dif) {
				Imagen imAux = new Imagen();
				imAux.escalaGrises();
				Imagen imDif = new Imagen(((FrameInterno)(panel.getSelectedFrame())).getImg().diferencia(imAux));
				FrameInterno fi = new FrameInterno(imDif);
				panel.setVisible(true);
				panel.add(fi);
				
			}
		}
       }

       @SuppressWarnings("unused")
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
