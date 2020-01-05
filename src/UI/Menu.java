package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                    }
                });
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
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                    }
                });
            }
        });

        JButton b5 = new JButton("Insertion sort");

        JButton b6 = new JButton("Grafs (anchura)");

        JButton b7 = new JButton("Cerrar sesion");
        b7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

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
}
