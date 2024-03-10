package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Player;

public class Options extends JPanel {

    private int difficulte;
    private JComboBox<String> difficulteComboBox;
    private final List<Player> players;
    private final List<Color> couleursDisponibles;
    private final JComboBox<Color> couleurComboBox;

    public Options() {
        JPanel panel = new JPanel();

        this.difficulte = 1;
        players = new ArrayList<>();

        JLabel labelDifficulte = new JLabel("Difficulté :");
        difficulteComboBox = new JComboBox<>(new String[] { "Facile", "Normal", "Difficile" });
        difficulteComboBox.setSelectedIndex(difficulte - 1);
        JButton buttonDifficulte = new JButton("Valider difficulté");
        panel.add(labelDifficulte);
        panel.add(difficulteComboBox);
        panel.add(buttonDifficulte);

        JLabel labelPlayer = new JLabel("Joueur :");
        JTextField nomPlayer = new JTextField(10);
        
        JLabel couleurLabel = new JLabel("Couleur :");
        couleursDisponibles = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA);
        couleurComboBox = new JComboBox<>((Color[]) couleursDisponibles.toArray(new Color[0]));
        JButton buttonAddPlayer = new JButton("Ajouter Joueur");
        panel.add(labelPlayer);
        panel.add(nomPlayer);
        panel.add(couleurLabel);
        panel.add(couleurComboBox);
        panel.add(buttonAddPlayer);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        buttonDifficulte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulte = difficulteComboBox.getSelectedIndex() + 1;
            }
        });

        buttonAddPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomPlayer.getText().trim();
                if (!nom.isEmpty()) {
                    Color couleur = couleursDisponibles.get(couleurComboBox.getSelectedIndex());
                    players.add(new Player(false, nom, null, couleur));
                    nomPlayer.setText("");
                    couleurComboBox.removeItem(couleur);
                }
            }
        });
    }

    public int getDifficulte() {
        return difficulte;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
