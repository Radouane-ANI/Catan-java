package logic;

public class Thief {
    private int position;
    private Player player; 
    // il n'appartient pas vraiment à un joueur, c'est plus qu'un joueur peut le faire bouger  à certains moments

    public Thief(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
