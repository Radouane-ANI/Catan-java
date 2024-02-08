package logic;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private List<Card> hand;
    private HashMap<String, Integer> ressources;
    private boolean bot;
    private String name;
    private List<Road> roads;
    private List<Settlement> settlements;
    private List<City> cities;
    private int points;
    private static List<Player> pLayers = new ArrayList<>();

    public Player(boolean bot, String nom) {
        this.bot = bot;
        this.name = nom;
        roads = new ArrayList<>();
        settlements = new ArrayList<>();
        cities = new ArrayList<>();
        ressources = new HashMap<>();
        ressources.put("bois", 0);
        ressources.put("brique", 0);
        ressources.put("ble", 0);
        ressources.put("pierre", 0);
        ressources.put("laine", 0);
        pLayers.add(this);
    }

    public static List<Player> getpLayers() {
        return pLayers;
    }

    public static void resetPLayers() {
        pLayers.clear();
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
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

    public int getResourceQuantity(String resourceType) {
        if (ressources.containsKey(resourceType)) {
            return ressources.get(resourceType);
        }
        return -1;
    }

    public void addResource(String resourceType, int quantity) {
        if (ressources.containsKey(resourceType) && quantity > 0) {
            ressources.put(resourceType, ressources.get(resourceType) + quantity);
        }
    }

    public void removeResource(String resourceType, int quantity) {
        if (ressources.containsKey(resourceType) && quantity > 0) {
            int total = (ressources.get(resourceType) >= quantity) ? ressources.get(resourceType) - quantity : 0;
            ressources.put(resourceType, total);
        }
    }

    public boolean canBuildRoad() {
        return ressources.get("bois") >= 1 && ressources.get("brique") >= 1 && roads.size() < 15;
    }

    public boolean canBuildSettlement() {
        return ressources.get("bois") >= 1 && ressources.get("brique") >= 1 && ressources.get("ble") >= 1
                && ressources.get("laine") >= 1 && settlements.size() < 5;
    }

    public boolean canBuildCity() {
        return ressources.get("ble") >= 2 && ressources.get("pierre") >= 3 && cities.size() < 4;
    }

    public void buildRoad(Road route) {
        ressources.put("bois", ressources.get("bois") - 1);
        ressources.put("brique", ressources.get("brique") - 1);
        roads.add(route);
    }

    public void buildSettlement(Settlement colonie) {
        ressources.put("bois", ressources.get("bois") - 1);
        ressources.put("brique", ressources.get("brique") - 1);
        ressources.put("ble", ressources.get("ble") - 1);
        ressources.put("laine", ressources.get("laine") - 1);
        settlements.add(colonie);
    }

    public void buildCity(City ville, Settlement colonie) {
        ressources.put("ble", ressources.get("ble") - 2);
        ressources.put("pierre", ressources.get("pierre") - 3);
        cities.add(ville);
        settlements.remove(colonie);
    }

    public void trade(String offerResourceType, int offerQuantity, String requestResourceType, int requestQuantity) {
        if (offerQuantity < 0 || requestQuantity < 0) {
            return;
        }
        List<Player> accepter = new ArrayList<>();
        for (Player p : pLayers) {
            if (p != this && p.bot) {
                if (accepte(p, offerResourceType, requestQuantity, requestResourceType, offerQuantity)) {
                    accepter.add(p);
                }
            }
        }
        if (accepter.size() > 0) {
            Random rd = new Random();
            Player choisi = accepter.get(rd.nextInt(accepter.size()));
            addResource(requestResourceType, requestQuantity);
            removeResource(offerResourceType, offerQuantity);

            choisi.addResource(offerResourceType, offerQuantity);
            choisi.removeResource(requestResourceType, requestQuantity);
        }
    }

    public boolean accepte(Player p, String offer, int oQuantity, String request, int rQuantity) {
        HashMap<String, Integer> etats = p.etatsRessources();

        if (etats.get(offer) < 0 && etats.get(request) - rQuantity >= 0) {
            return true;
        }
        return false;
    }

    public void demande() {
        HashMap<String, Integer> etats = etatsRessources();
        List<String> keys = new ArrayList<>(etats.keySet());

        String min = keys.get(0);
        String max = keys.get(0);
        for (String key : keys) {
            if (etats.get(key) > 0 && etats.get(key) > etats.get(max)) {
                max = key;
            } else if (etats.get(key) < 0 && etats.get(key) < etats.get(min)) {
                min = key;
            }
        }
        if (etats.get(max) > 0 && etats.get(min) < 0) {
            int quantite = etats.get(min) * -1;
            trade(max, quantite, min, quantite);
        }

    }

    public HashMap<String, Integer> etatsRessources() {
        HashMap<String, Integer> etats = new HashMap<>();
        etats.put("bois", ressources.get("bois") - 1);
        etats.put("brique", ressources.get("brique") - 1);
        etats.put("ble", ressources.get("ble") - 2);
        etats.put("pierre", ressources.get("pierre") - 3);
        etats.put("laine", ressources.get("laine") - 1);
        return etats;
    }
    
}
