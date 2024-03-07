package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Options extends JPanel {

    private int difficulte;
    private JComboBox<String> difficulteComboBox;

    public Options() {
        this.difficulte = 1;

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Difficulté :");
        difficulteComboBox = new JComboBox<>(new String[] { "Facile", "Normal", "Difficile" });
        difficulteComboBox.setSelectedIndex(difficulte - 1);
        JButton button = new JButton("Valider");

        panel.add(label);
        panel.add(difficulteComboBox);
        panel.add(button);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulte = difficulteComboBox.getSelectedIndex() + 1;
            }
        });
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        if (difficulte < 1) {
            difficulte = 1;
        } else if (difficulte > 3) {
            difficulte = 3;
        }
        this.difficulte = difficulte;
        difficulteComboBox.setSelectedIndex(difficulte - 1);
    }

}
