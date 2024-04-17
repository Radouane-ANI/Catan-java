package gui;

import logic.Player;

import javax.swing.JPanel;
import javax.swing.JLabel;

import controleur.Game;

import java.awt.Container;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ProposeEchange extends JPanel {
    private CardSuit saleList;
    private CardSuit wishList;
    private Game game;
    private List<Player> accepter;
    private Player currentPlayer;

    public ProposeEchange(Player currentPlayer, List<Player> accepter, Player p, Game game) {
        this.game = game;

        this.currentPlayer = currentPlayer;
        this.accepter = accepter;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.saleList = new CardSuit(currentPlayer, 2);
        this.wishList = new CardSuit(currentPlayer, 3);
        this.wishList.setImageButton("yes.png");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel a = new JLabel(currentPlayer.getName() + " demande a " + p.getName());
        add(a);
        this.add(wishList);
        this.add(saleList);

        JButton refuseButton = saleList.getButton();
        refuseButton.setEnabled(true);
        refuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accepter.add(currentPlayer);
                echange();
                supprimer();
            }
        });
        JButton accepteButton = wishList.getButton();
        accepteButton.setEnabled(true);
        accepteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accepter.add(p);
                echange();
                supprimer();
            }
        });
    }

    private void supprimer() {
        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        game.update();
    }

    private void echange() {
        if (accepter.size() >= 3) {
            game.echange(accepter);
        }
    }

}
