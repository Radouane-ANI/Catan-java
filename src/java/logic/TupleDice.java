package src.java.logic;

import src.java.util.Tuple;

public class TupleDice extends Tuple<Dice>{
    
    public TupleDice(){
        super(new Dice(),new Dice());
    }

    public int lancer(){
        return getX().lancer() + getY().lancer();
    }
}