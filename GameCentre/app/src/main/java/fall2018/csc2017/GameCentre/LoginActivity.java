package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fall2018.csc2017.GameCentre.Utility.FileSaver;


/**
 * The login activity.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * The email EditText
     */
    private EditText mEmailView;

    /**
     * The password EditText
     */
    private EditText mPasswordView;

    /**
     * The file that stores accounts information
     */
    public static final String ACC_INFO = "acc_save.ser";

    /**
     * The account manager
     */
    public UserAccManager accManager;

    /**
     * The login activity controller
     */
    private LoginActivityController lController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lController = new LoginActivityController();
        mEmailView = findViewById(R.id.emailEdit);
        mPasswordView = findViewById(R.id.passwordEdit);
        loadAccounts();
        addLoginButtonListener();
        addRegisterButtonListener();
    }

    /**
     * Load the UserAccManager.
     */
    private void loadAccounts(){
        UserAccManager tempManager = (UserAccManager)FileSaver.loadFromFile
                (getApplicationContext(), ACC_INFO);
        accManager = lController.loadAccount(tempManager);
        FileSaver.saveToFile(getApplicationContext(), accManager, ACC_INFO);
    }

    /**
     * Activate login button
     */
    private void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(), ACC_INFO);
                Intent intent = new Intent(LoginActivity.this,
                        lController.accountExistListener(mEmailView.getText().toString(),
                                mPasswordView.getText().toString(), accManager,
                                getApplicationContext()));
                intent.putExtra("accountManager", accManager);
                FileSaver.saveToFile(getApplicationContext(), accManager, LoginActivity.ACC_INFO);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }


    /**
     * Activate register button
     */
    private void addRegisterButtonListener(){
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(), ACC_INFO);
                writeAccount(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
        });
    }

    /**
     * Write new account into UserAccManager and save it into local storage
     * If the username is already registered, then cannot register this account again.
     *
     * @param email email/username
     * @param password password of the user
     */

    private void writeAccount(String email, String password) {
        String toastMsg = accManager.writeAcc(email, password, getApplicationContext());
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        FileSaver.saveToFile(getApplicationContext(), accManager
                , LoginActivity.ACC_INFO);
    }


}