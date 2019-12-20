package fall2018.csc2017.GameCentre.The2048;

import java.util.ArrayList;
import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * Class responsible for algorithm of adding numbers together and changing positions of tiles on
 * the board, i.e., merging rows or columns.
 */
public class Merge2048 {

    /**
     * The resulting list after merging the original row/column.
     */
    private TofeTile[] resultingList = new TofeTile[4];

    /**
     * The input list for merging.
     */
    private TofeTile[] inputArray;

    /**
     * The input list without removing blank tiles.
     */
    private TofeTile[] originalArray;
    /**
     * The position the next tile to be put in the resulting list
     */
    private int posInResult = 0;

    /**
     * The position the tile in the inputArray to be checked.
     */
    private int currentPosition = 0;


    /**
     * Initialize the list to be merged.
     * Precondition: the length of the inputArray should be 4.
     * Precondition: identity can only be "row" or "column".
     *
     * @param originalArray the array to be merged.
     */
    public Merge2048(TofeTile[] originalArray){
        this.originalArray = originalArray;
        this.inputArray = removingBlank(originalArray);
    }

    /**
     * Return an array of tile that contains all non-blank tile of the input array of tiles.
     *
     * @param inputArray input array of tiles
     * @return an array of tile without blank tile.
     */
    private static TofeTile[] removingBlank(TofeTile[] inputArray){
        ArrayList<TofeTile> temResult = new ArrayList<>();
        for (TofeTile t: inputArray){
            if(t.getValue() != 0)
                temResult.add(t);
        }
        TofeTile[] result = new TofeTile[temResult.size()];
        for (int i = 0; i < temResult.size(); i++){
            result[i] = temResult.get(i);
        }
        return result;
    }

    /**
     * Return the merged version of inputArray, which is resultingList after series of operation.
     *
     * @return the merged version of inputArray.
     */
    public TofeTile[] merge(){
        while (currentPosition < inputArray.length - 1){
            if(inputArray[currentPosition].getValue() == inputArray[currentPosition+1].getValue()){
                resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue()*2
                        , originalArray[posInResult].getId());
                currentPosition += 2;
                posInResult += 1;
            }else{
                resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue()
                        , originalArray[posInResult].getId());
                currentPosition += 1;
                posInResult += 1;
            }
        }
        this.addingToResultingList();
        return resultingList;
    }

    /**
     * Adding tiles to the resultingList if the posInResult is less than 4. Also add the last tile
     * in inputArray to the resultingList if needed.
     */
    private void addingToResultingList(){
        if (currentPosition == inputArray.length - 1){
            resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue()
                    , originalArray[posInResult].getId());
            posInResult += 1;
        }
        while (posInResult < 4){
            resultingList[posInResult] = new TofeTile(0, originalArray[posInResult].getId());
            posInResult += 1;
        }
    }

}
