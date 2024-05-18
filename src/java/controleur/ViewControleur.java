package controleur;

import gui.GameOverView;
import gui.GameMenu;
import gui.GameView;
import gui.MainFrame;
import java.util.List;
import gui.Options;
import logic.Bank;
import logic.Player;

public class ViewControleur {
    private static Game game;
    private static MainFrame frame;
    private static CatanBoardControleur catanControleur;
    private static Player player;
    private static Bank bank;
    private static Options gameOption;


    public ViewControleur(MainFrame mainFrame) {
        frame = mainFrame;
        frame.setPanel(new GameMenu());
        gameOption = new Options();
    }

    public static void quitter() {
        System.exit(0);
    }

    public static void jouer() {
        if (gameOption.getPlayers().size() != 4) {
            gameOption.completeJoueur();
        }

        game = new Game(gameOption.getPlayers());

        GameView gameView = new GameView(game);
        frame.setPanel(gameView);

        catanControleur = new controleur.CatanBoardControleur(gameView.getBoardView());

        gameView.revalidate();
        gameView.repaint();

        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public static void showMainMenu() {
        frame.setContentPane(new GameMenu());
        frame.revalidate();
        frame.repaint();
    }

    public static void endGame(List<Player> players) {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getPoints(), p2.getPoints())).orElse(null);
        if (winner != null) {
            frame.setContentPane(new GameOverView(winner, players));
            frame.revalidate();
            frame.repaint();
        }
    }

    public static void option() {
        frame.setPanel(gameOption);
    }

    public static void menu() {
        frame.setPanel(new GameMenu());
    }

    public static CatanBoardControleur getCatanControleur() {
        return catanControleur;
    }

    public static List<Player> getPlayers() {
        return gameOption.getPlayers();
    }

    public static Game getGame() {
        return game;
    }

    public static void NextTurn(boolean bot) {
        game.NextTurn(bot);
    }

    public static Bank getBank() {
        return gameOption.getBank();
    }

}
