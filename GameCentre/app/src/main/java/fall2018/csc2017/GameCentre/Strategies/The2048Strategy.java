package fall2018.csc2017.GameCentre.Strategies;

import android.support.annotation.NonNull;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.The2048.The2048Board;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

/**
 * The scoring strategy for 2048
 **/
public class The2048Strategy implements ScoringStrategy {

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
     * Set up the strategy for 2048 game.
     *
     * @param accManager the account manager.
     */
    public The2048Strategy(@NonNull UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = "2048";
    }

    @Override
    public void addScore(int moves, AbstractBoard board) {
        The2048Board tempBoard = (The2048Board) board;
        score = tempBoard.getScore();
        if (score > accountMap.get(currentUser).getScores().get(currentGame)) {
            accountMap.get(currentUser).setScore(currentGame, score);
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
