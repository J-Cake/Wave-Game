package main;

import java.awt.*;

public class Block extends GameObject {

    public Block(int x, int y, ID id) {
        super(x, y, id);
    }

    public void tick () {

    }

    public void render (Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, 16, 16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }

    public void jump() {

    }
}
