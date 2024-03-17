package controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import map.Node;
import logic.Player;
import logic.TupleDice;
import map.Board;
import map.Tile;
import util.TerrainType;
import logic.HumanGroup;
import gui.DiceGUI;



public class Turn {

    protected List<Player> playersList;
    protected int currentPlayerIndex;
    private DiceGUI diceGUI;

    public Turn(List<Player> players) {
        playersList = players;
        currentPlayerIndex = 0; // Commence avec le premier joueur
        this.diceGUI = new DiceGUI(); 
        diceGUI.roll();
    }

    void tour() {
        int sumDices = diceGUI.getResult();
        recupRessources(playersList, sumDices);
        echange();
        creationCity();
    }

    private void recupRessources(List<Player> players, int sumDices){
        System.out.println("result(Turn): " + sumDices);
        ArrayList<Tile> tiles = Board.getTileByDiceNumberArray(sumDices);
        for (Tile t : tiles){
            if (t.getTerrain() == TerrainType.DESERT) continue;
            Node[] nodes = t.getNeighbors();
            for (Node n : nodes){
                HumanGroup hG = n.getHumanGroup();
                if (hG != null){
                    hG.getOwner().addCard(t.getTerrain().toCard(),1);
                }
            }
        }
    }

    private void echange(){
        Player currentPlayer = playersList.get(currentPlayerIndex);
        if (!currentPlayer.exchangeSuggestion()) {
            return;
        }
        List<Player> accepter = new ArrayList<>();
        for (Player p : playersList) {
            if (p != currentPlayer && p.isBot()) {
                p.updateTradeLists();
                if (currentPlayer.isTradeInteresting(p)) {
                    accepter.add(p);
                }
            } else if (!p.isBot()) {
                if (proposeEchange(currentPlayer)) {
                    accepter.add(p);
                }
            }
        }
        if (accepter.size() > 0) {
            Random rd = new Random();
            Player choisi = accepter.get(rd.nextInt(accepter.size()));
            currentPlayer.trade(choisi);
        }else if (currentPlayer.isBot()) {
            currentPlayer.trade(currentPlayer.getSaleList(),currentPlayer.getBank(),currentPlayer.getWishList(),currentPlayer.getMyCards());
        }
    }

    /* 
    private boolean buyRessourceCard(Player p,Card c){
        return p.buyRessourceCard(c);
    }
    */

    private void creationCity(){}

    private boolean proposeEchange(Player p) {
        // affiche a l'ecran un echange que le joueur peut accepeter ou non
        return false;
    }
}
