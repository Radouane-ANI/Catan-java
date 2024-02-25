package gui;

import javax.swing.*;
import logic.City;
import logic.Settlement;
import java.awt.*;
import map.*;

public class CatanBoardView extends JPanel {

    private static final int TILE_SIZE = 60;

    private Dimension dim;
    private Point center;

    private Hexagon hex;

    public CatanBoardView(Dimension d) {
        dim = d;
        center = new Point((int) d.getWidth() / 2, (int) d.getHeight() / 2);
        hex = new Hexagon(TILE_SIZE);
        setPreferredSize(d);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        for (Tile t : Tile.getTilesIntern()) {
            double x = t.getX();
            int y = t.getY();

            double screenX = calculateScreenX(x);
            double screenY = calculateScreenY(x, y);

            drawTile(t, g2d, screenX, screenY);
        }

        for (Node n : Node.getNodesIntern()) {
            double x = n.getX();
            int y = n.getY();

            double screenX = calculateScreenX(x);
            double screenY = calculateScreenY(x, y);

            if (y % 2 == 0) {
                screenY = x == (int) x ? screenY + TILE_SIZE / 2 : screenY;
            } else {
                screenY = x != (int) x ? screenY + TILE_SIZE / 2 : screenY;
            }

            int centerX = (int) (screenX - (TILE_SIZE / 6));
            int centerY = (int) (screenY - (TILE_SIZE / 6));
            if (n.getGroup() instanceof Settlement) {
                g.setColor(n.getGroup().getColor());
                g.fillOval(centerX, centerY, TILE_SIZE / 3, TILE_SIZE / 3);
            } else if (n.getGroup() instanceof City) {
                g.setColor(n.getGroup().getColor());
                g.fill3DRect(centerX, centerY, TILE_SIZE / 3, TILE_SIZE / 3, true);
            }
        }

        for (Edge e : Edge.getEdgesIntern()) {
            if (e.getRoad() == null) {
                continue;
            }
            double x1 = e.getX().getX();
            int y1 = e.getX().getY();
            double x2 = e.getY().getX();
            int y2 = e.getY().getY();

            double screenX1 = calculateScreenX(x1);
            double screenY1 = calculateScreenY(x1, y1);
            double screenX2 = calculateScreenX(x2);
            double screenY2 = calculateScreenY(x2, y2);

            g2d.setStroke(new BasicStroke(5.0f));
            g2d.setColor(e.getRoad().getColor());
            g2d.drawLine((int) screenX1, (int) screenY1, (int) screenX2, (int) screenY2);
        }
    }

    private void drawTile(Tile t, Graphics2D g2d, double screenX, double screenY) {
        Shape pointer = hex.translate(new Point((int) screenX, (int) screenY));
        g2d.setColor(TerrainColor.getTerrainColors(t.getTerrain()));
        g2d.fill(pointer);
        g2d.setColor(Color.BLACK);
        g2d.draw(pointer);

        // numéro de dé
        int diceNumber = t.getDiceNumber();
        if (diceNumber != 0) {
            String diceNumberText = Integer.toString(diceNumber);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(diceNumberText);
            int textHeight = fm.getHeight();
            int textX = (int) screenX + (TILE_SIZE - textWidth) / 2;
            int textY = (int) screenY + (TILE_SIZE + textHeight) / 2;
            g2d.drawString(diceNumberText, textX, textY);
        }
    }

    private double calculateScreenX(double x) {
        int spacing = TILE_SIZE - 13; // peut être ajusté
        int padding = 10;

        return x * (TILE_SIZE + spacing) + padding;
    }

    private int calculateScreenY(double x, int y) {
        int spacing = TILE_SIZE - 27; // peut être ajusté
        int padding = 10;
        return y * (TILE_SIZE + spacing) + padding;
    }
}
