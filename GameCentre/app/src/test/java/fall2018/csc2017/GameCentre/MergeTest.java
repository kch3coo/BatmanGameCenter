package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.Arrays;

import fall2018.csc2017.GameCentre.tiles.TofeTile;
import fall2018.csc2017.GameCentre.The2048.Merge2048;

import static org.junit.Assert.assertTrue;

/**
 * Test the merge class
 */
public class MergeTest {

    /**
     * The board manager for testing.
     */
    private Merge2048 merge2048;

    /**
     * setting up a merge object in which the list to be merged is the second row of the board
     * and the direction of moving is left
     */
    private void setUpLeft() {
        TofeTile[] tiles = {new TofeTile(4, 4), new TofeTile(0, 5),
                new TofeTile(4, 6), new TofeTile(2, 7)};
        merge2048 = new Merge2048(tiles);
    }

    /**
     * setting up a merge object in which the list to be merged is the second row of the board
     * and the direction of moving is right
     */
    private void setUpRight() {
        TofeTile[] tiles = {new TofeTile(0, 7), new TofeTile(2, 6),
                new TofeTile(2, 5), new TofeTile(2, 4)};
        merge2048 = new Merge2048(tiles);
    }

    /**
     * setting up a merge object in which the list to be merged is the first column of the board
     * and the direction of moving is up
     */
    private void setUpUp() {
        TofeTile[] tiles = {new TofeTile(4, 0), new TofeTile(4, 4),
                new TofeTile(4, 8), new TofeTile(4, 12)};
        merge2048 = new Merge2048(tiles);
    }

    /**
     * setting up a merge object in which the list to be merged is the first column of the board
     * and the direction of moving is down.
     */
    private void setUpDown() {
        TofeTile[] tiles = {new TofeTile(2, 12), new TofeTile(4, 8),
                new TofeTile(2, 4), new TofeTile(4, 0)};
        merge2048 = new Merge2048(tiles);
    }

    /**
     * Test merge works if we are trying to move to left
     */
    @Test
    public void testMergeLeft() {
        setUpLeft();
        TofeTile[] result = {new TofeTile(8, 4), new TofeTile(2, 5),
                new TofeTile(0, 6), new TofeTile(0, 7)};
        assertTrue(Arrays.equals(result, merge2048.merge()));
    }

    /**
     * Test merge works if we are trying to move to right
     */
    @Test
    public void testMergeRight() {
        setUpRight();
        TofeTile[] result = {new TofeTile(4, 7), new TofeTile(2, 6),
                new TofeTile(0, 5), new TofeTile(0, 4)};
        assertTrue(Arrays.equals(result, merge2048.merge()));
    }

    /**
     * Test merge works if we are trying to move to right
     */
    @Test
    public void testMergeUp() {
        setUpUp();
        TofeTile[] result = {new TofeTile(8, 0), new TofeTile(8, 4),
                new TofeTile(0, 8), new TofeTile(0, 12)};
        assertTrue(Arrays.equals(result, merge2048.merge()));
    }

    /**
     * Test merge works if we are trying to move to right
     */
    @Test
    public void testMergeDown() {
        setUpDown();
        TofeTile[] result = {new TofeTile(2, 12), new TofeTile(4, 8),
                new TofeTile(2, 4), new TofeTile(4, 0)};
        assertTrue(Arrays.equals(result, merge2048.merge()));
    }
}
