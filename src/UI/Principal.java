package UI;

import Clases.Usuario;
import EDD.HashTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal {
    JFrame m;
    public static HashTable users;
    public Principal() {
        users = new HashTable();
        //HashTable users = new HashTable();
        try {
//            users.add(new Usuario("n1", "a", 33, "1234"));
//            users.add(new Usuario("n1", "a", 70, "1234"));
//            users.add(new Usuario("n1", "a", 107, "1234"));
//            users.add(new Usuario("n1", "a", 144, "1234"));
//            users.add(new Usuario("n1", "a", 181, "1234"));
//            users.add(new Usuario("n1", "a", 218, "1234"));
//            users.add(new Usuario("n1", "a", 255, "1234"));
//            users.add(new Usuario("n1", "a", 292, "1234"));
//            users.add(new Usuario("n1", "a", 183, "1234"));
//            users.add(new Usuario("n1", "a", 109, "1234"));
//
//            for (int i = 71; i < 73; i++) {
//                users.add(new Usuario("n1", "a", i, "1234"));
//            }
//            for (int i = 108; i < 110; i++) {
//                users.add(new Usuario("n1", "a", i, "1234"));
//            }
//            System.out.println(users.buscar(33).getCarne());
//            System.out.println(users.buscar(70).getCarne());
//            System.out.println(users.buscar(107).getCarne());
//            System.out.println(users.buscar(144).getCarne());
//            System.out.println(users.buscar(181).getCarne());
//            System.out.println(users.buscar(218).getCarne());
//            System.out.println(users.buscar(255).getCarne());
//            System.out.println(users.buscar(292).getCarne());
//            System.out.println(users.buscar(183).getCarne());
//            System.out.println(users.buscar(109).getCarne());
//            System.out.println(users.buscar(71).getCarne());
//            System.out.println(users.buscar(72).getCarne());
//            System.out.println(users.buscar(108).getCarne());
//            System.out.println(users.buscar(109).getCarne());
        }catch (Exception e){
            e.printStackTrace();
        }


        m = new JFrame("Visualizador de algoritmos");

        JLabel l1;
        l1 = new JLabel("Bienvenido");
        l1.setBounds(65, 40, 180, 20);

        JButton b1 = new JButton("Iniciar sesion");
        b1.setBounds(65,90 ,180, 20);

        m.add(l1);
        m.add(b1);

        b1.addActionListener(
                e -> {
                    LoginDialog loginDlg = new LoginDialog(m);
                    loginDlg.setVisible(true);

                    if (loginDlg.estaAdentro()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                System.out.println("Dentro");
                                m.dispose();
                            }
                        });
                    }
                });


        m.setLayout(null);
        m.setSize(340, 200);
        m.setLocationRelativeTo(null);
        m.setVisible(true);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Principal();
    }
}
