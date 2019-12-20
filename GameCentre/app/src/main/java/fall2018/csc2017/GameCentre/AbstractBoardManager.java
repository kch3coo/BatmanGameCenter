package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * The generic abstract board manager, that operates based on any class that's a subclass of
 * AbstractBoard.
 * @param <T> any class that's a subclass of AbstractBoard.
 */
public abstract class AbstractBoardManager<T extends AbstractBoard> implements Serializable {

    /**
     * The board
     */
    private T board;

    /**
     * get the board.
     * @return board.
     */
    public T getBoard(){return board;}

    /**
     * Set the board.
     * @param board the board to set to.
     */
    public void setBoard(T board){
        this.board = board;
    }

    /**
     * Check whether the puzzle is solved.
     * @return whether puzzle is solved.
     */
    public abstract boolean puzzleSolved();

    /**
     * Translate the board into a string.
     * @return the string representation of the board.
     */
    @Override
    public abstract String toString();
}
