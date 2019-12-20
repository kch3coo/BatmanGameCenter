package fall2018.csc2017.GameCentre;

import android.content.Context;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;

/**
 * The serializable user account manager.
 */
public class UserAccManager implements Serializable {

    /**
     * The HashMap that stores all counts.
     */
    private Map<String, UserAccount> accountMap = new HashMap<>();

    /**
     * The String that stores the current user.
     */
    private String currentUser;

    /**
     * The boolean that keeps track of whether last game saves load is successful or not.
     */
    public boolean gameLoaded;

    /**
     * The String that stores the current game.
     */
    private String currentGame;

    /**
     * Check whether a given email and password exists in the accountMap or not.
     *
     * @param email    the email/username
     * @param password the password
     * @return whether a given email and password exists in the accountMap or not.
     */
    public Boolean accountExist(String email, String password) {
        Boolean found = false;
        for (String key : accountMap.keySet()) {
            if (key.equals(email) && accountMap.get(key).getPassword().equals(password)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Return the current user.
     *
     * @return the current user.
     */
    public String getCurrentUser(){
        return currentUser;
    }

    /**
     * Set the current user.
     *
     * @param currentUser current user.
     */
    public void setCurrentUser(String currentUser){
        this.currentUser = currentUser;
    }

    /**
     * Return the account map.
     *
     * @return account map
     */
    public HashMap<String, UserAccount> getAccountMap() {
        return (HashMap<String, UserAccount>) accountMap;
    }

    /**
     *  Write the accounts into accountMap.
     *
     * @param email    the email/username
     * @param password the password
     * @param context the current context
     * @return the string for which toast to display
     */
    public String writeAcc(String email, String password, Context context) {
        if (accountMap.containsKey(email)){
            return "Username already taken!";
        } else if (password.equals("") || email.equals("")){
            return "Field cannot be empty!";
        } else {
            accountMap.put(email, new UserAccount(email, password));
            return "Registered!";
        }
    }

    /**
     * Return the current game user is playing.
     *
     * @return the current game user is playing.
     */
    public String getCurrentGame() {
        return currentGame;
    }

    /**
     * Set the current game user is playing.
     *
     * @param game the current game
     */
    public void setCurrentGame(String game) {
        if (game != null){
            currentGame = game;
        }
    }

    /**
     * Return the current game user is playing.
     *
     * @param accountMap the account hashMap.
     */
    public void setAccountMap(Map<String, UserAccount> accountMap){
        this.accountMap = accountMap;
    }

    /**
     * Add score of a user based on how many moves he/she made.
     *
     * @param scoringStrategy the strategy to calculate score.
     * @param moves number of moves user made.
     * @param boardManager board manager user is playing on.
     */
    public void addScore(ScoringStrategy scoringStrategy, int moves,
                         AbstractBoardManager boardManager) {
        if (boardManager.toString().equals("2048 Board Manager")){
            scoringStrategy.addScore(moves, boardManager.getBoard());
        }
        else {
            if (boardManager.puzzleSolved()) {
                scoringStrategy.addScore(moves, boardManager.getBoard());
            }
        }
    }

    /**
     * Set the user's game save to be the current board manager.
     *
     * @param boardManager the board manager
     */
    public void setCurrentGameState(AbstractBoardManager boardManager){
        if (currentGame != null && accountMap.containsKey(currentUser)) {
            accountMap.get(currentUser).setSaves(currentGame, boardManager);
        }
    }

    /**
     * Set the user's game save to be the current board manager.
     *
     * @param game the game name that user wants to load.
     */
    public AbstractBoardManager getCurrentGameState(String game){
        Map<String, AbstractBoardManager> tempGameSaves = accountMap.get(currentUser).getSaves();
        if (containPartOfKey(tempGameSaves, game)) {
            currentGame = game;
            gameLoaded = true;
            return getGame(tempGameSaves, game);
        } else {
            gameLoaded = false;
            return null;
        }
    }

    /**
     * Check whether the saves contain the game type.
     *
     * @param gameSaves the map of game types corresponding to the board manager for each game.
     * @param game the game name that user wants to load.
     *
     * @return whether the saves contain the game type.
     */
    private boolean containPartOfKey(Map<String, AbstractBoardManager> gameSaves, String game){
        for (String key : gameSaves.keySet()){
            if (game != null && game.contains(key) && gameSaves.get(key) != null){
                return true;
            }
        }
        return false;
    }

    /**
     * Return the game board manager.
     *
     * @param gameSaves the map of game types corresponding to the board manager for each game.
     * @param game the game name that user wants to load.
     *
     * @return the game board manager
     */
    private AbstractBoardManager getGame(Map<String, AbstractBoardManager> gameSaves, String game){
        if (game.contains("sliding")){
            return gameSaves.get("sliding");
        } else {
            return gameSaves.get(game);
        }
    }

    /**
     * Make a toast message of whether game board is initialized successfully or not.
     * Combined with getCurrentGameState method above, to clarify for user.
     *
     * @return the string that tells which toast to show.
     */
    String makeToastTextGameState(){
        if (gameLoaded) {
            return "Game save successfully loaded! Enjoy your game.";
        } else {
            return "Game save not found!";
        }
    }

    /**
     * Return number of undo times user chose to have.
     *
     * @return number of undo times user chose to have.
     */
    public int getUserUndoTime() {
        return accountMap.get(currentUser).getMaxUndo();
    }

    /**
     * Update the number of undo times user chose to have.
     *
     * @param undoTime numbers of undo
     */
    public void updateUndoTime(int undoTime) {
        accountMap.get(currentUser).setMaxUndo(undoTime);
    }

}
