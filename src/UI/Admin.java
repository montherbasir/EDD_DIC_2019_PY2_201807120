package UI;

import Clases.ErrorU;
import Clases.Usuario;

import EDD.HashTable;
import EDD.Lista;
import ordenamiento.Arreglo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Admin implements Runnable{
    JFrame mdA;
    JPanel f;
    private Thread hilo;
    private final Object clave = new Object();
    private boolean mover;
    private JLabel thumb;

    public void setThumb(JLabel thumb) {
        this.thumb = thumb;
    }
    private  static HashTable users = Principal.users;
    private static Lista<ErrorU> problematicos = Principal.problematicos;
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
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        users.setThumb(vis.thumb);
                        users.G();
                    }
                });
            }
        });

        JButton b3 = new JButton("Usuarios con error");
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JFrame frame = new JFrame("Visualizador");
                        Visualizador vis = new Visualizador();
                        frame.setContentPane(vis.rootPanel);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        graficarError(vis.thumb);
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
                        problematicos.add_last(new ErrorU(u,"Password muy corta"));
                    }
                }else{
                    System.out.println("NO VALIDO repetido "+c);
                    problematicos.add_last(new ErrorU(u,"Usuario repetido"));
                }
            }

        } catch (IOException | ParseException | NullPointerException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void graficarError(JLabel thumb){
        mover=true;
        setThumb(thumb);
        System.out.println("Entra");
        hilo = new Thread(this);
        hilo.start();
    }

    private void G(JLabel thumb) throws IOException, InterruptedException {
        StringBuilder graph = new StringBuilder("digraph G\n" +
                "{\n" +
                "    rankdir = TB;\n" +
                "    dpi=300;\n"+
                "    \n" +
                "    node1\n" +
                "    [\n" +
                "        shape = none\n" +
                "        label = <<table border=\"0\" cellspacing=\"0\">\n"+
                "        <tr>"+
                "        <td border=\"1\" width=\"80\">Carne</td>"+
                "        <td border=\"1\" width=\"80\">Nombre</td>"+
                "        <td border=\"1\" width=\"80\">Apellido</td>"+
                "        <td border=\"1\" width=\"80\">Razon</td>"+
                "        </tr>");

        for (int y=0; y<problematicos.getSize();y++) {
                Usuario usr = problematicos.get_element_at(y).getUser();
                graph.append("      <tr>\n<td border=\"1\" width=\"80\">").append(usr.getCarne()).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(usr.getNombre()).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(usr.getApellido()).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(problematicos.get_element_at(y).getProblem()).append("</td>\n</tr>\n");
        }

        graph.append(
                "                </table>>\n" +
                        "    ]\n" +
                        "}");

        BufferedWriter writer = new BufferedWriter(new FileWriter("hash.dot"));
        writer.write(String.valueOf(graph));

        writer.close();
        Thread.sleep(300);

        String command = "dot -Tpng hash.dot -o hash.png";
        Process p = Runtime.getRuntime().exec(command);
        Thread.sleep(1000);
        BufferedImage img= ImageIO.read(new File("hash.png"));
        Thread.sleep(100);
        thumb.setIcon(new ImageIcon(Arreglo.scaleimage(3000,2000,img)));
        System.out.println("jala");
        mover = false;

    }

    @Override
    public void run() {
        while(mover) {
            synchronized(clave){
                try {
                    G(thumb);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
