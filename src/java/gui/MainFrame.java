package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Katan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

        setPanel(new GameMenu());
        setVisible(true);
    }

    public void setPanel(JPanel panel){
        setContentPane(panel);
        revalidate();
        repaint();
    }
}
