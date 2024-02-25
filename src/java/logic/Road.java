package logic;

import java.awt.Color;

public class Road {
    private Player owner;
    private Color color;

    public Road(Player owner) {
        this.owner = owner;
        this.color = owner.getColor();
    }

    public Player getOwner() {
        return owner;
    }

    public Color getColor() {
        return color;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
