package logic;

public class Dice {
    
    private int value;

    int lancer(){
        this.value = (int) (1 + Math.random()*5);
        return value;
    };

    public int getValue() {
        return value;
    }

}
