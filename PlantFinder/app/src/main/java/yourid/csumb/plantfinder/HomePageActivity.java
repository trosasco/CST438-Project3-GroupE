package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class HomePageActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String ADMIN = "admin";
    public static final String USER_ID_KEY = "yourid.csumb.plantfinder.model.userIdKey";

    private SharedPreferences mPreference = null;
    private Account mAccount;
    private int mUserId = -1;

    TextView mUsername;

    AccountDao mAccountDAO;

    List<Account> mAccounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        setTitle("Welcome to Plant Finder!");

        Intent prevIntent = getIntent();
        prevIntent.getExtras();

        //Set shared preferences
        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");
        boolean admin = sharedPreferences.getBoolean(ADMIN, false);

        //Set username display
        TextView showUser = (TextView) findViewById(R.id.showUsername);
        showUser.setText(user);

        getDataBase();
        mAccount = mAccountDAO.getUserByUsername(user);

        Button adminButton = (Button) findViewById(R.id.adminButton);

        if (admin) {
            adminButton.setVisibility(View.VISIBLE);
        }
        else
        {
            adminButton.setVisibility(View.INVISIBLE);
        }

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> {
            Intent intent = SearchPage.intentFactory(getApplicationContext(), mAccount.getUserId());
            startActivity(intent);
        });

        adminButton.setOnClickListener(view -> {
            Intent intent = AdminActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });

        Button profileButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            Intent intent = ViewProfile.intentFactory(getApplicationContext());
            startActivity(intent);
        });

        //Create List
        Button createListButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(v -> {
            //Intent intent = CreateListActivity.intentFactory(this,user);
            //startActivity(intent);
        });

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            clearUserFromIntent();
            clearUserFromPreference();
            mUserId = -1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //Resets the SharedPreferences
            editor.clear().apply();

            Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(startIntent);
        });
    }

    //Clear user on logout
    private void clearUserFromIntent() {
        getIntent().removeExtra(USER_ID_KEY);
    }

    //Clear user
    private void clearUserFromPreference() {
        addUserToPreference(-1);
    }

    //Add user to shared preferences
    private void addUserToPreference(int userId) {
        if(mPreference == null) {
            mPreference = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    public void getDataBase(){
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    public static Intent intentFactory(Context context, int userId) {
        return new Intent(context, HomePageActivity.class);
    }
}
