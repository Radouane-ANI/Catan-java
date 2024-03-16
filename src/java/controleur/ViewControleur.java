package src.java.controleur;

import java.awt.Color;

import src.java.gui.GameMenu;
import src.java.gui.GameView;
import src.java.gui.MainFrame;
import src.java.logic.Player;
import src.java.map.Board;
import src.java.gui.CatanBoardView;


public class ViewControleur {

    private static MainFrame frame;
    private static CatanBoardControleur catanControleur;
    private static Player player;

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

        player = new Player(false, "Sam", null, Color.BLUE);
        catanControleur.firstBuild(player);;

        gameView.add(mapComponent);

        gameView.revalidate();
        gameView.repaint();
    }

    public static CatanBoardControleur getCatanControleur() {
        return catanControleur;
    }

}
