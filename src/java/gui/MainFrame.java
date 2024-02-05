package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

    //private static JPanel panel;

    public MainFrame(){
        setTitle("Katan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setResizable(false);
        setVisible(true);
    }

    public void setPanel(JPanel panel){
        setVisible(false);
        setContentPane(panel);
        setVisible(true);
    }
}