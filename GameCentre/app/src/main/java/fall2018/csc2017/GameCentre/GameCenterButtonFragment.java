package fall2018.csc2017.GameCentre;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Button fragment for game center activity
 */
public abstract class GameCenterButtonFragment extends Fragment {

    //AbstractBoardManager boardManager;


    public GameCenterButtonFragment() {
        // Required empty public constructor
    }

    public abstract void activateGame();

    /**
     * Switch to the GameActivity view to play the game.
     */
    public abstract void switchToGame();


    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

}