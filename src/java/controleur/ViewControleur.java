package src.java.controleur;

import src.java.gui.GameMenu;
import src.java.gui.GameView;
import src.java.gui.MainFrame;

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
