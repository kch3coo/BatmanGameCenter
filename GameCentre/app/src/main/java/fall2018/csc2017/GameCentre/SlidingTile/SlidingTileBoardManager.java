package fall2018.csc2017.GameCentre.SlidingTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCentre.AbstractBoardManager;
import fall2018.csc2017.GameCentre.tiles.SlidingTile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingTileBoardManager extends AbstractBoardManager<SlidingTileBoard>{

    /**
     * Manage a new shuffled board.
     */
    public SlidingTileBoardManager(int gridSize) {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = gridSize * gridSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, gridSize));
        }

        Collections.shuffle(tiles);
        while (!solvable(tiles, gridSize)){
            Collections.shuffle(tiles);
       }
        setBoard(new SlidingTileBoard(tiles, gridSize));
    }

    /**
     * Manage a board that has been pre-populated.
     * @param slidingTileBoard the board
     */
    public SlidingTileBoardManager(SlidingTileBoard slidingTileBoard) {
        setBoard(slidingTileBoard);
    }



    /**
     * Return an boolean to check if the game could be solved.
     * Cite from https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
     *
     * @param tile the list to check
     * @return true if it could; otherwise, false
     */
    private boolean solvable(List<SlidingTile> tile, int gridSize){
        if (gridSize % 2 == 1){
            return  (inversion(tile) % 2 == 0);
        }else{
            return (inversion(tile) % 2 + blankFromBottom(tile, gridSize) % 2 == 1);
        }
    }

    /**
     * Return the blank Tile position from bottom (need by solvable).
     *
     * @param tile the list to check
     * @param gridSize the row and col number
     * @return the int blank Tile position from bottom which need by solvable
     */
    private int blankFromBottom(List<SlidingTile> tile, int gridSize) {
        int answer = 0;
        int blankId = tile.size();
        int totalTile = tile.size();
        for (int r = 0; r != totalTile; r++) {
            if (tile.get(r).getId() == blankId){
                answer = gridSize - r/gridSize;
                break;
            }
        }
        return answer;
    }

    /**
     * Return an inversion (need by solvable).
     *
     * @param tile the list to check
     * @return the int named inversion which need by solvable
     */
    private int inversion(List<SlidingTile> tile){
        int blankId = tile.size();
        int totalTile = tile.size();
        int i = 0;
        for (int r = 0; r != totalTile; r++) {
            if (tile.get(r).getId() != blankId){
                for (int rr = r+1; rr != totalTile; rr++) {
                    if (tile.get(r).getId() > tile.get(rr).getId()) {
                        i = i + 1;
                    }
                }
            }
        }
        return i;
    }

    /**
     * Return the string representation
     *
     * @return string representation
     */
    @Override
    public String toString(){
        return "Sliding Tile Board Manager";
    }


    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;

        int tileId = 1;
        for (SlidingTile tile: getBoard()) {
            solved = (tile.getId() == tileId);
            tileId++;
            if (!solved) break;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int row = position / getBoard().getNumRows();
        int col = position % getBoard().getNumCols();
        int blankId = getBoard().numTiles();
        SlidingTile above = row == 0 ? null : getBoard().getTile(row - 1, col);
        SlidingTile below = row == getBoard().getNumRows() - 1 ? null :
                getBoard().getTile(row + 1, col);
        SlidingTile left = col == 0 ? null : getBoard().getTile(row, col - 1);
        SlidingTile right = col == getBoard().getNumCols() - 1 ? null :
                getBoard().getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Find and return an array of the row and col of blankTile.
     *
     * @return An int array of the row and col of blankTile.
     */
    private int[] blankTilePos(){
        int blankId = getBoard().numTiles();
        int row1 = 0;
        int col1 = 0;
        for (int r = 0; r != getBoard().getNumRows(); r++) {
            for (int c = 0; c != getBoard().getNumCols(); c++) {
                if (getBoard().getTile(r, c).getId() == blankId) {
                    row1 = r;
                    col1 = c;
                }
            }
        }
        return new int[]{row1, col1};
    }

    /**
     * Upon receiving a valid tap, switch the position of the
     * blank tile and the tile at position.
     *
     * @param position the position
     */
    public void touchMove(int position, boolean ifUndo) {
            int row = position / getBoard().getNumRows();
            int col = position % getBoard().getNumCols();
            int[] position2 = blankTilePos();
            if (!ifUndo) {
                moveHistory();
                getBoard().increaseNumOfMoves();
            }
            getBoard().swapTiles(row, col, position2[0], position2[1]);
    }

    /**
     * get the exactly position of method blankTilePos().
     *
     * @return An int blankTile position.
     */
    public int blankTilePosition(){
        return blankTilePos()[0] * getBoard().getNumRows() + blankTilePos()[1];
    }

    /**
     * the method will be used in Class MovementController.
     *
     */
    public void undo(){
        touchMove(getBoard().historyStack.pop(), true);
        getBoard().setMaxUndoTime(getBoard().getMaxUndoTime()-1);
    }

    /**
     * every time when user makes a move, historyStack will add the blankTile position.
     *
     */
    private void moveHistory(){
        getBoard().historyStack.push(blankTilePosition());
    }
}