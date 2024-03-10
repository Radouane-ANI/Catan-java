package controleur;

import gui.MainFrame;
import gui.Options;
import gui.GameMenu;
import gui.GameView;

import gui.CatanBoardView;

public class ViewControleur {

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
        Game game = new Game(gameOption.getPlayers());

        CatanBoardView mapComponent = new CatanBoardView(gameView.getSize());
        catanControleur = new controleur.CatanBoardControleur(mapComponent);

        gameView.add(mapComponent);

        gameView.revalidate();
        gameView.repaint();
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

}
