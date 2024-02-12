package controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.Player;
import logic.Player.DemandeResult;
import logic.TupleDice;
import map.Board;

public class GameControleur {

    private List<Player> playersList;
    private int currentPlayerIndex;

    private Board board;

    public GameControleur(List<Player> players) {
        Board.createBoard();
        playersList = players;
        currentPlayerIndex = 0; // Commence avec le premier joueur
        startGame(playersList, currentPlayerIndex);

    }

    private void startGame(List<Player> players, int currentPlayerIndex) {
        while (!isOver(players)) {
            tour(playersList, currentPlayerIndex);
            currentPlayerIndex++;
        }
    }

    private boolean isOver(List<Player> players) {
        for (Player p : players)
            if (p.getPoints() == 10)
                return true;
        return false;
    }

    private void tour(List<Player> players, int currentPlayerIndex) {
        Player player = playersList.get(currentPlayerIndex);

        TupleDice dices = new TupleDice();
        recupRessources(players, dices.lancer());
        if (player.isBot()) {
            DemandeResult result = player.demande();
            trade(result, player);
        }
        creationCity();
        buyCard();
    }

    private void recupRessources(List<Player> players, int sumDices) {
        // récuperer les ressources
    }

    private void trade(DemandeResult result, Player currentPlayer) {
        if (result == null || result.getOfferQuantite() < 0 || result.getRequestQuantity() < 0) {
            return;
        }

        List<Player> accepter = new ArrayList<>();
        for (Player p : playersList) {
            if (p != currentPlayer && p.isBot()) {
                if (p.accepte(result)) {
                    accepter.add(p);
                }
            } else if (!p.isBot()) {
                if (proposeEchange(result)) {
                    accepter.add(p);
                }
            }
        }
        if (accepter.size() > 0) {
            Random rd = new Random();
            Player choisi = accepter.get(rd.nextInt(accepter.size()));
            currentPlayer.echange(choisi, result);
        }
    }

    public void playerTrade(DemandeResult ressources) {
        Player currentPlayer = playersList.get(currentPlayerIndex);
        if (currentPlayer.canTrade(ressources)) {
            trade(ressources, currentPlayer);
        }
    }

    private void creationCity() {
    }

    private void buyCard() {
    }

    public boolean proposeEchange(DemandeResult echange) {
        // affiche a l'ecran un echange que le joueur peut accepeter ou non
        return false;
    }

}
