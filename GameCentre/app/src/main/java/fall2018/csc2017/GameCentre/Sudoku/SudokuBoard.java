package fall2018.csc2017.GameCentre.Sudoku;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.tiles.SudokuTile;

/**
 * The Sudoku board.
 */
public class SudokuBoard extends AbstractBoard<SudokuTile> {

    /**
     * Initialize a new Sudoku Board
     *
     * @param tiles list of tiles
     */
    public SudokuBoard(List<SudokuTile> tiles) {
        setNumCols(9);
        setNumRows(9);
        setEntireTile(new SudokuTile[getNumRows()][getNumCols()]);
        Iterator<SudokuTile> iter = tiles.iterator();
        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                setTile(row, col, iter.next());
                getTile(row, col).setId(getNumCols() * row + col + 1);
            }
        }
    }

    /**
     * Return a horizontal iterator that iterate Sodoku board horizontally.
     *
     * @return the iterable Sodoku tile, iterating horizontally.
     */
    public Iterable<SudokuTile> horizontal() {
        return new Iterable<SudokuTile>() {
            @NonNull
            @Override
            public Iterator<SudokuTile> iterator() {
                return new SudokuIterator();
            }
        };
    }

    /**
     * Return a vertical iterator that iterate Sodoku board vertically.
     *
     * @return the iterable Sodoku tile, iterating vertically.
     */
    public Iterable<SudokuTile> vertical() {
        return new Iterable<SudokuTile>() {
            @NonNull
            @Override
            public Iterator<SudokuTile> iterator() {
                return new SudokuVerticalIterator();
            }
        };
    }

    /**
     * Return a sectional iterator that iterate Sodoku board by each 3x3 section.
     *
     * @return the iterable Sodoku tile, iterating by each 3x3 section.
     */
    public Iterable<SudokuTile> sectional() {
        return new Iterable<SudokuTile>() {
            @NonNull
            @Override
            public Iterator<SudokuTile> iterator() {
                return new SudokuSectionalIterator();
            }
        };
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     * (Horizontal iterator)
     */
    private class SudokuIterator implements Iterator<SudokuTile> {

        /**
         * The index of next item in tiles list.
         */
        int nextIndex = 0;

        /**
         * Return if there's a next tile in the array of Tiles.
         *
         * @return whether there's a next tile in the array.
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        /**
         * Return the next tile in the array, and update the index tracker by 1.
         *
         * @return the next tile in the array.
         */
        @Override
        public SudokuTile next() {
            int curRow = nextIndex / getNumRows();
            int curColumn = nextIndex % getNumCols();
            SudokuTile tile = getTile(curRow, curColumn);
            nextIndex += 1;
            return tile;
        }
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     * (Vertical iterator)
     */
    private class SudokuVerticalIterator implements Iterator<SudokuTile> {

        /**
         * The index of next item in tiles list.
         */
        int nextIndex = 0;

        /**
         * Return if there's a next tile in the array of Tiles.
         *
         * @return whether there's a next tile in the array.
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        /**
         * Return the next tile in the array, and update the index tracker by 1.
         *
         * @return the next tile in the array.
         */
        @Override
        public SudokuTile next() {
            int curRow = nextIndex / getNumRows();
            int curColumn = nextIndex % getNumCols();
            SudokuTile tile = getTile(curColumn, curRow);
            nextIndex += 1;
            return tile;
        }
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     * (Sectional iterator)
     */
    private class SudokuSectionalIterator implements Iterator<SudokuTile> {

        /**
         * The index of next item in tiles list.
         */
        int nextIndex = 0;

        /**
         * Return if there's a next tile in the array of Tiles.
         *
         * @return whether there's a next tile in the array.
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        /**
         * Return the next tile in the array, and update the index tracker by 1.
         *
         * @return the next tile in the array.
         */
        @Override
        public SudokuTile next() {
            int curRow = ((nextIndex / 9) / 3) * 3 + (nextIndex % 9) / 3;
            int curCol = ((nextIndex / 9) % 3) * 3 + (nextIndex % 9) % 3;
            SudokuTile tile = getTile(curCol, curRow);
            nextIndex += 1;
            return tile;
        }
    }

    /**
     * Increment tile by 1
     *
     * @param row the row
     * @param col the column
     */
    void incrementTile(int row, int col) {
        if (getTile(row, col).getValue() == getNumRows()) {
            getTile(row, col).setValue(0);
        } else {
            getTile(row, col).setValue(getTile(row, col).getValue() + 1);
        }
        setChanged();
        notifyObservers();
    }
}
