package controleur;

import java.util.List;

import logic.Player;
import map.Board;

public class Game extends Turn implements Runnable {
    private boolean nextTurn;

    Game(List<Player> playersList) {
        super(playersList);
        Board.createBoard();
    }

    public void startGame() {
        firstTurn();
        recupFirstRessources();
        while (!isOver(playersList)) {
            nextTurn = false;
            updateWeather();
            tour();
            waitNextTurn();
            nextPlayer();
        }update();
    }

    private void waitNextTurn() {
        if (currentPlayer.isBot()) {
            for (int i = 0; i < 4; i++) sleep();
            return;
        }
        while (!nextTurn) {
            sleep();
        }
        nextTurn = false;
    }

    private void firstTurn() {
        for (Player player : playersList) {
            currentPlayer = player;
            update();
            super.firstBuild(player);
            waitNextTurn();
            currentPlayer.setFinishedTurn(false);
        }
        for (int i = playersList.size() - 1; i >= 0; i--) {
            currentPlayerIndex = i;
            currentPlayer = playersList.get(i);
            update();
            super.firstBuild(currentPlayer);
            waitNextTurn();
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
        this.nextTurn = currentPlayer.isBot() == bot;
    }

}