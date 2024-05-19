package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class DiceGUI extends JPanel implements Serializable {

    private TupleDice dicePanel;
    private JButton rollButton;
    private boolean rollDice;

    public DiceGUI() {
        setLayout(new FlowLayout());
        setOpaque(false);
        dicePanel = new TupleDice();

        rollButton = new JButton("Roll");
        rollButton.setEnabled(false);
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roll();
                rollDice = true;
                rollButton.setEnabled(false);
            }
        });

        add(dicePanel);
        add(rollButton);
    }

    public void roll() {
        dicePanel.roll();
    }

    public int getResult() {
        return dicePanel.getResult();
    }

    public boolean isRollDice() {
        return rollDice;
    }

    public void setRollDice(boolean rollDice) {
        this.rollDice = rollDice;
        rollButton.setEnabled(!rollDice);
    }

}

class TupleDice extends JPanel {

    private Dice dice1;
    private Dice dice2;

    public TupleDice() {
        setLayout(new FlowLayout());
        dice1 = new Dice();
        dice2 = new Dice();
        add(dice1);
        add(dice2);
    }

    public void roll() {
        dice1.roll();
        dice2.roll();
        int result = dice1.getValue() + dice2.getValue();
    }

    public int getResult() {
        return dice1.getValue() + dice2.getValue();
    }
}

class Dice extends JPanel {

    private int value;

    public int getValue() {
        return this.value;
    }

    public Dice() {
        this.setPreferredSize(new Dimension(100, 100));
    }

    public void roll() {
        this.value = (int) (1 + Math.random() * 6);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        int dotSize = getWidth() / 6;
        switch (value) {
            case 1:
                drawDot(g, getWidth() / 2, getHeight() / 2, dotSize);
                break;
            case 2:
                drawDot(g, getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, 3 * getHeight() / 4, dotSize);
                break;
            case 3:
                drawDot(g, getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, getWidth() / 2, getHeight() / 2, dotSize);
                drawDot(g, 3 * getWidth() / 4, 3 * getHeight() / 4, dotSize);
                break;
            case 4:
                drawDot(g, getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, 3 * getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, getWidth() / 4, 3 * getHeight() / 4, dotSize);
                break;
            case 5:
                drawDot(g, getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, 3 * getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, getWidth() / 4, 3 * getHeight() / 4, dotSize);
                drawDot(g, getWidth() / 2, getHeight() / 2, dotSize);
                break;
            case 6:
                drawDot(g, getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, 3 * getHeight() / 4, dotSize);
                drawDot(g, 3 * getWidth() / 4, getHeight() / 4, dotSize);
                drawDot(g, getWidth() / 4, 3 * getHeight() / 4, dotSize);
                drawDot(g, getWidth() / 4, getHeight() / 2, dotSize);
                drawDot(g, 3 * getWidth() / 4, getHeight() / 2, dotSize);
                break;
        }
    }

    private void drawDot(Graphics g, int x, int y, int size) {
        g.fillOval(x - size / 2, y - size / 2, size, size);
    }
}