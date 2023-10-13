package pl.chwilaprogramowaniadladebila;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Game extends JFrame {

    public static int width = 1000;
    public static int height = 1000;
    public static Font courier, biggerCourier;

    public static GameField gameField = new GameField();

    public static KeyAdapter keyAdapter = new KeyAdapter();

    public Game() {
        setTitle("2048");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(width, height);
        setVisible(true);
        addKeyListener(keyAdapter);

        try {
            courier = Font.createFont(Font.TRUETYPE_FONT, new File("res\\CourierPrimeSans.ttf")).deriveFont(150 * 0.5f);
            biggerCourier = Font.createFont(Font.TRUETYPE_FONT, new File("res\\CourierPrimeSans.ttf")).deriveFont(150 * 1f);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        add(gameField);

        reload();
    }

    public void reload(){
        repaint();
        revalidate();
    }
}
