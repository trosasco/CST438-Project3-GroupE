package yourid.csumb.plantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button create;
    Button search;
    Button login;

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
                Intent startIntent = new Intent(getApplicationContext(), CreateAccountActivity.class);
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

    private void setUpBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        activeFragment = home;
    }

    private void addAllFragmentOnce() {

        manager.beginTransaction()
                .add(R.id.userFragment, activeFragment)
                .commit();

        manager.beginTransaction()
                .add(R.id.userFragment, searchPlants)
                .hide(searchPlants)
                .commit();

        manager.beginTransaction()
                .add(R.id.userFragment, profile)
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

            case R.id.account:
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