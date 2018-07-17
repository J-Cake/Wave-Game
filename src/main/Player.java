package main;

import java.awt.*;

public class Player extends GameObject{
    Handler handler;

    public static int moveSpeed = 5;
    public static int maxHealth = 100;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (Game.sprint) {
            moveSpeed = 10;
        } else {
            moveSpeed = 5;
        }

        x = Game.constrain((int)x, 0, Game.WIDTH - 32);
        y = Game.constrain((int)y, 0, Game.HEIGHT - 56);

        collision();
    }

    public void collision() {
        for (GameObject obj : handler.object) {
            if (obj.getId() == ID.BasicEnemy) {
                if (getBounds().intersects(obj.getBounds())) HUD.HEALTH -= 5;
            } else if (obj.getId() == ID.IntermediateEnemy) {
                if (getBounds().intersects(obj.getBounds())) HUD.HEALTH -= 5;
            } else if (obj.getId() == ID.Block) {
                if (getBounds().intersects(obj.getBounds())) y = obj.getY() - 16;
                else setVelY(getVelY() + 0.1);
            }
        }
    }
    public void render(Graphics g) {
        if (Game.sprint)
            g.setColor(new Color(100, 100, 255));
        else {
            if (Game.sprintCooldown <= 0)
                g.setColor(Color.blue);
            else
                g.setColor(new Color(255, 115, 6));
        }
        g.fillRect((int)x, (int)y, 16, 16);
    }

    public void jump() {

    }

}
