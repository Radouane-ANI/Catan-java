package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import controleur.Controleur;

public class GameMenu extends JPanel{
    
    public GameMenu(){
        setBackground(new Color(0));
        setLayout(new BorderLayout());
        makeButtons();
    }

    private void makeButtons(){
        JPanel buttons = new JPanel();
        //JButton quitter = new JButton("Quitter"); //,Controleur::quitter
        JButton quitter = createImageButton("src/ressources/quitter.png", Controleur::quitter); 
        JButton jouer = createImageButton("src/ressources/jouer.png", Controleur::jouer); 

        quitter.addActionListener(( event ) -> { Controleur.quitter();});
        buttons.add(jouer,BorderLayout.PAGE_END);
        buttons.add(quitter,BorderLayout.PAGE_END);
        buttons.setBackground(new Color(0));
        add(buttons,BorderLayout.PAGE_END);
    }

    private JButton createImageButton(String imagePath, Runnable action) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.addActionListener(e -> action.run());
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

}
