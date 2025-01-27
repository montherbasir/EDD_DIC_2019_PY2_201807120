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

    public int findKey(int k) {
        int idx = 0;
        while (idx < n && keys[idx] < k) {
            ++idx;
        }
        return idx;
    }

    // A function to remove the key k from the sub-tree rooted with this node
    public void remove(int k, BTreeNode root, JLabel thumb, JLabel desc) throws IOException, InterruptedException {
        System.out.println("remove");

        Thread.sleep(300);
        int idx = findKey(k);

        // The key to be removed is present in this node
        if (idx < n && keys[idx] == k) {
            BTree.graficar(root,this,thumb);
            // If the node is a leaf node - removeFromLeaf is called
            // Otherwise, removeFromNonLeaf function is called
            if (leaf){
                removeFromLeaf(idx, root, thumb);
            }else {
                removeFromNonLeaf(idx, root, thumb, desc);
            }
        } else {

            // If this node is a leaf node, then the key is not present in tree
            if (leaf) {
                System.out.println("The key " + k + " is does not exist in the tree\n");
                return;
            }

            // The key to be removed is present in the sub-tree rooted with this node
            // The flag indicates whether the key is present in the sub-tree rooted
            // with the last child of this node
            boolean flag = (idx == n);

            // If the child where the key is supposed to exist has less that t keys,
            // we fill that child
            System.out.println("idx "+idx);
            System.out.println(C[idx].n+" t "+t);
            if (C[idx].n < t) {
                desc.setText("Moviendo valores");
                BTree.graficar(root, C[idx], thumb);
                fill(idx, root, thumb, desc);
            }

            // If the last child has been merged, it must have merged with the previous
            // child and so we recurse on the (idx-1)th child. Else, we recurse on the
            // (idx)th child which now has atleast t keys
            if (flag && idx > n) {
                C[idx - 1].remove(k, root, thumb, desc);
            }else {
                C[idx].remove(k, root, thumb, desc);
            }
        }
    }

    public void removeG(int k, BTreeNode root, JLabel thumb, JLabel desc) throws IOException, InterruptedException {
        System.out.println("remove");

        Thread.sleep(300);
        int idx = findKey(k);

        // The key to be removed is present in this node
        if (idx < n && keys[idx] == k) {
            BTree.graficar(root,this,thumb);
            // If the node is a leaf node - removeFromLeaf is called
            // Otherwise, removeFromNonLeaf function is called
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            if (leaf){
                removeFromLeaf(idx, root, thumb);
            }else {
                removeFromNonLeaf(idx, root, thumb, desc);
            }
        } else {

            // If this node is a leaf node, then the key is not present in tree
            if (leaf) {
                System.out.println("The key " + k + " is does not exist in the tree\n");
                return;
            }

            // The key to be removed is present in the sub-tree rooted with this node
            // The flag indicates whether the key is present in the sub-tree rooted
            // with the last child of this node
            boolean flag = (idx == n);

            // If the child where the key is supposed to exist has less that t keys,
            // we fill that child
            System.out.println("idx "+idx);
            System.out.println(C[idx].n+" t "+t);
            if (C[idx].n < t) {
                desc.setText("Moviendo valores");
                BTree.graficar(root, C[idx], thumb);
                //JOptionPane.showMessageDialog(null, "Paso Siguiente?");
                fill(idx, root, thumb, desc);
                JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            }

            // If the last child has been merged, it must have merged with the previous
            // child and so we recurse on the (idx-1)th child. Else, we recurse on the
            // (idx)th child which now has atleast t keys
            if (flag && idx > n) {
                C[idx - 1].removeG(k, root, thumb, desc);
            }else {
                C[idx].removeG(k, root, thumb, desc);
            }
        }
    }

    // A function to remove the idx-th key from this node - which is a leaf node
    public void removeFromLeaf(int idx, BTreeNode root, JLabel thumb) throws IOException, InterruptedException {
        //BTree.graficar(root,this,thumb);
        // Move all the keys after the idx-th pos one place backward
        System.out.println("removeFromLeaf");
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1] = keys[i];
        }
        // Reduce the count of keys
        n--;
        BTree.graficar(root,this,thumb);
    }

    // A function to remove the idx-th key from this node - which is a non-leaf node
    public void removeFromNonLeaf(int idx, BTreeNode root, JLabel thumb, JLabel desc) throws IOException, InterruptedException {
        System.out.println("removeFromNonLeaf");
        BTree.graficar(root,this,thumb);
        int k = keys[idx];

        // If the child that precedes k (C[idx]) has at least t keys,
        // find the predecessor 'pred' of k in the subtree rooted at
        // C[idx]. Replace k by pred. Recursively delete pred
        // in C[idx]
        if (C[idx].n >= t) {
            int pred = getPred(idx);
            keys[idx] = pred;
            C[idx].remove(pred, root, thumb, desc);
        }

        // If the child C[idx] has less that t keys, examine C[idx+1].
        // If C[idx+1] has at least t keys, find the successor 'succ' of k in
        // the subtree rooted at C[idx+1]
        // Replace k by succ
        // Recursively delete succ in C[idx+1]
        else if (C[idx + 1].n >= t) {
            int succ = getSucc(idx);
            keys[idx] = succ;
            C[idx + 1].remove(succ, root, thumb, desc);
        }

        // If both C[idx] and C[idx+1] has less that t keys,merge k and all of C[idx+1]
        // into C[idx]
        // Now C[idx] contains 2t-1 keys
        // Free C[idx+1] and recursively delete k from C[idx]
        else {
            merge(idx, root, thumb, desc);
            C[idx].remove(k, root, thumb, desc);
            BTree.graficar(root,this,thumb);
        }

    }

    // A function to get predecessor of keys[idx]
    public int getPred(int idx) {
        // Keep moving to the right most node until we reach a leaf
        BTreeNode cur = C[idx];
        while (!cur.leaf) {
            cur = cur.C[cur.n];
        }
        // Return the last key of the leaf
        return cur.keys[cur.n - 1];
    }

    public int getSucc(int idx) {

        // Keep moving the left most node starting from C[idx+1] until we reach a leaf
        BTreeNode cur = C[idx + 1];
        while (!cur.leaf) {
            cur = cur.C[0];
        }
        // Return the first key of the leaf
        return cur.keys[0];
    }

    // A function to fill child C[idx] which has less than t-1 keys
    public void fill(int idx, BTreeNode root, JLabel thumb, JLabel desc) throws IOException, InterruptedException {

        // If the previous child(C[idx-1]) has more than t-1 keys, borrow a key
        // from that child
        if (idx != 0 && C[idx - 1].n >= t) {
            borrowFromPrev(idx, desc);
        }
            // If the next child(C[idx+1]) has more than t-1 keys, borrow a key
            // from that child
        else if (idx != n && C[idx + 1].n >= t) {
            borrowFromNext(idx, desc);
        }
            // Merge C[idx] with its sibling
            // If C[idx] is the last child, merge it with with its previous sibling
            // Otherwise merge it with its next sibling
        else {
            if (idx != n) {
                merge(idx, root, thumb, desc);
            }else {
                merge(idx - 1, root, thumb, desc);
            }
        }
    }

    // A function to borrow a key from C[idx-1] and insert it
    // into C[idx]
    public void borrowFromPrev(int idx, JLabel desc) throws InterruptedException {
        desc.setText("Prestando de izquierda");
        Thread.sleep(1000);
        BTreeNode child = C[idx];
        BTreeNode sibling = C[idx - 1];

        // The last key from C[idx-1] goes up to the parent and key[idx-1]
        // from parent is inserted as the first key in C[idx]. Thus, the  loses
        // sibling one key and child gains one key

        // Moving all key in C[idx] one step ahead
        for (int i = child.n - 1; i >= 0; --i) {
            child.keys[i + 1] = child.keys[i];
        }
        // If C[idx] is not a leaf, move all its child pointers one step ahead
        if (!child.leaf) {
            for (int i = child.n; i >= 0; --i) {
                child.C[i + 1] = child.C[i];
            }
        }

        // Setting child's first key equal to keys[idx-1] from the current node
        child.keys[0] = keys[idx - 1];

        // Moving sibling's last child as C[idx]'s first child
        if (!child.leaf) {
            child.C[0] = sibling.C[sibling.n];
        }
        // Moving the key from the sibling to the parent
        // This reduces the number of keys in the sibling
        keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n += 1;
        sibling.n -= 1;
    }

    // A function to borrow a key from the C[idx+1] and place
    // it in C[idx]
    public void borrowFromNext(int idx, JLabel desc) throws InterruptedException {
        desc.setText("Prestando de derecha");
        Thread.sleep(1000);
        BTreeNode child = C[idx];
        BTreeNode sibling = C[idx + 1];

        // keys[idx] is inserted as the last key in C[idx]
        child.keys[(child.n)] = keys[idx];

        // Sibling's first child is inserted as the last child
        // into C[idx]
        if (!(child.leaf)) {
            child.C[(child.n) + 1] = sibling.C[0];
        }
        //The first key from sibling is inserted into keys[idx]
        keys[idx] = sibling.keys[0];

        // Moving all keys in sibling one step behind
        for (int i = 1; i < sibling.n; ++i) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        // Moving the child pointers one step behind
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; ++i) {
                sibling.C[i - 1] = sibling.C[i];
            }
        }

        // Increasing and decreasing the key count of C[idx] and C[idx+1]
        // respectively
        child.n += 1;
        sibling.n -= 1;
    }

    // A function to merge C[idx] with C[idx+1]
    // C[idx+1] is freed after merging
    public void merge(int idx, BTreeNode root, JLabel thumb, JLabel desc) throws IOException, InterruptedException {
        desc.setText("Haciendo merge");
        Thread.sleep(900);
        BTreeNode child = C[idx];
        BTreeNode sibling = C[idx + 1];

        // Pulling a key from the current node and inserting it into (t-1)th
        // position of C[idx]
        System.out.println("keys[idx] "+keys[idx]);
        child.keys[t - 1] = keys[idx];

        // Copying the keys from C[idx+1] to C[idx] at the end
        for (int i = 0; i < sibling.n; ++i) {
            System.out.println(sibling.keys[i]);
            child.keys[i + t] = sibling.keys[i];
        }
        // Copying the child pointers from C[idx+1] to C[idx]
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; ++i) {
                child.C[i + t] = sibling.C[i];
            }
        }

        // Moving all keys after idx in the current node one step before -
        // to fill the gap created by moving keys[idx] to C[idx]
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1] = keys[i];
        }
        // Moving the child pointers after (idx+1) in the current node one
        // step before
        for (int i = idx + 2; i <= n; ++i) {
            C[i - 1] = C[i];
        }
        // Updating the key count of child and the current node
        child.n += sibling.n + 1;
        n--;

        // Freeing the memory occupied by sibling
        sibling = null;
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
            if (i < n - 1) {
                childs.append("|");
            }
        }

        if (!leaf) {
            childs.append("|<C").append(n).append(">");
        }

        childs.append("\"");
        if (val == this) {
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

        for (int k = 0; k < n + 1; k++) {
            // If this is not leaf, then before printing key[i],
            // traverse the subtree rooted with child C[i].
            if (!leaf) {
                con.append("\"").append(keys[0]).append("\":C").append(k).append("->\"").append(C[k].keys[0]).append("\";\n");
            }
        }
        Thread.sleep(300);
        return con.toString();
    }

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

