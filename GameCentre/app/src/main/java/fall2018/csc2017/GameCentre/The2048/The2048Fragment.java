package fall2018.csc2017.GameCentre.The2048;


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
public class The2048Fragment extends GameCenterButtonFragment {


    public The2048Fragment() {
        // Required empty public constructor
    }


    /**
     * initialize the fragment
     */
    private The2048BoardManager boardManager;
    private UserAccManager accManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accManager = (UserAccManager) FileSaver.loadFromFile(getActivity(),
                LoginActivity.ACC_INFO);
        view = inflater.inflate(R.layout.fragment_the2048, container, false);
        add2048ButtonListener();
        return view;

    }


    /**
     * Activate the 2048 game.
     */
    private void add2048ButtonListener() {
        Button the2048 = view.findViewById(R.id.start2048);
        the2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    @Override
    public void switchToGame() {
        Intent tmp = new Intent(getActivity(), The2048GameActivity.class);
        tmp.putExtra("accManager", accManager);
        FileSaver.saveToFile(getActivity(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Activate the 2048 game.
     */
    @Override
    public void activateGame() {
        boardManager = new The2048BoardManager();
        switchToGame();
    }


    /**
     * View of the fragment
     */
    private View view;
}
