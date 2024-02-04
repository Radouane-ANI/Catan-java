package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Player {
    private HashMap<String, Integer> ressources;
    private boolean bot;
    private String nom;
    private List<Road> routes;
    private List<Settlement> colonies;
    private List<City> villes;

    public Player(boolean bot, String nom) {
        this.bot = bot;
        this.nom = nom;
        routes = new ArrayList<>();
        colonies = new ArrayList<>();
        villes = new ArrayList<>();
        ressources = new HashMap<>();
        ressources.put("bois", 0);
        ressources.put("brique", 0);
        ressources.put("ble", 0);
        ressources.put("pierre", 0);
        ressources.put("laine", 0);
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
        return ressources.get("bois") >= 1 && ressources.get("brique") >= 1 && routes.size() < 15;
    }

    public boolean canBuildSettlement() {
        return ressources.get("bois") >= 1 && ressources.get("brique") >= 1 && ressources.get("ble") >= 1
                && ressources.get("laine") >= 1 && colonies.size() < 5;
    }

    public boolean canBuildCity() {
        return ressources.get("ble") >= 2 && ressources.get("pierre") >= 3 && villes.size() < 4;
    }

}
