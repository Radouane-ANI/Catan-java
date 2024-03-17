package gui;

import javax.swing.*;

import controleur.ViewControleur;
import logic.City;
import logic.Settlement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import map.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CatanBoardView extends JPanel {

    public static final int TILE_SIZE = 130;

    private Dimension dim;
    private Point center;

    private BoardImage tuileImage;

    public CatanBoardView(Dimension d) {
        dim = d;
        center = new Point((int) d.getWidth() / 2, (int) d.getHeight() / 2);
        tuileImage = new BoardImage();
        setPreferredSize(d);
        setLayout(null);
        JButton turnButton = new JButton("Next Turn"); // mis ici pour l'instant
        turnButton.setBounds(700, 500, 125, 25);
        turnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ViewControleur.NextTurn(false);
            }

        });
        add(turnButton);
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
        for (Edge e : Edge.getEdgesIntern()) {
            addRoad(null, e, g2d);
        }
        for (Node n : Node.getNodesIntern()) {
            addCity(null, n, g2d);
        }
        g2d.dispose();
    }

    private void drawTile(Tile t, Graphics2D g2d, double screenX, double screenY) {
        ImageIcon image = tuileImage.getTerrainImageIcons(t.getTerrain());
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
    }

    private double calculateScreenX(double x) {
        int spacing = TILE_SIZE / 20; // peut être ajusté
        int padding = 10;

        return x * (TILE_SIZE + spacing) + padding;
    }

    private int calculateScreenY(double x, int y) {
        int spacing = -TILE_SIZE / 4;
        int tileHeight = (int) (TILE_SIZE * 1.15);
        int padding = 10;
        return y * (tileHeight + spacing) + padding;
    }

    private double centerIntersection(double x, int y, double screenY) {
        return screenY += (x != (int) x) ^ (y % 2 == 0) ? TILE_SIZE / 4 : 0;
    }

    public void addCity(JLabel component, Node pos, Graphics2D g) {
        if (g != null && pos.getGroup() == null) {
            return;
        }
        double x = pos.getX();
        int y = pos.getY();

        double screenX = calculateScreenX(x);
        double screenY = calculateScreenY(x, y);

        screenY = centerIntersection(x, y, screenY);

        int centerX = (int) (screenX - (TILE_SIZE / 6));
        int centerY = (int) (screenY - (TILE_SIZE / 6));
        if (component != null) {
            component.setBounds(centerX, centerY, TILE_SIZE / 4 + 1, TILE_SIZE / 4 + 1);
            add(component);
        } else if (g != null) {

            ImageIcon image = tuileImage.getCityImageIcons(pos.getGroup().getOwner());
            g.drawImage(image.getImage(), (int) screenX - image.getIconWidth() / 2, (int) screenY, null);

            // File file = new File("/home/radouane/k-catan/output.png");
            // BufferedImage originalImage;
            // try {
            //     originalImage = resizeImage(file, TILE_SIZE / 2, TILE_SIZE / 2);
            //     g.drawImage(originalImage, centerX, centerY, null);

            // } catch (IOException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }



            // if (pos.getGroup() instanceof Settlement) {
            // g.setColor(pos.getGroup().getColor());
            // g.fillOval(centerX, centerY, TILE_SIZE / 4, TILE_SIZE / 4);
            // } else if (pos.getGroup() instanceof City) {
            // g.setColor(pos.getGroup().getColor());
            // g.fill3DRect(centerX, centerY, TILE_SIZE / 4, TILE_SIZE / 4, true);
            // }
        }
    }

    public void addRoad(RoadComponent component, Edge e, Graphics2D g2d) {
        if (e.getRoad() == null && g2d != null) {
            return;
        }
        double x1 = e.getX().getX();
        int y1 = e.getX().getY();
        double x2 = e.getY().getX();
        int y2 = e.getY().getY();

        double screenX1 = calculateScreenX(x1);
        double screenY1 = calculateScreenY(x1, y1);
        double screenX2 = calculateScreenX(x2);
        double screenY2 = calculateScreenY(x2, y2);

        screenY1 = centerIntersection(x1, y1, screenY1);
        screenY2 = centerIntersection(x2, y2, screenY2);
        if (g2d != null) {
            g2d.setStroke(new BasicStroke(TILE_SIZE / 10 + 1));
            g2d.setColor(e.getRoad().getColor());
            g2d.drawLine((int) screenX1, (int) screenY1, (int) screenX2, (int) screenY2);
        } else if (component != null) {
            component.setSize((int) screenX1, (int) screenY1, (int) screenX2, (int) screenY2);
            component.repaint();
            x1 = Math.min((int) screenX1, (int) screenX2);
            y1 = Math.min((int) screenY1, (int) screenY2);
            x2 = Math.max((int) screenX1, (int) screenX2);
            y2 = Math.max((int) screenY1, (int) screenY2);
            component.setBounds((int) x1 - TILE_SIZE / 20, y1, (int) (x2 + TILE_SIZE / 10 - x1), y2 - y1);
            add(component);
        }
    }

    public static BufferedImage resizeImage(File file, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(file);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }
}
