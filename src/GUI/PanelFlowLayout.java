package GUI;
import Imagen.*;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.image.BufferedImage;
import java.io.IOException;


import java.awt.event.*;


public class PanelFlowLayout extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar barra1;
    private JMenu menu;
    private JMenuItem abrir, gris, info, ROI, histogram, histogramAc, bc, eqAc, espHist, guardar, gamma, dif, cerrar, histogramAcNorm, tramos, mapacamb, evertical, ehorizontal, trasp, rotDer, rotIzq, escalado, rotacion;
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
    		   menu.addSeparator();
    		   
    		   cerrar= new JMenuItem("Salir");
    		   menu.add(cerrar);
    		   cerrar.addActionListener(this);
       
    		   menu= new JMenu("Editar");
    		   barra1.add(menu);

    		   gris= new JMenuItem("Escala Grises");
    		   menu.add(gris);
    		   gris.addActionListener(this);
    		   
    		   info= new JMenuItem("Info");
    		   menu.add(info);
    		   info.addActionListener(this);
    		   
    		   bc= new JMenuItem("Brillo/Contraste");
    		   menu.add(bc);
    		   bc.addActionListener(this);
    		   
    		   gamma= new JMenuItem("Correccion gamma");
    		   menu.add(gamma);
    		   gamma.addActionListener(this);
    		   
    		   tramos= new JMenuItem("Trans. lineal por tramos");
    		   menu.add(tramos);
    		   tramos.addActionListener(this);
    		   
    		   menu= new JMenu("Analisis");
    		   barra1.add(menu);
    		   
    		   histogram= new JMenuItem("Histograma");
    		   menu.add(histogram);
    		   histogram.addActionListener(this);
    		   
    		   histogramAc= new JMenuItem("Histograma Acumulativo");
    		   menu.add(histogramAc);
    		   histogramAc.addActionListener(this);
    		   
    		   histogramAcNorm= new JMenuItem("Histograma Acumulativo Normalizado");
    		   menu.add(histogramAcNorm);
    		   histogramAcNorm.addActionListener(this);
    		   menu.addSeparator();
    		   
    		   espHist= new JMenuItem("Especificacion del histograma");
    		   menu.add(espHist);
    		   espHist.addActionListener(this);
    		   
    		   eqAc= new JMenuItem("Ecualizar Histograma");
    		   menu.add(eqAc);
    		   eqAc.addActionListener(this);
    		   
    		   dif= new JMenuItem("Diferencia entre dos imagenes");
    		   menu.add(dif);
    		   dif.addActionListener(this);
    		   
    		   mapacamb= new JMenuItem("Mapa de Cambios");
    		   menu.add(mapacamb);
    		   mapacamb.addActionListener(this);
    		   
    		   menu = new JMenu("Operaciones geometricas");
    		   barra1.add(menu);

    		   ROI= new JMenuItem("Separar Region de interes");
    		   menu.add(ROI);
    		   ROI.addActionListener(this);
    		   
    		   
    		   evertical= new JMenuItem("Espejo Vertical");
    		   menu.add(evertical);
    		   evertical.addActionListener(this);
    		   
    		   
    		   ehorizontal= new JMenuItem("Espejo Horizontal");
    		   menu.add(ehorizontal);
    		   ehorizontal.addActionListener(this);
    		   
    		   
    		   trasp= new JMenuItem("Traspuesta");
    		   menu.add(trasp);
    		   trasp.addActionListener(this);
    		   
    		   
    		   rotIzq= new JMenuItem("Rotacion a la izquierda");
    		   menu.add(rotIzq);
    		   rotIzq.addActionListener(this);
    		   
    		   
    		   rotDer= new JMenuItem("Rotacion a la derecha");
    		   menu.add(rotDer);
    		   rotDer.addActionListener(this);
    		   
    		   
    		   escalado= new JMenuItem("Operacion de escalado");
    		   menu.add(escalado);
    		   escalado.addActionListener(this);
    		   
    		   
    		   rotacion= new JMenuItem("Operacion de rotacion");
    		   menu.add(rotacion);
    		   rotacion.addActionListener(this);

    	   }

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
			else if(e.getSource() == info){
				((FrameInterno)(panel.getSelectedFrame())).getImg().escalaGrises();
				JLabel ancho = new JLabel("Ancho: " + Integer.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getTam()[0]));
				JLabel alto = new JLabel("Alto: " + Integer.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getTam()[1]));
				JLabel form = new JLabel("Formato: " + ((FrameInterno)(panel.getSelectedFrame())).getImg().getFormato());
				JLabel bri = new JLabel("Brillo: " + Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo()));
				JLabel cont = new JLabel("Contraste: " + Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getContraste()));
				JLabel ent = new JLabel("Entropia: " + Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getEntropia()));
				JLabel min = new JLabel("Min. valor gris: " + Integer.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getMinmax()[0]));
				JLabel max = new JLabel("Max. valor gris: " + Integer.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getMinmax()[1]));
				JFrame fram = new JFrame();
				JPanel pan = new JPanel();
				pan.setLayout(new GridLayout(8,1));
				pan.add(ancho);
				pan.add(alto);
				pan.add(form);
				pan.add(bri);
				pan.add(cont);
				pan.add(ent);
				pan.add(min);
				pan.add(max);
				pan.setVisible(true);
				fram.setVisible(true);
				fram.setContentPane(pan);
				fram.pack();
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
			else if(e.getSource() == histogramAcNorm) {
				//Generamos el histograma
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().generarHistogramaAcNorm();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Se crea un objeto imagen de la gr�fica y se a�ade a un FrameInterno
				FrameInterno fi = new FrameInterno(((FrameInterno)(panel.getSelectedFrame())).getImg().getHistogramaAcNormImg());
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
				((FrameInterno)(panel.getSelectedFrame())).getImg().rellenarArrayGrises();
				JFrame frame = new JFrame("Brillo y Contraste");
				JPanel pan = new JPanel();
				final JSlider brillo = new JSlider(0, 255, (int) ((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo());
				JLabel bri = new JLabel("Brillo: ");
				final JLabel numBri = new JLabel(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo()));
				JLabel con = new JLabel("Contraste: ");
				final JLabel numCon = new JLabel(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getContraste()));
				final JSlider contraste = new JSlider(0,127,(int) ((FrameInterno)(panel.getSelectedFrame())).getImg().getContraste());
				
				brillo.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent e) {
				    	  try {
							((FrameInterno)(panel.getSelectedFrame())).getImg().setBrilloContraste(brillo.getValue(),contraste.getValue());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    	  numBri.setText(Double.toString(((FrameInterno)(panel.getSelectedFrame())).getImg().getBrillo()));
				    	  ((FrameInterno)(panel.getSelectedFrame())).actualize();
				      }
				});
				 
				contraste.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent e) {
				    	  try {
							((FrameInterno)(panel.getSelectedFrame())).getImg().setBrilloContraste(brillo.getValue(),contraste.getValue());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().generarHistograma();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					((FrameInterno)(panel.getSelectedFrame())).getImg().generarHistogramaAc();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(e.getSource() == espHist) {
				Imagen imAux = new Imagen();
				try {
					imAux.escalaGrises();
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
			else if(e.getSource() == mapacamb) {
				int umbral = (int)(Float.parseFloat(JOptionPane.showInputDialog("Seleccione el umbral para establecer el mapa de cambios")));
				Imagen imAux = new Imagen();
				imAux.escalaGrises();
				Imagen imDif = null;
				imDif = new Imagen(((FrameInterno)(panel.getSelectedFrame())).getImg().diferenciaRojo(imAux, umbral), 0);
				FrameInterno fi = new FrameInterno(imDif);
				panel.setVisible(true);
				panel.add(fi);
			}
			else if(e.getSource() == dif) {
				Imagen imAux = new Imagen();
				imAux.escalaGrises();
				Imagen imDif = null;
				try {
					imDif = new Imagen(((FrameInterno)(panel.getSelectedFrame())).getImg().diferencia(imAux), 0);
					imDif.escalaGrises();
					imDif.generarHistograma();
					imDif.generarHistogramaAc();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FrameInterno fi = new FrameInterno(imDif);
				panel.setVisible(true);
				panel.add(fi);
				
			}
			else if(e.getSource() == tramos) {
				int numTramos = (int)(Float.parseFloat(JOptionPane.showInputDialog("Indique el n�mero de tramos(1-4):")));
				while(numTramos < 1 || numTramos > 4) {
					numTramos = (int)(Float.parseFloat(JOptionPane.showInputDialog("Error: solo entre 1 y 4 tramos. Introduzca de nuevo:")));
				}
				class GridTramos extends JFrame implements ActionListener {
					
					private static final long serialVersionUID = 1L;
					int nTramos;
					int nCoords;
					GridLayout inputs;
					JButton confirm = new JButton("Confirmar Coordenadas");
					int[][] arrayCampos = null;
					JTextField[] textfields = null;
					
					public GridTramos(String name, int tramos) {
				        super(name);
				        nTramos = tramos;
				        inputs = new GridLayout(nTramos+2,2);
				        arrayCampos = new int[nTramos+1][2];
				        nCoords = nTramos*2+2;
				        final JPanel pan = new JPanel();
				        pan.setLayout(inputs);
				        confirm.addActionListener(this);
				        
				        textfields = new JTextField[nCoords];
				        
				        for(int i = 0; i < nCoords; i++) {
				        	textfields[i] = new JTextField(5);
				        	pan.add(textfields[i]);
				        }
				    	pan.add(confirm);
						
						this.setContentPane(pan);
						this.setLocationRelativeTo(null);
						this.setVisible(true);
						this.pack();
				        this.setResizable(false);
				    } //Constructor
					
					public void actionPerformed(ActionEvent e) {
						if(e.getSource() == confirm) {
							
							int pos = 0;
							for(int i = 0; i < nTramos+1; i++) {
								for(int j = 0; j < 2; j++) {
									arrayCampos[i][j] = Integer.parseInt(textfields[pos].getText());
									pos++;
								}
							}
							
							((FrameInterno)(panel.getSelectedFrame())).getImg().transPorTramos(arrayCampos);
							((FrameInterno)(panel.getSelectedFrame())).actualize();
						}
					}
				} //Class
				switch(numTramos){
				case 1:
					GridTramos tipo1 = new GridTramos("Coordenadas 1 Tramo", 1);
				break;
				case 2:
					GridTramos tipo2 = new GridTramos("Coordenadas 2 Tramos", 2);
				break;
				case 3:
					GridTramos tipo3 = new GridTramos("Coordenadas 3 Tramos", 3);
				break;
				case 4:
					GridTramos tipo4 = new GridTramos("Coordenadas 4 Tramos", 4);
				} //Switch
			} //elseif
			else if(e.getSource() == cerrar) {
				System.exit(0);
			}
			
			// -----Op. Geom
			
			else if(e.getSource() == evertical){
				((FrameInterno)(panel.getSelectedFrame())).getImg().espejoVertical();
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			
			else if(e.getSource() == ehorizontal){
				((FrameInterno)(panel.getSelectedFrame())).getImg().espejoHorizontal();
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			
			else if(e.getSource() == trasp){
				((FrameInterno)(panel.getSelectedFrame())).getImg().traspuesta();
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			
			else if(e.getSource() == rotIzq) {
				double mult;
				do{
					mult = Float.parseFloat(JOptionPane.showInputDialog("Multiplo de 90 a rotar:"));
					if(mult%90 != 0) JOptionPane.showMessageDialog(panel, "Error: " + mult + " no es multiplo de 90!");
				}while(mult%90 != 0);
				((FrameInterno)(panel.getSelectedFrame())).getImg().rotacion(0, mult);	
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			
			else if(e.getSource() == rotDer) {
				double mult;
				do{
					mult = Float.parseFloat(JOptionPane.showInputDialog("Multiplo de 90 a rotar:"));
					if(mult%90 != 0) JOptionPane.showMessageDialog(panel, "Error: " + mult + " no es multiplo de 90!");
				}while(mult%90 != 0);
				((FrameInterno)(panel.getSelectedFrame())).getImg().rotacion(1, mult);	
				((FrameInterno)(panel.getSelectedFrame())).actualize();
			}
			
			else if(e.getSource() == escalado) {
				int tamH, tamV;
				tamH = Integer.parseInt(JOptionPane.showInputDialog("Tamaño horizontal de la imagen: " + ((FrameInterno)(panel.getSelectedFrame())).getImg().getTam()[0] + "px. Introduzca el nuevo tamaño horizontal:"));
				tamV = Integer.parseInt(JOptionPane.showInputDialog("Tamaño vertical de la imagen: " + ((FrameInterno)(panel.getSelectedFrame())).getImg().getTam()[1] + "px. Introduzca el nuevo tamaño vertical:"));
				((FrameInterno)(panel.getSelectedFrame())).getImg().escalado(tamH,tamV);	
				((FrameInterno)(panel.getSelectedFrame())).actualize();
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
