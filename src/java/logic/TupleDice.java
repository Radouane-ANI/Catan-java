package logic;

import util.Tuple;

public class TupleDice extends Tuple<Dice>{
    TupleDice(){
        super(new Dice(),new Dice());
    }

    public int lancer(){
        return getX().lancer() + getY().lancer();
    }
}