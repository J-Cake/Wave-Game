package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class Window extends Canvas {
    private JFrame frame;
    public Window (int w, int h, String title, Game game) {
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(w, h));
        frame.setMaximumSize(new Dimension(w, h));
        frame.setMinimumSize(new Dimension(w, h));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        game.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Game.mouseClicked(e);
            }
        });
        frame.setVisible(true);
        game.start();


    }

    public void tick () {
        Game.frameCoords = frame.getLocationOnScreen();
    }
}

// Built on Swing
// there is also
// JavaFX