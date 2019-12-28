package ordenamiento;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import com.mortennobel.imagescaling.ResampleOp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Arreglo implements Runnable{
    public long[] arreglo;
    public int size;
    private Thread hilo;
    private final Object clave = new Object();
    private boolean pausado;
    private boolean mover;
    private int alg;
    private JLabel thumb;

    public Arreglo(String ruta){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(ruta)) {

            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject numbers = (JSONObject) obj;

            JSONArray numArray = (JSONArray) numbers.get("Array");
            this.size = numArray.size();
            this.arreglo = new long[this.size];

            // Iterate over employee array
            for (int i=0; i<this.size; i++) {
                this.arreglo[i]=parseObject((JSONObject) numArray.get(i));
            }

        } catch (IOException | ParseException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static long parseObject(JSONObject user) {
        return (long) user.get("num");
    }

    public JLabel getThumb() {
        return thumb;
    }

    public void setThumb(JLabel thumb) {
        this.thumb = thumb;
    }

    public long[] getArreglo() {
        return arreglo;
    }

    public void setArreglo(long[] arreglo) {
        this.arreglo = arreglo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void Bsort(){
        try {
            mover = true;
            pausado = false;
            alg = 1;
            hilo = new Thread(this, "Bsort");
            hilo.start();
            Thread.sleep(200);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void bubbleSort() throws IOException, InterruptedException {

        long aux;
        for (int i = 0; i < this.size - 1; i++) {
            for (int j = i + 1; j < this.size; j++) {
                try {
                    graficar(i, j, thumb);
                    Thread.sleep(500);
                    System.out.println("hola "+i+" "+j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (this.arreglo[i] > this.arreglo[j]) {
                    aux = this.arreglo[i];
                    this.arreglo[i] = this.arreglo[j];
                    this.arreglo[j] = aux;
                }
                //Thread.sleep(2000);
            }
        }
        Thread.sleep(500);
        graficar(size-2, size-1, thumb);
        mover = false;
        print();
    }

    public void Isort(){
        try {
            mover = true;
            pausado = false;
            alg = 2;
            hilo = new Thread(this, "Isort");
            hilo.start();
            Thread.sleep(200);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void insertionSort() {
        for (int i = 1; i < this.size; ++i) {
            long key = this.arreglo[i];
            int j = i - 1;
            try {
                graficarInsertion(i, j, key, thumb);
                Thread.sleep(2000);
//                    System.out.println("hola "+i+" "+j);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && this.arreglo[j] > key) {
                try {
                    graficarInsertion(i, j, key, thumb);
                    Thread.sleep(2000);
//                    System.out.println("hola "+i+" "+j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.arreglo[j + 1] = this.arreglo[j];
                j = j - 1;
            }
            this.arreglo[j + 1] = key;
        }
        try {
            Thread.sleep(200);
            graficarInsertion(size-1, size-2, arreglo[size-1],thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mover=false;
    }

    /* This function takes last element as pivot,
       places the pivot element at its correct
       position in sorted array, and places all
       smaller (smaller than pivot) to left of
       pivot and all greater elements to right
       of pivot */
    private int partition(long[] arr, int low, int high) {
        long pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                i++;

                // swap arr[i] and arr[j]
                long temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        long temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void Qsort(long[] arr, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            Qsort(arr, low, pi - 1);
            Qsort(arr, pi + 1, high);
        }
    }

    public void quickSort() {
        Qsort(this.arreglo, 0, this.arreglo.length - 1);
    }

    public void print() {
        for (long l : this.arreglo) {
            System.out.println(l);
        }

    }

    private void graficar(int i, int j, JLabel thumb) throws IOException, InterruptedException {
        StringBuilder graph = new StringBuilder("digraph G\n" +
                "{\n" +
                "    rankdir = TB;\n" +
                "    dpi=200;\n"+
                "    \"Valores:\"[shape=plaintext, fontcolor=red, fontsize=18];\n" +
                "    \"Indices:\"[shape=plaintext, fontcolor=red, fontsize=18];\n" +
                "    \"Valores:\" -> \"Indices:\" [color=white];\n" +
                "    \n" +
                "    node3\n" +
                "    [\n" +
                "        shape = none\n" +
                "        label = <<table border=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                    <td port=\"p1\" border=\"1\" width=\"50\" bgcolor=\"#1ac6ff\">i</td>\n" +
                "                    <td port=\"p2\" border=\"1\" width=\"50\" bgcolor=\"#00ffbf\">j</td>\n" +
                "                    </tr>\n" +
                "                </table>>\n" +
                "    ]\n\n");

        graphBody(graph,i,j,thumb);

    }

    private void graficarInsertion(int i, int j, long key, JLabel thumb) throws IOException, InterruptedException {
        StringBuilder graph = new StringBuilder("digraph G\n" +
                "{\n" +
                "    rankdir = TB;\n" +
                "    dpi=200;\n"+
                "    \"Valores:\"[shape=plaintext, fontcolor=red, fontsize=18];\n" +
                "    \"Indices:\"[shape=plaintext, fontcolor=red, fontsize=18];\n" +
                "    \"Valores:\" -> \"Indices:\" [color=white];\n" +
                "    \n" +
                "    node3\n" +
                "    [\n" +
                "        shape = none\n" +
                "        label = <<table border=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                    <td port=\"p1\" border=\"1\" width=\"50\" bgcolor=\"#1ac6ff\">i</td>\n" +
                "                    <td port=\"p2\" border=\"1\" width=\"50\" bgcolor=\"#00ffbf\">j</td>\n" +
                "                    <td port=\"p3\" border=\"1\" width=\"50\" >Key: "+key+"</td>\n" +
                "                    </tr>\n" +
                "                </table>>\n" +
                "    ]\n\n");

        graphBody(graph,i,j,thumb);

    }

    private void graphBody(StringBuilder graph, int i, int j, JLabel thumb) throws IOException, InterruptedException {
        graph.append("node1\n" + "    [\n" + "        shape = none\n" + "        label = <<table border=\"0\" cellspacing=\"0\">\n" + "                    <tr>\n");

        int a = 0;
        for (long l : this.arreglo) {
            graph.append("<td border=\"1\" width=\"80\" ");
            if(a==i){
                graph.append("bgcolor=\"#1ac6ff\"");
                System.out.println("i = "+a);
            }
            if(a==j){
                graph.append("bgcolor=\"#00ffbf\"");
                System.out.println("j = "+a);
            }
            graph.append(">").append(l).append("</td>\n");
            a++;
        }

        graph.append("                    </tr>\n" +
                "                </table>>\n" +
                "    ]\n" +
                "    \n" +
                "    node2\n" +
                "    [\n" +
                "        shape = none\n" +
                "        label = <<table border=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>");

        int y = 0;
        for (long l : this.arreglo) {
            graph.append("<td border=\"0\" width=\"80\">").append(y).append("</td>\n");
            y++;
        }

        graph.append("                    </tr>\n" +
                "                </table>>\n" +
                "    ]\n" +
                "    \n" +
                "    node1 -> node2[ style = invis ];\n" +
                "    node3 -> node1[ style = invis ];\n" +
                "    \n" +
                "    { rank=same; \"Valores:\"; node1 }\n" +
                "    { rank=same; \"Indices:\"; node2 }\n" +
                "    \n" +
                "}");

        //System.out.println(graph);

        BufferedWriter writer = new BufferedWriter(new FileWriter("arr.dot"));
        writer.write(String.valueOf(graph));

        writer.close();
        Thread.sleep(300);

        String command = "dot -Tpng arr.dot -o arr.png";
        Process p = Runtime.getRuntime().exec(command);
        Thread.sleep(300);
        BufferedImage img= ImageIO.read(new File("arr.png"));
        Thread.sleep(300);
        thumb.setIcon(new ImageIcon(scaleimage(1000,400,img)));
    }

    @Override
    public void run() {
        while(mover) {
            synchronized(clave){
                    try {
                        switch (alg) {
                            case 1:
                                bubbleSort();
                                break;
                            case 2:
                                insertionSort();
                                break;
                        }
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