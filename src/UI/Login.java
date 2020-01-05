package UI;

import Clases.Usuario;
import EDD.HashTable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class Login {
    public static boolean autenticar(String username, String password) {
        if (username.equals("admin")) {
            return password.equals("123456");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            HashTable users = Principal.users;
            Usuario u = users.buscar(Integer.parseInt(username));
            if (u != null) {
                System.out.println(Arrays.toString(u.getPassword()));
                System.out.println(Arrays.toString(hash));
                return Arrays.equals(u.getPassword(), hash);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}