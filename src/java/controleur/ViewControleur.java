package controleur;

import java.util.List;

import gui.MainFrame;
import gui.Options;
import gui.GameMenu;
import gui.GameView;
import logic.Player;
import gui.CatanBoardView;

public class ViewControleur {
    private static Game game;
    private static MainFrame frame;
    private static controleur.CatanBoardControleur catanControleur;
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
        GameView gameView = new GameView();
        frame.setPanel(gameView);
        if (gameOption.getPlayers().size() != 4) {
            gameOption.completeJoueur();
        }
        game = new Game(gameOption.getPlayers());

        CatanBoardView mapComponent = new CatanBoardView(gameView.getSize());
        mapComponent.setOpaque(false);
        catanControleur = new controleur.CatanBoardControleur(mapComponent);

        gameView.add(mapComponent);

        gameView.revalidate();
        gameView.repaint();

        Thread gameThread = new Thread(game);
        gameThread.start();
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

    public static void setFinishedTurn(boolean f) {
        game.setFinishedTurn(f);
    }

}
