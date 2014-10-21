
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
    private JMenuItem item1, item2, item3, item4;

    public PanelFlowLayout(){
       setLayout(new FlowLayout());
       barra1= new JMenuBar();
       setJMenuBar(barra1);
       
       this.setExtendedState(MAXIMIZED_BOTH);
       this.setTitle("Pikaphoto");

       menu= new JMenu("Archivo");
       barra1.add(menu);

       item1= new JMenuItem("Abrir imagen");
       menu.add(item1);
   
       item2= new JMenuItem("Guardar Imagen");
       menu.add(item2);

       item3= new JMenuItem("Cerrar");
       menu.add(item3);
       
       menu= new JMenu("Editar");
       barra1.add(menu);

       item1= new JMenuItem("Copiar");
       menu.add(item1);
   
       item2= new JMenuItem("Cortar");
       menu.add(item2);

       item3= new JMenuItem("Pegar");
       menu.add(item3);

       item4= new JMenuItem("Eliminar");
       menu.add(item4);
    }
	
	public static void main(String[] args) {
		
		PanelFlowLayout marco = new PanelFlowLayout();
	    marco.setVisible(true);
	}

}
