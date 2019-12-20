package fall2018.csc2017.GameCentre.Sudoku;


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
public class SudokuFragment extends GameCenterButtonFragment {

    /**
     * The Sudoku board manager.
     */
    private SudokuBoardManager boardManager;

    /**
     * The user account manager.
     */
    private UserAccManager accManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accManager = (UserAccManager) FileSaver.loadFromFile(getActivity(),
                LoginActivity.ACC_INFO);
        view = inflater.inflate(R.layout.fragment_sudoku, container, false);
        addSudokuButtonListener();
        return view;

    }

    /**
     * A required empty public constructor for fragment.
     */
    public SudokuFragment() {
        // Required empty public constructor
    }

    /**
     * Activate the Sudoku game.
     */
    private void addSudokuButtonListener() {
        Button startSudokuGame = view.findViewById(R.id.startSudoku);
        startSudokuGame.setOnClickListener(new View.OnClickListener() {
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
        Intent tmp = new Intent(getActivity(), SudokuGameActivity.class);
        tmp.putExtra("accManager", accManager);
        FileSaver.saveToFile(getActivity(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Activate the Sudoku game.
     */
    @Override
    public void activateGame() {
        boardManager = new SudokuBoardManager();
        switchToGame();
    }

    /**
     * The view.
     */
    private View view;



}
