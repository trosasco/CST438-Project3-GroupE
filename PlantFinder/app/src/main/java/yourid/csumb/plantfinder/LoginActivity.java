package yourid.csumb.plantfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class LoginActivity extends AppCompatActivity {

    //Variables for shared preferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String ID = "id";
    public static final String ADMIN = "admin";

    private String mUsername;
    private String mPassword;

    private EditText tempNewUserName;
    private EditText tempNewPassWord;
    private Button loginButton;

    private AccountDao mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Plant Finder - Login Page");

        getDataBase();
        getUsers();


        loginButton = findViewById(R.id.enter_button);
        loginButton.setOnClickListener(view -> {

            tempNewUserName = findViewById(R.id.new_username);
            tempNewPassWord = findViewById(R.id.new_password);

            getValuesFromDisplay();

            //Verify that this isn't already a username

            Log.i("Check", "Checking!");

            //Check for user, true if they exist in the DB
            if(checkForUserInDatabase())
            {
                //Check if password matches
                if(!validatePassword()) {
                    Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                } else {
                    //Set shared preferences to this user
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(USERNAME, mUsername);
                    editor.putInt(ID, mAccount.getUserId());
                    editor.putBoolean(ADMIN, mAccount.isAdmin());
                    editor.apply();

                    Intent intent = HomePageActivity.intentFactory(getApplicationContext(), mAccount.getUserId());
                    startActivity(intent);
                }
            }

        });

    }

    private void getValuesFromDisplay() {
        mUsername = tempNewUserName.getText().toString();
        mPassword = tempNewPassWord.getText().toString();
    }

    private boolean checkForUserInDatabase() {
        mAccount = mAccountDAO.getUserByUsername(mUsername);

        if (mAccount == null) {
            Toast.makeText(this, "No user " + mUsername + " found.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validatePassword() { return mAccount.getUserPassword().equals(mPassword); }

    private void getUsers(){
        mAccounts = mAccountDAO.getAll();
    }


    public void getDataBase(){
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

}
