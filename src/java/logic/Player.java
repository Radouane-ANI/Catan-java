package logic;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import map.TerrainType;

public class Player {
    private List<Card> hand;
    private HashMap<TerrainType, Integer> ressources;
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
        ressources.put(TerrainType.FOREST, 0);
        ressources.put(TerrainType.BRICK, 0);
        ressources.put(TerrainType.FIELD, 0);
        ressources.put(TerrainType.MOUNTAIN, 0);
        ressources.put(TerrainType.PASTURE, 0);
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

    public int getResourceQuantity(TerrainType resourceType) {
        if (ressources.containsKey(resourceType)) {
            return ressources.get(resourceType);
        }
        return -1;
    }

    public void addResource(TerrainType resourceType, int quantity) {
        if (ressources.containsKey(resourceType) && quantity > 0) {
            ressources.put(resourceType, ressources.get(resourceType) + quantity);
        }
    }

    public void removeResource(TerrainType resourceType, int quantity) {
        if (ressources.containsKey(resourceType) && quantity > 0) {
            int total = (ressources.get(resourceType) >= quantity) ? ressources.get(resourceType) - quantity : 0;
            ressources.put(resourceType, total);
        }
    }

    public boolean canBuildRoad() {
        return ressources.get(TerrainType.FOREST) >= 1 && ressources.get(TerrainType.BRICK) >= 1 && roads.size() < 15;
    }

    public boolean canBuildSettlement() {
        return ressources.get(TerrainType.FOREST) >= 1 && ressources.get(TerrainType.BRICK) >= 1
                && ressources.get(TerrainType.FIELD) >= 1
                && ressources.get(TerrainType.PASTURE) >= 1 && settlements.size() < 5;
    }

    public boolean canBuildCity() {
        return ressources.get(TerrainType.FIELD) >= 2 && ressources.get(TerrainType.MOUNTAIN) >= 3 && cities.size() < 4;
    }

    public void buildRoad(Road route) {
        ressources.put(TerrainType.FOREST, ressources.get(TerrainType.FOREST) - 1);
        ressources.put(TerrainType.BRICK, ressources.get(TerrainType.BRICK) - 1);
        roads.add(route);
    }

    public void buildSettlement(Settlement colonie) {
        ressources.put(TerrainType.FOREST, ressources.get(TerrainType.FOREST) - 1);
        ressources.put(TerrainType.BRICK, ressources.get(TerrainType.BRICK) - 1);
        ressources.put(TerrainType.FIELD, ressources.get(TerrainType.FIELD) - 1);
        ressources.put(TerrainType.PASTURE, ressources.get(TerrainType.PASTURE) - 1);
        settlements.add(colonie);
    }

    public void buildCity(City ville, Settlement colonie) {
        ressources.put(TerrainType.FIELD, ressources.get(TerrainType.FIELD) - 2);
        ressources.put(TerrainType.MOUNTAIN, ressources.get(TerrainType.MOUNTAIN) - 3);
        cities.add(ville);
        settlements.remove(colonie);
    }

    public void trade(TerrainType offerResourceType, int offerQuantity, TerrainType requestResourceType,
            int requestQuantity) {
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

    private boolean accepte(Player p, TerrainType offer, int oQuantity, TerrainType request, int rQuantity) {
        HashMap<TerrainType, Integer> etats = p.etatsRessources();

        return (etats.get(offer) < 0 && etats.get(request) - rQuantity >= 0);
    }

    public void demande() {
        HashMap<TerrainType, Integer> etats = etatsRessources();
        List<TerrainType> keys = new ArrayList<>(etats.keySet());

        TerrainType min = keys.get(0);
        TerrainType max = keys.get(0);
        for (TerrainType key : keys) {
            if (etats.get(key) > 0 && etats.get(key) > etats.get(max)) {
                max = key;
            } else if (etats.get(key) < 0 && etats.get(key) < etats.get(min)) {
                min = key;
            }
        }
        if (etats.get(max) > 0 && etats.get(min) < 0) {
            int quantite = etats.get(min) * -1 <= etats.get(max) ? etats.get(min) * -1 : etats.get(max);
            trade(max, quantite, min, quantite);
        }

    }

    private HashMap<TerrainType, Integer> etatsRessources() {
        HashMap<TerrainType, Integer> etats = new HashMap<>();
        etats.put(TerrainType.FOREST, ressources.get(TerrainType.FOREST) - 1);
        etats.put(TerrainType.BRICK, ressources.get(TerrainType.BRICK) - 1);
        etats.put(TerrainType.FIELD, ressources.get(TerrainType.FIELD) - 2);
        etats.put(TerrainType.MOUNTAIN, ressources.get(TerrainType.MOUNTAIN) - 3);
        etats.put(TerrainType.PASTURE, ressources.get(TerrainType.PASTURE) - 1);
        return etats;
    }

}
