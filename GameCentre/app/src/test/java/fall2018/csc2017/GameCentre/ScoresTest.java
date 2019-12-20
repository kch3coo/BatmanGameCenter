package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * The unit test for Scores class.
 */
public class ScoresTest {

    /**
     * User account manager for testing.
     */
    private UserAccManager accManager;
    /**
     * Mocked context for testing.
     */
    private Context context = mock(Context.class);
    /**
     * Score instance for testing.
     */
    private Scores scores;

    /**
     * Set up the user account manager
     *
     * @param accManager the user account manager
     */
    private void setUp(UserAccManager accManager) {
        scores = new Scores("2048", accManager);
    }

    /**
     * Add 5 new players to account manager.
     */
    private void populateAccManagerNewPlayer() {
        for (char alphabet = 'A'; alphabet <= 'E'; alphabet++) {
            String value = Character.toString(alphabet);
            accManager.writeAcc(value, value, context);
        }
    }

    /**
     * Add five players (F - J) that played this game, and have scores from 0 to 4.
     */
    private void populateAccManagerPlayedPlayer() {
        accManager = new UserAccManager();
        int i = 0;
        for (char alphabet = 'F'; alphabet <= 'J'; alphabet++) {
            String value = Character.toString(alphabet);
            accManager.writeAcc(value, value, context);
        }
        for (String key : accManager.getAccountMap().keySet()) {
            accManager.getAccountMap().get(key).setScore("2048", i);
            i++;
        }
    }

    /**
     * test get score based user's name.
     */
    @Test
    public void testGetAccountNamesScores() {
        populateAccManagerPlayedPlayer();
        populateAccManagerNewPlayer();
        setUp(accManager);
        List<String> players = new ArrayList<>();
        for (char alphabet = 'J'; alphabet >= 'F'; alphabet--) {
            players.add(Character.toString(alphabet));
        }
        List<String> oldScores = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            oldScores.add(Integer.toString(i));
        }

        assertEquals(players, scores.getAccountNames());
        assertEquals(oldScores, scores.getAccountScores());
    }


}
