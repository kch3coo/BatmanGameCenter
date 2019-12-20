package fall2018.csc2017.GameCentre.Strategies;

import android.support.annotation.NonNull;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileBoard;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

/**
 * The strategy of calculating score
 **/
public class SlidingTileStrategy implements ScoringStrategy {

    /**
     * The account map that stores all accounts
     **/
    private Map<String, UserAccount> accountMap;

    /**
     * The strategy of calculating score
     **/
    private String currentUser;

    /**
     * The strategy of calculating score
     **/
    private String currentGame;

    /**
     * The strategy of calculating score
     **/
    private int currScore;

    /**
     * The strategy of calculating score
     **/
    private int numMoves;

    /**
     * The strategy of calculating score
     **/
    private SlidingTileBoard tempBoard;

    /**
     * The sliding tile scoring strategy.
     *
     * @param accManager the account manager.
     */
    public SlidingTileStrategy(@NonNull UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = accManager.getCurrentGame();
    }

    @Override
    public void addScore(int moves, AbstractBoard board) {
        int score = accountMap.get(currentUser).getScores().get(currentGame);
        numMoves = moves;
        tempBoard = (SlidingTileBoard) board;
        tempBoard.setNumRows(board.getNumRows());
        tempBoard.setNumCols(board.getNumCols());
        if (getScore() / moves > score && moves != 1) {
            accountMap.get(currentUser).setScore(currentGame, currScore);
        }
        accountMap.get(currentUser).setSaves(currentGame, null);
    }

    @Override
    public int getScore() {
        currScore = 1000 * tempBoard.numTiles() / numMoves;
        return currScore;
    }
}

