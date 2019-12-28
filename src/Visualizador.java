import ordenamiento.Arreglo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualizador{
    private JLabel thumb;
    private JPanel rootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Visualizador");
        Visualizador vis = new Visualizador();
        frame.setContentPane(vis.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        try{
            BufferedImage img= ImageIO.read(new File("arr.png"));
            ImageIcon icon=new ImageIcon(img);
            frame.setSize(1000,400);
            //vis.thumb.setIcon(icon);


//            ImageIcon icon = new ImageIcon("arr.png");
//            vis.thumb.setIcon(icon);
            Arreglo arr = new Arreglo("C:\\Users\\Monther\\Downloads\\Arreglos.json");
            arr.print();
            System.out.println('\n');
            arr.setThumb(vis.thumb);
            arr.Isort();
            //arr.print();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
