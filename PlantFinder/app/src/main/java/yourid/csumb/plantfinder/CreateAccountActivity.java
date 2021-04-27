package yourid.csumb.plantfinder;
import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

import android.content.Context;
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

public class CreateAccountActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String ADMIN = "admin";

    private String mUsername;
    private String mPassword;

    private EditText usernameInput;
    private EditText passwordInput;
    private Button createAccount;

    private Account mAccount;
    private AccountDao mAccountDAO;

    List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Check", "onCreate: HELLO");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getDataBase();
        getUsers();

        createAccount();
        checkDatabase();
    }

    private void getUsers(){
        accounts = mAccountDAO.getAll();
    }

    public void getDataBase(){
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class,AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    public void createAccount(){

        usernameInput = (EditText) findViewById(R.id.new_username);
        passwordInput = (EditText) findViewById(R.id.new_password);

        createAccount = findViewById(R.id.enter_button);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();

                //Check for username in the DB
                if(checkUsername())
                {
                    //Check if password field is empty
                    if(!validatePassword()) {
                        Toast.makeText(CreateAccountActivity.this, "Password is not long enough.", Toast.LENGTH_SHORT).show();
                    } else {
                        Account account = new Account(mUsername, mPassword, false);
                        mAccount = account;
                        mAccountDAO.insert(account);
                        Toast.makeText(getApplicationContext(), "Account Created!!!", Toast.LENGTH_SHORT).show();

                        //Set shared preferences for user
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(USERNAME, usernameInput.getText().toString());
                        editor.putBoolean(ADMIN, mAccount.isAdmin());
                        editor.apply();

                        //when account is created, it takes you back to the main activity
                        Intent i = HomePageActivity.intentFactory(getApplicationContext(), mAccount.getUserId());
                        startActivity(i);
                    }

                }
            }
        });

    }

    private void getValuesFromDisplay() {
        mUsername = usernameInput.getText().toString();
        mPassword = passwordInput.getText().toString();
    }

    //Check for Duplicates
    private boolean checkUsername(){
        mAccount = mAccountDAO.getUserByUsername(mUsername);

        if(mAccount == null)
        {
            return true;
        }
        Toast.makeText(this, "Username " + mUsername + " is taken.", Toast.LENGTH_SHORT).show();
        return false;

    }

    private boolean validatePassword() {return mPassword.length() > 0;}

    //Check Stuff
    private void checkDatabase(){
        for(int i = 0; i < accounts.size(); i++){
            Log.d("User", accounts.get(i).getUserName());
        }
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, CreateAccountActivity.class);
    }

}
