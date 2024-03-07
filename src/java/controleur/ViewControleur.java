package controleur;

import gui.MainFrame;
import gui.Options;
import gui.GameMenu;
import gui.GameView;
import map.Board;

import javax.swing.text.html.Option;

import gui.CatanBoardView;

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

        gameView.add(mapComponent);

        gameView.revalidate();
        gameView.repaint();
    }

    public static void option() {
        Options opt = new Options();
        frame.setPanel(opt);
    }

    public static CatanBoardControleur getCatanControleur() {
        return catanControleur;
    }

}
