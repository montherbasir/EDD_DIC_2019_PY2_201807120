package EDD;
import Clases.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class HashNode
{
    private int key;
    private Usuario usuario;

    public HashNode(Usuario usuario) {
        this.usuario = usuario;
        this.key = usuario.getCarne();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

public class HashTable implements Runnable{
    private Thread hilo;
    private final Object clave = new Object();
    private boolean mover;
    private JLabel thumb;

    public void setThumb(JLabel thumb) {
        this.thumb = thumb;
    }

    private HashNode[] bucketArray;

    private int capacidad;

    private int size;

    private int redimension;

    private final int[] PRIMOS= {41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
            127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
            233, 239, 241, 251, 257, 263, 269 ,271 ,277 ,281,
            283, 293 ,307, 311, 313, 317, 331, 337, 347, 349,
            353, 359, 367, 373 ,379 ,383 ,389 ,397, 401, 409,
            419, 421, 431, 433, 439 ,443, 449 ,457, 461, 463,
            467, 479, 487 ,491, 499 ,503 ,509, 521, 523, 541,
            547, 557, 563, 569, 571, 577 ,587 ,593 ,599 ,601,
            607, 613, 617 ,619 ,631 ,641 ,643 ,647 ,653 ,659,
            661, 673, 677, 683 ,691, 701, 709, 719, 727, 733,
            739 ,743, 751, 757, 761 ,769 ,773 ,787 ,797 ,809,
            811, 821, 823, 827, 829, 839, 853, 857 ,859, 863,
            877, 881, 883, 887, 907, 911, 919, 929, 937, 941,
            947 ,953, 967, 971 ,977 ,983, 991, 997 ,1009, 1013,
            1019, 1021, 1031, 1033, 1039, 1049 ,1051 ,1061 ,1063, 1069,
            1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151,
            1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223,
            1229, 1231, 1237, 1249, 1259, 1277, 1279 ,1283, 1289, 1291
            };

    public HashTable()
    {
        capacidad = 37;
        bucketArray = new HashNode[capacidad];
        size = 0;
        redimension = 0;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size() == 0; }

    private int getIndex(int key)
    {
        return key % capacidad;
    }

    public Usuario remove(int key)
    {
        Usuario u=null;
        int ind = getIndex(key);
        if(bucketArray[ind]!=null){
            if(bucketArray[ind].getKey()==key){
                u= bucketArray[ind].getUsuario();
                bucketArray[ind] = null;
            }else{
                int i = 0;
                int index = ind;
                while(bucketArray[index]!=null){
                    if(bucketArray[index].getKey()==key){
                        u = bucketArray[index].getUsuario();
                        bucketArray[index] = null;
                        break;
                    }else{
                        index = (key % capacidad + 1 ) * i;
                        i++;
                    }
                }
            }
        }

        size--;
        return u;
    }

    public Usuario buscar(int key)
    {
        Usuario u=null;
        int ind = getIndex(key);
        if(bucketArray[ind]!=null){
            if(bucketArray[ind].getKey()==key){
                u= bucketArray[ind].getUsuario();
            }else{
                int i = 0;
                int index = ind;
                while(bucketArray[index]!=null){
                    if(bucketArray[index].getKey()==key){
                        u = bucketArray[index].getUsuario();
                        break;
                    }else{
                        index = (key % capacidad + 1 ) * i;
                        i++;
                    }
                }
            }
        }

        return u;
    }

    public void add(Usuario value)
    {
        int ind = getIndex(value.getCarne());
        if(bucketArray[ind]==null){
            bucketArray[ind] = new HashNode(value);
        }else{
            int i = 0;
            int index = ind;
            while(bucketArray[index]!=null){
                index = (value.getCarne() % capacidad + 1 ) * i;
                i++;
                if(index>capacidad-1){
                    index = index % capacidad;
                }
            }
            bucketArray[index] = new HashNode(value);
        }
        size++;

        // If load factor goes beyond threshold, then
        // double hash table size
        System.out.println("size "+size+" cap "+capacidad);
        if ((1.0*size)/capacidad > 0.55)
        {
            System.out.println("crecio");
            HashNode[] temp = bucketArray;
            capacidad = PRIMOS[redimension];
            redimension++;
            bucketArray = new HashNode[capacidad];
            size = 0;

            for (HashNode headNode : temp)
            {
                if (headNode != null)
                {
                    add(headNode.getUsuario());
                }
            }
        }
    }

    public void G(){
        mover=true;
        System.out.println("Entra");
        hilo = new Thread(this);
        hilo.start();
    }

    private void graficar(JLabel thumb) throws IOException, InterruptedException {
        StringBuilder graph = new StringBuilder("digraph G\n" +
                "{\n" +
                "    rankdir = TB;\n" +
                "    dpi=300;\n"+
                "    \n" +
                "    node1\n" +
                "    [\n" +
                "        shape = none\n" +
                "        label = <<table border=\"0\" cellspacing=\"0\">\n"+
                "        <tr><td border=\"1\" width=\"80\">Indice</td>"+"" +
                "        <td border=\"1\" width=\"80\">Carne</td>"+
                "        <td border=\"1\" width=\"80\">Nombre</td>"+
                "        <td border=\"1\" width=\"80\">Apellido</td>"+
                "        <td border=\"1\" width=\"80\">Password</td>"+
                "        </tr>");

        int y = 0;
        for (HashNode n : this.bucketArray) {
            if(n!=null){
                Usuario usr = n.getUsuario();
                graph.append("      <tr>\n<td border=\"1\" width=\"80\">").append(y).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(usr.getCarne()).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(usr.getNombre()).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(usr.getApellido()).append("</td>\n");
                graph.append("      <td border=\"1\" width=\"80\">").append(usr.getPassword()).append("</td>\n</tr>\n");
            }
            y++;
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
        thumb.setIcon(new ImageIcon(scaleimage(900,2000,img)));
        System.out.println("jala");
        mover = false;

    }

    @Override
    public void run() {
        while(mover) {
            synchronized(clave){
                try {
                    graficar(thumb);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    private Image scaleimage(int wid, int hei, BufferedImage img){
        Image im = img;
        double scale;
        double imw = img.getWidth();
        double imh = img.getHeight();
        if (wid > imw && hei > imh){
            im = img;
        }else if(wid/imw < hei/imh){
            scale = wid/imw;
            im = img.getScaledInstance((int) (scale*imw), (int) (scale*imh), Image.SCALE_SMOOTH);
        }else if (wid/imw > hei/imh){
            scale = hei/imh;
            im = img.getScaledInstance((int) (scale*imw), (int) (scale*imh), Image.SCALE_SMOOTH);
        }else if (wid/imw == hei/imh){
            scale = wid/imw;
            im = img.getScaledInstance((int) (scale*imw), (int) (scale*imh), Image.SCALE_SMOOTH);
        }
        return im;
    }
}
