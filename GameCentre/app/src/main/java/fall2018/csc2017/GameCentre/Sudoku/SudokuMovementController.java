package fall2018.csc2017.GameCentre.Sudoku;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.GameCentre.AbstractBoardManager;
import fall2018.csc2017.GameCentre.GameMovementController;

/**
 * Sudoku's movement controller
 */
public class SudokuMovementController implements GameMovementController {

    /**
     * The boardManager.
     */
    private SudokuBoardManager boardManager = null;

    /**
     * Set the boardManager.
     *
     * @param boardManager the board manager
     */
    public void setBoardManager(AbstractBoardManager boardManager) {
        this.boardManager = (SudokuBoardManager) boardManager;
    }

    /**
     * Process the movement for each tap on position.
     *
     * @param context  the current context
     * @param position the tapped position
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            processValidTap(context, position);
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * process the valid tap.
     *
     * @param context  the current context
     * @param position the position tapped
     */
    private void processValidTap(Context context, int position) {
        boardManager.touchMove(position);
        if (boardManager.puzzleSolved()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
    }
}
