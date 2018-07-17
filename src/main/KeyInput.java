package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) Game.controllerKeys[0] = true;
        if (key == KeyEvent.VK_S) Game.controllerKeys[1] = true;
        if (key == KeyEvent.VK_A) Game.controllerKeys[2] = true;
        if (key == KeyEvent.VK_D) Game.controllerKeys[3] = true;

        if (key == KeyEvent.VK_SHIFT) if (Game.sprintCooldown <= 0) Game.sprint = true;

        if (key == KeyEvent.VK_ESCAPE) Game.paused = !Game.paused;

        if (key == KeyEvent.VK_SPACE) {
            if (HUD.DEAD) {
                Game.newGame(handler);
            }
        }
    }
    public void keyReleased (KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) Game.controllerKeys[0] = false;
        if (key == KeyEvent.VK_S) Game.controllerKeys[1] = false;
        if (key == KeyEvent.VK_A) Game.controllerKeys[2] = false;
        if (key == KeyEvent.VK_D) Game.controllerKeys[3] = false;

        if (key == KeyEvent.VK_SHIFT) Game.sprint = false;
    }

}
