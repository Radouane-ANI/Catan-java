package src.java.controleur;

import gui.GameMenu;
import gui.GameView;
import gui.MainFrame;

public class ViewControleur {
    
    private static MainFrame frame;

    public ViewControleur(MainFrame mainFrame) {
        frame = mainFrame;
        frame.setPanel(new GameMenu());
    }

    public static void quitter() {
        System.exit(0);
    }

    public static void jouer() {
        frame.setPanel(new GameView());
    }
}
