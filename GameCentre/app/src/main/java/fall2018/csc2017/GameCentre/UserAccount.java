package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A serializable user account.
 */
public class UserAccount implements Serializable {

    /**
     * User's games
     */
    public static final String[] GAMES = {"3X3sliding", "4X4sliding", "5X5sliding", "Sudoku",
            "2048"};

    /**
     * Game types
     */
    public static final String[] GAME_TYPES = {"sliding", "Sudoku", "2048"};

    /**
     * The username of user.
     */
    private String name;

    /**
     * The password of user.
     */
    private String password;

    /**
     * The maximum number of undo user choose.
     */
    private int maxUndo = 3;

    /**
     * The current image type of sliding tiles game.
     */
    private ImageType imageType;

    /**
     * The HashMap of game map to user's corresponding score for each game.
     */
    private HashMap<String, Integer> scores;

    /**
     * The HashMap of BoardManager map to each game, this is the saves for this user.
     */
    private HashMap<String, AbstractBoardManager> gameSaves;

    /**
     * Return the HashMap of the scores of the user.
     *
     * @return scores
     */
    public HashMap<String, Integer> getScores(){
        return scores;
    }

    /**
     * Return the HashMap of the scores of the user.
     *
     * @return board manager for game
     */
    HashMap<String, AbstractBoardManager> getSaves(){
        return gameSaves;
    }

    /**
     * Return the HashMap of the scores of the user.
     *
     * @param game the game name
     * @param boardManager board manager for this game
     */
    public void setSaves(String game, AbstractBoardManager boardManager){
        for (String key : gameSaves.keySet()){
            if (game.contains(key)){gameSaves.put(key, boardManager);}
        }
    }

    /**
     * Set the image type of the current sliding tile game of the current user.
     *
     * @param imageType image type of sliding tiles
     */
    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    /**
     * Return the image type of the current sliding tile game of the current user.
     *
     * @return image type of sliding tiles
     */
    public ImageType getImageType() {
        return imageType;
    }

    /**
     * Image types that affect how the images are chosen from
     */
    public enum ImageType {
        Default, Resource, Imported
    }

    /**
     * Return the password of the user.
     *
     * @return password
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Set the max number of undo of this user.
     */
    void setMaxUndo(int maxUndo) {
        this.maxUndo = maxUndo;
    }

    /**
     * Get the max number of undo of this user.
     */
    int getMaxUndo() {
        return this.maxUndo;
    }

    /**
     * Get the username of this user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the score of the user for certain game type.
     *
     * @param gameType type of the game
     */
    Integer getScores(String gameType) {
        return scores.get(gameType);
    }

    /**
     * Initialize a new user with username and password.
     *
     * @param n username
     * @param p password
     */
    UserAccount(String n, String p) {
        this.name = n;
        this.password = p;
        this.imageType = ImageType.Default;
        this.scores = new HashMap<>();
        this.gameSaves = new HashMap<>();

        //setting all scores to -1, initialize games saves to null.
        for (String game : GAMES) {
            scores.put(game, -1);
        }
        for (String gameTypes : GAME_TYPES) {
            gameSaves.put(gameTypes, null);
        }
    }

    /**
     * Set the score of this user of current game.
     *
     * @param currentGame current game
     * @param score score for this game
     */
    public void setScore(String currentGame, int score) {
        scores.put(currentGame, score);
    }

}
