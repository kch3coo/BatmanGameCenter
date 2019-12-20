package fall2018.csc2017.GameCentre.SlidingTile;

import android.support.annotation.NonNull;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.tiles.SlidingTile;

/**
 * The sliding tiles board.
 */
public class SlidingTileBoard extends AbstractBoard<SlidingTile> implements Iterable<SlidingTile> {

    /**
     * The stack which keeps track of the clickable undo positions.
     */
    Stack<Integer> historyStack = new Stack<>();

    /**
     * The max times player can undo(default is 3, player could set any positive integer).
     */
    private int maxUndoTime = 3;

    /**
     * How many moves players make
     */
    private int numOfMoves = 0;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public SlidingTileBoard(List<SlidingTile> tiles, int gridSize) {
        Iterator<SlidingTile> iter = tiles.iterator();
        setNumRows(gridSize);
        setNumCols(gridSize);
        setEntireTile(new SlidingTile[getNumRows()][getNumCols()]);

        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                setTile(row, col, iter.next());
            }
        }
    }

    /**
     * Function let numOfMoves increase 1.
     */
    public void increaseNumOfMoves() {
        numOfMoves++;
    }

    /**
     * get number of moves.
     *
     * @return the number of moves
     */
    public int getNumOfMoves() {
        return numOfMoves;
    }

    /**
     * Return the board size.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return getNumRows() * getNumCols();
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        SlidingTile temp = getTile(row1, col1);
        setTile(row1, col1, getTile(row2, col2));
        setTile(row2, col2, temp);
        setChanged();
        notifyObservers();
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     *
     * @return the new board iterator
     */
    @Override
    @NonNull
    public Iterator<SlidingTile> iterator() {
        return new BoardIterator();
    }

    /**
     * The board iterator which checks the tiles in corresponding board position.
     */
    private class BoardIterator implements Iterator<SlidingTile> {

        /**
         * The index of the next item in the class list.
         */
        private int nextIndex = 0;

        /**
         * Check whether the iterator has the next tile.
         *
         * @return whether the iterator has the next tile.
         */
        @Override
        public boolean hasNext() {
            return (nextIndex < numTiles() - 1);
        }

        /**
         * Return the next tile.
         *
         * @return the next tile.
         */
        @Override
        public SlidingTile next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            SlidingTile nextTile = getTile(nextIndex / getNumRows(),
                    nextIndex % getNumCols());
            nextIndex++;
            return nextTile;
        }
    }

    /**
     * get the max undo times.
     *
     * @return the number of max undo times
     */
    public int getMaxUndoTime() {
        return maxUndoTime;
    }

    /**
     * user could set their own undo time instead of 3.
     */
    public void setMaxUndoTime(int num) {
        maxUndoTime = num;
    }

}
