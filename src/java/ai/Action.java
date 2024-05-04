package ai;

import java.util.List;
import java.util.Random;

import controleur.ViewControleur;
import logic.City;
import logic.Player;
import logic.Road;
import logic.Settlement;
import map.Edge;
import map.Node;

/*
 * Trade:
 *  SimpleTrade
 *  BetterTrade
 * Build:
 *  ...
 */

/**
 * Action
 */
public class Action {
    Random rd;

    public Action() {
        rd = new Random();
    }

    public void firstBuild(Player player) {
        List<Node> avaibleSettelement = ViewControleur.getCatanControleur().firstBuild(player);
        Node choiceNode = avaibleSettelement.get(rd.nextInt(avaibleSettelement.size()));
        Settlement s = new Settlement(player);
        player.buildSettlement(s);
        choiceNode.setNode(s);

        List<Edge> avaibleRoad = ViewControleur.getCatanControleur().firstBuildRoad(player, choiceNode);
        Edge choicEdge = avaibleRoad.get(rd.nextInt(avaibleRoad.size()));
        Road r = new Road(player);
        player.buildRoad(r);
        choicEdge.setRoad(r);
    }

    public void randomBuild(Player player) {
        if (rd.nextInt(2) == 0 && player.canBuildSettlement()) {
            randomSettlement(player);
        }
        if (rd.nextInt(2) == 0 && player.canBuildRoad()) {
            randomRoad(player);
        }
        if (rd.nextInt(2) == 0 && player.canBuildCity()) {
            randomCity(player);
        }
    }

    private void randomSettlement(Player p) {
        List<Node> avaibleSettelement = ViewControleur.getCatanControleur().buildSettlement(p);
        if (avaibleSettelement.size() == 0) {
            return;
        }
        Node choiceNode = avaibleSettelement.get(rd.nextInt(avaibleSettelement.size()));
        Settlement s = new Settlement(p);
        p.buildSettlement(s);
        choiceNode.setNode(s);
    }

    private void randomRoad(Player p) {
        List<Edge> avaibleRoad = ViewControleur.getCatanControleur().buildRoad(p);
        if (avaibleRoad.size() == 0) {
            return;
        }
        Edge choicEdge = avaibleRoad.get(rd.nextInt(avaibleRoad.size()));
        Road r = new Road(p);
        p.buildRoad(r);
        choicEdge.setRoad(r);
    }

    private void randomCity(Player p) {
        List<Settlement> avaibleSettelement = ViewControleur.getCatanControleur().buildCity(p);
        if (avaibleSettelement.size() == 0) {
            return;
        }
        Settlement choiceSettlement = avaibleSettelement.get(rd.nextInt(avaibleSettelement.size()));
        City c = new City(p);
        p.buildCity(c, choiceSettlement);
        Node.getNode(choiceSettlement).setNode(c);
        ;
    }

}