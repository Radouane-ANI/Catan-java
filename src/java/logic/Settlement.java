package logic;

public class Settlement extends HumanGroup {
    private Player owner;

    public Settlement(Player owner) {
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
