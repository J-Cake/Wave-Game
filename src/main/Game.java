package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private Spawn spawn;
    private Random rnd;
    private Menu menu;

    private Window window;

    public static final int WIDTH = 720, HEIGHT = WIDTH / 12 * 8;
    public boolean loaded = false;
    public static boolean controllerKeys[] = {false, false, false, false};
    public static MouseListener mouse;
    public static Point frameCoords;

    public static boolean sprint = false;
    public int sprintDuration = 5;
    public int sprintTime = 0;
    public static int sprintCooldown = 0;
    public static boolean paused = false;

    public static MouseEvent lastMouseEvent;

    public Game () {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        this.window = new Window(WIDTH, HEIGHT, "Some Game", this);

        menu = new Menu(handler);
    }
    private static void doSpawn(Handler handler) {

        handler.addObject(new Player((WIDTH / 2) - 32, (HEIGHT / 2) - 32, ID.Player, handler));

    }
    public void finishedLoading() {
        hud = new HUD(handler);
        spawn = new Spawn(handler, hud);

        doSpawn(handler);

//        generateMap(new Random(), handler);

        this.requestFocus();
    }
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (loaded) tick();
                delta--;
            }
            if (running && loaded) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                if (!loaded) {
                    loaded = true;
                    finishedLoading();
                }
                HUD.SECONDS++;
                if (sprint) {
                    sprintTime++;
                    if (sprintTime > sprintDuration) {
                        sprint = false;
                        sprintTime = 0;
                        sprintCooldown = sprintDuration;
                    }
                }
                sprintCooldown--;
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    public static void mouseClicked(MouseEvent e) {
        lastMouseEvent = e;

        Menu.click();
    }

    private void tick() {
        if (!paused) {
            if (!HUD.DEAD) {
                handler.tick();
                hud.tick();

                handler.object.get(0).setVelY((controllerKeys[0] && controllerKeys[1]) ? 0 : (controllerKeys[0] ? -Player.moveSpeed : (controllerKeys[1] ? Player.moveSpeed : 0)));
                handler.object.get(0).setVelX((controllerKeys[2] && controllerKeys[3]) ? 0 : (controllerKeys[2] ? -Player.moveSpeed : (controllerKeys[3] ? Player.moveSpeed : 0)));
            }
        } else {
            menu.tick();
        }

        window.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        if (!paused) {
            if (HUD.DEAD) {
                g.setColor(Color.red);
                g.fillRect(0, 0, WIDTH, HEIGHT);
            } else {
                g.setColor(new Color(30, 30, 30)); // background color
                g.fillRect(0, 0, WIDTH, HEIGHT);

                handler.render(g);
                hud.render(g);
            }
        } else {
            if (!HUD.DEAD) g.setColor(new Color(30, 30, 30));
            else g.setColor(Color.red);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static int constrain(int val, int min, int max) {
        if (val >= max) return val = max;
        else if (val <= min) return val = min;
        else return val;
    }
    public static double constrain(double val, double min, double max) {
        if (val >= max) return val = max;
        else if (val <= min) return val = min;
        else return val;
    }
    public static int loop (int val, int min, int max) {
        if (val > max) {
            return val % max;
        } else if (val < min) {
            return max - Math.abs(val % max);
        } else {
            return val;
        }
    }
    public static double loop (double val, double min, double max) {
        if (val > max) {
            return val % max;
        } else if (val < min) {
            return max - Math.abs(val % max);
        } else {
            return val;
        }
    }

    public static int map (int n, int start1, int stop1, int start2, int stop2) {
        return (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
    }
    public static double map (double n, double start1, double stop1, double start2, double stop2) {
        return (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
    }

    public static void newGame(Handler h) {
        h.object.clear();
        doSpawn(h);

        HUD.DEAD = false;
        HUD.SCORE = 0;
        HUD.ROUND = 1;
        HUD.SECONDS = 0;
        HUD.TOTALSCORE = 0;
        HUD.HEALTH = 100;
        Player.maxHealth = 100;

//        generateMap(new Random(), h);
    }

    public static void generateMap (Random rnd, Handler handler) {
        for (int i = 0; i < WIDTH / 16; i++) {
            for (int j = 0; j < HEIGHT / 16; j++) {
                if (j % 2 == 0) {
                    if (rnd.nextBoolean())
                    handler.addObject(new Block(WIDTH / (WIDTH / 16) * i, HEIGHT / (HEIGHT / 16) * j, ID.Block));
                }
            }
        }
    }

    public static void main(String args[]) {
        new Game();
    }

    public static void print(String str) {
        System.out.println(str);
    }
}
