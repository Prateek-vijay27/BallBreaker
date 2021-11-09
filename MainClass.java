package game;
import java.awt.*;
import javax.swing.*;

public class MainClass extends JFrame {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setTitle("BRICK BREAKER");
        f.setBounds(10,10,708,610);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);

        Game game=new Game();
        f.add(game);

    }

}
