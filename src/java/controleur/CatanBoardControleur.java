package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import gui.CatanBoardView;
import gui.CityTileComponent;
import gui.RoadComponent;
import logic.City;
import logic.Player;
import logic.Road;
import logic.Settlement;
import logic.Thief;
import map.*;

public class CatanBoardControleur {
    private CatanBoardView view;
    private List<CityTileComponent> cityTileComp = new ArrayList<>();
    private List<RoadComponent> roadComponents = new ArrayList<>();

    public CatanBoardControleur(CatanBoardView view) {
        this.view = view;
    }

    public void buildCity(Player p) {
        for (Settlement settlement : p.getSettlements()) {
            Node node = Node.getNode(settlement);
            if (node != null) {
                avaibleCity(node, p, settlement);
            }
        }
        view.repaint();
    }

    public void buildSettlement(Player p) {
        for (Road road : p.getRoads()) {
            Edge edge = Edge.getEdge(road);
            Node posX = Node.canBuildSettlement(edge.getX());
            if (posX != null) {
                avaibleSettelement(posX, p);
            }
            Node posY = Node.canBuildSettlement(edge.getY());
            if (posY != null) {
                avaibleSettelement(posY, p);
            }
        }
        view.repaint();
    }

    public void buildRoad(Player p) {
        for (Edge edge : Edge.listBuildRoad(p)) {
            avaibleRoad(p, edge);
        }
        view.repaint();
    }

    public void moveThief(Thief thief) {
        for (Tile tile : Tile.getTilesIntern()) {
            if (tile.getThief() != null) {
                continue;
            }
            CityTileComponent tileComp = new CityTileComponent();
            view.addThief(tileComp, tile, null);
            cityTileComp.add(tileComp);

            tileComp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    thief.setPosition(tile);
                    removeCityComponents();
                    view.repaint();
                }
            });

        }
        view.repaint();
    }

    public void firstBuild(Player p) {
        if (p == null || p.getRoads().size() > 2) {
            return;
        }
        for (Node node : Node.getNodesIntern()) {
            if (node.getGroup() == null) {
                avaibleSettelement(node, p);
            }
        }
        view.repaint();
    }

    private void firstBuildRoad(Player p, Node n) {
        for (Edge edge : Edge.getEdgeNeighbor(n)) {
            avaibleRoad(p, edge);
        }
    }

    private void avaibleRoad(Player p, Edge edge) {
        RoadComponent road = new RoadComponent();
        view.addRoad(road, edge, null);
        roadComponents.add(road);
        ViewControleur.setFinishedTurn(false);

        road.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (p.getRoads().size() < 2) {
                    ViewControleur.NextTurn(false);
                }
                Road r = new Road(p);
                p.buildRoad(r);
                edge.setRoad(r);
                removeRoadComponents();
                ViewControleur.setFinishedTurn(true);
            }
        });
    }

    private void avaibleSettelement(Node n, Player p) {
        CityTileComponent city = new CityTileComponent();
        view.addCity(city, n, null);
        cityTileComp.add(city);
        ViewControleur.setFinishedTurn(false);

        city.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Settlement c = new Settlement(p);
                p.buildSettlement(c);
                n.setNode(c);
                removeCityComponents();
                if (p.getRoads().size() < 2) {
                    firstBuildRoad(p, n);
                } else {
                    ViewControleur.setFinishedTurn(true);
                }
                view.repaint();
            }
        });
    }

    private void avaibleCity(Node n, Player p, Settlement s) {
        CityTileComponent city = new CityTileComponent();
        view.addCity(city, n, null);
        cityTileComp.add(city);
        ViewControleur.setFinishedTurn(false);

        city.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                City c = new City(p);
                p.buildCity(c, s);
                n.setNode(c);
                removeCityComponents();
                ViewControleur.setFinishedTurn(true);
                view.repaint();
            }
        });
    }

    private void removeCityComponents() {
        for (CityTileComponent cityComponent : cityTileComp) {
            cityComponent.setVisible(false);
        }
        cityTileComp.clear();
    }

    private void removeRoadComponents() {
        for (RoadComponent roadComponent : roadComponents) {
            roadComponent.setVisible(false);
        }
        roadComponents.clear();
    }
}