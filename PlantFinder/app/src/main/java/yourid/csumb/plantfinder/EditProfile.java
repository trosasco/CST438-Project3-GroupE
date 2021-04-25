package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class EditProfile extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    EditText bio;
    Button saveButton;

    private String newBio;
    private String mUsername;

    private AccountDao mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile Page");

        //Set shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String user = sharedPreferences.getString(USERNAME, "");
        mUsername = user;

        getDataBase();
        getUsers();
        getUserByUsername();

        bio = findViewById(R.id.editTextBio);
        saveButton = findViewById(R.id.saveButton);

        bio.setText(mAccount.getBio());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
                mAccount.setBio(newBio);

                mAccountDAO.update(mAccount);

                Intent intent = ViewProfile.intentFactory(getApplicationContext());
                finish();
                startActivity(intent);
            }
        });
    }

    private void getInput() {
        newBio = bio.getText().toString();
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

    public static Intent intentFactory(Context context) {
        return new Intent(context, EditProfile.class);
    }
}
