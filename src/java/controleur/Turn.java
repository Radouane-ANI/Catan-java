package controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import map.Node;
import logic.Player;
import logic.City;
import logic.HumanGroup;
import logic.Settlement;
import map.Board;
import map.Tile;
import util.TerrainType;
import gui.DiceGUI;
import gui.GameView;

public class Turn {
    private GameView gameView;
    protected List<Player> playersList;
    protected int currentPlayerIndex;
    private DiceGUI diceGUI;
    protected Player currentPlayer;

    public Turn(List<Player> players) {
        playersList = players;
        currentPlayerIndex = 0; // Commence avec le premier joueur
        this.diceGUI = new DiceGUI(); 
        currentPlayer = playersList.get(currentPlayerIndex);
    }

    void tour() {
        currentPlayer.setDiced(false);
        currentPlayer = playersList.get(currentPlayerIndex);
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
        echange();
        creationCity();
    }

    protected void firstBuild(Player player) {
        if (!player.isBot() && player.getRoads().size() < 2) {
            ViewControleur.getCatanControleur().firstBuild(player);
        }else if (player.isBot()) {
            // placement des batiments pour le bot
        }
    }

    private void recupRessources(List<Player> players, int sumDices){
        System.out.println("result(Turn): " + sumDices);
        ArrayList<Tile> tiles = Board.getTileByDiceNumberArray(sumDices);
        for (Tile t : tiles){
            if (t.getTerrain() == TerrainType.DESERT) continue;
            Node[] nodes = t.getNeighbors();
            for (Node n : nodes){
                HumanGroup hG = n.getHumanGroup();
                if (hG != null){
                    int nb =1;
                    if (hG instanceof City) {
                        nb =2;
                    }
                    hG.getOwner().addCard(t.getTerrain().toCard(), nb);
                }
            }
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

    private void echange(){
        if (!currentPlayer.exchangeSuggestion()) {
            return;
        }
        List<Player> accepter = new ArrayList<>();
        for (Player p : playersList) {
            if (p != currentPlayer && p.isBot()) {
                p.updateTradeLists();
                if (currentPlayer.isTradeInteresting(p)) {
                    accepter.add(p);
                }
            } else if (!p.isBot()) {
                if (proposeEchange(currentPlayer)) {
                    accepter.add(p);
                }
            }
        }
        if (accepter.size() > 0) {
            Random rd = new Random();
            Player choisi = accepter.get(rd.nextInt(accepter.size()));
            currentPlayer.trade(choisi);
        }else if (currentPlayer.isBot()) {
            currentPlayer.trade(currentPlayer.getSaleList(),currentPlayer.getBank(),currentPlayer.getWishList(),currentPlayer.getMyCards());
        }
    }

    /* 
    private boolean buyRessourceCard(Player p,Card c){
        return p.buyRessourceCard(c);
    }
    */

    private void creationCity(){}

    private boolean proposeEchange(Player p) {
        // affiche a l'ecran un echange que le joueur peut accepeter ou non
        return false;
    }

    private void waitRollDice() {
        while (!diceGUI.isRollDice()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        diceGUI.setRollDice(false);
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

    protected void update(){
        gameView.update();
    }
}
