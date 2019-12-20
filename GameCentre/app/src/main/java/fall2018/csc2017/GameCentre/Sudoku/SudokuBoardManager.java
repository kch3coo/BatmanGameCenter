package fall2018.csc2017.GameCentre.Sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fall2018.csc2017.GameCentre.AbstractBoardManager;
import fall2018.csc2017.GameCentre.Utility.ImageOperation;
import fall2018.csc2017.GameCentre.tiles.SudokuTile;
import fall2018.csc2017.GameCentre.tiles.Tile;

/**
 * The Sudoku board manager
 */
public class SudokuBoardManager extends AbstractBoardManager<SudokuBoard> {

    /**
     * The numbers list that are use to store 1 to 9.
     */
    private ArrayList<Integer> NUMBERS = new ArrayList<>();

    /**
     * Initialize the Sudoku board manager.
     *
     */
    public SudokuBoardManager(){
        for (int i = 1; i <= 9; i++) {
            NUMBERS.add(i);
        }
        List<SudokuTile> numbers = new ArrayList<>();
        randomAdd(numbers);
        setBoard(new SudokuBoard(numbers));
        solve();
        randomRemove();
    }
    /**
     * Another constructor that initialize Sudoku board manager by taking in a new board.
     *
     * @param board the Sudoku board.
     */
    public SudokuBoardManager(SudokuBoard board){
        setBoard(board);
    }

    /**
     * Return the string representation
     *
     * @return string representation
     */
    @Override
    public String toString(){
        return "Sudoku Board Manager";
    }

    /**
     * Randomly remove some of the tiles, and make them mutable.
     *
     */
    private void randomRemove(){
        Random random = new Random();
        for (int i = 0; i < getBoard().numTiles() / 2 + 1; i++){
            int randomInt = random.nextInt(getBoard().numTiles());
            int col = randomInt % getBoard().getNumCols();
            int row = randomInt / getBoard().getNumRows();
            getBoard().getTile(row, col).setValue(0);
            getBoard().getTile(row, col).setIsMutable(true);
        }
    }

    /**
     * Randomly add sudoku tile value from 1-9, into list of sudoku tiles.
     * @param tiles the list of tiles.
     */
    private void randomAdd(List<SudokuTile> tiles){
        for (int i = 0; i < 72; i++) {
            tiles.add(new SudokuTile(0, 0, false));
        }
        for (int i = 0; i < NUMBERS.size(); i++) {
            tiles.add(new SudokuTile(0, NUMBERS.get(i), false));
        }
        Collections.shuffle(tiles);
    }

    /**
     * A recursive solve function, that solves the board by adding and checking if the board
     * is solved.
     *
     * @return true if the board is solved, and false if it's not.
     */
    public boolean solve() {
        for (int row = 0; row < getBoard().getNumRows(); row++) {
            for (int col = 0; col < getBoard().getNumCols(); col++) {
                Boolean x = checkSolveAndValid(row, col);
                if (x != null) return x;
            }
        }
        return true;
    }

    /**
     * Second part of the recursive solve function.
     *
     * @param row the row of tile
     * @param col the column of tile
     * @return true if board is solved, false if it's not, and null if there's no tile left.
     */
    @Nullable
    private Boolean checkSolveAndValid(int row, int col) {
        if (getBoard().getTile(row, col).getValue() == 0) {
            for (Integer k : NUMBERS) {
                getBoard().getTile(row, col).setValue(k);
                if (isValid() && solve()) {
                    return true;
                }
                getBoard().getTile(row, col).setValue(0);
            }
            return false;
        }
        return null;
    }

    /**
     * Return whether the tap position is valid by checking if the change is available at such
     * position.
     *
     * @param position the tile to check
     * @return whether the tile is open to modify
     */
    public boolean isValidTap(int position) {
        int row = position / getBoard().getNumRows();
        int col = position % getBoard().getNumCols();
        return getBoard().getTile(row, col).getIsMutable();
    }

    /**
     * check if the board is valid, by checking if each column, row and 3x3 section is valid.
     * @return whether the board is valid.
     */
    public boolean isValid() {
        return isPartialValid(getBoard().horizontal()) &&
                isPartialValid(getBoard().vertical()) &&
                isPartialValid(getBoard().sectional());
    }

    /**
     * check if it's partial valid, by column, row, or section.
     *
     * @param part the way to iterate through the board.
     * @return whether it's partial valid.
     */
    private boolean isPartialValid(Iterable<SudokuTile> part) {
        int count = 0;
        int totalCount = 0;
        Set<Integer> tempSet = new HashSet<>();

        return  !(checkValid(part, count, totalCount, tempSet));
    }

    /**
     * Check if it's valid, by column, row, or section, serve as a helper function for
     * isPartialValid().
     *
     * @param part way to iterate through the board.
     * @param count count number of tiles in each section we have iterated through,
     *              reset after reaches 9.
     * @param totalCount total number of tiles we iterated,
     * @param tempSet the set that contains value of tiles, cannot have duplicate.
     * @return whether the board is valid by column, row, or section.
     */
    private boolean checkValid(Iterable<SudokuTile> part, int count,
                               int totalCount, Set<Integer> tempSet) {
        for (SudokuTile i: part){
            totalCount ++;
            if (i.getValue() != 0) {
                count++;
                tempSet.add(i.getValue());
                if (tempSet.size() != count){
                    return true;
                }
            }
            if (totalCount == getBoard().getNumRows()){
                totalCount = 0;
                count = 0;
                tempSet.clear();
            }
        }
        return false;
    }

    /**
     * Check if puzzle is solved.
     *
     * @return whether puzzle is solved.
     */
    public boolean puzzleSolved(){
        boolean filled = true;
        for (SudokuTile i: getBoard().horizontal()){
            if (i.getValue() == 0){filled = false;}
        }
        return isValid() && filled;
    }

    /**
     * Decide what will happen if a touch is applied at position.
     *
     * @param position the position of touch.
     */
    public void touchMove(int position){
        int row = position / getBoard().getNumRows();
        int col = position % getBoard().getNumCols();
        getBoard().incrementTile(row, col);
    }
}
