package controleur;

import gui.MainFrame;
import gui.GameMenu;
import gui.GameView;
import map.Board;
import gui.CatanBoardView;

public class Controleur {
    
    private static MainFrame frame;

    public Controleur(MainFrame mainFrame){
        frame = mainFrame;
        frame.setPanel(new GameMenu());
    }

    public static void quitter(){
        System.exit(0);
    }

    public static void jouer(){
        GameView gameView = new GameView();
        frame.setPanel(gameView);
        Board.createBoard();
        CatanBoardView mapComponent = new CatanBoardView(frame.getSize());
        gameView.add(mapComponent);
        gameView.revalidate();
        gameView.repaint();
    }
}
