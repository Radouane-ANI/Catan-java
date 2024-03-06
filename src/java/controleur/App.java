package src.java.controleur;

import src.java.gui.MainFrame;

public class App {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        new ViewControleur(frame); 
    }
}
