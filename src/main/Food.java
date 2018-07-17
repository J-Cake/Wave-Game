package main;

import java.awt.*;
import java.util.Random;

public class Food extends GameObject {

    private Random rnd;
    private Handler handler;

    public Food (Handler handler) {
        super (0, 0, ID.Food);
        rnd = new Random();

        this.handler = handler;

        x = Game.constrain(rnd.nextInt(Game.WIDTH), 0, Game.WIDTH - 32);
        y = Game.constrain(rnd.nextInt(Game.HEIGHT), 0, Game.HEIGHT - 56);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }

    public void tick () {
        if (getBounds().intersects(handler.object.get(0).getBounds())) {
            HUD.HEALTH += 5;
            HUD.SCORE++;
            HUD.TOTALSCORE++;
            handler.removeObject(this);
        }
    }

    public void render (Graphics g) {
        g.setColor(Color.pink);
        g.fillRect((int)x, (int)y, 16, 16);
    }

    public void jump() {

    }


}
