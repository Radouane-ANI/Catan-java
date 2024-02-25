package logic;

public class City extends HumanGroup {
    private Player owner;

    public City(Player owner) {
        this.owner = owner;
        if (owner != null) {
            super.setColor(owner.getColor());
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
