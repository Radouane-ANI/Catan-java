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
import ai.Action;

import javax.swing.JOptionPane;

public class Turn {
    private GameView gameView;
    private Thief thief;
    protected List<Player> playersList;
    protected int currentPlayerIndex;
    private DiceGUI diceGUI;
    protected Player currentPlayer;
    private boolean promptForReroll, finishedTrade;
    private String currentWeather;
    private Action actionBot;

    public Turn(List<Player> players) {
        playersList = players;
        currentPlayerIndex = 0; // Commence avec le premier joueur
        this.diceGUI = new DiceGUI(); 
        currentPlayer = playersList.get(currentPlayerIndex);
        thief = new Thief(null);
        actionBot = new Action();
        finishedTrade = true;
    }

    void tour() {
        currentPlayer = playersList.get(currentPlayerIndex);
        currentPlayer.setFinishedTurn(false);
        update();
        updateWeather();
        boolean isSunnyWeather = gameView != null && gameView.getWeatherDisplay() != null && gameView.getWeatherDisplay().getCurrentWeather().equals("Soleil");
        boolean isSnowWeather = gameView != null && gameView.getWeatherDisplay() != null && gameView.getWeatherDisplay() .getCurrentWeather().equals("Neige");
        if (currentPlayer.isBot()) {
            diceGUI.roll();
        } else {
            waitRollDice(isSunnyWeather);
        }
        currentPlayer.setFinishedTurn(true);
        int sumDices = diceGUI.getResult();
        recupRessources(playersList, sumDices);
        update();
        if (currentPlayer.isBot()) {
            initierEchange();
            waitFinishedTrade();
            actionBot.randomBuild(currentPlayer, isSnowWeather);
        }
        promptForReroll = false;
        currentPlayer.setFinishedTurn(true);
        
    }
    
    protected void firstBuild(Player player) {
        if (!player.isBot()) {
            ViewControleur.getCatanControleur().firstBuild(player);
        } else {
            actionBot.firstBuild(player);
        }
    }

    private void recupRessources(List<Player> players, int sumDices) {
        if (gameView != null && gameView.getWeatherDisplay() != null) {
            currentWeather = gameView.getWeatherDisplay().getCurrentWeather();
        }
        if (sumDices == 7) {
            voleur();
        } else {
            ArrayList<Tile> tiles = Board.getTileByDiceNumberArray(sumDices);
            for (Tile t : tiles) {
                if (t.getTerrain() == TerrainType.DESERT || t.getThief() != null) continue;
                Node[] nodes = t.getNeighbors();
                for (Node n : nodes) {
                    HumanGroup hG = n.getHumanGroup();
                    if (hG != null) {
                        int nb = 1;
                        if (hG instanceof City) {
                            nb = 2;
                        }
                        if (currentWeather.equals("Pluie")) {
                            if (t.getTerrain() == TerrainType.FIELD || t.getTerrain() == TerrainType.FOREST) {
                                nb++;
                            }
                        }
                        if (currentWeather.equals("Vent")) {
                            if (t.getTerrain() == TerrainType.MOUNTAIN || t.getTerrain() == TerrainType.BRICK) {
                                nb++;
                            }
                        }
                        hG.getOwner().addCard(t.getTerrain().toCard(), nb);
                    }
                }
            }
        }
    }

    private void voleur() {
        if (!currentWeather.equals("Nuageux")) {
            for (Player player : playersList) {
                if (player.isBot()) {
                    actionBot.stolenRandom(player);
                }
                else if (player.getMyCards().getNumberOfRes() > 7) {
                    gameView.updateStolen(player);
                    player.setFinishedTurn(false);
                    while (!player.isFinishedTurn()) {
                        sleep();
                    }
                    player.setFinishedTurn(false);    
                }
            }
            update();
            if (!currentPlayer.isBot()) {
                ViewControleur.getCatanControleur().moveThief(thief, currentPlayer);
            }else {
                actionBot.moveThief(currentPlayer, thief);
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

    public void initierEchange() {
        finishedTrade = false;
        update();
        if (currentPlayer.isBot() && !currentPlayer.exchangeSuggestion()) {
            finishedTrade = true;
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
                } else {
                    accepter.add(currentPlayer);
                }
            } else if (!p.isBot()) {
                proposeEchange(accepter, p);
            }
        }
        if (accepter.size() == playersList.size() - 1) {
            echange(accepter);
        }
    }

    public void echange(List<Player> accepter) {
        if (accepter.size() < playersList.size() - 1) {
            currentPlayer.revertFromSaleList();
            return;
        }
        for (int i = 0; i < playersList.size() - 1; i++)
            accepter.remove(currentPlayer);
        if (accepter.size() > 0) {
            Random rd = new Random();
            Player choisi = accepter.get(rd.nextInt(accepter.size()));
            currentPlayer.trade(choisi);
        } else if (currentPlayer.isBot() && currentPlayer.isTradableInBank()) {
            currentPlayer.trade(currentPlayer.getSaleList(), currentPlayer.getBank(), currentPlayer.getWishList(),
                    currentPlayer.getMyCards());
        }
        currentPlayer.revertFromSaleList();
        currentPlayer.getWishList().clearBox();
        finishedTrade = true;
        update();
    }

    /* 
    private boolean buyRessourceCard(Player p,Card c){
        return p.buyRessourceCard(c);
    }
    */

    private void proposeEchange(List<Player> accepter, Player p) {
        if (currentPlayer.canTradeWith(p)) {
            gameView.proposeEchange(currentPlayer, accepter, p);
        } else {
            accepter.add(currentPlayer);
            if (accepter.size() == playersList.size() -1) {
                echange(accepter);
            }
        }
    }

    protected void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    private void waitFinishedTrade() {
        while (!finishedTrade) {
            sleep();
        }
    }

    public boolean isFinishedTrade() {
        return finishedTrade;
    }

    private void waitRollDice(boolean isSunnyWeather) {
        diceGUI.setRollDice(false);
    
        while (!diceGUI.isRollDice()) {
            sleep();
        }
    
        if (isSunnyWeather && !promptForReroll) {
            if (!currentPlayer.isBot()) {
                int choice = JOptionPane.showConfirmDialog(null, "Il fait beau aujourd'hui! Voulez-vous relancer les dés?", "Reroll",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    diceGUI.roll();
                }
            }
            promptForReroll = true;
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

    private void updateWeather() {
        if (gameView != null && gameView.getWeatherDisplay() != null) {
            gameView.getWeatherDisplay().updateWeather(!currentPlayer.isBot());
        }
    }
}