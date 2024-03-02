package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import gui.CatanBoardView;
import gui.CityComponent;
import gui.RoadComponent;
import logic.Player;
import logic.Road;
import logic.Settlement;
import map.*;

public class CatanBoardControleur {
    private CatanBoardView view;
    private List<CityComponent> cityComponents = new ArrayList<>();
    private List<RoadComponent> roadComponents = new ArrayList<>();

    public CatanBoardControleur(CatanBoardView view) {
        this.view = view;
    }

    public void firstBuild(Player p) {
        if (p == null) {
            return;
        }
        for (Node node : Node.getNodesIntern()) {
            if (node.getGroup() == null) {
                avaibleSettelement(node, p);
            }
        }
    }

    private void firstBuildRoad(Player p, Node n) {
        for (Edge edge : Edge.getEdgeNeighbor(n)) {
            RoadComponent road = new RoadComponent();
            view.addRoad(road, edge);
            roadComponents.add(road);
            road.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Road r = new Road(p);
                    p.getRoads().add(r);
                    edge.setRoad(r);
                    removeRoadComponents();
                    view.repaint();
                    if (p.getSettlements().size() < 2) {
                        firstBuild(p);
                    }
                }
            });

        }
    }

    public void avaibleSettelement(Node n, Player p) {
        CityComponent city = new CityComponent();
        view.add(city, n);
        cityComponents.add(city);

        city.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Settlement c = new Settlement(p);
                p.getSettlements().add(c);
                n.setNode(c);
                removeCityComponents();
                view.repaint();
                firstBuildRoad(p, n);
            }
        });
    }

    private void removeCityComponents() {
        for (CityComponent cityComponent : cityComponents) {
            cityComponent.setVisible(false);
        }
        cityComponents.clear();
    }

    private void removeRoadComponents() {
        for (RoadComponent roadComponent : roadComponents) {
            roadComponent.setVisible(false);
        }
        roadComponents.clear();
    }
}