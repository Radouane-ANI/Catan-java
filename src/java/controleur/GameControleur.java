package src.java.controleur;

import java.util.List;

import src.java.logic.Player;
import src.java.logic.TupleDice;
import src.java.map.Board;

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
        //en attente de Gabriel
    }

    private void echange(){}

    private void creationCity(){}

    private void buyCard(){}
  
}
