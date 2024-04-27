package gui;

import java.awt.Graphics;

import javax.swing.JLabel;

public class CityTileComponent extends JLabel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, CatanBoardView.TILE_SIZE / 4, CatanBoardView.TILE_SIZE / 4);
    }
}
