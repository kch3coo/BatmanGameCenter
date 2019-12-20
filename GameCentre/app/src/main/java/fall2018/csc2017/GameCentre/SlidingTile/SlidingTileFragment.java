package fall2018.csc2017.GameCentre.SlidingTile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fall2018.csc2017.GameCentre.Utility.FileSaver;
import fall2018.csc2017.GameCentre.GameCenterActivity;
import fall2018.csc2017.GameCentre.GameCenterButtonFragment;
import fall2018.csc2017.GameCentre.LoginActivity;
import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.UserAccManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlidingTileFragment extends GameCenterButtonFragment {

    /**
     * The board manager.
     */
    private SlidingTileBoardManager slidingTileBoardManager;

    /**
     * The account manager
     */
    private UserAccManager accManager;


    public SlidingTileFragment() {
        // Required empty public constructor
    }

    private int gridSizeSelected;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accManager = (UserAccManager) FileSaver.loadFromFile(getActivity(),
                LoginActivity.ACC_INFO);
        view = inflater.inflate(R.layout.fragment_sliding_tile, container, false);
        add3x3ButtonListener();
        add4x4ButtonListener();
        add5x5ButtonListener();
        return view;

    }

    /**
     * Activate the 3x3 game.
     */
    private void add3x3ButtonListener() {
        Button by3sliding = view.findViewById(R.id.by3sliding);
        by3sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridSizeSelected = 3;
                activateGame();
            }
        });

    }


    /**
     * Activate the 4x4 game.
     */
    private void add4x4ButtonListener() {
        Button by4sliding = view.findViewById(R.id.by4sliding);
        by4sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridSizeSelected = 4;
                activateGame();
            }
        });
    }

    /**
     * Activate the 5x5 game.
     */
    private void add5x5ButtonListener() {
        Button by5sliding = view.findViewById(R.id.by5sliding);
        by5sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridSizeSelected = 5;
                activateGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    @Override
    public void switchToGame() {
        Intent tmp = new Intent(getActivity(), SlidingTileGameActivity.class);
        tmp.putExtra("accManager", accManager);
        FileSaver.saveToFile(getActivity(), slidingTileBoardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Activate the gridSizeSelected size SlidingTile game.
     *
     */
    @Override
    public void activateGame() {
        accManager = (UserAccManager) FileSaver.loadFromFile(getActivity(),
                LoginActivity.ACC_INFO);
        slidingTileBoardManager = new SlidingTileBoardManager(gridSizeSelected);
        slidingTileBoardManager.getBoard().setMaxUndoTime(accManager.getUserUndoTime());
        switchToGame();
    }
}

