package fall2018.csc2017.GameCentre.Strategies;

import fall2018.csc2017.GameCentre.AbstractBoard;

/**The strategy of calculating score**/
public interface ScoringStrategy {

    /**
     * Add score for users
     *
     * @param moves the number of moves
     * @param board the game board
     */
    void addScore(int moves, AbstractBoard board);

    /**
     * Return the score
     *
     * @return score for user
     */
    int getScore();
}
