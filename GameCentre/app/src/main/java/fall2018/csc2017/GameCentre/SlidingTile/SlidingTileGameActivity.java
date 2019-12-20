package fall2018.csc2017.GameCentre.SlidingTile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCentre.CustomAdapter;
import fall2018.csc2017.GameCentre.Utility.FileSaver;
import fall2018.csc2017.GameCentre.GameActivityOverController;
import fall2018.csc2017.GameCentre.GameCenterActivity;
import fall2018.csc2017.GameCentre.LoginActivity;
import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.Strategies.SlidingTileStrategy;
import fall2018.csc2017.GameCentre.TileNamingInterface;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;
import fall2018.csc2017.GameCentre.tiles.Tile;

/**
 * The game activity.
 */
public class SlidingTileGameActivity extends AppCompatActivity implements Observer,
        TileNamingInterface {

    /**
     * The board manager.
     */
    private SlidingTileBoardManager boardManager;

    /**
     * The current sliding tile game name.
     */
    private String currentGame;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The user account manager
     */
    private UserAccManager userAccManager;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private SlidingTileGestureDetectGridView gridView;

    /**
     * column width and height device size
     */
    private static int columnWidth, columnHeight;

    /**
     * Activity controller when the game is solved
     */
    private GameActivityOverController gController;

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
        boardManager = (SlidingTileBoardManager) FileSaver.loadFromFile(getApplicationContext(),
                GameCenterActivity.TEMP_SAVE_FILENAME);
        gController = new GameActivityOverController();
        setUpBoard();
        createTileButtons(this);
        setContentView(R.layout.activity_slidingtile_game);

        // Add View to activity
        gridView = findViewById(R.id.grid);
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
        gridView.setUndoText("Undo left: " + boardManager.getBoard().getMaxUndoTime());
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
    private void setCurrentGameName() {
        String gridSize = Integer.valueOf(boardManager.getBoard().getNumCols()).toString();
        currentGame = gridSize + "X" + gridSize + "sliding";
    }

    /**
     * create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingTileBoard board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNumRows(); row++) {
            for (int col = 0; col != board.getNumCols(); col++) {
                Button tmp = new Button(context);
                UserAccount userAccount = userAccManager.getAccountMap().
                        get(userAccManager.getCurrentUser());
                if (userAccount.getImageType() == UserAccount.ImageType.Default) {
                    setImage(board, row, col, tmp);
                } else {
                    setTile(board, tmp, row, col);
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Set the single image based on row, column and board.
     *
     * @param board the sliding tile game board
     * @param row   the row
     * @param col   the column
     * @param tmp   the button
     */
    private void setImage(SlidingTileBoard board, int row, int col, Button tmp) {
        if (board.getTile(row, col).getId() == board.getBoardSize()) {
            tmp.setBackgroundResource(R.drawable.white);
        } else tmp.setBackgroundResource(Tile.FirstSlidingTileDefaultId +
                board.getTile(row, col).getId() - 1);
    }

    /**
     * create the name of a specific tile.
     *
     * @param numRows board's num rows
     * @param numCols board's num cols
     * @param tileId  tile's id
     * @return generated tile's file name
     */
    @Override
    @NonNull
    public String createTileName(int numRows, int numCols, int tileId) {
        String grid = numRows + "x" + numCols;
        return "tile_" + grid + "_" + tileId + ".png";
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SlidingTileBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            UserAccount userAccount = userAccManager.getAccountMap().get(userAccManager.
                    getCurrentUser());
            if (userAccount.getImageType() == UserAccount.ImageType.Default) {
                setImage(board, row, col, b);
            } else {
                setTile(board, b, row, col);
            }
            nextPos++;
        }
    }

    /**
     * Set all other tiles to their background image.
     *
     * @param board the game board.
     * @param b     the button
     * @param row   the row
     * @param col   the column
     */
    private void setTile(SlidingTileBoard board, Button b, int row, int col) {
        if (board.getTile(row, col).getId() == board.getBoardSize()) {
            b.setBackgroundResource(R.drawable.white);
        } else {
            int tileId = board.getTile(row, col).getId();
            String tileName = createTileName(boardManager.getBoard().getNumRows(),
                    board.getNumCols(), tileId);
            String path = getDataDir().getPath() + "/app_" +
                    userAccManager.getCurrentUser() + "/" + tileName;
            b.setBackground(Drawable.createFromPath(path));
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        FileSaver.saveToFile(getApplicationContext(), userAccManager,
                LoginActivity.ACC_INFO);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Save the user's game state according to the current game, to local storage.
     */
    private void saveToFile() {
        userAccManager.setCurrentGameState(boardManager);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
    }

    /**
     * Update the score of the current user if puzzle is solved.
     */
    public void onSolved() {
        SlidingTileStrategy strategy = new SlidingTileStrategy(userAccManager);
        userAccManager.addScore(strategy, boardManager.getBoard().getNumOfMoves(), boardManager);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
        gController.startOverControl(boardManager, getApplicationContext(), strategy, currentGame);
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


    @Override
    public void update(Observable o, Object arg) {
        saveToFile();
        display();
        onSolved();
    }
}
