package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileFragment;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileGameActivity;
import fall2018.csc2017.GameCentre.Sudoku.SudokuFragment;
import fall2018.csc2017.GameCentre.Sudoku.SudokuGameActivity;
import fall2018.csc2017.GameCentre.The2048.The2048Fragment;
import fall2018.csc2017.GameCentre.The2048.The2048GameActivity;

/**
 * Game center activity's controller.
 */
public class GameCenterActivityController {

    /**
     * Decide which button fragment to be shown depending on what game it is.
     *
     * @param game the game currently playing
     * @return return the button fragment.
     */
    GameCenterButtonFragment switchFragment(String game) {
        if (game == null) {return null;}
        else {
            switch (game) {
                case GameSelectionActivity.GameSlidingTile:
                    GameCenterButtonFragment fragment0 = new SlidingTileFragment();
                    return fragment0;
                case GameSelectionActivity.Game2048:
                    GameCenterButtonFragment fragment1 = new The2048Fragment();
                    return fragment1;
                case GameSelectionActivity.GameSudoku:
                    GameCenterButtonFragment fragment2 = new SudokuFragment();
                    return fragment2;
                default:
                    GameCenterButtonFragment fragment4 = new SlidingTileFragment();
                    return fragment4;
            }
        }
    }

    /**
     * Start the game based on which game it is.
     *
     * @param game the game
     * @param context context
     * @param manager abstract board manager
     */
    void startGame(String game, Context context, AbstractBoardManager manager){
        if (game != null && manager != null){
            if (!game.contains("sliding")) {
                startNonSlidingTileGames(game, context);
            } else {
                context.startActivity(new Intent(context, SlidingTileGameActivity.class));
            }
        }
    }

    /**
     * Start the game if it's not sliding tile.
     * @param game the game
     * @param context the context
     */
    private void startNonSlidingTileGames(String game, Context context) {
        switch (game) {
            case "Sudoku":
                context.startActivity(new Intent(context, SudokuGameActivity.class));
                break;
            case "2048":
                context.startActivity(new Intent(context, The2048GameActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * Set settings button to be invisible if game is not sliding tile.
     * @param game the game
     * @param button the button
     */
    public void setSettingButton(String game, Button button){
        if(! game.contains(GameSelectionActivity.GameSlidingTile)){
            button.setVisibility(View.GONE);
        }
    }
}
