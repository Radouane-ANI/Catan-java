package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controleur.ViewControleur;
import logic.Card;
import logic.City;
import logic.HumanGroup;
import logic.Player;
import logic.Road;
import logic.Settlement;
import logic.Thief;
import map.Edge;
import map.Node;
import map.Tile;

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
    private Random rd;
    private int difficulte;

    public Action() {
        this.rd = new Random();
        this.difficulte = ViewControleur.getDifficulte();
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

    public void randomBuild(Player player, boolean neige) {
        if (rd.nextInt(3 - difficulte) == 0 && player.canBuildSettlement()) {
            randomSettlement(player);
        }
        if (rd.nextInt(3 - difficulte) == 0 && player.canBuildRoad() && !neige) {
            randomRoad(player);
        }
        if (rd.nextInt(3 - difficulte) == 0 && player.canBuildCity()) {
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
    }

    public void stolenRandom(Player player) {
        player.getSaleList().clearBox();
        player.getWishList().clearBox();
        int total = player.getMyCards().getNumberOfRes();
        if (total <= 7) {
            return;
        }
        int defausse = total / 2;
        for (int i = 0; i < defausse; i++) {
            Card randomCard = player.getMyCards().getRandomCard();
            player.getMyCards().removeCard(randomCard, 1);
            player.getBank().addCard(randomCard, 1);
        }
    }

    public void moveThief(Player currentPlayer, Thief thief) {
        int pos = rd.nextInt(Tile.getTilesIntern().length);
        while (Tile.getTilesIntern()[pos].getThief() != null) {
            pos = rd.nextInt(Tile.getTilesIntern().length);
        }
        thief.setPosition(Tile.getTilesIntern()[pos]);

        Node[] adjacentNodes = thief.getPosition().getNeighbors();
        List<HumanGroup> victimsGroups = new ArrayList<>();
        for (Node node : adjacentNodes) {
            HumanGroup group = node.getGroup();
            if (group != null && group.getOwner() != currentPlayer) {
                victimsGroups.add(group);
            }
        }

        if (victimsGroups.size() > 0) {
            HumanGroup victime = victimsGroups.get(rd.nextInt(victimsGroups.size()));
            currentPlayer.stealCard(victime.getOwner());

        }
    }

}