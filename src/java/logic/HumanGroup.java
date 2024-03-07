package logic;

import java.awt.Color;

public class HumanGroup {
    // in purpose to put City or Settlement in a Node of the Board
    private Color color;
    private Player owner;

    public HumanGroup(Player owner) {
        this.owner = owner;
        if (owner != null) {
            color = owner.getColor();
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
