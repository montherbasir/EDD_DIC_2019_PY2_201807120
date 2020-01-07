package EDD;

import ordenamiento.Arreglo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Node {
    private Node left, right;
    private int height = 1;
    private int value;

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node(int val) {
        this.value = val;
    }

    public String getGraph(int k){
        String g;
        if(left==null&&right==null){
            g = "\""+value+"\" [label=\""+value+"\"";
            if(k==this.value){
                g+=", color=\"green\"";
            }
            g+="];\n";
        }else{
            g = "\""+value+"\" [label=\"<C0>|"+value+"|<C1>\"";
            if(k==this.value){
                g+=", color=\"green\"";
            }
            g+="];\n";
        }

        if(left!=null){
            g += left.getGraph(k) + "\""+value+"\":C0 -> \""+left.value+"\";\n";
        }
        if(right!=null){
            g += right.getGraph(k) + "\""+value+"\":C1 -> \""+right.value+"\";\n";
        }
        return g;
    }

    public String getGraphRot(int n1, int n2){
        String g;
        if(left==null&&right==null){
            g = "\""+value+"\" [label=\""+value;
            if(n1==this.value){
                g+=": n\", color=\"dodgerblue4\"";
            }
            else if(n2==this.value){
                g+=": n1\", color=\"firebrick\"";
            }
            else{
                g+="\"";
            }
            g+="];\n";
        }else{
            g = "\""+value+"\" [label=\"<C0>|"+value;
            if(n1==this.value){
                g+=": n|<C1>\", color=\"dodgerblue4\"";
            }
            else if(n2==this.value){
                g+=": n1|<C1>\", color=\"firebrick\"";
            }
            else{
                g+="|<C1>\"";
            }
            g+="];\n";
        }

        if(left!=null){
            g += left.getGraphRot(n1,n2) + "\""+value+"\":C0 -> \""+left.value+"\";\n";
        }
        if(right!=null){
            g += right.getGraphRot(n1,n2) + "\""+value+"\":C1 -> \""+right.value+"\";\n";
        }
        return g;
    }
}

public class AVLTree implements Runnable{

    private Node root;
    boolean mover;
    Thread hilo;
    public static final Object clave = new Object();
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

    public AVLTree(){
        this.root = null;
    }

    private int height(Node N) {
        if (N == null)
            return 0;
        return N.getHeight();
    }

    private Node insert(Node root,Node node, int value) throws IOException, InterruptedException {
        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            node = new Node(value);
            if(root==null){
                graficar(node,node.getValue());
            }else{
                graficar(root,node.getValue());
            }

            return node;
        }
        graficar(root, node.getValue());
        if (value < node.getValue()) {
            node.setLeft(insert(root, node.getLeft(), value));
            graficar(root,node.getLeft().getValue());
        }else {
            node.setRight(insert(root, node.getRight(), value));
            graficar(root,node.getRight().getValue());
        }

