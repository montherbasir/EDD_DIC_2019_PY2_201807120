package EDD;

import ordenamiento.Arreglo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class BTreeNode {
    int[] keys;  // An array of keys
    int t;      // Minimum degree (defines the range for number of keys)
    BTreeNode[] C; // An array of child pointers
    int n;     // Current number of keys
    boolean leaf; // Is true when node is leaf. Otherwise false

    public BTreeNode(int t1, boolean leaf1) {
        // Copy the given minimum degree and leaf property
        t = t1;
        leaf = leaf1;

        // Allocate memory for maximum number of possible keys
        // and child pointers
        keys = new int[2 * t - 1];
        C = new BTreeNode[2 * t];

        // Initialize the number of keys as 0
        n = 0;
    }

    public String getGraph(BTreeNode val) throws InterruptedException {

        StringBuilder childs = new StringBuilder();
        childs.append("\"").append(keys[0]).append("\"");
        childs.append("[label=\"");
        // There are n keys and n+1 children, traverse through n keys
        // and first n children
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf) {
                childs.append("<C").append(i).append(">|");
            }
            childs.append(keys[i]);
            if(i<n-1){
                childs.append("|");
            }
        }

        if (!leaf) {
            childs.append("|<C").append(n).append(">");
        }

        childs.append("\"");
        if(val == this){
            childs.append(", color=\"green\"");
        }
        childs.append("];\n");

        for (int j = 0; j < n; j++) {
            if (!leaf) {
                childs.append(C[j].getGraph(val));
            }
        }

        if (!leaf) {
            childs.append(C[n].getGraph(val));
        }
        Thread.sleep(300);
        return childs.append(getConnections()).toString();
    }

    private String getConnections() throws InterruptedException {
        StringBuilder con = new StringBuilder();

        for (int k = 0; k < n+1; k++) {
            // If this is not leaf, then before printing key[i],
            // traverse the subtree rooted with child C[i].
            if (!leaf) {
                con.append("\"").append(keys[0]).append("\":C").append(k).append("->\"").append(C[k].keys[0]).append("\";\n");
            }
        }
        Thread.sleep(300);
        return con.toString();
    }

    // A function to traverse all nodes in a subtree rooted with this node
    public void traverse() {
        // There are n keys and n+1 children, traverse through n keys
        // and first n children
        int i;
        for (i = 0; i < n; i++) {
            // If this is not leaf, then before printing key[i],
            // traverse the subtree rooted with child C[i].
            if (!leaf) {
                C[i].traverse();
            }
            System.out.println(keys[i]);
        }

        // Print the subtree rooted with last child
        if (!leaf) {
            C[i].traverse();
        }

    }


    // Function to search key k in subtree rooted with this node
    public BTreeNode search(int k) {
        // Find the first key greater than or equal to k
        int i = 0;
        while (i < n && k > keys[i]) {
            i++;
        }

        // If the found key is equal to k, return this node
        if (keys[i] == k) {
            return this;
        }

        // If the key is not found here and this is a leaf node
        if (leaf) {
            return null;
        }

        // Go to the appropriate child
        return C[i].search(k);
    }

    // A utility function to insert a new key in this node
    // The assumption is, the node must be non-full when this
    // function is called
    public synchronized BTreeNode insertNonFull(int k, JLabel desc, BTreeNode root, JLabel thumb) throws InterruptedException, IOException {
       synchronized (BTree.clave) {
           // Initialize index as index of rightmost element
           int i = n - 1;
           BTree.graficar(root, this, thumb);
           Thread.sleep(200);
           // If this is a leaf node
           if (leaf) {
               // The following loop does two things
               // a) Finds the location of new key to be inserted
               // b) Moves all greater keys to one place ahead
               while (i >= 0 && keys[i] > k) {
                   keys[i + 1] = keys[i];
                   i--;
               }

               // Insert the new key at found location
               keys[i + 1] = k;
               n = n + 1;
               BTree.graficar(root, this, thumb);
               Thread.sleep(200);
               return this;
           } else // If this node is not leaf
           {
               // Find the child which is going to have the new key
               while (i >= 0 && keys[i] > k) {
                   //graficar(C[0]);
                   i--;
               }
               // See if the found child is full
               if (C[i + 1].n == 2 * t - 1) {
                   // If the child is full, then split it
                   desc.setText("El nodo esta lleno, se separa antes de insertar");
                   Thread.sleep(900);
                   splitChild(i + 1, C[i + 1], desc, root, thumb);
                   // After split, the middle key of C[i] goes up and
                   // C[i] is splitted into two.  See which of the two
                   // is going to have the new key
                   if (keys[i + 1] < k) {
                       i++;
                   }
               }
               return C[i + 1].insertNonFull(k, desc, root, thumb);
           }
       }
    }

    public synchronized BTreeNode insertNonFullG(int k, JLabel desc, BTreeNode root, JLabel thumb) throws InterruptedException, IOException {
       synchronized (BTree.clave) {
           // Initialize index as index of rightmost element
           int i = n - 1;
           BTree.graficar(root, this, thumb);
           Thread.sleep(200);
           // If this is a leaf node
           if (leaf) {
               JOptionPane.showMessageDialog(null, "Paso Siguiente?");
               // The following loop does two things
               // a) Finds the location of new key to be inserted
               // b) Moves all greater keys to one place ahead
               while (i >= 0 && keys[i] > k) {
                   keys[i + 1] = keys[i];
                   i--;
               }

               // Insert the new key at found location
               keys[i + 1] = k;
               n = n + 1;
               BTree.graficar(root, this, thumb);
               Thread.sleep(200);
               return this;
           } else // If this node is not leaf
           {
               // Find the child which is going to have the new key
               while (i >= 0 && keys[i] > k) {
                   //graficar(C[0]);
                   i--;
               }
               // See if the found child is full
               if (C[i + 1].n == 2 * t - 1) {
                   // If the child is full, then split it
                   desc.setText("El nodo esta lleno, se separa antes de insertar");
                   Thread.sleep(900);
                   splitChildG(i + 1, C[i + 1], desc, root, thumb);
                   // After split, the middle key of C[i] goes up and
                   // C[i] is splitted into two.  See which of the two
                   // is going to have the new key
                   if (keys[i + 1] < k) {
                       i++;
                   }
               }
               return C[i + 1].insertNonFullG(k, desc, root, thumb);
           }
       }
    }


    // A utility function to split the child y of this node
    // Note that y must be full when this function is called
    public synchronized void splitChild(int i, BTreeNode y, JLabel desc, BTreeNode root, JLabel thumb) throws InterruptedException, IOException {
        // Create a new node which is going to store (t-1) keys
        // of y

        synchronized (BTree.clave) {
            BTree.graficar(root, y, thumb);

            BTreeNode z = new BTreeNode(y.t, y.leaf);
            z.n = t - 1;

            // Copy the last (t-1) keys of y to z
            for (int j = 0; j < t - 1; j++) {
                z.keys[j] = y.keys[j + t];
            }

            // Copy the last t children of y to z
            if (!y.leaf) {
                for (int j = 0; j < t; j++) {
                    z.C[j] = y.C[j + t];
                }
            }

            // Reduce the number of keys in y
            y.n = t - 1;

            // Since this node is going to have a new child,
            // create space of new child
            for (int j = n; j >= i + 1; j--) {
                C[j + 1] = C[j];
            }

            // Link the new child to this node
            C[i + 1] = z;

            // A key of y will move to this node. Find the location of
            // new key and move all greater keys one space ahead
            for (int j = n - 1; j >= i; j--) {
                keys[j + 1] = keys[j];
            }

            // Copy the middle key of y to this node
            keys[i] = y.keys[t - 1];
            Thread.sleep(600);
            desc.setText("La mediana es " + y.keys[t - 1]);
            Thread.sleep(100);
            // Increment count of keys in this node
            n = n + 1;
//        BTree.graficar(root,this,thumb);
//        System.out.println("TRES");
//        Thread.sleep(600);

        }
    }

    public synchronized void splitChildG(int i, BTreeNode y, JLabel desc, BTreeNode root, JLabel thumb) throws InterruptedException, IOException {
        // Create a new node which is going to store (t-1) keys
        // of y

        synchronized (BTree.clave) {
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            BTree.graficar(root, y, thumb);

            BTreeNode z = new BTreeNode(y.t, y.leaf);
            z.n = t - 1;

            // Copy the last (t-1) keys of y to z
            for (int j = 0; j < t - 1; j++) {
                z.keys[j] = y.keys[j + t];
            }

            // Copy the last t children of y to z
            if (!y.leaf) {
                for (int j = 0; j < t; j++) {
                    z.C[j] = y.C[j + t];
                }
            }

            // Reduce the number of keys in y
            y.n = t - 1;

            // Since this node is going to have a new child,
            // create space of new child
            for (int j = n; j >= i + 1; j--) {
                C[j + 1] = C[j];
            }

            // Link the new child to this node
            C[i + 1] = z;

            // A key of y will move to this node. Find the location of
            // new key and move all greater keys one space ahead
            for (int j = n - 1; j >= i; j--) {
                keys[j + 1] = keys[j];
            }

            // Copy the middle key of y to this node
            keys[i] = y.keys[t - 1];
            Thread.sleep(600);
            desc.setText("La mediana es " + y.keys[t - 1]);
            Thread.sleep(100);
            // Increment count of keys in this node
            n = n + 1;
//        BTree.graficar(root,this,thumb);
//        System.out.println("TRES");
//        Thread.sleep(600);
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
        }
    }
}

