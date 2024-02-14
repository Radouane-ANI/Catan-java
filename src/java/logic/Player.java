package src.java.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import static src.java.logic.Card.*;

public class Player implements Trade{
    private String name;

    private CardBox myCards;
    private CardBox saleList;
    private CardBox wishList;
    private Bank bank;

    private TradePort tradePorts;

    private HashMap<String, Integer> ressources;
    private boolean bot;
    private List<Road> roads;
    private List<Settlement> settlements;
    private List<City> cities;

    private int points;

    public Player(boolean bot, String nom,Bank bank) {
        this.name = nom;
        this.bot = bot;

        roads = new ArrayList<>();
        settlements = new ArrayList<>();
        cities = new ArrayList<>();

        this.myCards = new CardBox();
        this.saleList = new CardBox();
        this.wishList = new CardBox();
        this.bank = bank;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public String getName() {
        return name;
    }

    public CardBox getMyCards() {
        return myCards;
    }

    public boolean addInSaleList(Card c) {
        if(myCards.removeCard(c,1)) {
            saleList.addCard(c,1);
            return true;
        }
        return false;
    }

    public void addInWishList(Card c) {
        wishList.addCard(c,1);
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean win() {
        return points >= 10;
    }

    public boolean canBuildRoad() {
        return myCards.getNumber(TREE) >= 1 && myCards.getNumber(BRICK) >= 1 && roads.size() < 15;
    }

    public boolean canBuildSettlement() {
        return myCards.getNumber(TREE) >= 1 && myCards.getNumber(BRICK) >= 1 && myCards.getNumber(GRAIN) >= 1
                && myCards.getNumber(SHEEP) >= 1 && settlements.size() < 5;
    }

    public boolean canBuildCity() {
        return myCards.getNumber(GRAIN) >= 2 && myCards.getNumber(STONE) >= 3 && cities.size() < 4;
    }

    public void buildRoad(Road route) {
        myCards.removeCard(TREE,1);
        myCards.removeCard(BRICK,1);
        roads.add(route);
    }

    public void buildSettlement(Settlement colonie) {
        myCards.removeCard(TREE,1);
        myCards.removeCard(BRICK,1);
        myCards.removeCard(GRAIN,1);
        myCards.removeCard(SHEEP,1);
        settlements.add(colonie);
    }

    public void buildCity(City ville, Settlement colonie) {
        myCards.removeCard(GRAIN,2);
        myCards.removeCard(STONE,3);
        cities.add(ville);
        settlements.remove(colonie);
    }
}