public class BTree implements Runnable {
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
        mover = false;
    }

    // function to traverse the tree
    public void traverse() {
        if (root != null) {
            root.traverse();
        }
    }

    public synchronized static void graficar(BTreeNode root, BTreeNode val, JLabel thumb) throws IOException, InterruptedException {
        String g = "digraph {\n" +
                "splines=\"line\";\n" +
                "rankdir = TB;\n" +
                "node [shape=record, height=0.5, width=1.5];\n" +
                "graph[dpi=200];\n" + root.getGraph(val) + "}";

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

    public void In() {
        try {
            mover = true;
            hilo = new Thread(this, "Insert");
            hilo.start();
            op = 0;
            Thread.sleep(200);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void InDir() {
        try {
            mover = true;
            hilo = new Thread(this, "Insert");
            hilo.start();
            op = 1;
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

    public void remove(int k) throws IOException, InterruptedException {
        if (root==null)
        {
            System.out.println("The tree is empty\n");
            return;
        }

        // Call the remove function for root
        graficar(root, root, thumb);
        root.remove(k, root, thumb, desc);

        // If the root node has 0 keys, make its first child as the new root
        //  if it has a child, otherwise set root as NULL
        if (root.n==0)
        {
            BTreeNode tmp = root;
            if (root.leaf) {
                root = null;
            }else {
                root = root.C[0];
                graficar(root, root, thumb);
            }
            // Free the old root
            tmp=null;
        }
    }

    public void removeG(int k) throws IOException, InterruptedException {
        if (root==null)
        {
            System.out.println("The tree is empty\n");
            return;
        }

        // Call the remove function for root
        graficar(root, root, thumb);
        root.removeG(k, root, thumb, desc);

        // If the root node has 0 keys, make its first child as the new root
        //  if it has a child, otherwise set root as NULL
        if (root.n==0)
        {
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            BTreeNode tmp = root;
            if (root.leaf) {
                root = null;
            }else {
                root = root.C[0];
                graficar(root, root, thumb);
            }
            // Free the old root
            tmp=null;
        }
    }

    @Override
    public void run() {
        while (mover) {
            synchronized (clave) {
                try {
                    if (op == 0) {
                        for (int n : numeros) {
                            desc.setText("Insertando " + n);
                            insert(n);
                            Thread.sleep(200);
                        }
                        int r=-1;
                        while(r!=1){
                            r = JOptionPane.showConfirmDialog(null, "¿Desea eliminar un numero?");
                            if(r==0) {
                                int num = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el numero"));
                                desc.setText("Eliminando "+num);
                                remove(num);
                            }
                        }
                    } else {
                        for (int n : numeros) {
                            desc.setText("Insertando " + n);
                            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
                            insertG(n);
                            Thread.sleep(200);
                        }

                        int r=-1;
                        while(r!=1){
                            r = JOptionPane.showConfirmDialog(null, "¿Desea eliminar un numero?");
                            if(r==0) {
                                int num = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el numero"));
                                desc.setText("Eliminando "+num);
                                removeG(num);
                            }
                        }
                    }

                    desc.setText("Finalizado");
                    mover = false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
