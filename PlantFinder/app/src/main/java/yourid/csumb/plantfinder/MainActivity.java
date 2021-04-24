package yourid.csumb.plantfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class MainActivity extends AppCompatActivity {

    Button create;
    Button search;
    Button login;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String USER_ID_KEY = "yourid.csumb.plantfinder.model.userIdKey";

    private AccountDao mAccountDAO;

    private int mUserId = -1;

    private SharedPreferences mPreference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataBase();
        checkForUser();

        create = findViewById(R.id.createAcc);
        search = findViewById(R.id.search);
        login = findViewById(R.id.login);

        create.setOnClickListener(view -> createAccount());
        search.setOnClickListener(view -> searchPlants());
        login.setOnClickListener(view -> signIn());
    }

    public void createAccount(){
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(startIntent);
            }
        });
    }

    public void searchPlants(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(startIntent);
            }
        });
    }

    public void signIn(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);
            }
        });
    }

    public void getDataBase(){
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    //Checks for a logged in user, sets default users
    private void checkForUser() {
        List<Account> accounts = mAccountDAO.getAll();

        //Check if there are any accounts, create default and admin accounts if not
        if(accounts.size() <= 0) {
            Account defaultUser = new Account("default", "default", false);
            Account admin1 = new Account("admin", "password", true);
            mAccountDAO.insert(defaultUser);
            mAccountDAO.insert(admin1);
        }

        //Check if there is a user in the intent
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        if(mUserId != -1) {
            return;
        }

        //Gets sharedPreferences
        if(mPreference == null) {
            SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        }
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        mUserId = sharedPreferences.getInt(USER_ID_KEY, -1);
        if(mUserId == -1) {
            return;
        }

        Intent intent = SearchPage.intentFactory(this, mUserId);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

}