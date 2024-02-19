package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

    //private static JPanel panel;

    public MainFrame(){
        setTitle("Katan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setResizable(false);
        setVisible(true);
    }

    public void setPanel(JPanel panel){
        setVisible(false);
        setContentPane(panel);
        setVisible(true);
    }
}