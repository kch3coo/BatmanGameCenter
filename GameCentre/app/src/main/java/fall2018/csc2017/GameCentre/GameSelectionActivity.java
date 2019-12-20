package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Game selection activity
 */
public class GameSelectionActivity extends AppCompatActivity {

    /**
     * Game's names.
     */
    public static final String GameSudoku = "Sudoku";
    public static final String GameSlidingTile = "sliding";
    public static final String Game2048 = "2048";

    /**
     * The slide view pager.
     */
    private ViewPager mSlideViewPager;

    /**
     * The selection adapter.
     */
    private GameSelectionAdapter selectionAdapter;

    /**
     * Initiate the logOut button and set the pager for all games.
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);

        selectionAdapter = new GameSelectionAdapter(this);

        mSlideViewPager.setAdapter(selectionAdapter);

        logOut();
    }


    /**
     * Activate the log out button.
     */
    private void logOut(){
        ImageButton logOutButton =findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GameSelectionActivity.this,
                       LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
            }
        });
    }

}
