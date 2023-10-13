package pl.chwilaprogramowaniadladebila;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import static pl.chwilaprogramowaniadladebila.Game.keyAdapter;

public class GameField extends JPanel {

    private final int numberOfFields = 4;
    private final int gap = 200;
    private final int fieldSize = (Game.width - gap*2) / numberOfFields;
    private final HashMap<Integer, Color> colors = colors();

    private boolean win = false;
    private boolean loose = false;
    private int score = 0;

    private int[][] fields = {
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}
    };

    public GameField() {
        setOpaque(false);
        setVisible(true);
        setBounds(0,0, Game.width, Game.height);
        setLayout(null);

        lauchGame();
    }

    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(Game.courier);

        //draw score
        g.setColor(Color.black);
        g.drawString("Score: " + score, gap, gap/2);

        for (int i = 0 ; i < fields.length; i++){
            for (int j = 0; j < fields[0].length; j++){
                g.setColor(colors.get(fields[i][j]));
                g.fillRoundRect(gap + (j * fieldSize), gap + (i * fieldSize), fieldSize, fieldSize, 30, 30);
                g.setColor(Color.black);
                g.drawRoundRect(gap + (j * fieldSize), gap + (i * fieldSize), fieldSize, fieldSize, 30, 30);
                if (fields[i][j] != 0){
                    g.setColor(Color.black);
                    g.drawString(String.valueOf(fields[i][j]), gap + (j * fieldSize) + fieldSize/2 - g.getFontMetrics().stringWidth(String.valueOf(fields[i][j]))/2, (int)(gap * 1.1) + (i * fieldSize) + fieldSize/2);
                }
            }
        }
        //drawWinOrLoose
        if (win || loose){
            g.setFont(Game.biggerCourier);
            g.setColor(new Color(200,200,200, 200));
            g.fillRect(Game.width/6, Game.height/3, Game.width/3*2, Game.height/3);
            g.setColor(Color.red);
            if (win)
                g.drawString("Victory",Game.width/2 - g.getFontMetrics().stringWidth("Victory")/2, Game.height/2);
            else if (loose)
                g.drawString("Loose",Game.width/2 - g.getFontMetrics().stringWidth("Loose")/2, Game.height/2);
            Main.game.removeKeyListener(keyAdapter);
        }
    }

    public void setFields(int[][] fields) {
        this.fields = fields;
    }

    public HashMap<Integer, Color> colors(){
        HashMap<Integer, Color> colors = new HashMap();

        colors.put(0, new Color(255,255,255));
        colors.put(2, new Color(238,228,218));
        colors.put(4, new Color(237,224,200));
        colors.put(8, new Color(242,177,121));
        colors.put(16, new Color(245, 149, 99));
        colors.put(32, new Color(204,82,50));
        colors.put(64, new Color(246,94,59));
        colors.put(128, new Color(255,255,210));
        colors.put(256, new Color(255,255,200));
        colors.put(512, new Color(255,255,150));
        colors.put(1024, new Color(255,255,100));
        colors.put(2048, new Color(255,255,0));

        return colors;
    }

    public void lauchGame(){
        spawnRandomField();
    }

    public void spawnRandomField(){
        Random random = new Random();
        int x = random.nextInt(4);
        int y = random.nextInt(4);
        if (fields[x][y] == 0){
            fields[x][y] = 2;
        }
        else{
            try {
                spawnRandomField();
            }
            catch (StackOverflowError error){
                loose = true;
            }
        }


    }

    public void left(){
        int[][] fields = Game.gameField.fields;
        boolean change = false;
        for (int i = 0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                for (int k = 0; k <= j; k++){
                    if (fields[i][j] != 0) {
                        //moveFields
                        if (fields[i][k] == 0) {
                            fields[i][k] = fields[i][j];
                            fields[i][j] = 0;
                            change = true;
                        }
                    }

                }
                for (int k = 0; k <= j; k++) {
                    //connectFields
                    if (k == j)
                        break;
                    if (fields[i][k] == fields[i][k+1]){
                        fields[i][k] = fields[i][k]*2;
                        fields[i][k+1] = 0;
                        //countScore
                        if (fields[i][k] == 2048){
                            checkIfYouWin();
                        }
                        score += fields[i][k];
                        change = true;
                    }

                }
            }
        }
        if (change)
        spawnRandomField();

        Game.gameField.setFields(fields);
        Main.game.reload();
    }

    public void right(){
        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }

    public void top(){
        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }

    public void bottom(){
        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }

    public void rotate(){
        int[][] localFields = new int[Game.gameField.fields.length][Game.gameField.fields[0].length];
        for (int i = 0; i < Game.gameField.fields.length; i++){
            for (int j = 0; j < Game.gameField.fields[i].length; j++){
                localFields[j][localFields.length-1-i] = Game.gameField.fields[i][j];
            }
        }

        Main.game.reload();

        Game.gameField.setFields(localFields);
    }

    public void checkIfYouWin(){
        win = true;
    }
}
