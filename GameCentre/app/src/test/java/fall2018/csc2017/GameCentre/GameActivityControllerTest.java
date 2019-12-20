package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import fall2018.csc2017.GameCentre.SlidingTile.*;
import fall2018.csc2017.GameCentre.The2048.*;
import fall2018.csc2017.GameCentre.Sudoku.*;

/***Test the GameActivityController class*/
public class GameActivityControllerTest {

    /**
     * Game activity controller for testing
     **/
    private GameCenterActivityController gController;

    /**
     * A sliding tile fragment for testing
     **/
    private SlidingTileFragment slidingTileFragment;

    /**
     * A 2048 fragment for testing
     **/
    private The2048Fragment the2048Fragment;

    /**
     * A Sudoku fragment for testing
     **/
    private SudokuFragment sudokuFragment;

    /**
     * A fragment that's never been assigned, which is null
     **/
    private SlidingTileFragment nullFragment;

    /**
     * Create 3 fragment instances except nullFragment.
     */
    private void setUpFragments() {
        slidingTileFragment = new SlidingTileFragment();
        the2048Fragment = new The2048Fragment();
        sudokuFragment = new SudokuFragment();
    }

    /**
     * Set up the game activity controller
     */
    private void setUp() {
        gController = new GameCenterActivityController();
    }

    /**
     * Test whether switchFragment() works.
     */
    @Test
    public void testSwitchFragment() {
        setUp();
        setUpFragments();
        assertEquals(gController.switchFragment(null), nullFragment);
        assertEquals(gController.switchFragment("sliding").getClass(),
                slidingTileFragment.getClass());
        assertEquals(gController.switchFragment("2048").getClass(),
                the2048Fragment.getClass());
        assertEquals(gController.switchFragment("Sudoku").getClass(),
                sudokuFragment.getClass());
        assertEquals(gController.switchFragment("abcd").getClass(),
                slidingTileFragment.getClass());
        assertNotEquals(gController.switchFragment("2048").getClass(),
                sudokuFragment.getClass());
        assertNotEquals(gController.switchFragment("2048").getClass(),
                slidingTileFragment.getClass());
    }
}
