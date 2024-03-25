package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleur.ViewControleur;
import logic.Bank;
import logic.Player;

public class Options extends JPanel {

    private int difficulte;
    private JComboBox<String> difficulteComboBox;
    private List<Player> players;
    private List<Color> couleursDisponibles;
    private JPanel playerPanel;
    private Bank bank;

    public Options() {
        this.difficulte = 1;
        players = new ArrayList<>();
        bank = new Bank();
        couleursDisponibles = new ArrayList<>(
                List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA));

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setOpaque(false);
        JLabel labelDifficulte = new JLabel("Difficulté :");
        setLabelFont(labelDifficulte, 20, null);
        difficulteComboBox = new JComboBox<>(new String[] { "Facile", "Normal", "Difficile" });
        difficulteComboBox.setSelectedIndex(difficulte - 1);
        difficultyPanel.add(labelDifficulte);
        difficultyPanel.add(difficulteComboBox);

        playerPanel = new JPanel();
        buildPlayerPanel();

        JButton valider = new JButton("Valider");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulte = difficulteComboBox.getSelectedIndex() + 1;
                completeJoueur();
                ViewControleur.menu();
                ;
            }
        });

        this.setLayout(new BorderLayout());
        this.add(difficultyPanel, BorderLayout.WEST);
        this.add(playerPanel, BorderLayout.EAST);
        this.add(valider, BorderLayout.SOUTH);
    }

    public Bank getBank() {
        return bank;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/ressources/menu.jpeg").getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    private void buildPlayerPanel() {
        playerPanel.removeAll();
        playerPanel.setOpaque(false);
        playerPanel.setLayout(new GridLayout(5, 0));
        JPanel ajouteJoueur = new JPanel();
        ajouteJoueur.setOpaque(false);
        playerPanel.add(ajouteJoueur);

        JLabel labelPlayer = new JLabel("Joueur :");
        setLabelFont(labelPlayer, 20, null);
        JTextField nomPlayer = new JTextField(10);

        JLabel couleurLabel = new JLabel("Couleur :");
        setLabelFont(couleurLabel, 20, null);
        JComboBox<String> couleurComboBox = new JComboBox<>(
                new String[] { "Rouge", "Bleu", "Vert", "Jaune", "Orange", "Magenta" });

        JButton buttonAddPlayer = new JButton("Ajouter Joueur");
        buttonAddPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomPlayer.getText();
                if (!nom.isEmpty() && players.size() < 4) {
                    int indice = couleurComboBox.getSelectedIndex();
                    Color couleur = couleursDisponibles.get(indice);
                    players.add(new Player(false, nom, bank, couleur));
                    nomPlayer.setText("");
                    JLabel jlb = new JLabel(nom + " : " + couleurComboBox.getSelectedItem());
                    setLabelFont(jlb, 17, couleur);
                    playerPanel.add(jlb);

                    couleurComboBox.removeItemAt(indice);
                    couleursDisponibles.remove(indice);
                }
            }
        });

        ajouteJoueur.add(labelPlayer);
        ajouteJoueur.add(nomPlayer);
        ajouteJoueur.add(couleurLabel);
        ajouteJoueur.add(couleurComboBox);
        ajouteJoueur.add(buttonAddPlayer);
    }

    public int getDifficulte() {
        return difficulte;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void completeJoueur() {
        if (players.size() != 4) {
            int length = 4 - players.size();
            for (int i = 0; i < length; i++) {
                players.add(new Player(false, "bot" + i, bank, couleursDisponibles.get(i)));
            }
        }
        Collections.shuffle(players);
    }

    private void setLabelFont(JLabel label, int size, Color color) {
        Font currentFont = label.getFont();
        Font newFont = new Font(currentFont.getName(), Font.BOLD, size);
        label.setFont(newFont);
        label.setForeground(color);
    }
}
