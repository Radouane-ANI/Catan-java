package logic;

import map.Tile;

import java.io.Serializable;

public class Thief implements Serializable {
    private Tile position;

    public Thief(Tile position) {
        this.position = position;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile pos) {
        if (this.position != null) {
            this.position.setThief(null);
        }
        this.position = pos;
        this.position.setThief(this);
    }
}
