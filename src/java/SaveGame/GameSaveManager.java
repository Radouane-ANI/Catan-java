package SaveGame;

import controleur.Game;
import java.io.*;

public class GameSaveManager {

    public static final String SAVE_FOLDER_PATH = "game_save";
    public static final String SAVE_FILE_PATH = SAVE_FOLDER_PATH + "/game_save.txt";

    public static void saveGame(Game gameData) {
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

    public static Game loadGame() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH));
            Game gameData = (Game) inputStream.readObject();
            inputStream.close();

            System.out.println("Game loaded successfully.");
            return gameData;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}





