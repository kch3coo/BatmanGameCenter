package fall2018.csc2017.GameCentre.tiles;

/**
 * A Tile in a sudoku game.
 */
public class SudokuTile extends Tile {
    /**
     * Number at the current position.
     */
    private int value;

    /**
     * Number is mutable if check available is 1, 0 otherwise.
     */
    private boolean isMutable;

    /**
     * Set value of the current position
     *
     * @param value value of the current position
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Return the current value.
     *
     * @return the value of the tile
     */
    public int getValue() {
        return value;
    }

    /**
     * Return whether tile is mutable.
     *
     * @return whether tile is mutable
     */
    public boolean getIsMutable() {
        return isMutable;
    }

    /**
     * Set whether tile is mutable.
     *
     * @param isMutable whether tile is mutable
     */
    public void setIsMutable(boolean isMutable) {
        this.isMutable = isMutable;
    }

    /**
     * A tile with a background id, value and mutability.
     *
     * @param id        representing the position of the tile
     * @param value     current tile's displayed value 1, 2, ..., 9 or 0 (blank)
     * @param isMutable whether the tile is open to modification during the game
     */
    public SudokuTile(int id, int value, boolean isMutable) {
        setId(id);
        this.value = value;
        this.isMutable = isMutable;
    }
}
