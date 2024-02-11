package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Katan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700); 
        setResizable(false);
        
        setPanel(new GameMenu());
        setVisible(true);
    }

    public void setPanel(JPanel panel){
        setContentPane(panel);
        revalidate();
        repaint();
    }
}
