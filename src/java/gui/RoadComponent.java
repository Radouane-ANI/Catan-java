package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class RoadComponent extends JLabel {
    private int screenX1, screenY1, screenX2, screenY2;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setStroke(new BasicStroke(5.0f));
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.drawLine(screenX1, screenY1, screenX2, screenY2);
        g2d.dispose();
    }

    public void setSize(int screenX1, int screenY1, int screenX2, int screenY2) {
        this.screenX2 = screenX2 - screenX1;
        this.screenY2 = screenY2 - screenY1;

        int x1 = Math.min((int) screenX1, (int) screenX2);
        int y1 = Math.min((int) screenY1, (int) screenY2);
        if (x1 == (int) screenX1 && y1 == (int) screenY1) {
            this.screenX1 = 0;
            this.screenY1 = 0;
        } else {
            this.screenX1 = screenX1 - screenX1;
            this.screenY1 = screenY1 - screenY2;
            this.screenX2 += this.screenX1;
            this.screenY2 += this.screenY1;
        }

    }

}
