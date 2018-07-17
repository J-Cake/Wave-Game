package main;

import java.awt.*;

public class IntermediateEnemy extends GameObject {

    private double angle;
    private int attackMode;
    private int tickAmnt;
    private Handler handler;

    public IntermediateEnemy (int x, int y, ID id, Handler handler) {
        super (x, y, id);

        velX = 5;
        velY = 5;

        angle = 0;
        attackMode = 1;
        tickAmnt = 0;

        this.handler = handler;
    }

    public void tick () {

        if (tickAmnt == 0) {
            // assume regular behaviour

            x += velX;
            y += velY;

            if (x <= 0 || x >= Game.WIDTH - 48) {
                // striking bounds on x axis
                attackMode++;
                velX *= -1; // reverse direction
            }
            if (y <= 0 || y >= Game.HEIGHT - 64) {
                // striking bounds on y axis
                attackMode++;
                velY *= -1; // reverse direction
            }
        } else {
            x += 15 * Math.cos(angle);
            y += 15 * Math.sin(angle);
            // I know, they're inverted, it should be: x -> sin, y -> cos, but it work like this. stuff it.

            tickAmnt++;

            if (tickAmnt >= 30) {
                tickAmnt = 0;
                attackMode++;
            }
//            attack();

//            System.out.println(angle);
        }

        if (attackMode % 5 == 0 && tickAmnt == 0) {
            attack();
//            System.out.println(angle);
            tickAmnt++;
        }

        x = Game.constrain(x, 0, Game.WIDTH);
        y = Game.constrain(y, 0, Game.HEIGHT);

    }

    public void render (Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect((int)x, (int)y, 32, 32);
    }

    public Rectangle getBounds () {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
    public void jump() {
        y -= 5;
    }

    private void attack() {

        double opp = this.y - handler.object.get(0).y;
        double adj = this.x - handler.object.get(0).x;

        double hyp = Math.sqrt(Math.pow(opp, 2) + Math.pow(adj, 2)); // get square root of sums

        angle = Math.asin(opp / hyp);
        angle *= 180;
        angle /= Math.PI;
        angle = Math.floor(angle);

        double x = handler.object.get(0).x;
        double y = handler.object.get(0).y;

        angle = Math.abs(angle); // -> 0 degrees
        if (x < this.x) {
            if (y < this.y) {
                angle = 90 - angle;
            } else {
                angle = 0 + angle;
            }
        } else {
            if (y < this.y) {
                angle = 0 + angle;
            } else {
                angle = 90 - angle;
            }
        }
        if (x < this.x) {
            if (y < this.y) {
                angle = 270 - angle;
            } else {
                angle = 180 - angle;
            }
        } else {
            if (y < this.y) {
                angle = 360 - angle;
            } else {
                angle = 90 - angle;
            }
        }
        angle = Game.loop(angle, 0, 360);
        angle = Game.map(angle, 0, 360, 0, Math.PI * 2);

//        angle = Game.map((int)angle, 0, 360, 360, 0);
//
//        angle = Math.toRadians(angle);
//
//        angle += Math.PI/4;

    }

//    private void attack() {
//        double x = handler.object.get(0).x;
//        double y = handler.object.get(0).y;
//        angle = Math.toDegrees(Math.atan2(y - this.y, x - this.x));
//    }
}
