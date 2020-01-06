package UI;

import Clases.Usuario;
import EDD.BTree;
import ordenamiento.Arreglo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Menu {
    JFrame mdA;
    JPanel f;

    public Menu() {
        mdA = new JFrame("Modulo Aprendizaje");
        f = new JPanel(new GridLayout(4,2));

        JButton b1 = new JButton("Grafos (profundidad)");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                    }
                });
            }
        });

        JButton b2 = new JButton("Arbol B");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BTree t = new BTree(3);
                t.setNumeros(cargarArregloBTree(cargarArchivo(mdA)));
                JFrame frame = new JFrame("Visualizador");
                Animacion vis = new Animacion();
                frame.setContentPane(vis.root);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println('\n');
                t.setThumb(vis.img);
                t.setDesc(vis.desc);
                vis.title.setText("Arbol B");
                String[] options = {"Automatico", "Dirigido"};
                int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el modo", "Modo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                if(seleccion==1){
                    t.InDir();
                }else{
                    t.In();
                }

            }
        });

        JButton b3 = new JButton("Arbol AVL");
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                    }
                });
            }
        });

        JButton b4 = new JButton("Bubble sort");
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Arreglo ar = new Arreglo(cargarArchivo(mdA));
                JFrame frame = new JFrame("Visualizador");
                Animacion vis = new Animacion();
                frame.setContentPane(vis.root);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println('\n');
                ar.setThumb(vis.img);
                ar.setDesc(vis.desc);
                vis.title.setText("Bubble sort");
                String[] options = {"Automatico", "Dirigido"};
                int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el modo", "Modo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                if(seleccion==1){
                    ar.BsortG();
                }else{
                    ar.Bsort();
                }
            }
        });

        JButton b5 = new JButton("Insertion sort");
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Arreglo ar = new Arreglo(cargarArchivo(mdA));
                JFrame frame = new JFrame("Visualizador");
                Animacion vis = new Animacion();
                frame.setContentPane(vis.root);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println('\n');
                ar.setThumb(vis.img);
                ar.setDesc(vis.desc);
                vis.title.setText("Insertion sort");
                String[] options = {"Automatico", "Dirigido"};
                int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el modo", "Modo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                if(seleccion==1){
                    ar.IsortG();
                }else{
                    ar.Isort();
                }
            }
        });

        JButton b6 = new JButton("Grafos (anchura)");

        JButton b7 = new JButton("Cerrar sesion");
        b7.addActionListener(e -> SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Principal();
                mdA.dispose();
            }
        }));

        JButton b8 = new JButton("Salir");
        b8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mdA.dispose();
            }
        });

        f.add(b1);
        f.add(b6);
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.add(b5);
        f.add(b7);
        f.add(b8);

        f.setVisible(true);
        f.setSize(300, 300);
        mdA.add(f);
        mdA.setSize(500,300);
        mdA.setLocationRelativeTo(null);
        mdA.setVisible(true);
        mdA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static File cargarArchivo(JFrame padre) {
        File archivo=null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos json", "json");
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(padre);

        try {
            if (result == JFileChooser.APPROVE_OPTION) {
                archivo = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + archivo.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archivo;

    }

    private int[] cargarArregloBTree(File archivo){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(archivo)) {

            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject numbers = (JSONObject) obj;

            JSONArray numArray = (JSONArray) numbers.get("Input");
            int[] nums = new int[numArray.size()];

            // Iterate over employee array
            for (int i = 0; i < numArray.size(); i++) {
                JSONObject nob = (JSONObject) numArray.get(i);
                nums[i] =(int)(long)nob.get("num");
            }
            return nums;

        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
