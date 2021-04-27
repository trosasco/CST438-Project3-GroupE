package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;

public class SearchPage extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String USER_ID_KEY = "yourid.csumb.plantfinder.model.userIdKey";

    private SharedPreferences mPreference = null;

    AccountDao mAccountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //Enter in Key word
        //Hit Button to Search
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText keyWords = findViewById(R.id.search_entry);

            //Redirect to another page or display posts on the same page?


            }

        });
    }

    public static Intent intentFactory(Context context, int userId) {
        return new Intent(context, SearchPage.class);
    }
}
