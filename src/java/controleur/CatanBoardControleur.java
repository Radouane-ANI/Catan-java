package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import gui.CatanBoardView;
import gui.CityTileComponent;
import gui.RoadComponent;
import logic.City;
import logic.HumanGroup;
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

    public List<Settlement> buildCity(Player p) {
        List<Settlement> avaibleCity = new ArrayList<>();
        for (Settlement settlement : p.getSettlements()) {
            Node node = Node.getNode(settlement);
            if (node != null) {
                if (p.isBot()) {
                    avaibleCity.add(settlement);
                } else {
                    avaibleCity(node, p, settlement);
                }
            }
        }
        view.repaint();
        return avaibleCity;
    }

    public List<Node> buildSettlement(Player p) {
        List<Node> avaibleSettelement = new ArrayList<>();
        for (Road road : p.getRoads()) {
            Edge edge = Edge.getEdge(road);
            if (edge.getX().canBuildSettlement()) {
                if (p.isBot()) {
                    avaibleSettelement.add(edge.getX());
                } else {
                    avaibleSettelement(edge.getX(), p);
                }
            }
            if (edge.getY().canBuildSettlement()) {
                if (p.isBot()) {
                    avaibleSettelement.add(edge.getY());
                } else {
                    avaibleSettelement(edge.getY(), p);
                }
            }
        }
        view.repaint();
        return avaibleSettelement;
    }

    public List<Edge> buildRoad(Player p) {
        List<Edge> avaibleRoad = new ArrayList<>();
        for (Edge edge : Edge.listBuildRoad(p)) {
            if (p.isBot()) {
                avaibleRoad.add(edge);
            } else {
                avaibleRoad(p, edge);
            }
        }
        view.repaint();
        return avaibleRoad;
    }

    public void moveThief(Thief thief, Player p) {
        p.setFinishedTurn(false);
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
                    Node[] adjacentNodes = tile.getNeighbors();
                    p.setFinishedTurn(true);
                    stealCard(adjacentNodes, p);
                    ViewControleur.getGame().update();
                }
            });

        }
        view.repaint();
    }

    private void stealCard(Node[] adjacentNodes, Player p) {
        for (Node node : adjacentNodes) {
            HumanGroup group = node.getGroup();
            if (group != null && group.getOwner() != p) {
                CityTileComponent city = new CityTileComponent();
                view.addCity(city, node, null);
                cityTileComp.add(city);
                p.setFinishedTurn(false);

                city.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        p.stealCard(group.getOwner());
                        removeCityComponents();
                        p.setFinishedTurn(true);
                        ViewControleur.getGame().update();
                        view.repaint();
                    }
                });
            }
        }
        view.repaint();
    }

    public List<Node> firstBuild(Player p) {
        List<Node> avaibleSettelement = new ArrayList<>();
        if (p == null || p.getRoads().size() > 2) {
            return avaibleSettelement;
        }
        for (Node node : Node.getNodesIntern()) {
            if (node.canBuildSettlement()) {
                if (p.isBot()) {
                    avaibleSettelement.add(node);
                } else {
                    avaibleSettelement(node, p);
                }
            }
        }
        view.repaint();
        return avaibleSettelement;
    }

    public List<Edge> firstBuildRoad(Player p, Node n) {
        List<Edge> avaibleRoad = new ArrayList<>();
        if (p == null || p.getRoads().size() > 2) {
            return avaibleRoad;
        }
        for (Edge edge : Edge.getEdgeNeighbor(n)) {
            if (p.isBot()) {
                avaibleRoad.add(edge);
            } else {
                avaibleRoad(p, edge);
            }
        }
        return avaibleRoad;
    }

    private void avaibleRoad(Player p, Edge edge) {
        RoadComponent road = new RoadComponent();
        view.addRoad(road, edge, null);
        roadComponents.add(road);
        p.setFinishedTurn(false);

        road.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (p.getRoads().size() < 2) {
                    ViewControleur.NextTurn(true);
                }
                Road r = new Road(p);
                p.buildRoad(r);
                edge.setRoad(r);
                removeRoadComponents();
                p.setFinishedTurn(true);
                ViewControleur.getGame().update();
            }
        });
    }

    private void avaibleSettelement(Node n, Player p) {
        CityTileComponent city = new CityTileComponent();
        view.addCity(city, n, null);
        cityTileComp.add(city);
        p.setFinishedTurn(false);

        city.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Settlement c = new Settlement(p);
                p.buildSettlement(c);
                n.setNode(c);
                removeCityComponents();
                if (p.getRoads().size() < 2 && !p.isBot()) {
                    firstBuildRoad(p, n);
                } else if (p.getRoads().size() >= 2){
                    p.setFinishedTurn(true);
                    ViewControleur.getGame().update();
                }
                view.repaint();
            }
        });
    }

    private void avaibleCity(Node n, Player p, Settlement s) {
        CityTileComponent city = new CityTileComponent();
        view.addCity(city, n, null);
        cityTileComp.add(city);
        p.setFinishedTurn(false);

        city.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                City c = new City(p);
                p.buildCity(c, s);
                n.setNode(c);
                removeCityComponents();
                p.setFinishedTurn(true);
                ViewControleur.getGame().update();
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