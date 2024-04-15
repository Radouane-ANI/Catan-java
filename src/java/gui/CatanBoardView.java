package gui;

import logic.City;
import logic.Settlement;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import map.*;

public class CatanBoardView extends JPanel {

    public static final int TILE_SIZE = 110;

    private Dimension dim;
    private Point center;

    private BoardImage tuileImage;

    public CatanBoardView(Dimension d) {
        dim = d;
        center = new Point((int) d.getWidth() / 2, (int) d.getHeight() / 2);
        tuileImage = new BoardImage();
        setPreferredSize(d);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        for (Tile t : Tile.getTilesIntern()) {
            double x = t.getX();
            double y = t.getY();

            double screenX = calculateScreenX(x);
            double screenY = calculateScreenY(x, y);

            drawTile(t, g2d, screenX, screenY);
        }
        for (Edge e : Edge.getEdgesIntern()) {
            addRoad(null, e, g2d);
        }
        for (Node n : Node.getNodesIntern()) {
            addCity(null, n, g2d);
        }
        g2d.dispose();
    }

    private void drawTile(Tile t, Graphics2D g2d, double screenX, double screenY) {
        ImageIcon image = tuileImage.getTerrainImageIcon(t.getTerrain());
        g2d.drawImage(image.getImage(), (int) screenX - image.getIconWidth() / 2, (int) screenY, null);

        // Numéro de dé
        int diceNumber = t.getDiceNumber();
        if (diceNumber != 0) {
            int textX = (int) screenX;
            int textY = (int) screenY + TILE_SIZE / 2;
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 20));
            g2d.drawString(diceNumber + "", textX, textY);
        }
        if (t.getThief() != null) {
            addThief(null, t, g2d);System.out.println("pos;");
            System.out.println(t.getX() +" "+ t.getY());
        }
    }

    private double calculateScreenX(double x) {
        int spacing = TILE_SIZE / 20; // peut être ajusté
        int padding = 10;

        return x * (TILE_SIZE + spacing) + padding;
    }

    private double calculateScreenY(double x, double y) {
        int spacing = -TILE_SIZE / 4;
        int tileHeight = (int) (TILE_SIZE * 1.15);
        int padding = 10;
        return y * (tileHeight + spacing) + padding;
    }

    private double centerIntersection(double x, double y, double screenY) {
        return screenY += (x != (int) x) ^ (y % 2 == 0) ? TILE_SIZE / 4 : 0;
    }

    public void addCity(JLabel component, Node pos, Graphics2D g) {
        if (g != null && pos.getGroup() == null) {
            return;
        }
        double x = pos.getX();
        double y = pos.getY();

        double screenX = calculateScreenX(x);
        double screenY = calculateScreenY(x, y);

        screenY = centerIntersection(x, y, screenY);

        int centerX = (int) (screenX - (TILE_SIZE / 6));
        int centerY = (int) (screenY - (TILE_SIZE / 6));
        if (component != null) {
            component.setBounds(centerX, centerY, TILE_SIZE / 4 + 1, TILE_SIZE / 4 + 1);
            add(component);
        } else if (g != null) {

            if (pos.getGroup() instanceof Settlement) {
                ImageIcon image = tuileImage.getSettlementImageIcon(pos.getGroup().getOwner());
                int decalage = image.getIconWidth() / 2;
                g.drawImage(image.getImage(), (int) screenX - decalage, (int) screenY - decalage, null);
            } else if (pos.getGroup() instanceof City) {
                ImageIcon image = tuileImage.getCityImageIcon(pos.getGroup().getOwner());
                int decalage = image.getIconWidth() / 2;

                g.drawImage(image.getImage(), (int) screenX - decalage, (int) screenY - decalage, null);
            }
        }
    }

    public void addRoad(RoadComponent component, Edge e, Graphics2D g2d) {
        if (e.getRoad() == null && g2d != null) {
            return;
        }
        double x1 = e.getX().getX();
        double y1 = e.getX().getY();
        double x2 = e.getY().getX();
        double y2 = e.getY().getY();

        double screenX1 = calculateScreenX(x1);
        double screenY1 = calculateScreenY(x1, y1);
        double screenX2 = calculateScreenX(x2);
        double screenY2 = calculateScreenY(x2, y2);

        screenY1 = centerIntersection(x1, y1, screenY1);
        screenY2 = centerIntersection(x2, y2, screenY2);
        if (g2d != null) {
            g2d.setStroke(new BasicStroke(TILE_SIZE / 12 + 1));
            g2d.setColor(e.getRoad().getColor());
            g2d.drawLine((int) screenX1, (int) screenY1, (int) screenX2, (int) screenY2);
        } else if (component != null) {
            component.setSize((int) screenX1, (int) screenY1, (int) screenX2, (int) screenY2);
            component.repaint();
            x1 = Math.min((int) screenX1, (int) screenX2);
            y1 = Math.min((int) screenY1, (int) screenY2);
            x2 = Math.max((int) screenX1, (int) screenX2);
            y2 = Math.max((int) screenY1, (int) screenY2);
            component.setBounds((int) x1 - TILE_SIZE / 20, (int) y1, (int) (x2 + TILE_SIZE / 10 - x1), (int) (y2 - y1));
            add(component);
        }
    }

    public void addThief(CityTileComponent tileComp, Tile pos, Graphics2D g) {
        if (g != null && pos.getThief() == null) {
            return;
        }
        double x = pos.getX();
        double y = pos.getY();

        double screenX = calculateScreenX(x);
        double screenY = calculateScreenY(x, y);

        int centerX = (int) (screenX - (TILE_SIZE / 6));
        int centerY = (int) (screenY + (TILE_SIZE / 3));
        if (tileComp != null) {
            tileComp.setBounds(centerX, centerY, TILE_SIZE / 4 + 1, TILE_SIZE / 4 + 1);
            add(tileComp);
        } else if (g != null) {
            ImageIcon image = tuileImage.getThiefImage();
            int decalage = image.getIconWidth() / 2;

            g.drawImage(image.getImage(), (int) screenX - decalage, (int) screenY + decalage, null);
        }
    }

}
