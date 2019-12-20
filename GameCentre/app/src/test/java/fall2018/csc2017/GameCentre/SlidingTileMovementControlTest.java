package fall2018.csc2017.GameCentre;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fall2018.csc2017.GameCentre.SlidingTile.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SlidingTileBoardManager.class)
/**
 * Test sliding tile's movement control
 */
public class SlidingTileMovementControlTest {

    private int gridSize = 4;
    private SlidingTileMovementController movementController = new SlidingTileMovementController();

    @Mock
    private SlidingTileBoardManager boardManager;

    private int validTap = 2;
    private int invalidTap = 4;
    private int blankPosition = 1;


    /**
     * Setp up the board manager for test
     */
    @Before
    public void setup() {
        boardManager = PowerMockito.spy(new SlidingTileBoardManager(gridSize));

        movementController.setBoardManager(boardManager);
        when(boardManager.isValidTap(validTap)).thenReturn(true);
        when(boardManager.isValidTap(invalidTap)).thenReturn(false);
        when(boardManager.blankTilePosition()).thenReturn(1);
    }

    /**
     * Test a tap movement on a valid tap
     */
    @Test
    public void testValidTapMovement() {
        setup();
        movementController.processTapMovement(validTap);

        when(boardManager.blankTilePosition()).thenCallRealMethod();
        assertEquals(validTap, boardManager.blankTilePosition());

    }

    /**
     * Test a tap movement with a non-valid tap
     */
    @Test
    public void testInvalidTapMovement() {
        setup();
        SlidingTileBoard origin = boardManager.getBoard();
        movementController.processTapMovement(invalidTap);
        assertEquals(origin, boardManager.getBoard());
    }

    /**
     * Test a undo tap movement, the undo number should change
     */
    @Test
    public void testUndoTapMovement() {
        setup();
        movementController.processTapMovement(validTap);
        int originUndoNum = boardManager.getBoard().getMaxUndoTime();
        movementController.processTapMovement(blankPosition);
        int updatedUndoNum = boardManager.getBoard().getMaxUndoTime();
        assertEquals(originUndoNum, updatedUndoNum);

    }


    /**
     * Test a undo tap movement, when there is no more undo number
     */
    @Test
    public void testUndoTapWithNoMoreUndoNumberMovement() {
        setup();
        boardManager.getBoard().setMaxUndoTime(1);
        movementController.processTapMovement(validTap);
        movementController.processTapMovement(blankPosition);
        movementController.processTapMovement(blankPosition);
        movementController.processTapMovement(blankPosition);
        int noMoreUndo2 = boardManager.getBoard().getMaxUndoTime();
        assertEquals(0, noMoreUndo2);
    }

}
