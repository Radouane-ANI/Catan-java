package src.java.logic;

public class City implements HumanGroup {
    private Player owner;

    public City(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
