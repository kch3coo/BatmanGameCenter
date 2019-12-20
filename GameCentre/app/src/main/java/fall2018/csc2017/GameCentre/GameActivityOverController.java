package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;

import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Utility.FileSaver;

/**
 * Controller that controls the logic of what to do after game is over.
 */
public class GameActivityOverController {

    /**
     * controls what to do after game is over.
     *
     * @param boardManager the board manager
     * @param context the context
     * @param strategy the strategy of adding score
     * @param game the current game that's been played
     */
    public void startOverControl(AbstractBoardManager boardManager, Context context, ScoringStrategy
            strategy, String game){
        if (boardManager.puzzleSolved()){
            Intent i = new Intent(context, GameOverActivity.class);
            i.putExtra("GAME", game);
            i.putExtra(GameOverActivity.GameOverMessageName,
                    "Your Score is: " + strategy.getScore());
            context.startActivity(i);
        }
    }
}
