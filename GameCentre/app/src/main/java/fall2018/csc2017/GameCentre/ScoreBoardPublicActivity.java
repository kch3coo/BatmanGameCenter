package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * The public score board activity
 */
public class ScoreBoardPublicActivity extends AppCompatActivity {

    /*The view pager used for displaying scores*/
    private ViewPager mSlideViewPager;

    /*The score adapter that loads the score board information to the page.*/
    private ScoreAdapter selectionAdapter;

    /*The the name of game center that enters the score board*/
    private String currentGame;

    /**
     *
     * Activate backGameCenter button and set the Pager for score board.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_public);

        mSlideViewPager = findViewById(R.id.scorePage);

        selectionAdapter = new ScoreAdapter(this);

        mSlideViewPager.setAdapter(selectionAdapter);
        currentGame = getIntent().getStringExtra("GAME");
        backGameCenter();
    }

    /**
     *Activate the back button, goes bac to game center of the currentGame
     */
    private void backGameCenter(){
        ImageButton logOutButton =findViewById(R.id.backGameCenter);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ScoreBoardPublicActivity.this,
                        GameCenterActivity.class);
                i.putExtra("GAME", currentGame);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
            }
        });
    }
}