        /* 2. Update height of this ancestor node */
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);

        /* 3. Get the balance factor of this ancestor node to check whether
           this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases
        if(balance<2&&balance>-2){
            graficar(root, node.getValue());
            desc.setText("Balanceado, FE = "+balance);
            Thread.sleep(500);
        }
        // Left Left Case
        if (balance > 1 && value < node.getLeft().getValue()) {
            desc.setText("Desbalanceo Izquierda Izquierda");
            return rightRotate(node,root);
        }

        // Right Right Case
        if (balance < -1 && value > node.getRight().getValue()){
            graficar(root,node.getValue());
            desc.setText("Desbalanceo Derecha Derecha");
            return leftRotate(node, root);
        }

        // Left Right Case
        if (balance > 1 && value > node.getLeft().getValue()) {
            graficar(root,node.getValue());
            desc.setText("Desbalanceo Izquierda Derecha");
            node.setLeft(leftRotate(node.getLeft(), root));
            return rightRotate(node,root);
        }

        // Right Left Case
        if (balance < -1 && value < node.getRight().getValue()) {
            graficar(root,node.getValue());
            desc.setText("Desbalanceo Derecha Izquierda");
            node.setRight(rightRotate(node.getRight(),root));
            return leftRotate(node, root);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    private Node insertG(Node root,Node node, int value) throws IOException, InterruptedException {
        JOptionPane.showMessageDialog(null, "Paso Siguiente?");
        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            node = new Node(value);
            if(root==null){
                graficar(node,node.getValue());
            }else{
                graficar(root,node.getValue());
            }

            return node;
        }
        graficar(root, node.getValue());
        if (value < node.getValue()) {
            node.setLeft(insert(root, node.getLeft(), value));
            graficar(root,node.getLeft().getValue());
        }else {
            node.setRight(insert(root, node.getRight(), value));
            graficar(root,node.getRight().getValue());
        }

        /* 2. Update height of this ancestor node */
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);

        /* 3. Get the balance factor of this ancestor node to check whether
           this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases
        if(balance<2&&balance>-2){
            graficar(root, node.getValue());
            desc.setText("Balanceado, FE = "+balance);
            Thread.sleep(500);
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
        }
        // Left Left Case
        if (balance > 1 && value < node.getLeft().getValue()) {
            desc.setText("Desbalanceo Izquierda Izquierda");
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            return rightRotateG(node,root);
        }

        // Right Right Case
        if (balance < -1 && value > node.getRight().getValue()){
            graficar(root,node.getValue());
            desc.setText("Desbalanceo Derecha Derecha");
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            return leftRotateG(node, root);
        }

        // Left Right Case
        if (balance > 1 && value > node.getLeft().getValue()) {
            graficar(root,node.getValue());
            desc.setText("Desbalanceo Izquierda Derecha");
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            node.setLeft(leftRotateG(node.getLeft(), root));
            Thread.sleep(300);
            return rightRotateG(node,root);
        }

        // Right Left Case
        if (balance < -1 && value < node.getRight().getValue()) {
            graficar(root,node.getValue());
            desc.setText("Desbalanceo Derecha Izquierda");
            JOptionPane.showMessageDialog(null, "Paso Siguiente?");
            node.setRight(rightRotateG(node.getRight(),root));
            Thread.sleep(300);
            return leftRotateG(node, root);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    private Node rightRotate(Node y, Node r) throws IOException, InterruptedException {
        graficarRot(r,y.getValue(),y.getLeft().getValue());
        Node x = y.getLeft();
        Node T2 = x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        // Update heights
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        // Return new root
        return x;
    }

    private Node leftRotate(Node x,Node r) throws IOException, InterruptedException {
        graficarRot(r,x.getValue(),x.getRight().getValue());
        Node y = x.getRight();
        Node T2 = y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        //  Update heights
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        // Return new root
        return y;
    }

    private Node rightRotateG(Node y, Node r) throws IOException, InterruptedException {
        graficarRot(r,y.getValue(),y.getLeft().getValue());
        Node x = y.getLeft();
        Node T2 = x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        // Update heights
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        JOptionPane.showMessageDialog(null, "Paso Siguiente?");
        // Return new root
        return x;
    }

    private Node leftRotateG(Node x,Node r) throws IOException, InterruptedException {
        graficarRot(r,x.getValue(),x.getRight().getValue());
        Node y = x.getRight();
        Node T2 = y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        //  Update heights
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        JOptionPane.showMessageDialog(null, "Paso Siguiente?");
        // Return new root
        return y;
    }

    // Get Balance factor of node N
    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.getLeft()) - height(N.getRight());
    }

    public void preOrder(Node root) {
        if (root != null) {
            preOrder(root.getLeft());
            System.out.printf("%d ", root.getValue());
            preOrder(root.getRight());
        }
    }

    private Node minValueNode(Node node) {
        Node current = node;
        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    private Node deleteNode(Node root, int value, Node r) throws IOException, InterruptedException {
        // STEP 1: PERFORM STANDARD BST DELETE

        if (root == null)
            return root;

        // If the value to be deleted is smaller than the root's value,
        // then it lies in left subtree
        if (value < root.getValue())
            root.setLeft(deleteNode(root.getLeft(), value, r));

            // If the value to be deleted is greater than the root's value,
            // then it lies in right subtree
        else if (value > root.getValue())
            root.setRight(deleteNode(root.getRight(), value, r));

            // if value is same as root's value, then This is the node
            // to be deleted
        else {
            // node with only one child or no child
            if ((root.getLeft() == null) || (root.getRight() == null)) {

                Node temp;
                if (root.getLeft() != null)
                    temp = root.getLeft();
                else
                    temp = root.getRight();

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child

                temp = null;
            } else {
                // node with two children: Get the inorder successor (smallest
                // in the right subtree)
                Node temp = minValueNode(root.getRight());

                // Copy the inorder successor's data to this node
                root.setValue(temp.getValue());

                // Delete the inorder successor
                root.setRight(deleteNode(root.getRight(), temp.getValue(), r));
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root,r);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotate(root.getLeft(), r));
            return rightRotate(root,r);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root, r);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotate(root.getRight(),r));
            return leftRotate(root, r);
        }

        return root;
    }

    public void graficar(Node root, int value) throws IOException, InterruptedException {
        String graph = "digraph {\n"+
        "splines=\"line\";\n"+
        "rankdir = TB;\n"+
        "node [shape=record, height=0.5, width=1.5];\n"+
        "graph[dpi=300];\n\n";

        graph += root.getGraph(value);

        graph += "}";

        System.out.println(graph);

        BufferedWriter writer = new BufferedWriter(new FileWriter("avl.dot"));
        writer.write(graph);

        writer.close();
        Thread.sleep(100);

        String command = "dot -Tpng avl.dot -o avl.png";
        Process p = Runtime.getRuntime().exec(command);
        Thread.sleep(650);
        p.destroy();
        Thread.sleep(1000);
        BufferedImage img = ImageIO.read(new File("avl.png"));
        Thread.sleep(700);
        thumb.setIcon(new ImageIcon(Arreglo.scaleimage(1000, 400, img)));
        thumb.repaint();
        thumb.revalidate();
        Thread.sleep(300);
    }

    public void graficarRot(Node root, int n1, int n2) throws IOException, InterruptedException {
        String graph = "digraph {\n"+
                "splines=\"line\";\n"+
                "rankdir = TB;\n"+
                "node [shape=record, height=0.5, width=1.5];\n"+
                "graph[dpi=300];\n\n";

        graph += root.getGraphRot(n1, n2);

        graph += "}";

        System.out.println(graph);

        BufferedWriter writer = new BufferedWriter(new FileWriter("avl.dot"));
        writer.write(graph);

        writer.close();
        Thread.sleep(100);

        String command = "dot -Tpng avl.dot -o avl.png";
        Process p = Runtime.getRuntime().exec(command);
        Thread.sleep(650);
        p.destroy();
        Thread.sleep(1000);
        BufferedImage img = ImageIO.read(new File("avl.png"));
        Thread.sleep(700);
        thumb.setIcon(new ImageIcon(Arreglo.scaleimage(1000, 400, img)));
        thumb.repaint();
        thumb.revalidate();
        Thread.sleep(300);
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

    @Override
    public void run() {
        while (mover) {
            synchronized (clave) {
                try {
                    if (op == 0) {
                        Node root = null;
                        for (int n : numeros) {
                            desc.setText("Insertando " + n);
                            root = insert(root, root, n);
                            System.out.println(root.getValue());
                            graficar(root,root.getValue());
                            Thread.sleep(200);
                        }
                        //desc.setText("Eliminando 89");
                    } else {
                        Node root = null;
                        for (int n : numeros) {
                            desc.setText("Insertando " + n);
                            //JOptionPane.showMessageDialog(null, "Paso Siguiente?");
                            root = insertG(root, root, n);
                            System.out.println(root.getValue());
                            graficar(root,root.getValue());
                            Thread.sleep(200);
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

//    public void print(Node root) {
//
//        if(root == null) {
//            System.out.println("(XXXXXX)");
//            return;
//        }
//
//        int height = root.getHeight(),
//                width = (int)Math.pow(2, height-1);
//
//        // Preparing variables for loop.
//        List<Node> current = new ArrayList<Node>(1),
//                next = new ArrayList<Node>(2);
//        current.add(root);
//
//        final int maxHalfLength = 4;
//        int elements = 1;
//
//        StringBuilder sb = new StringBuilder(maxHalfLength*width);
//        for(int i = 0; i < maxHalfLength*width; i++)
//            sb.append(' ');
//        String textBuffer;
//
//        // Iterating through height levels.
//        for(int i = 0; i < height; i++) {
//
//            sb.setLength(maxHalfLength * ((int)Math.pow(2, height-1-i) - 1));
//
//            // Creating spacer space indicator.
//            textBuffer = sb.toString();
//
//            // Print tree node elements
//            for(Node n : current) {
//
//                System.out.print(textBuffer);
//
//                if(n == null) {
//
//                    System.out.print("        ");
//                    next.add(null);
//                    next.add(null);
//
//                } else {
//
//                    System.out.printf("(%6d)", n.getValue());
//                    next.add(n.left);
//                    next.add(n.right);
//
//                }
//
//                System.out.print(textBuffer);
//
//            }
//
//            System.out.println();
//            // Print tree node extensions for next level.
//            if(i < height - 1) {
//
//                for(Node n : current) {
//
//                    System.out.print(textBuffer);
//
//                    if(n == null)
//                        System.out.print("        ");
//                    else
//                        System.out.printf("%s      %s",
//                                n.s¿getLeft() == null ? " " : "/", n.right == null ? " " : "\\");
//
//                    System.out.print(textBuffer);
//
//                }
//
//                System.out.println();
//
//            }
//
//            // Renewing indicators for next run.
//            elements *= 2;
//            current = next;
//            next = new ArrayList<Node>(elements);
//
//        }
//
//    }
}