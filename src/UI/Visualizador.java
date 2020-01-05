package UI;

import Clases.Usuario;
import EDD.BTree;
import EDD.HashTable;
import ordenamiento.Arreglo;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Visualizador{
    public JLabel thumb;
    public JPanel rootPanel;

//    public static void main(String[] args) {
////        JFrame frame = new JFrame("Visualizador");
////        Visualizador vis = new Visualizador();
////        frame.setContentPane(vis.rootPanel);
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.pack();
////        frame.setVisible(true);
////
////        try{
//////            BufferedImage img= ImageIO.read(new File("arr.png"));
//////            ImageIcon icon=new ImageIcon(img);
////            frame.setSize(1000,500);
////            //vis.thumb.setIcon(icon);
////
////
//////            ImageIcon icon = new ImageIcon("arr.png");
//////            vis.thumb.setIcon(icon);
//////            Arreglo arr = new Arreglo("C:\\Users\\Monther\\Downloads\\Arreglos.json");
//////            arr.print();
//////            System.out.println('\n');
//////            arr.setThumb(vis.thumb);
//////            arr.Isort();
////            //arr.print();
//////            HashTable table = new HashTable();
//////            table.add(new Usuario("n1","a",33,"1234"));
//////            table.add(new Usuario("n1","a",70,"1234"));
//////            table.add(new Usuario("n1","a",107,"1234"));
//////            table.add(new Usuario("n1","a",144,"1234"));
//////            table.add(new Usuario("n1","a",181,"1234"));
//////            table.add(new Usuario("n1","a",218,"1234"));
//////            table.add(new Usuario("n1","a",255,"1234"));
//////            table.add(new Usuario("n1","a",292,"1234"));
//////            table.add(new Usuario("n1","a",183,"1234"));
//////            table.add(new Usuario("n1","a",109,"1234"));
//////
//////            for(int i=71;i<73;i++){
//////                table.add(new Usuario("n1","a",i,"1234"));
//////            }
//////            for(int i=108;i<110;i++){
//////                table.add(new Usuario("n1","a",i,"1234"));
//////            }
//////            table.add(new Usuario("n1","a",40,"1234"));
//////            table.add(new Usuario("n1","a",81,"1234"));
//////            table.add(new Usuario("n1","a",121,"1234"));
////
//////            table.setThumb(vis.thumb);
//////            table.G();
////
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////
//        BTree t = new BTree(3); // A B-Tree with minium degree 3
//        t.insert(10);
//        t.insert(20);
//        t.insert(5);
//        t.insert(6);
//        t.insert(12);
//        t.insert(30);
//        t.insert(7);
//        t.insert(17);
//        t.insert(11);
//        t.insert(25);
//        t.insert(50);
//        t.insert(63);
//        t.insert(123);
//        t.insert(3);
////        t.insert(8);
////        t.insert(23);
//
//        System.out.println("Traversal of the constucted tree is ");
//        t.traverse();
//        t.graficar();
////
////        int k = 6;
////        if(t.search(k)==null){
////            System.out.println("Not present");
////        }else{
////            System.out.println("Present");
////        }
////        k = 15;
////        if(t.search(k)==null){
////            System.out.println("Not present");
////        }else{
////            System.out.println("Present");
////        }
////
//    }
////

}
