package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.SlidingTile;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileBoardManager;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileBoard;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileTest {

    /**
     * The board manager for testing.
     */
    SlidingTileBoardManager boardManager = new SlidingTileBoardManager(4);

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<SlidingTile> makeTiles() {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = boardManager.getBoard().getNumRows() *
                boardManager.getBoard().getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved 4x4 Board.
     */
    private void setUpCorrect() {
        List<SlidingTile> tiles = makeTiles();
        AbstractBoard board = new SlidingTileBoard(tiles, 4);
        boardManager = new SlidingTileBoardManager((SlidingTileBoard) board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0,
                0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.puzzleSolved());
    }

    /**
     * Test whether getBoardSize() works.
     */
    @Test
    public void testGetBoardSize() {
        SlidingTileBoardManager managerFour = new SlidingTileBoardManager(4);
        SlidingTileBoardManager managerFive = new SlidingTileBoardManager(5);
        assertEquals(4 * 4, managerFour.getBoard().getBoardSize());
        assertEquals(5 * 5, managerFive.getBoard().getBoardSize());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(false, boardManager.isValidTap(15));
        assertEquals(false, boardManager.isValidTap(10));
    }

    /**
     * Test whether touchMove works.
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        assertEquals(15, boardManager.blankTilePosition());
        boardManager.touchMove(11, false);
        assertEquals(11, boardManager.blankTilePosition());
    }

    /**
     * Test whether undo works.
     */
    @Test
    public void testUndo() {
        setUpCorrect();
        boardManager.touchMove(11, false);
        assertEquals(11, boardManager.blankTilePosition());
        boardManager.undo();
        assertEquals(15, boardManager.blankTilePosition());
    }

    /**
     * Test whether getNumMoves and increasing the number of moves works.
     */
    @Test
    public void testGetSetNumMoves() {
        setUpCorrect();
        assertEquals(0, boardManager.getBoard().getNumOfMoves());
        boardManager.touchMove(14, false);
        assertEquals(1, boardManager.getBoard().getNumOfMoves());
        boardManager.undo();
        assertEquals(1, boardManager.getBoard().getNumOfMoves());
    }

}

