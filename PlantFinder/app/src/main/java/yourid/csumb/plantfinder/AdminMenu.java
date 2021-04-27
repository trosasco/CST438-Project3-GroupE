package yourid.csumb.plantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMenu extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeFragment home2 = new HomeFragment();
    private SearchFragment searchPlants2 = new SearchFragment();
    private ProfileFragment profile2 = new ProfileFragment();

    private Fragment activeFragment2 = null;
    private FragmentManager manager2;
    BottomNavigationView bottomNavigationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        bottomNavigationView2 = findViewById(R.id.bottomNavigationView); //step 7


        setUpBottomNavigation();
        addAllFragmentOnce();

    }

    private void setUpBottomNavigation() {
        bottomNavigationView2.setOnNavigationItemSelectedListener(this);
        manager2 = getSupportFragmentManager();
        activeFragment2 = home2;
    }

    private void addAllFragmentOnce() {

        manager2.beginTransaction()
                .add(R.id.adminFragment, activeFragment2)
                .commit();

        manager2.beginTransaction()
                .add(R.id.adminFragment, searchPlants2)
                .hide(searchPlants2)
                .commit();

        manager2.beginTransaction()
                .add(R.id.adminFragment, profile2)
                .hide(profile2)
                .commit();

    }

    private void showHideFragment(Fragment fragment) {
        manager2.beginTransaction()
                .hide(activeFragment2)
                .show(fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home2:
                clearBackStack();
                showHideFragment(home2);
                activeFragment2 = home2;
                break;

            case R.id.search2:
                clearBackStack();
                showHideFragment(searchPlants2);
                activeFragment2 = searchPlants2;
                break;

            case R.id.account2:
                clearBackStack();
                showHideFragment(profile2);
                activeFragment2 = profile2;
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