public class BTree implements Runnable{
    BTreeNode root; // Pointer to root node
    int t;  // Minimum degree
    boolean mover;
    Thread hilo;
    public static final Object clave = new Object();
    int actual;
    private JLabel thumb;
    private JLabel desc;
    private int[] numeros;
    private int op;

    public int[] getNumeros() {
        return numeros;
    }

    public void setNumeros(int[] numeros) {
        this.numeros = numeros;
    }

    public JLabel getThumb() {
        return thumb;
    }

    public void setThumb(JLabel thumb) {
        this.thumb = thumb;
    }

    public JLabel getDesc() {
        return desc;
    }

    public void setDesc(JLabel desc) {
        this.desc = desc;
    }

    // Constructor (Initializes tree as empty)
    public BTree(int _t) {
        root = null;
        t = _t;
        mover=false;
    }

    // function to traverse the tree
    public void traverse() {
        if (root != null){
            root.traverse();
        }
    }

    public synchronized static void graficar(BTreeNode root,BTreeNode val, JLabel thumb) throws IOException, InterruptedException {
        String g = "digraph {\n" +
                "splines=\"line\";\n" +
                "rankdir = TB;\n" +
                "node [shape=record, height=0.5, width=1.5];\n" +
                "graph[dpi=200];\n"+root.getGraph(val)+"}";

        BufferedWriter writer = new BufferedWriter(new FileWriter("btree.dot"));
        writer.write(g);

        writer.close();
        Thread.sleep(100);

        String command = "dot -Tpng btree.dot -o btree.png";
        Process p = Runtime.getRuntime().exec(command);
        Thread.sleep(500);
        p.destroy();
        BufferedImage img = ImageIO.read(new File("btree.png"));
        Thread.sleep(300);
        thumb.setIcon(new ImageIcon(Arreglo.scaleimage(1000, 400, img)));
        thumb.repaint();
        thumb.revalidate();
        Thread.sleep(300);
    }

