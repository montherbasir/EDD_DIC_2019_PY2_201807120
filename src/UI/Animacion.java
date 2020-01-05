package UI;

import javax.swing.*;
import java.awt.*;

public class Animacion {
    public JPanel root;
    public JLabel title;
    public JLabel desc;
    public JLabel img;
    public Animacion(){
        title.setFont (title.getFont().deriveFont (25.0f));
        desc.setFont (desc.getFont().deriveFont (16.0f));
    }
}
