package controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import map.Node;
import logic.Player;
import logic.City;
import logic.HumanGroup;
import logic.Settlement;
import logic.Thief;
import map.Board;
import map.Tile;
import util.TerrainType;
import gui.DiceGUI;
import gui.GameView;

public class Turn {
    private GameView gameView;
    private Thief thief;
    protected List<Player> playersList;
    protected int currentPlayerIndex;
    private DiceGUI diceGUI;
    protected Player currentPlayer;

    public Turn(List<Player> players) {
        playersList = players;
        currentPlayerIndex = 0; // Commence avec le premier joueur
        this.diceGUI = new DiceGUI(); 
        currentPlayer = playersList.get(currentPlayerIndex);
        thief = new Thief(null);
    }

    void tour() {
        currentPlayer = playersList.get(currentPlayerIndex);
        currentPlayer.setDiced(false);
        update();
        if (currentPlayer.isBot()) {
            diceGUI.roll();
        } else {
            waitRollDice();
        }
        currentPlayer.setDiced(true);
        int sumDices = diceGUI.getResult();
        recupRessources(playersList, sumDices);
        update();
        if (currentPlayer.isBot()) {
            initierEchange();
        }
        creationCity();
    }

    protected void firstBuild(Player player) {
        if (!player.isBot() && player.getRoads().size() < 2) {
            ViewControleur.getCatanControleur().firstBuild(player);
        }else if (player.isBot()) {
            // placement des batiments pour le bot
        }
    }

    private void recupRessources(List<Player> players, int sumDices) {
        System.out.println("result(Turn): " + sumDices);
        if (sumDices == 7) {
            voleur();
        } else {
            ArrayList<Tile> tiles = Board.getTileByDiceNumberArray(sumDices);
            for (Tile t : tiles) {
                if (t.getTerrain() == TerrainType.DESERT || t.getThief() != null)
                    continue;
                Node[] nodes = t.getNeighbors();
                for (Node n : nodes) {
                    HumanGroup hG = n.getHumanGroup();
                    if (hG != null) {
                        int nb = 1;
                        if (hG instanceof City) {
                            nb = 2;
                        }
                        hG.getOwner().addCard(t.getTerrain().toCard(), nb);
                    }
                }
            }
        }
    }

    private void voleur() {
        if (!currentPlayer.isBot()) {
            ViewControleur.getCatanControleur().moveThief(thief);
        }
    }

    protected void recupFirstRessources(){
        for (Player player : playersList) {
            Settlement s = player.getSettlements().get(1);
            Node n = Node.getNode(s);
            List<Tile> t = Tile.getTileAdjacents(n);
            for (Tile tile : t) {
                if (tile.getTerrain() != TerrainType.DESERT) {
                    player.addCard(tile.getTerrain().toCard(), 1);
                }
            }
        }
    }

    public void initierEchange() {
        if (currentPlayer.isBot() && !currentPlayer.exchangeSuggestion()) {
            return;
        }
        List<Player> accepter = new ArrayList<>();
        for (Player p : playersList) {
            if (p == currentPlayer) {
                continue;
            }
            if (p.isBot()) {
                p.updateTradeLists();
                if (currentPlayer.isTradeInteresting(p)) {
                    accepter.add(p);
                }else{
                    accepter.add(currentPlayer);
                }
            } else if (!p.isBot()) {
                proposeEchange(accepter, p);
            }p.revertFromSaleList();
            p.getWishList().clearBox();
        }
        if (accepter.size() == playersList.size() - 1) {
            echange(accepter);
        }
    }

    public void echange(List<Player> accepter) {
        if (accepter.size() < playersList.size() - 1) {
            return;
        }
        for (int i = 0; i < playersList.size() - 1; i++)
            accepter.remove(currentPlayer);
        if (accepter.size() > 0) {
            Random rd = new Random();
            Player choisi = accepter.get(rd.nextInt(accepter.size()));
            currentPlayer.trade(choisi);
        } else if (currentPlayer.isBot()) {
            currentPlayer.trade(currentPlayer.getSaleList(), currentPlayer.getBank(), currentPlayer.getWishList(),
                    currentPlayer.getMyCards());
        }currentPlayer.revertFromSaleList();
        currentPlayer.getWishList().clearBox();
    }

    /* 
    private boolean buyRessourceCard(Player p,Card c){
        return p.buyRessourceCard(c);
    }
    */

    private void creationCity(){}

    private void proposeEchange(List<Player> accepter, Player p) {
        if (currentPlayer.canTradeWith(p)) {
            gameView.proposeEchange(currentPlayer, accepter, p);
        } else {
            accepter.add(currentPlayer);
            if (accepter.size() == playersList.size()) {
                currentPlayer.revertFromSaleList();
                currentPlayer.getWishList().clearBox();
            }
        }
    }

    private void waitRollDice() {
        diceGUI.setRollDice(false);
        while (!diceGUI.isRollDice()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
    
    public DiceGUI getDiceGUI() {
        return diceGUI;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void update() {
        currentPlayer.calculePoints();
        if (gameView != null) {
            gameView.update();
        }
    }
}
