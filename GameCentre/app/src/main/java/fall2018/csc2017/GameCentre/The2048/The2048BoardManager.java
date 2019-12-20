package fall2018.csc2017.GameCentre.The2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import fall2018.csc2017.GameCentre.AbstractBoardManager;
import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class The2048BoardManager extends AbstractBoardManager<The2048Board> {

    /**
     * Number of tiles for this board specifically.
     */
    private int numOfTiles = 16;

    /**
     * The stack which keeps track of the previous board.
     */
    private Stack<TofeTile[]> historyStack = new Stack<>();

    /**
     * The stack which keeps track of the previous add score.
     */
    private Stack<Integer> scoreStack = new Stack<>();


    /**
     * Manage a new shuffled board.
     */
    private int[] beginBoardList() {
        int[] boardNumber = new int[16];
        int set1 = (int) (Math.random() * 16);
        int set2 = (int) (Math.random() * 16);
        while (set1 == set2) {
            set2 = (int) (Math.random() * 16);
        }
        boardNumber[set1] = ((int) (Math.random() * 2) + 1) * 2;
        boardNumber[set2] = ((int) (Math.random() * 2) + 1) * 2;
        return boardNumber;
    }

    /**
     * Create a new The2048BoardManager
     */
    public The2048BoardManager() {
        int[] boardNumber = beginBoardList();
        List<TofeTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numOfTiles; tileNum++) {
            if (boardNumber[tileNum] == 2) {
                tiles.add(new TofeTile(2, tileNum));
            } else if (boardNumber[tileNum] == 4) {
                tiles.add(new TofeTile(4, tileNum));
            } else {
                tiles.add(new TofeTile(0, tileNum));
            }
        }
        setBoard(new The2048Board(tiles));
    }

    /**
     * Move the board based on rOrC and inverted
     * @param rOrC moving horizontally or vertically
     * @param inverted up vs down; left vs right
     * Precondition: rOrC can only be row or column
     */
    public void move(String rOrC, boolean inverted){
        scoreStack.push(getBoard().getScore());
        historyStack.push(getBoard().getAllTiles());
        TofeTile[] mergedList = getBoard().merge(rOrC, inverted);
        addScore(mergedList);
        getBoard().setAllTiles(getBoard().generateNewTile(mergedList));
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    public boolean puzzleSolved() {
        TofeTile[] currentTiles = getBoard().getAllTiles();
        return(Arrays.equals(currentTiles, getBoard().merge("row", true)) &&
                Arrays.equals(currentTiles, getBoard().merge("row", false)) &&
                Arrays.equals(currentTiles, getBoard().merge("column", true)) &&
                Arrays.equals(currentTiles, getBoard().merge("column", false)));
    }

    /**
     * Return the string representation
     *
     * @return string representation
     */
    @Override
    public String toString(){
        return "2048 Board Manager";
    }

    /**
     * add score from previous board to merged board
     * @param mergeTiles The array of TofeTiles (after the merge method in board class)
     */
    private void addScore(TofeTile[] mergeTiles){
        TofeTile[] previous = historyStack.peek();
        int score = getBoard().getScore();
        if (count(mergeTiles, 0) > count(previous, 0)){
            int i = max(mergeTiles);
            int difference = 0;
            while (i >= 4){
                difference = count(mergeTiles, i) - count(previous, i) + 2 * difference;
                if (difference > 0){
                    score = score + difference * i;
                    getBoard().setScore(score);
                }
                i = i/2;
            }
        }

    }

    /**
     * get the maximum int from a array TofeTiles
     * @param mergeTiles The array of TofeTiles (after the merge method in board class)
     * @return the maximum int from a array TofeTiles
     */
    private int max(TofeTile[] mergeTiles){
        int theMax = mergeTiles[0].getValue();
        int size = mergeTiles.length;
        for (int i = 1; i < size; i++){
            if (mergeTiles[i].getValue() > theMax){
                theMax = mergeTiles[i].getValue();
            }
        }
        return theMax;
    }

    /**
     * count the number from a array TofeTiles
     * @param mergeTiles The array of TofeTiles (after the merge method in board class)
     * @param number the number we want to count
     * @return return count
     */
    private int count(TofeTile[] mergeTiles, int number){
        int total = 0;
        int size = mergeTiles.length;
        for (int i = 0; i < size; i++){
            if (mergeTiles[i].getValue() == number){
                total = total + 1;
            }
        }
        return total;
    }

    /**
     * the method will be used in Class MovementController.
     */
    public void undo() {
        if(!historyStack.empty() && !puzzleSolved()) {
            getBoard().setScore(Math.max(scoreStack.pop() - 100, 0));
            getBoard().setAllTiles(historyStack.pop());
        }
    }
}