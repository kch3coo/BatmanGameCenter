package fall2018.csc2017.GameCentre.The2048;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCentre.CustomAdapter;
import fall2018.csc2017.GameCentre.Utility.FileSaver;
import fall2018.csc2017.GameCentre.GameActivityOverController;
import fall2018.csc2017.GameCentre.GameCenterActivity;
import fall2018.csc2017.GameCentre.LoginActivity;
import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Strategies.The2048Strategy;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * Game activity for 2048
 */
public class The2048GameActivity extends AppCompatActivity implements Observer{

    /**
     * The board manager.
     */
    private The2048BoardManager boardManager;

    /**
     * The current sliding tile game name.
     */
    private String currentGame;

    /**
     * The buttons to display.
     */
    private ArrayList<ImageView> tileButtons;

    /**
     * The user account manager
     */
    private UserAccManager userAccManager;

    // Grid View and calculated column height and width based on device size
    /**
     * Grid View
     */
    private The2048GestureDetectGridView gridView;

    /**
     * width and column of board in game activity
     */
    private static int columnWidth, columnHeight;

    /**
     * controller for the game
     */
    private GameActivityOverController gController;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        gridView.UpdateScore();
    }

    /**
     * Initialize the activity
     * @param savedInstanceState the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = (The2048BoardManager) FileSaver.loadFromFile(getApplicationContext(),
                GameCenterActivity.TEMP_SAVE_FILENAME);
        gController = new GameActivityOverController();
        setUpBoard();

        createTileButtons(this);
        setContentView(R.layout.activity_the2048_main);
        // Add View to activity

        gridView = findViewById(R.id.the2048_grid);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getNumCols();
                        columnHeight = displayHeight / boardManager.getBoard().getNumRows();

                        display();
                    }
                });
        addUndoButton();
    }

    /**
     * Set up the board and account manager.
     */
    private void setUpBoard() {
        currentGame = "2048";
        userAccManager = (UserAccManager) FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        userAccManager.setCurrentGame(currentGame);
    }

    /**
     * create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        The2048Board the2048Board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                ImageView tmp = new ImageView(context);
                tmp.setBackgroundResource(the2048Board.getTile(row, col).getDrawableId());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Save the game state and account state when game activity is about to close.
     */
    @Override
    protected void onStop() {
        super.onStop();
        FileSaver.saveToFile(getApplicationContext(), userAccManager,
                LoginActivity.ACC_INFO);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        The2048Board the2048Board = boardManager.getBoard();
        int nextPos = 0;
        for (ImageView b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            b.setBackgroundResource(the2048Board.getTile(row, col).getDrawableId());
            //b.setBackground(Drawable.createFromPath(the2048Board.getTile(row, col).getBackground()));
            nextPos++;
        }
    }

    /**
     * Save the user's game state according to the current game, to local storage.
     */
    private void saveToFile(){
        userAccManager.setCurrentGameState(boardManager);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
    }

    /**
     * Update the score of the current user if puzzle is solved.
     */
    public void onSolved(){
        ScoringStrategy strategy = new The2048Strategy(userAccManager);
        userAccManager.addScore(strategy, 0, boardManager);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
        gController.startOverControl(boardManager, getApplicationContext(), strategy, currentGame);
    }

    /**
     * update the activity when needed
     * @param o object being changed
     * @param arg argument passed along with object being changed
     */
    @Override
    public void update(Observable o, Object arg) {
        saveToFile();
        display();
        TofeTile[] currentTiles = boardManager.getBoard().getAllTiles();
        System.out.println(Arrays.equals(currentTiles, boardManager.getBoard().merge("row",
                true)));
        onSolved();
    }

    /**
     * add undo button to the board
     */
    private void addUndoButton(){
        Button undoButton = findViewById(R.id.the2048UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.processUndo();
            }
        });
    }
}
