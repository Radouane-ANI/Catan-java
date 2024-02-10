package controleur;

import gui.GameMenu;
import gui.GameView;
import gui.MainFrame;
import logic.Player;
import logic.Player.DemandeResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controleur {

    private static MainFrame frame;
    private static List<Player> playersList;
    private static int currentPlayerIndex;

    public Controleur(MainFrame mainFrame) {
        frame = mainFrame;
        frame.setPanel(new GameMenu());
    }

    public Controleur(MainFrame mainFrame, List<Player> players) {
        frame = mainFrame;
        playersList = players;
        frame.setPanel(new GameMenu());
        currentPlayerIndex = 0; // Commence avec le premier joueur
    }

    public static void quitter() {
        System.exit(0);
    }

    public static void jouer() {
        frame.setPanel(new GameView());
        startGame();
    }

    private static void startGame() {
        // boucle de jeu à implémenter

        while (!isGameEnded()) {
            Player currentPlayer = playersList.get(currentPlayerIndex);
            executeTurn(currentPlayer);
            nextPlayer();
        }

        // implémenter logic de fin de jeu
    }

    private static void executeTurn(Player player) {
        // lance les deux dés
        int diceRoll = rollDice();
        // Update visuel du résutat: frame.displayDiceRoll(diceRoll);
        // collecte des resources selon le résultat aux dés
        collectResources(diceRoll, player);
        // code pour permettre au joueur de faire des actions
        if (player.isBot()) {
            DemandeResult result = player.demande();
            trade(result);
        }
        // active le voleur
        if (diceRoll == 7) {
            handleRobberMovement();
        }
    }

    private static int rollDice() {
        return (int) (Math.random() * 6 + 1) + (int) (Math.random() * 6 + 1);
    }

    private static void collectResources(int diceRoll, Player player) {
        // probablment à mettre dans une autre classe (Player)
    }

    private static void handleRobberMovement() {
    }

    private static void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playersList.size();
    }

    private static boolean isGameEnded() {
        // vérifie si un jouer n'a pas gagné (10PV)
        return false;
    }

    private static void trade(DemandeResult result) {
        if (result == null || result.getOfferQuantite() < 0 || result.getRequestQuantity() < 0) {
            return;
        }
        Player currentPlayer = playersList.get(currentPlayerIndex);

        List<Player> accepter = new ArrayList<>();
        for (Player p : playersList) {
            if (p != currentPlayer || p.isBot()) {
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
            currentPlayer.addResource(result.getRequest(), result.getRequestQuantity());
            currentPlayer.removeResource(result.getOffer(), result.getOfferQuantite());

            choisi.addResource(result.getOffer(), result.getOfferQuantite());
            choisi.removeResource(result.getRequest(), result.getRequestQuantity());
        }
    }

    public static boolean proposeEchange(DemandeResult echange) {
        // affiche a l'ecran un echange que le joueur peut accepeter ou non
        return false;
    }

    public static void playerTrade(DemandeResult ressources) {
        Player currentPlayer = playersList.get(currentPlayerIndex);
        if (currentPlayer.canTrade(ressources)) {
            trade(ressources);
        }
    }

}
