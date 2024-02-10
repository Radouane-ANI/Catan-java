package controleur;

import gui.MainFrame;
import gui.GameMenu;
import gui.GameView;
import map.Board;
import map.MapComponent;

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
        MapComponent mapComponent = new MapComponent(Board.getInstance());
        gameView.add(mapComponent);
        gameView.revalidate();
        gameView.repaint();
    }
}
