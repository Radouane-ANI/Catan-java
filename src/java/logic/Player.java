package logic;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import static logic.Card.*;

public class Player implements Trade {
    private String name;

    private CardBox myCards;
    private CardBox saleList;
    private CardBox wishList;
    private Bank bank;

    private TradePort tradePorts;

    private boolean bot;
    private List<Road> roads;
    private List<Settlement> settlements;
    private List<City> cities;

    private int points;

    public Player(boolean bot, String nom, Bank bank) {
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

    public boolean isBot() {
        return bot;
    }

    public CardBox getMyCards() {
        return myCards;
    }

    public boolean addInSaleList(Card c) {
        if (myCards.removeCard(c, 1)) {
            saleList.addCard(c, 1);
            return true;
        }
        return false;
    }

    public void addCard(Card c, int number){
        myCards.addCard(c, number);
    }

    public void addInWishList(Card c) {
        wishList.addCard(c, 1);
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
        myCards.removeCard(TREE, 1);
        myCards.removeCard(BRICK, 1);
        roads.add(route);
    }

    public void buildSettlement(Settlement colonie) {
        myCards.removeCard(TREE, 1);
        myCards.removeCard(BRICK, 1);
        myCards.removeCard(GRAIN, 1);
        myCards.removeCard(SHEEP, 1);
        settlements.add(colonie);
    }

    public void buildCity(City ville, Settlement colonie) {
        myCards.removeCard(GRAIN, 2);
        myCards.removeCard(STONE, 3);
        cities.add(ville);
        settlements.remove(colonie);
    }

    private HashMap<Card, Integer> getMissingCardsForBuildings() {
        HashMap<Card, Integer> missingCards = new HashMap<>();
        missingCards.put(TREE, 1 - myCards.getNumber(TREE));
        missingCards.put(BRICK, 1 - myCards.getNumber(BRICK));
        if (settlements.size() <= 3) {
            missingCards.put(GRAIN, 1 - myCards.getNumber(GRAIN));
            missingCards.put(SHEEP, 1 - myCards.getNumber(SHEEP));
            missingCards.put(STONE, 0 - myCards.getNumber(STONE));
        } else {
            missingCards.put(GRAIN, 2 - myCards.getNumber(GRAIN));
            missingCards.put(STONE, 3 - myCards.getNumber(STONE));
            missingCards.put(SHEEP, 0 - myCards.getNumber(SHEEP));

        }
        return missingCards;
    }

    public void updateTradeLists() {
        saleList.clearBox();
        wishList.clearBox();
        HashMap<Card, Integer> missingCards = getMissingCardsForBuildings();

        missingCards.forEach((card, quantity) -> {
            if (quantity > 0) {
                wishList.addCard(card, quantity);
            } else if (quantity < 0) {
                saleList.addCard(card, -quantity);
            }
        });
    }

    public boolean exchangeSuggestion() {
        saleList.clearBox();
        wishList.clearBox();
        HashMap<Card, Integer> etats = getMissingCardsForBuildings();
        List<Card> keys = new ArrayList<>(etats.keySet());

        Card min = keys.get(0);
        Card max = keys.get(0);
        for (Card key : keys) {
            if (etats.get(key) > 0 && etats.get(key) > etats.get(max)) {
                max = key;
            } else if (etats.get(key) < 0 && etats.get(key) < etats.get(min)) {
                min = key;
            }
        }
        if (etats.get(max) > 0 && etats.get(min) < 0) {
            int quantite = etats.get(min) * -1 <= etats.get(max) ? etats.get(min) * -1 : etats.get(max);
            wishList.addCard(max, quantite);
            saleList.addCard(min, quantite);
        }
        return wishList.getNumberOfRes() != 0;
    }

    public boolean isTradeInteresting(Player player) {
        return isTradeInteresting(saleList, wishList, player.wishList, player.saleList);
    }
    
    public void trade(Player player) {
        trade(saleList, player.myCards, wishList, myCards);
    }

    public void tradeWithBank() {
        if (isTradableInBank(saleList, tradePorts)) {
            TradBank(saleList, wishList.getFirst(), myCards, tradePorts, bank);
        }
    }

    public void buyCard(Card c){
        
    }
}