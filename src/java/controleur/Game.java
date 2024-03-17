package controleur;

import java.util.List;

import logic.Player;
import logic.Settlement;
import map.Board;

public class Game extends Turn implements Runnable {
    private boolean nextTurn;

    Game(List<Player> playersList) {
        super(playersList);
        Board.createBoard();
    }

    public void startGame() {
        firstTurn();
        while (!isOver(playersList)) {
            nextTurn = false;
            tour(playersList, currentPlayerIndex);
            waitNextTurn();
            nextPlayer();
        }
    }

    private void waitNextTurn() {
        while (!nextTurn) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        nextTurn = false;
    }

    private void firstTurn() {
        for (Player player : playersList) {
            super.firstBuild(player);
            waitNextTurn();
        }
        for (int i = playersList.size() - 1; i >= 0; i--) {
            Player currentPlayer = playersList.get(i);
            super.firstBuild(currentPlayer);
            waitNextTurn();
            Settlement s = currentPlayer.getSettlements().get(1);
        }
    }

    private void nextPlayer() {
        if (currentPlayerIndex == (playersList.size() - 1)) {
            currentPlayerIndex = 0;
            return;
        }
        currentPlayerIndex++;

    }

    private boolean isOver(List<Player> players) {
        for (Player p : players)
            if (p.win())
                return true;
        return false;
    }

    @Override
    public void run() {
        startGame();
    }

    public void NextTurn(boolean bot) {
        Player currentPlayer = playersList.get(currentPlayerIndex);
        this.nextTurn = currentPlayer.isBot() == bot;
    }

}