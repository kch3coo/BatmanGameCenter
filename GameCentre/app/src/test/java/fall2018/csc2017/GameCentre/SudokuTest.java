package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import fall2018.csc2017.GameCentre.tiles.SudokuTile;

import static org.junit.Assert.*;

import fall2018.csc2017.GameCentre.Sudoku.*;

/**
 * The test for Sudoku game.
 */
public class SudokuTest {

    /**
     * The board manager.
     */
    private SudokuBoardManager boardManager;

    /**
     * Make a partially solved Board.
     */
    @Test
    public void setUpCorrect() {
        this.boardManager = new SudokuBoardManager();
    }

    /**
     * Make a first row of the Sudoku board be numbers from 1-6.
     */

    private void setFirstRow() {
        for (int i = 0; i < 9; i++) {
            this.boardManager.getBoard().getTile(0, i).setValue(i + 1);
        }
    }

    /**
     * Make a empty Board, filled with all 0s.
     */
    private void setUp() {
        ArrayList<Integer> values = new ArrayList<>(Collections.nCopies(81, 0));
        ArrayList<SudokuTile> tiles = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            tiles.add(new SudokuTile(i + 1, values.get(i), false));
        }
        SudokuBoard board = new SudokuBoard(tiles);
        boardManager = new SudokuBoardManager(board);
    }

    /**
     * Test isSolved() works or not.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertFalse(boardManager.puzzleSolved());
        boardManager.solve();
        assertTrue(boardManager.puzzleSolved());
        boardManager.touchMove(1);
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test touchMove() on an empty board at the first position.
     */
    @Test
    public void testTouchMoveEmptyFirst() {
        setUp();
        assertEquals(0, boardManager.getBoard().getTile(0, 0).getValue());
        boardManager.touchMove(0);
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getValue());
        boardManager.touchMove(0);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getValue());
    }

    /**
     * Test touchMove() on a empty board at the last position.
     */
    @Test
    public void testTouchMoveEmptyLast() {
        setUp();
        assertEquals(0, boardManager.getBoard().getTile(8, 8).getValue());
        boardManager.touchMove(80);
        assertEquals(1, boardManager.getBoard().getTile(8, 8).getValue());
        boardManager.touchMove(80);
        assertEquals(2, boardManager.getBoard().getTile(8, 8).getValue());
    }

    /**
     * Test touchMove() on a solved board.
     */
    @Test
    public void testTouchMoveSolved() {
        setUp();
        SudokuTile temp = boardManager.getBoard().getTile(3, 2);
        SudokuTile tile = new SudokuTile(temp.getId(), temp.getValue(), temp.getIsMutable());
        boardManager.touchMove(29);
        SudokuTile newTile = boardManager.getBoard().getTile(3, 2);
        if (tile.getValue() == 9) {
            assertEquals(0, newTile.getValue());
        } else {
            assertEquals(tile.getValue() + 1, newTile.getValue());
        }
    }

    /**
     * Test touchMove() on a board on a tile that's 9, to reset it back to 0.
     */
    @Test
    public void testTouchMoveReset() {
        setUp();
        for (int i = 0; i <= 9; i++) {
            boardManager.touchMove(1);
        }
        assertEquals(0, boardManager.getBoard().getTile(0, 1).getValue());
    }

    /**
     * Test isValid() works or not.
     */
    @Test
    public void testIsValid() {
        setUpCorrect();
        assertTrue(boardManager.isValid());
    }

    /**
     * Test whether isValid() works on an empty board.
     */
    @Test
    public void testIsValidEmpty() {
        setUp();
        assertTrue(boardManager.isValid());
        setFirstRow();
        assertTrue(boardManager.isValid());
        boardManager.getBoard().getTile(1, 0).setValue(1);
        assertFalse(boardManager.isValid());
    }

    /**
     * Test whether isValidTap() works.
     */
    @Test
    public void testIsValidTap() {
        setUp();
        assertFalse(boardManager.isValidTap(0));
        boardManager.getBoard().getTile(0, 0).setIsMutable(true);
        assertTrue(boardManager.isValidTap(0));
    }

    /**
     * Test whether toString() works.
     */
    @Test
    public void testToString() {
        setUp();
        assertEquals(boardManager.toString(), "Sudoku Board Manager");
    }


}
