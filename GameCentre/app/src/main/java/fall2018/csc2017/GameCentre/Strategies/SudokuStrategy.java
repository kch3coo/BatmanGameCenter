package fall2018.csc2017.GameCentre.Strategies;

import android.support.annotation.NonNull;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

/**
 * Strategy for Sudoku
 */
public class SudokuStrategy implements ScoringStrategy {

    /**
     * The account map that stores all accounts
     **/
    private Map<String, UserAccount> accountMap;

    /**
     * The current user.
     **/
    private String currentUser;

    /**
     * The current game.
     **/
    private String currentGame;

    /**
     * The score.
     **/
    private int score;

    /**
     * Set up the strategy for Sudoku
     *
     * @param accManager the account manager.
     */
    public SudokuStrategy(@NonNull UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = "Sudoku";
    }

    @Override
    public void addScore(int moves, AbstractBoard board) {
        score = accountMap.get(currentUser).getScores().get(currentGame) + 1;
        accountMap.get(currentUser).setScore(currentGame, score);
    }

    @Override
    public int getScore() {
        return score;
    }
}
