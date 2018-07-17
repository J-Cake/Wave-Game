package main;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Button {

    public String text;
    public int x, y, w, h;

    public Point mouseCoords;
    public Color border;

    public boolean hover = false;

    public Callback callback;

    public Button (String text, int x, int y, int w, int h, Callback callback) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.callback = callback;

        this.border = Color.lightGray;

        mouseCoords = MouseInfo.getPointerInfo().getLocation();
    }

    public void render (Graphics g) {
        Graphics2D graphics2 = (Graphics2D) g;
        g.setColor(Color.LIGHT_GRAY);

        RoundRectangle2D rect = new RoundRectangle2D.Float(x, y - h, w, h, 20, 20);

        graphics2.setColor(new Color(30, 30, 30)); // background color
        graphics2.fill(rect);
        if (this.hover) {
            graphics2.setColor(Color.lightGray);
        } else {
            graphics2.setColor(new Color(100, 100, 100));
        }
        graphics2.draw(rect);

        g.setFont(new Font("Bauhaus 93", Font.PLAIN, h / 2));
        FontMetrics fm2 = g.getFontMetrics();
        int w2 = fm2.stringWidth(this.text);
        g.drawString(this.text, x + ((w / 2) - (w2 / 2)), y - (h / 2));
    }

    public void tick() {
        mouseCoords = MouseInfo.getPointerInfo().getLocation();

        if (mouseCoords.x > Game.frameCoords.x + this.x && mouseCoords.x < (Game.frameCoords.x + this.x) + this.w) {
            if (mouseCoords.y > Game.frameCoords.y + this.y - (this.h / 2) && mouseCoords.y < (Game.frameCoords.y + this.y) + (this.h / 2)) {
                this.hover = true;
            } else {
                this.hover = false;
            }
        } else {
            this.hover = false;
        }
    }

    public void click() {
        if (this.hover) {
            this.callback.onSuccess();
        }
    }
}
