package controleur;

import gui.GameMenu;
import gui.GameView;
import gui.MainFrame;
import map.Board;
import gui.CatanBoardView;
import logic.Player;

import java.awt.Color;

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
