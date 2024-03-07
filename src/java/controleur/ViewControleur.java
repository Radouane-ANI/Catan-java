package controleur;

import gui.MainFrame;
import logic.Player;
import gui.GameMenu;
import gui.GameView;
import map.Board;
import gui.CatanBoardView;

import java.awt.Color;


public class ViewControleur {

    private static MainFrame frame;
    private static CatanBoardControleur catanControleur;

    public ViewControleur(MainFrame mainFrame) {
        frame = mainFrame;
        frame.setPanel(new GameMenu());
    }

    public static void quitter() {
        System.exit(0);
    }

    public static void jouer() {
        GameView gameView = new GameView();
        frame.setPanel(gameView);
        Board.createBoard();

        CatanBoardView mapComponent = new CatanBoardView(gameView.getSize());
        catanControleur = new CatanBoardControleur(mapComponent);
        catanControleur.firstBuild(new Player(true, null, null, new Color(35,26,211)));

        gameView.add(mapComponent);

        gameView.revalidate();
        gameView.repaint();
    }

    public static CatanBoardControleur getCatanControleur() {
        return catanControleur;
    }

}
