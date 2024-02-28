package src.java.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.java.map.Node;
import src.java.logic.HumanGroup;
import src.java.logic.Player;
import src.java.logic.TupleDice;
import src.java.map.Board;
import src.java.map.Tile;
import src.java.util.TerrainType;


public class GameControleur {

    private List<Player> playersList;
    private int currentPlayerIndex;

    public GameControleur(List<Player> players) {
        Board.createBoard();
        playersList = players;
        currentPlayerIndex = 0; // Commence avec le premier joueur
        startGame(playersList, currentPlayerIndex);
        
    }

    private void startGame(List<Player> players, int currentPlayerIndex){
        while (!isOver(players)){
            tour(playersList,currentPlayerIndex);
            currentPlayerIndex++;
        }
    }

    private boolean isOver(List<Player> players){
        for (Player p : players) if (p.getPoints() == 10) return true;
        return false;
    }

    private void tour(List<Player> players, int currentPlayerIndex){
        TupleDice dices = new TupleDice();
        recupRessources(players,dices.lancer());
        echange();
        creationCity();
        buyCard();
    }

    private void recupRessources(List<Player> players, int sumDices){
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
            currentPlayer.tradeWithBank();
        }
    }

    private void creationCity(){}

    private void buyCard(){}

    private boolean proposeEchange(Player p) {
        // affiche a l'ecran un echange que le joueur peut accepeter ou non
        return false;
    }

    public void playerTrade() {
        echange();
    }
  
}
