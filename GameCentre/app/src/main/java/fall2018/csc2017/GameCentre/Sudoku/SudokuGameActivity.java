package fall2018.csc2017.GameCentre.Sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCentre.CustomAdapter;
import fall2018.csc2017.GameCentre.Utility.FileSaver;
import fall2018.csc2017.GameCentre.GameActivityOverController;
import fall2018.csc2017.GameCentre.GameCenterActivity;
import fall2018.csc2017.GameCentre.LoginActivity;
import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Strategies.SudokuStrategy;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.Utility.ImageOperation;
import fall2018.csc2017.GameCentre.tiles.Tile;

/**
 * The game activity.
 */
public class SudokuGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SudokuBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The current sliding tile game name.
     */
    private String currentGame;


    // Grid View and calculated column height and width based on device size
    private SudokuGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The game activity controller.
     */
    private GameActivityOverController gController;

    /**
     * The user account manager
     */
    private UserAccManager userAccManager;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
        // Add View to activity
        gridView = findViewById(R.id.sudoku_grid);
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
    }

    /**
     * Set up the board and game with required information.
     */
    private void setUp() {
        boardManager = (SudokuBoardManager) FileSaver.loadFromFile(getApplicationContext(),
                GameCenterActivity.TEMP_SAVE_FILENAME);
        gController = new GameActivityOverController();
        setUpBoard();
        createTileButtons(this);
        setContentView(R.layout.activity_sudoku_main);
    }

    /**
     * Set up the board and account manager.
     */
    private void setUpBoard() {
        setCurrentGameName();
        userAccManager = (UserAccManager) FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        userAccManager.setCurrentGame(currentGame);
    }

    /**
     * A method that set the game name based on gridSize.
     */
    private void setCurrentGameName(){
        currentGame = "Sudoku";
    }

    /**
     * create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SudokuBoard board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                Button tmp = new Button(context);
                Bitmap updated = ImageOperation.getUpdatedSudokuTileBackground(context,
                        board.getTile(row, col));
                tmp.setBackground(new BitmapDrawable(getResources(), updated));
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
        SudokuBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            Bitmap updated = ImageOperation.getUpdatedSudokuTileBackground(this,
                    board.getTile(row, col));
            b.setBackground(new BitmapDrawable(getResources(), updated));
            nextPos++;
        }
    }

    /**
     * Update the score of the current user if puzzle is solved.
     */
    public void onSolved(){
        ScoringStrategy strategy = new SudokuStrategy(userAccManager);
        userAccManager.addScore(strategy, 0, boardManager);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
        gController.startOverControl(boardManager, getApplicationContext(), strategy, currentGame);
    }

    /**
     * Save the board manager to fileName.
     */
    public void saveToFile() {
        userAccManager.setCurrentGameState(boardManager);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
    }

    @Override
    public void update(Observable o, Object arg) {
        saveToFile();
        display();
        onSolved();
    }
}
