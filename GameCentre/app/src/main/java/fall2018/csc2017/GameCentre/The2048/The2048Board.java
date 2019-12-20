package fall2018.csc2017.GameCentre.The2048;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.tiles.TofeTile;

import java.util.Random;

/**
 * The board for game 2048.
 */
public class The2048Board extends AbstractBoard<TofeTile> implements Iterable<TofeTile> {

    /**
     * The score of game.
     */
    private int score = 0;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public The2048Board(List<TofeTile> tiles) {
        setNumCols(4);
        setNumRows(4);
        Iterator<TofeTile> iter = tiles.iterator();
        setEntireTile(new TofeTile[getNumRows()][getNumCols()]);

        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                setTile(row, col, iter.next());
            }
        }
    }

    /**
     * Return whether two 2048 boards, or 1 2048 board and an object is equal.
     *
     * @return whether this and Object o is equal.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof The2048Board)) {
            return false;
        }
        int i = 0;
        for (TofeTile tile : (The2048Board) o) {
            int row = i / getNumRows();
            int col = i % getNumCols();
            if (tile.getValue() != getTile(row, col).getValue()) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Return the column corresponding to colNum, can be inverted if specified.
     *
     * @param colNum   the number of the needed column.
     * @param inverted whether we want the column to be inverted.
     * @return the column corresponding to colNum, can be inverted if specified.
     */
    private TofeTile[] getCol(int colNum, boolean inverted) {
        TofeTile[] result = new TofeTile[4];
        if (!inverted) {
            for (int i = 0; i < 4; i++) {
                result[i] = getTile(i, colNum);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                result[i] = getTile(3 - i, colNum);
            }
        }
        return result;
    }

    /**
     * Return the row corresponding to rowNum, can be inverted if specified.
     *
     * @param rowNum   the number of the needed row.
     * @param inverted whether we want the row to be inverted.
     * @return the row corresponding to rowNum, can be inverted if specified.
     */
    private TofeTile[] getRow(int rowNum, boolean inverted) {
        TofeTile[] result = new TofeTile[4];
        if (!inverted) {
            for (int i = 0; i < 4; i++) {
                result[i] = getTile(rowNum, i);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                result[i] = getTile(rowNum, 3 - i);
            }
        }
        return result;
    }

    /**
     * Return the row or column based on String rowOrColumn, the number of row or column
     * to be returned is determined by int rowORColumnNum, can be inverted if specified.
     *
     * @param rowOrColumn    whether we want row or column.
     * @param rowORColumnNum the number of row/column we need.
     * @param inverted       whether we want the row/column to be inverted.
     * @return the row/column corresponding to rowOrColumnNum, can be inverted if specified.
     */
    private TofeTile[] getRowOrColumn(String rowOrColumn, int rowORColumnNum, boolean inverted) {
        if (rowOrColumn.equals("row")) {
            return getRow(rowORColumnNum, inverted);
        } else if (rowOrColumn.equals("column")) {
            return getCol(rowORColumnNum, inverted);
        }
        return null;
    }

    /**
     * Return a Array with length 16 that contains all tiles from the board.
     *
     * @return the array with length 16 that contains all tiles from the board.
     */
    public TofeTile[] getAllTiles() {
        TofeTile[] result = new TofeTile[16];
        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                result[row * 4 + col] = getTile(row, col);
            }
        }
        return result;
    }

    /**
     * Setting the entire board with inputTiles.
     * Precondition: inputTiles has size of 16.
     *
     * @param inputTiles tiles to be put in the board
     */
    public void setAllTiles(TofeTile[] inputTiles) {
        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                this.setTile(row, col, inputTiles[(row * 4) + col]);
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return a list of TofeTile representing the result after merging.
     *
     * @param rOrC     String representing whether we are trying to merge the board by
     *                 row or column
     * @param inverted representing the direction of the merging movement.
     * @return a list of TofeTile representing the result after merging. the position
     * is determined by row * 4 + col
     */
    public TofeTile[] merge(String rOrC, boolean inverted) {
        TofeTile[] resultingTiles = new TofeTile[16];
        for (int i = 0; i < 4; i++) {
            TofeTile[] mergedTiles = new Merge2048(this.getRowOrColumn(rOrC, i, inverted)).merge();
            for (int j = 0; j < 4; j++)
                resultingTiles[mergedTiles[j].getId()] = mergedTiles[j];
        }
        return resultingTiles;
    }

    /**
     * Return true if the input TofeTile Array contains blank tile
     *
     * @return whether the input TofeTile Array contains blank tile
     */
    private boolean containsBlank(TofeTile[] inputs) {
        for (int i = 0; i < 16; i++) {
            if (inputs[i].getValue() == 0)
                return true;
        }
        return false;
    }

    /**
     * Return the updated TofeTile list that contains a newly generated tile if there is blank
     * position on the board.
     *
     * @param inputs input tiles
     * @return the updated TofeTile list that contains a newly generated tile if there is blank
     * position on the board.
     */
    public TofeTile[] generateNewTile(TofeTile[] inputs) {
        if ((!(Arrays.equals(this.getAllTiles(), inputs))) && containsBlank(inputs)) {
            Random rnd = new Random();
            int pos = rnd.nextInt(16);
            while (inputs[pos].getValue() != 0)
                pos = rnd.nextInt(16);
            inputs[pos] = new TofeTile((((int) (Math.random() * 2)) + 1) * 2, pos);
        }
        return inputs;
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     *
     * @return the new board iterator
     */
    @Override
    @NonNull
    public Iterator<TofeTile> iterator() {
        return new BoardIterator();
    }


    /**
     * The board iterator which checks the tiles in corresponding board position.
     */
    private class BoardIterator implements Iterator<TofeTile> {

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
            return (nextIndex < numTiles());
        }

        /**
         * Return the next tile.
         *
         * @return the next tile.
         */
        @Override
        public TofeTile next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            TofeTile nextTile = getTile(nextIndex / getNumRows(),
                    nextIndex % getNumCols());
            nextIndex++;
            return nextTile;
        }
    }

    /**
     * set the score
     *
     * @param renew the score we want to set
     */
    public void setScore(int renew) {
        score = renew;
    }


    /**
     * get the score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
}
