package fall2018.csc2017.GameCentre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * The controller that controls logic in login activity.
 */
public class LoginActivityController {

    /**
     * The listner that decide which class to switch to based on if account exists.
     *
     * @param email email from user input.
     * @param password password from user input.
     * @param accManager account manager.
     * @param context the context.
     * @return the class to switch to.
     */
    Class<? extends Activity> accountExistListener(String email, String password, UserAccManager
            accManager, Context context){
        if (accManager.accountExist(email, password)) {
            accManager.setCurrentUser(email);
            return GameSelectionActivity.class;
        } else {
            Toast.makeText(context, "Invalid password or email", Toast.LENGTH_SHORT).show();
            return LoginActivity.class;
        }
    }

    /**
     * Load the account manager based on if it's null or not.
     *
     * @param userAccManager user account manager.
     * @return a new user account manager if the loaded one is null, or the loaded one.
     */
    UserAccManager loadAccount(UserAccManager userAccManager) {
        if (userAccManager != null) {
            return userAccManager;
        } else {
            return new UserAccManager();
        }
    }
}
