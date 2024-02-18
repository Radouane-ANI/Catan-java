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
        center = new Point((int)d.getWidth()/2, (int)d.getHeight()/2);
        hex = new Hexagon(TILE_SIZE);
    }

    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            for (Tile t : Board.getTileArray()) {
                drawTile(t, g2d);
            }
        }

    private void drawTile(Tile t, Graphics2D g2) {
        Shape pointer = hex.translate(convertCoordinate(t.getX(), t.getY()));
        g2.setColor(TerrainColor.getTerrainColors(t.getTerrain()));
        g2.fill(pointer);
        g2.setColor(Color.BLACK);
        g2.draw(pointer);
    }

    private Point convertCoordinate(int x, int y) {
        double a = Math.toRadians(-30);
        double b = Math.toRadians(-60);
        return new Point((int)(TILE_SIZE*(Math.cos(a)*x-Math.sin(a)*y)) + 200, (int)(TILE_SIZE*(Math.sin(b)*x+Math.cos(b)*y)) + 200);
    }
}
