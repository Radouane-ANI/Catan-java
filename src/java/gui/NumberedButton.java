package src.java.gui;

import javax.swing.*;
import java.awt.*;

public class NumberedButton extends JButton {
    private int number = 0;

    public NumberedButton() {
        super();
    }

    public void setNumber(int number) {
        this.number = number;
        this.repaint();
    }

    public int getNumber() {
        return number;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (number > 0) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 12));

            // calcul the location of the number; Here is on the right top corner
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(Integer.toString(number));
            int stringAscent = fm.getAscent();
            int x = getWidth() - stringWidth - 5;
            int y = stringAscent + 5;

            // paint number
            g.drawString(Integer.toString(number), x, y);
        }
    }
}
