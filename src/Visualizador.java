import Clases.Usuario;
import EDD.HashTable;
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
//            BufferedImage img= ImageIO.read(new File("arr.png"));
//            ImageIcon icon=new ImageIcon(img);
            frame.setSize(1000,500);
            //vis.thumb.setIcon(icon);


//            ImageIcon icon = new ImageIcon("arr.png");
//            vis.thumb.setIcon(icon);
//            Arreglo arr = new Arreglo("C:\\Users\\Monther\\Downloads\\Arreglos.json");
//            arr.print();
//            System.out.println('\n');
//            arr.setThumb(vis.thumb);
//            arr.Isort();
            //arr.print();
            HashTable table = new HashTable();
            table.add(new Usuario("n1","a",33,"1234"));
            table.add(new Usuario("n1","a",70,"1234"));
            table.add(new Usuario("n1","a",107,"1234"));
            table.add(new Usuario("n1","a",144,"1234"));
            table.add(new Usuario("n1","a",181,"1234"));
            table.add(new Usuario("n1","a",218,"1234"));
            table.add(new Usuario("n1","a",255,"1234"));
            table.add(new Usuario("n1","a",292,"1234"));
            //table.add(new Usuario("n1","a",183,"1234"));
            //table.add(new Usuario("n1","a",109,"1234"));
//            for(int i=33;i<37;i++){
//                table.add(new Usuario("n1","a",i,"1234"));
//            }
//            for(int i=70;i<75;i++){
//                table.add(new Usuario("n1","a",i,"1234"));
//            }
//            for(int i=107;i<111;i++){
//                table.add(new Usuario("n1","a",i,"1234"));
//            }

            table.setThumb(vis.thumb);
            table.G();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
