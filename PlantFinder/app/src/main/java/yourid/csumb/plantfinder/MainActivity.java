package yourid.csumb.plantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;

import android.view.MenuItem;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button create;
    Button search;
    Button login;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String USER_ID_KEY = "yourid.csumb.plantfinder.model.userIdKey";

    private AccountDao mAccountDAO;

    private int mUserId = -1;

    private SharedPreferences mPreference = null;

    private HomeFragment home = new HomeFragment();
    private SearchFragment searchPlants = new SearchFragment();
    private ProfileFragment profile = new ProfileFragment();

    private Fragment activeFragment = null;
    private FragmentManager manager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataBase();
        checkForUser();

        create = findViewById(R.id.createAcc);
        search = findViewById(R.id.search);
        login = findViewById(R.id.login);

        bottomNavigationView = findViewById(R.id.bottomNavigationView); //step 7


        setUpBottomNavigation();
        addAllFragmentOnce();

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

    private void setUpBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        activeFragment = home;
    }

    private void addAllFragmentOnce() {

        manager.beginTransaction()
                .add(R.id.flFragment, activeFragment)
                .commit();

        manager.beginTransaction()
                .add(R.id.flFragment, searchPlants)
                .hide(searchPlants)
                .commit();

        manager.beginTransaction()
                .add(R.id.flFragment, profile)
                .hide(profile)
                .commit();

    }

    private void showHideFragment(Fragment fragment) {
        manager.beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                clearBackStack();
                showHideFragment(home);
                activeFragment = home;
                break;

            case R.id.search:
                clearBackStack();
                showHideFragment(searchPlants);
                activeFragment = searchPlants;
                break;

            case R.id.person:
                clearBackStack();
                showHideFragment(profile);
                activeFragment = profile;
                break;

        }
        return true;
    }


    private void clearBackStack() {
        FragmentManager fm = this.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
}