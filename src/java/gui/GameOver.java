package gui;
import controleur.Game;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class GameOver extends JPanel {
    private JPanel logoPanel;
    private JPanel scoreDetail;
    private JLabel champion;
    private JLabel settlement;
    private JLabel city;
    private JLabel logo;
    private Game game;
   //List<Player> playerList;

    private int nbPlayer;

    public GameOver(Game game) {
       this.game = game;
       this.nbPlayer = game.getNbOfPlayers();
        //this.playerList = playerList;
        scoreDetail = new JPanel();
        logoPanel = new JPanel();
        scoreDetail. setLayout(new GridLayout(1, 4,0,10));
        loadPics();

        this.setLayout(new BorderLayout());

        makePlayersRanked();
        addClassOfPlayer();

        JPanel namePanel = new JPanel();
        namePanel = addName(namePanel);

        JPanel scorePanel = new JPanel();
        scorePanel = addScore(scorePanel);

        JPanel sttPanel = new JPanel();
        sttPanel = addNbOfSettle(sttPanel);

        JPanel cityPanel = new JPanel();
        cityPanel = addCity(cityPanel);

        scoreDetail.add(namePanel);
        scoreDetail.add(scorePanel);
        scoreDetail.add(sttPanel);
        scoreDetail.add(cityPanel);

        this.add(logoPanel,BorderLayout.NORTH);
        this.add(scoreDetail);

    }

    public void loadPics() {
        ImageIcon scoreP = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/score.png");
        ImageIcon settlementP = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/settlement.png");
        ImageIcon cityP = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/city.png");
        ImageIcon logop = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/gameover.png");


        Image scaledScore = scoreP.getImage().getScaledInstance((int) (scoreP.getIconWidth() * 0.6), (int) (scoreP.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
        Image scaledSettle = settlementP.getImage().getScaledInstance((int) (settlementP.getIconWidth() * 0.6), (int) (settlementP.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
        Image scaledCity = cityP.getImage().getScaledInstance((int) (cityP.getIconWidth() * 0.6), (int) (cityP.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
        Image scaledlogo = logop.getImage().getScaledInstance((int) (logop.getIconWidth() * 0.6), (int) (logop.getIconHeight() * 0.6), Image.SCALE_SMOOTH);


        champion = new JLabel(new ImageIcon(scaledScore));
        settlement = new JLabel(new ImageIcon((scaledSettle)));
        city = new JLabel(new ImageIcon(scaledCity));
        logo = new JLabel(new ImageIcon(scaledlogo));
    }

    private void makePlayersRanked() {
        Collections.sort(game.getPlayersList(), Comparator.comparingInt(Player::getPointsToTal).reversed());
        //Collections.sort(playerList, Comparator.comparingInt(Player::getPointsToTal).reversed());
    }

    private int getClassofPlayer() {
        Player playerCurr = game.getCurrentPlayer();
        //Player playerCurr = playerList.get(0);
        int classofPlayer = 1;
        for (Player player : game.getPlayersList()) {
        //for (Player player : playerList) {
            if (playerCurr.getPointsToTal() == player.getPointsToTal()) {
                return classofPlayer;
            }else{
                classofPlayer++;
            }
        }
        return nbPlayer;
    }

    private void addClassOfPlayer() {
        logoPanel.setLayout(new BorderLayout());
        logoPanel.add(logo,BorderLayout.CENTER);

        int classNb = getClassofPlayer();
        String s;
        switch (classNb) {
            case 1 : s = "ère";break;
            case 2 : s = "nde";break;
            default: s = "eme";break;
        }
        JLabel classLabel = new JLabel("Vous êtes le "+classNb+s);
        classLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        classLabel.setHorizontalAlignment(SwingConstants.CENTER);
        classLabel.setVerticalAlignment(SwingConstants.CENTER);


        logoPanel.add(classLabel,BorderLayout.SOUTH);
    }

    private JPanel addName(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel vide = new JLabel("xxxxxxxx");
        vide.setForeground(new Color(0, 0, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(vide, gbc);

        // Add player names
        //for (Player player : game.getPlayersList()) {
        for (int i = 0; i < game.getPlayersList().size(); i++) {
            Player player = game.getPlayersList().get(i);
            JLabel name = new JLabel(player.getName());
            setFont(new Font("Arial", Font.PLAIN, 20));
            name.setForeground(player.getColor());
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(name, gbc);
        }

        return panel;
    }

    private JPanel addScore(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        panel.add(champion);

        for (int i = 0; i < game.getPlayersList().size(); i++) {
            Player player = game.getPlayersList().get(i);
            JLabel score = new JLabel(player.getPointsToTal() + "");
            setFont(new Font("Arial", Font.PLAIN, 20));
            score.setForeground(player.getColor());

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(score, gbc);
        }

        return panel;
    }

    private JPanel addNbOfSettle(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        panel.add(settlement);

        for (int i = 0; i < game.getPlayersList().size(); i++) {
            Player player = game.getPlayersList().get(i);
            JLabel settle = new JLabel(player.getNbOfSettlemernts() + "");
            setFont(new Font("Arial", Font.PLAIN, 20));
            settle.setForeground(player.getColor());

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(settle, gbc);
        }

        return panel;
    }

    private JPanel addCity(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        panel.add(city);

        for (int i = 0; i < nbPlayer; i++) {
            Player player = game.getPlayersList().get(i);
            JLabel cityLabel = new JLabel(player.getNbOfCity() + "");
            setFont(new Font("Arial", Font.PLAIN, 20));
            cityLabel.setForeground(player.getColor());

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(cityLabel, gbc);
        }

        return panel;
    }

    /**
     * public static void main(String[] args) {
     *         Bank b = new Bank();
     *         Player p1= new Player(false,"1hao",b,Color.red);
     *         Player p2= new Player(false,"2hao",b,Color.blue);
     *         Player p3= new Player(false,"3hao",b,Color.green);
     *         Player p4= new Player(false,"4hao",b,Color.orange);
     *
     *         p1.setPoints(1);
     *         p2.setPoints(2);
     *         p3.setPoints(3);
     *         p4.setPoints(4);
     *
     *
     *         List<Player> lis = new ArrayList<>();
     *         lis.add(p1);
     *         lis.add(p2);
     *         lis.add(p3);
     *         lis.add(p4);
     *
     *         GameOver game = new GameOver(lis);
     *
     *
     *         SwingUtilities.invokeLater(() -> {
     *             JFrame frame = new JFrame("Split Panel Example");
     *             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     *
     *
     *
     *             frame.add(game);
     *             frame.setSize(400, 300);
     *             frame.setVisible(true);
     *         });
     *
     *     }
     */

}