package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Test the LoginActivityController class
 **/
public class LoginActivityControllerTest {

    /**
     * The LoginActivityController class for testing
     **/
    private LoginActivityController lController;

    /**
     * The Game selection activity
     **/
    private GameSelectionActivity gameSelectionActivity;

    /**
     * The account manager for testing
     **/
    private UserAccManager accManager;

    /**
     * The mocked context class
     **/
    private Context context = mock(Context.class);

    /**
     * Set up the login activity controller and the game selection activity
     **/
    private void setUp() {
        lController = new LoginActivityController();
        gameSelectionActivity = new GameSelectionActivity();
    }

    /**
     * Set up the user account manager
     */
    private void setUpUserAcc() {
        accManager = new UserAccManager();
        accManager.writeAcc("a", "a", context);
    }

    /**
     * Test the accountExistListener() method
     **/
    @Test
    public void testAccExistListener() {
        setUp();
        setUpUserAcc();
        assertEquals(gameSelectionActivity.getClass(),
                lController.accountExistListener("a", "a", accManager, context));
    }

    /**
     * Test the loadAccount() method
     **/
    @Test
    public void testLoadAccount() {
        setUp();
        setUpUserAcc();
        assertEquals(accManager, lController.loadAccount(accManager));
        assertEquals(UserAccManager.class,
                lController.loadAccount(null).getClass());
    }
}
