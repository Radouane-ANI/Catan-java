package controleur;

import java.util.List;

import logic.Player;
import map.Board;

public class Game extends Turn implements Runnable{
    private boolean nextTurn;
    
    Game(List<Player> playersList){
        super(playersList);
        Board.createBoard();
    }

    public void startGame() {
        while (!isOver(playersList)) {
            nextTurn =false;
            tour(playersList, currentPlayerIndex);
            while (!nextTurn) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
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
        for (Player p : players) if (p.win()) return true;
        return false;
    }

    @Override
    public void run() {
        startGame();
    }

    public void setNextTurn(boolean nextTurn) {
        this.nextTurn = nextTurn;
    }

}