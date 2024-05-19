package SaveGame;

import controleur.Turn;
import logic.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {

    public static final String SAVE_FOLDER_PATH = "game_save";
    public static final String SAVE_FILE_PATH = SAVE_FOLDER_PATH + "/game_save.txt";

    public static void saveGame(Turn gameData) {
        try {
            File saveFolder = new File(SAVE_FOLDER_PATH);
            if (!saveFolder.exists()) {
                saveFolder.mkdirs();
            }

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH));
            outputStream.writeObject(gameData);
            outputStream.close();

            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Turn loadGame() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH));
            Turn gameData = (Turn) inputStream.readObject();
            inputStream.close();

            System.out.println("Game loaded successfully.");
            return gameData;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<Player> players ;
        Turn gameData = new Turn(new Turn(players));
        saveGame(gameData);

        Turn loadedGameData = loadGame();

        if (loadedGameData != null) {

            loadedGameData.setGameView(gameData.getGameView());
            List<Player> playersList = loadedTurn.getPlayersList();
            if (loadedTurn != null) {
                // 处理加载的 Turn 数据
            }
        }
    }
}

}



