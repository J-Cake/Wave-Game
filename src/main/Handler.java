package main;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    public LinkedList<GameObject> object = new LinkedList<GameObject>();

    public void tick () {
        for (int i = 0; i < object.size(); i++) {
            GameObject tmp =  object.get(i);
            tmp.tick();
        }
    }

    public void render (Graphics g) {
        for (int i = 0; i < object.size(); i++ ) {
            GameObject tmp = object.get(i);

            tmp.render(g);
        }
    }

    public boolean exists(ID id) {
        for (GameObject obj : object) {
            if (obj.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
}
