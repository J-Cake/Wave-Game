package main;

import java.awt.*;
import java.util.LinkedList;

public class Menu {

    public Handler h;
    public static LinkedList<Button> buttons = new LinkedList<Button>();
    public Menu (Handler h) {
        this.h = h;
        buttons.add(new Button ("Resume", Game.WIDTH / 4, (Game.HEIGHT / 4) + (Game.HEIGHT / 8), Game.WIDTH / 2, Game.HEIGHT / 8, new Callback() {
            @Override
            public void onSuccess() {
                Game.paused = false;
            }
        }));
        buttons.add(new Button ("Restart", Game.WIDTH / 4, ((Game.HEIGHT / 4) * 2) + (Game.HEIGHT / 8), Game.WIDTH / 2, Game.HEIGHT / 8, new Callback() {
            @Override
            public void onSuccess() {
                Game.newGame(h);
                Game.paused = false;
            }
        }));
        buttons.add(new Button ("Exit", Game.WIDTH / 4, ((Game.HEIGHT / 4) * 3) + (Game.HEIGHT / 8), Game.WIDTH / 2, Game.HEIGHT / 8, new Callback() {
            @Override
            public void onSuccess() {
                System.exit(0);
            }
        }));
//        buttons.add(new Button ("Store", Game.WIDTH / 4, ((Game.HEIGHT / 4) * 2) + (Game.HEIGHT / 8), Game.WIDTH / 2, Game.HEIGHT / 8, new Callback() {
//            @Override
//            public void onSuccess() {
//                System.out.println("Store not supported yet.");
//            }
//        }));
    }

    public void tick() {
        for (Button btn : buttons) {
            btn.tick();
        }
    }

    public void render(Graphics tmp) {
        Graphics2D g = (Graphics2D) tmp;

        for (Button btn : buttons) {
            btn.render(g);
        }

    }

    public static void click() {
        for (Button btn : buttons) {
            btn.click();
        }
    }
}
