package logic;

public class Player implements Trade{
    private String name;
    private CardBox myCards;
    private CardBox saleList;
    private CardBox wishList;
    private Bank bank;
    private TradePort tradePorts;


    //private List<Settlement> settlements;
    //private List<City> cities;
    //private List<Road> roads;


    public Player(String name,Bank bank) {
        this.name = name;
        this.myCards = new CardBox();
        this.saleList = new CardBox();
        this.wishList = new CardBox();
        this.bank = bank;
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



    /*public List<Settlement> getSettlements() {
        return settlements;
    }*/

    /*public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }*/

    /*public List<City> getCities() {
        return cities;
    }*/

    /*public void setCities(List<City> cities) {
        this.cities = cities;
    }*/

    /*public List<Road> getRoads() {
        return roads;
    }*/

    /*public void setRoads(List<Road> roads) {
        this.roads = roads;
    }*/
}


