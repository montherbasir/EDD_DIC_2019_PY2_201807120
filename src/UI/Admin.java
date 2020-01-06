package UI;

import Clases.Usuario;
import EDD.HashTable;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Admin {
    JFrame mdA;
    JPanel f;
    private  static HashTable users = Principal.users;
    public Admin() {
        mdA = new JFrame("Modulo Administrativo");
        f = new JPanel(new GridLayout(3,2));

        JButton b1 = new JButton("Cargar usuarios");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        cargarArchivo(mdA);
                    }
                });
            }
        });

        JButton b2 = new JButton("Reporte usuarios");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JFrame frame = new JFrame("Visualizador");
                        Visualizador vis = new Visualizador();
                        frame.setContentPane(vis.rootPanel);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        users.setThumb(vis.thumb);
                        users.G();
                    }
                });
            }
        });

        JButton b3 = new JButton("Editar usuarios");
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JFrame frame = new JFrame("Visualizador");
                        Animacion vis = new Animacion();
                        frame.setContentPane(vis.root);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        Arreglo arr = new Arreglo(new File("C:\\Users\\Monther\\Downloads\\Arreglos.json"));
                        arr.print();
                        System.out.println('\n');
                        arr.setThumb(vis.img);
                        arr.setDesc(vis.desc);
                        vis.title.setText("Hola esto es el titulo");
                        vis.desc.setText("esto es una descripcion de lo que esta pasando a ver que pasa con esa babosada si se que da muy graaande");
                        arr.Bsort();
                    }
                });
            }
        });

        JButton b4 = new JButton("Cerrar sesión");
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Principal();
                        mdA.dispose();
                    }
                });
            }
        });

        JButton b8 = new JButton("Salir");
        b8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mdA.dispose();
            }
        });

        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.add(b8);

        f.setVisible(true);
        f.setSize(300, 300);
        mdA.add(f);
        mdA.setSize(500,300);
        mdA.setLocationRelativeTo(null);
        mdA.setVisible(true);
        mdA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static void cargarArchivo(JFrame padre) {
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

        JSONParser jsonParser = new JSONParser();

        assert archivo != null;
        try (FileReader reader = new FileReader(archivo)) {

            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray usrArray = (JSONArray) obj;

            // Iterate over employee array
            for (Object o : usrArray) {
                JSONObject usrObj = (JSONObject) o;
                String carne = (String) usrObj.get("Carnet");
                int c = Integer.parseInt(carne.replace("-", ""));
                System.out.println(c);
                String p = (String) usrObj.get("Password");
                Usuario u = new Usuario((String) usrObj.get("Nombre"), (String) usrObj.get("Apellido"), c, p);
                if(users.buscar(c)==null){
                    if(p.length()>=8){
                        users.add(u);
                    }else{
                        System.out.println("NO VALIDO corto "+c);
                    }
                }else{
                    System.out.println("NO VALIDO repetido "+c);
                }
            }

        } catch (IOException | ParseException | NullPointerException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
