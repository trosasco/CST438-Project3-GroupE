package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class ViewProfile extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    private TextView profileName;
    private TextView profileBio;
    private Button editProfile;

    private Button lists;
    private Button posts;
    private Button deleteAccount;

    private AccountDao mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;
    private String mUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Plant Finder - Profile Page");
        wireupDisplay();
        getDataBase();
        getUsers();

        //Set shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");
        mUsername = user;
        getUserByUsername();

        String profilePage = user + "'s Profile Page";
        profileName.setText(profilePage);

        profileBio.setText(returnBio(mAccount));

        editProfile.setOnClickListener(v -> {
            Intent intent = EditProfile.intentFactory(getApplicationContext());
            finish();
            startActivity(intent);
        });

        deleteAccount.setOnClickListener(v -> {
            AlertDialog.Builder popUp = new AlertDialog.Builder(ViewProfile.this);
            popUp.setMessage("You are about to delete your account. Are you sure?");

            popUp.setPositiveButton("Yes", (dialog, which) -> {
                mAccountDAO.delete(mAccount);

                Toast.makeText(ViewProfile.this, user + " deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mAccount.getUserId());
                startActivity(intent);
            });

            popUp.setNegativeButton("Cancel", (dialog, which) -> finish());

            popUp.show();
        });
    }

    public void wireupDisplay() {
        profileName = findViewById(R.id.topTextView);
        profileBio = findViewById(R.id.textViewBio);
        editProfile = findViewById(R.id.editProfileButton);
        lists = findViewById(R.id.listsButton);
        posts = findViewById(R.id.postsButton);
        deleteAccount = findViewById(R.id.deleteAccount);
    }

    private void getUsers(){
        mAccounts = mAccountDAO.getAll();
    }

    public void getDataBase(){
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    private void getUserByUsername() {mAccount = mAccountDAO.getUserByUsername(mUsername); }

    private String returnBio(Account acc) {
        if (acc.getBio() == null) {
            acc.setBio("Bio not added");
        }
        return acc.getBio();
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, ViewProfile.class);
    }
}