    // function to search a key in this tree
    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    public void In(){
        try {
            mover = true;
            hilo = new Thread(this, "Insert");
            hilo.start();
            op=0;
            Thread.sleep(200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void InDir(){
        try {
            mover = true;
            hilo = new Thread(this, "Insert");
            hilo.start();
            op=1;
            Thread.sleep(200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // The main function that inserts a new key in this B-Tree
    public synchronized void insert(int k) throws InterruptedException, IOException {
        synchronized (clave) {
            //graficar(null);
            BTreeNode colorN;

            // If tree is empty
            if (root == null) {
                // Allocate memory for root
                root = new BTreeNode(t, true);
                root.keys[0] = k;  // Insert key
                root.n = 1;  // Update number of keys in root
                graficar(this.root, this.root, this.thumb);
            } else // If tree is not empty
            {
                graficar(this.root, this.root, this.thumb);

                // If root is full, then tree grows in height
                if (root.n == 2 * t - 1) {
                    desc.setText("La raiz esta llena, se separa antes de insertar");
                    Thread.sleep(800);
                    // Allocate memory for new root
                    BTreeNode s = new BTreeNode(t, false);

                    // Make old root as child of new root
                    s.C[0] = root;

                    // Split the old root and move 1 key to the new root
                    s.splitChild(0, root, desc, root, thumb);

                    // New root has two children now.  Decide which of the
                    // two children is going to have new key
                    int i = 0;
                    if (s.keys[0] < k) {
                        i++;
                    }

                    colorN = s.C[i].insertNonFull(k, desc, s, thumb);
                    Thread.sleep(100);
                    // Change root
                    root = s;
                } else {  // If root is not full, call insertNonFull for root
                    root.insertNonFull(k, desc, this.root, thumb);
                }
                //
            }
        }
    }

    public synchronized void insertG(int k) throws InterruptedException, IOException {
        synchronized (clave) {

            //graficar(null);
            BTreeNode colorN;

            // If tree is empty
            if (root == null) {
                // Allocate memory for root
                root = new BTreeNode(t, true);
                root.keys[0] = k;  // Insert key
                root.n = 1;  // Update number of keys in root
                graficar(this.root, this.root, this.thumb);
            } else // If tree is not empty
            {
                graficar(this.root, this.root, this.thumb);

                // If root is full, then tree grows in height
                if (root.n == 2 * t - 1) {
                    desc.setText("La raiz esta llena, se separa antes de insertar");
                    Thread.sleep(800);
                    // Allocate memory for new root
                    BTreeNode s = new BTreeNode(t, false);

                    // Make old root as child of new root
                    s.C[0] = root;

                    // Split the old root and move 1 key to the new root
                    s.splitChildG(0, root, desc, root, thumb);

                    // New root has two children now.  Decide which of the
                    // two children is going to have new key
                    int i = 0;
                    if (s.keys[0] < k) {
                        i++;
                    }

                    colorN = s.C[i].insertNonFullG(k, desc, s, thumb);
                    Thread.sleep(100);
                    // Change root
                    root = s;
                } else {  // If root is not full, call insertNonFull for root
                    root.insertNonFullG(k, desc, this.root, thumb);
                }
                //
            }
        }
    }


    @Override
    public void run() {
        while (mover) {
            synchronized (clave) {
                try {
                    if(op==0){
                        for(int n : numeros){
                            desc.setText("Insertando "+n);
                            insert(n);
                            Thread.sleep(200);
                        }
                    }else{
                        for(int n : numeros){
                            desc.setText("Insertando "+n);
                            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
                            insertG(n);
                            Thread.sleep(200);
                        }
                    }

                    desc.setText("Finalizado");
                    mover=false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
