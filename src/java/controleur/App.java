package controleur;

import SaveGame.GameSaveManager;
import gui.MainFrame;

public class App {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Game loadedGame = GameSaveManager.loadGame();

        if (loadedGame != null) {
            System.out.println("Loaded saved game data.");
            ViewControleur viewControleur = new ViewControleur(frame);
            viewControleur.continueGame(loadedGame);
        } else {
            System.out.println("No saved game data found. Starting new game.");
            new ViewControleur(frame);
        }
    }
}


