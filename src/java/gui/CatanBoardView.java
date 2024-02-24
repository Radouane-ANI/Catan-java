package gui;

import javax.swing.*;
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
            screenY = y % 2 == 0 ? screenY + TILE_SIZE / 2 : screenY;

            int centerX = (int) (screenX - (TILE_SIZE / 6));
            int centerY = (int) (screenY - (TILE_SIZE / 6));

            if (n.getGroup() != null) {
                g.drawRect(centerX, centerY, TILE_SIZE / 3, TILE_SIZE / 3);
                g.drawString(n + "", centerX, centerY);

            }
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
