package util;

import static logic.Card.BRICK;

import logic.Card;

public enum TerrainType {
    FOREST,
    FIELD,
    PASTURE,
    BRICK,
    MOUNTAIN,
    DESERT;





    public Card toCard(){
        switch (this){
            case FOREST : return Card.TREE; 
            case FIELD : return Card.GRAIN;
            case PASTURE : return Card.SHEEP;
            case MOUNTAIN : return Card.STONE;
            case BRICK : return Card.BRICK;
            default : return null;
        }
    }
}