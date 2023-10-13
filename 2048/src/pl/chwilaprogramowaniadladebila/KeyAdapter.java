package pl.chwilaprogramowaniadladebila;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAdapter implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code){
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                Game.gameField.top();
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                Game.gameField.right();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                Game.gameField.left();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                Game.gameField.bottom();
                break;
            case KeyEvent.VK_R:
                Game.gameField.rotate();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
