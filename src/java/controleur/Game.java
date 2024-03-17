package controleur;

import java.util.List;

import logic.Player;
import map.Board;

public class Game extends Turn {
    
    public Game(List<Player> playersList){
        super(playersList);
        Board.createBoard();
    }

    public void startGame(){
        while (!isOver(playersList)){
            tour();
            nextPlayer();
        }
    }

    private void nextPlayer(){
        if (currentPlayerIndex == (playersList.size() -1)){
            currentPlayerIndex = 0;
            return;
        }
        currentPlayerIndex++;
    }

    private boolean isOver(List<Player> players){
        for (Player p : players) if (p.getPoints() == 10) return true;
        return false;
    }
}