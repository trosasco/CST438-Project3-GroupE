package yourid.csumb.plantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button edit;
    Button search;
    Button logout;

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

        edit = findViewById(R.id.editAcc);
        search = findViewById(R.id.search);
        logout = findViewById(R.id.logout);

        bottomNavigationView = findViewById(R.id.bottomNavigationView); //step 7


        setUpBottomNavigation();
        addAllFragmentOnce();

        edit.setOnClickListener(view -> editAccount());
        search.setOnClickListener(view -> searchPlants());
        logout.setOnClickListener(view -> signOut());
    }

    public void editAccount() {

    }

    public void searchPlants() {

    }

    public void signOut() {

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