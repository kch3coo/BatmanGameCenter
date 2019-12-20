package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileBoard;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileBoardManager;
import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Strategies.SlidingTileStrategy;
import fall2018.csc2017.GameCentre.Strategies.The2048Strategy;
import fall2018.csc2017.GameCentre.Sudoku.SudokuBoardManager;
import fall2018.csc2017.GameCentre.The2048.The2048BoardManager;
import fall2018.csc2017.GameCentre.tiles.SlidingTile;
import fall2018.csc2017.GameCentre.tiles.TofeTile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * The account manager and account test
 */
public class AccountAndAccountManagerTest {

    /**
     * The account manager for testing
     **/
    private UserAccManager accManager;

    /**
     * The user account for testing
     **/
    private UserAccount testAccount;

    /**
     * The account map for testing
     **/
    private Map<String, UserAccount> accountMap = new HashMap<>();

    /**
     * The mocked context for testing
     **/
    private Context context = mock(Context.class);

    /**
     * Set up the account "207", and the game "5X5sliding".
     */
    private void setUpAccountAndGame() {
        accManager = new UserAccManager();
        testAccount = new UserAccount("207", "207");
        accountMap.put("207", testAccount);
        accManager.setAccountMap(accountMap);
        accManager.setCurrentUser("207");
        accManager.setCurrentGame("5X5sliding");
    }

    /**
     * Set up an empty account manager.
     */
    private void setUp() {
        accManager = new UserAccManager();
    }

    /**
     * Test whether accountExist() works.
     */
    @Test
    public void testAccountExist() {
        setUp();
        accManager.writeAcc("CSC207", "hello", context);
        assertTrue(accManager.accountExist("CSC207", "hello"));
        assertFalse(accManager.accountExist("CSC207", "???"));
        assertFalse(accManager.accountExist("???", "hello"));
    }

    /**
     * Test whether set and getCurrentUser() works.
     */
    @Test
    public void testSetGetCurrentUser() {
        setUp();
        accManager.setCurrentUser("CSC207");
        assertEquals("CSC207", accManager.getCurrentUser());
    }

    /**
     * Test whether setAccountMap() and getAccountMap() works.
     */
    @Test
    public void testSetGetAccountMap() {
        setUp();
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        assertEquals(accountMap, accManager.getAccountMap());
    }

    /**
     * Test whether set and getCurrentGameState() works.
     */
    @Test
    public void testSetGetCurrentGameState() {
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        AbstractBoardManager boardManager = new SlidingTileBoardManager(5);
        AbstractBoardManager sudokuManager = new SudokuBoardManager();
        accManager.setCurrentGame("sliding");
        accManager.setCurrentGameState(boardManager);
        assertSame(boardManager, accManager.getCurrentGameState("sliding"));
        accManager.setCurrentGame("Sudoku");
        accManager.setCurrentGameState(sudokuManager);
        assertSame(sudokuManager, accManager.getCurrentGameState("Sudoku"));
        accManager.setCurrentGameState(null);
        assertSame(null, accManager.getCurrentGameState("Sudoku"));
        assertSame(null, accManager.getCurrentGameState("abc"));
        assertSame(null, accManager.getCurrentGameState(null));
    }

    /**
     * Test whether set and getCurrentGame() works.
     */
    @Test
    public void testGetCurrentGame() {
        setUp();
        accManager.setCurrentGame("5X5sliding");
        assertSame(accManager.getCurrentGame(), "5X5sliding");

    }

    /**
     * Test whether get and updateUndoTime()(set user's undo time) works.
     */
    @Test
    public void testGetUserUndoTime() {
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        accManager.updateUndoTime(5);
        assertEquals(accManager.getUserUndoTime(), 5);
    }

    /**
     * Test whether getName() works.
     */
    @Test
    public void testUserGetName() {
        setUpAccountAndGame();
        assertEquals(testAccount.getName(), "207");
    }

    /**
     * Test whether addScore() works on sliding tile.
     */
    @Test
    public void testAddScoreSlidingTile() {
        setUpAccountAndGame();
        ScoringStrategy slidingTileStrategy = new SlidingTileStrategy(accManager);
        List<SlidingTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 25; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, tileNum));
        }
        SlidingTileBoard board = new SlidingTileBoard(tiles, 5);
        AbstractBoardManager boardManager = new SlidingTileBoardManager(board);
        accManager.addScore(slidingTileStrategy, 4, boardManager);
        assertEquals(true,
                6250 == accManager.getAccountMap().get("207").
                        getScores("5X5sliding"));
    }

    /**
     * Test whether addScore() works on 2048.
     */
    @Test
    public void testAddScore2048() {
        setUpAccountAndGame();
        ScoringStrategy the2048Strategy = new The2048Strategy(accManager);
        TofeTile[] tiles = new TofeTile[16];
        for (int i = 0; i < 16; i++) {
            tiles[i] = new TofeTile(4, i);
        }
        The2048BoardManager boardManager = new The2048BoardManager();
        boardManager.getBoard().setAllTiles(tiles);
        boardManager.move("column", false);
        accManager.addScore(the2048Strategy, 0, boardManager);
        assertEquals(true, 64 == accManager.getAccountMap().get("207").
                getScores("2048"));
    }

    /**
     * Test whether writeAcc() works
     */
    @Test
    public void testWriteAccountOutput() {
        setUp();
        accManager.writeAcc("CSC207", "hello", context);
        assertEquals("Username already taken!",
                accManager.writeAcc("CSC207", "hello", context));
        assertEquals("Field cannot be empty!",
                accManager.writeAcc("", "hello", context));
        assertEquals("Field cannot be empty!",
                accManager.writeAcc("a", "", context));
    }

    /**
     * Test whether makeToastGameState() works.
     */
    @Test
    public void testMakeToastTextGameState() {
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        AbstractBoardManager boardManager = new SlidingTileBoardManager(5);
        accManager.setCurrentGameState(boardManager);
        accManager.getCurrentGameState("sliding");
        assertEquals("Game save successfully loaded! Enjoy your game.",
                accManager.makeToastTextGameState());
        accManager.getCurrentGameState("2048");
        assertEquals("Game save not found!", accManager.makeToastTextGameState());
        accManager.getCurrentGameState("Sudoku");
        assertEquals("Game save not found!", accManager.makeToastTextGameState());
    }


}
