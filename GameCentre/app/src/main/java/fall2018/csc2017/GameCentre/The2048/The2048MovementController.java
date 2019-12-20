package fall2018.csc2017.GameCentre.The2048;

import android.content.Context;

/**
 * 2048 game's movement controller
 */
public class The2048MovementController {

    /**
     * The boardManager.
     */
    private The2048BoardManager boardManager = null;

    /**
     * Set the boardManager.
     *
     * @param boardManager the board manager
     */
    public void setBoardManager(The2048BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process the movement for each slides.
     *
     * @param direction      either "row" or "col", "row" means horizontal movenment, "col" means vertical movement
     * @param directionValue deciding vector, if "row" is left or right, and if "col" is up or down.
     */
    public void processMovement(String direction, boolean directionValue) {
        boardManager.move(direction, directionValue);
    }

    /**
     * Process the undo movement for each tap.
     */
    void processUndo() {
        boardManager.undo();
    }

}
