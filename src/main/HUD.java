package main;

import java.awt.*;
import java.lang.Math;

public class HUD {

    private Handler handler;

    public static int HEALTH = Player.maxHealth;
    public static int SCORE = 0;
    public static int TOTALSCORE = 0;
    public static int ROUND = 1;
    public static int SECONDS = 0;
    public static boolean DEAD = true;
    private boolean enemySpawned = false;
    private int green = 255;

    public HUD(Handler handler) {
        this.handler = handler;
    }

    public void tick() {
        green = Game.constrain(green, 0, 255);
        HEALTH = Game.constrain(HEALTH, 0, Player.maxHealth);

        green = HEALTH * (255 / Player.maxHealth); // p5's map function
//        System.out.println(HEALTH + ":" + Player.maxHealth + ":" + green);

        if (SECONDS % 10 == 0) if (!handler.exists(ID.Food)) handler.addObject(new Food(handler));

        if (SCORE == ROUND) {
            ROUND++;
            enemySpawned = false;
            SCORE = 0;

            handler.addObject(new BasicEnemy(Game.WIDTH - 64, Game.HEIGHT - 64, ID.BasicEnemy));
        }

        if (ROUND % 5 == 0) {
            if (!enemySpawned) handler.addObject(new IntermediateEnemy(Game.WIDTH - 64, 0, ID.IntermediateEnemy, handler));
            enemySpawned = true;
        }

        if (HEALTH == 0) {
            DEAD = true;
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(Game.WIDTH - (32 + Player.maxHealth), 16, Player.maxHealth, 5);
        g.setColor(new Color(255 - green, green, green));
        g.fillRect(Game.WIDTH - (32 + Player.maxHealth), 16, HEALTH, 5);

        g.drawString("$ "+ TOTALSCORE, 16, 24);
        g.drawString("Round " + Integer.toString(ROUND), 16, 36);
        g.drawString(Integer.toString(SECONDS) + "s", 16, 48);

    }

}
