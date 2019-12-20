package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.Observable;

import fall2018.csc2017.GameCentre.tiles.Tile;

/**
 * The generic abstract board that operate based on any class that extends Tile.
 * @param <T> Any class that's a subclass of Tile.
 */
public abstract class AbstractBoard<T extends Tile> extends Observable implements Serializable {

    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of columns.
     */
    private int numCols;

    /**
     * The tiles board.
     */
    private T[][] tiles;

    /**
     * get number of rows.
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * get number of columns.
     *
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * set number of rows.
     *
     * @param numRows number of rows
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * set number of columns.
     *
     * @param numCols number of columns
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    /**
     * get the tile at that position
     *
     * @param col current column
     * @param row current row
     *
     * @return the tile at position (row, col)
     */
    public T getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * set the tile at that position
     *
     * @param col current column
     * @param row current row
     * @param tile the tile
     */
    public void setTile(int row, int col, T tile) {
        tiles[row][col] = tile;
    }

    /**
     * set the entire tile list.
     *
     * @param listOfTiles the list of tiles
     */
    public void setEntireTile(T[][] listOfTiles) {
        this.tiles = listOfTiles;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return getNumRows() * getNumCols();
    }
}
