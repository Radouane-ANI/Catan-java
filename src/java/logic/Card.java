package logic;

public enum Card {
    TREE,
    GRAIN,
    SHEEP,
    BRICK,
    STONE,

    KNIGHT,
    ROAD_BUILD,
    YEAR_PLENTY,
    MONOPOLY,
    ONE_POINT;

    boolean isRessourceCard(){
        return this.ordinal() <= 4;
    }
}