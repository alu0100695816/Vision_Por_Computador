import Imagen.*;

import java.awt.FlowLayout;

import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.event.*;

import javax.swing.JComponent;

public class PanelFlowLayout extends JFrame {

	private JMenuBar barra1;
    private JMenu menu;
    private JMenuItem abrir, item2, item3, gris;
    private Imagen img = new Imagen();
    private JLabel jLabel1;
    private JFrame frame = new JFrame("img");
    

    public PanelFlowLayout(){
       setLayout(new FlowLayout());
       barra1= new JMenuBar();
       setJMenuBar(barra1);
       jLabel1 = new JLabel();
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   frame.add(jLabel1);
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
				frame.setResizable(true);
			}
			else if(e.getSource() == gris){
				jLabel1.setIcon(new ImageIcon(img.escalaGrises()));
			}
		}
       }
       SwingUtilities.invokeLater(new Runnable() { //The EDT //explained below 
    	   public void run() { 
    		   MenuDemo menu = new MenuDemo();
    	   } 
       }); 
    }
	
	public static void main(String[] args) {
		
		PanelFlowLayout marco = new PanelFlowLayout();
	    marco.setVisible(true);
	}

}
