package fall2018.csc2017.GameCentre.tiles;

import fall2018.csc2017.GameCentre.R;

/**
 * A Tile in a 2048 game.
 */
public class TofeTile extends Tile {

    /**
     * The background image id to find the tile image.
     */
    private int drawableId;

    /**
     * The value on the tile image.
     */
    private int value;

    /**
     * Return the value of the tile
     *
     * @return the value of the tile
     */
    public int getValue() {
        return value;
    }


    /**
     * Set the value of the tile.
     *
     * @param value the value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Return the background image id.
     *
     * @return the background id
     */
    public int getDrawableId() {
        return drawableId;
    }

    /**
     * A tile with a background id and image id; look up and set the id.
     *
     * @param value current tile's displayed value such as 1024, 2048...
     * @param id    representing the position of the tile.
     */
    public TofeTile(int value, int id) {
        setId(id);
        if (value != 0)
            this.drawableId = First2048TileDrawableId + powerOfTwo(value) - 1;
        else
            this.drawableId = R.drawable.white;
        this.value = value;
    }

    /**
     * Return whether 2 2048 tiles or 1 tile and an object are equal
     * @param other other object
     * @return whether 2 2048 tiles or 1 tile and an object are equal
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TofeTile))
            return false;
        return this.getId() == ((TofeTile) other).getId() &&
                this.getValue() == ((TofeTile) other).getValue();
    }

    /**
     * A helper that returns the power of 2 of a given value.
     *
     * @param value a value which is 2^x for positive integer x
     * @return power of two of the given value
     */
    private int powerOfTwo(int value) {
        return value / 2 == 1 ? 1 : 1 +
                powerOfTwo(value / 2);
    }
}
