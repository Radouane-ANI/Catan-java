package controleur;

import gui.GameMenu;
import gui.GameView;
import gui.MainFrame;
import logic.Player;
import map.Tile;

import java.util.List;

import org.w3c.dom.Node;

public class Controleur {
    
    private static MainFrame frame;
    private static List<Player> players;
    private static int currentPlayerIndex;

    public Controleur(MainFrame mainFrame) {
        frame = mainFrame;
        frame.setPanel(new GameMenu());
    }

    public Controleur(MainFrame mainFrame, List<Player> players) {
        frame = mainFrame;
        this.players = players;
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
            Player currentPlayer = players.get(currentPlayerIndex);
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
        // active le voleur
        if (diceRoll == 7) {
            handleRobberMovement();
        }
    }

    private static int rollDice() {
        return (int) (Math.random() * 6 + 1) + (int) (Math.random() * 6 + 1);
    }

    private static void collectResources(int diceRoll, Player player) {
        //probablment à mettre dans une autre classe (Player)
    }

    private static void handleRobberMovement() {
    }

    private static void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private static boolean isGameEnded() {
        //vérifie si un jouer n'a pas gagné (10PV)
        return false;
    }
}
