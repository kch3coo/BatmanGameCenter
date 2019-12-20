package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc2017.GameCentre.Utility.FileSaver;

/**
 * The GameOverActivity, that activate after game is over.
 */
public class GameOverActivity extends AppCompatActivity {

    /**The message for game over**/
    static final String GameOverMessageName = "GameOverMessage";

    /**The current game**/
    private String currentGame;

    /**The message to display**/
    private String displayMessage;

    /**
     * Initiate the display message and back to game center button
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        currentGame = getIntent().getStringExtra("GAME");
        displayMessage = getIntent().getStringExtra(GameOverMessageName);
        displayMessage();
        addGameCenterButtonListener();
    }

    /**
     * Delete the previous activity when back button is pressed.
     */
    @Override
    public void onBackPressed(){
        getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    /**
     * Go back to game center Button.
     */
    private void addGameCenterButtonListener() {
        Button gameCenterButton = findViewById(R.id.goBackGameCenter);
        gameCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOverActivity.this,
                        GameCenterActivity.class);
                i.putExtra("GAME", currentGame);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }

    /**
     * Display the message of what game it is and what the score is.
     */
    private void displayMessage(){
        TextView msg= findViewById(R.id.displayMsg);
        msg.setText(displayMessage);
    }

}
