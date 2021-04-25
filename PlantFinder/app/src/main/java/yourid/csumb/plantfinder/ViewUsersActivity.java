package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class ViewUsersActivity extends AppCompatActivity {

    ListView mMainDisplay;

    Button mAddButton;

    AccountDao mAccountDAO;

    List<Account> mAccounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewusers);

        setTitle("View Users");

        //Check for intent extras
        Intent prevIntent = getIntent();
        prevIntent.getExtras();

        //Display toast based on the extra
        if(prevIntent.hasExtra("added")) {
            Toast.makeText(ViewUsersActivity.this, "User has been added.", Toast.LENGTH_SHORT).show();
        }
        if(prevIntent.hasExtra("edited")) {
            Toast.makeText(ViewUsersActivity.this, "User has been edited", Toast.LENGTH_SHORT).show();
        }

        //Wire up add user button
        mAddButton = findViewById(R.id.add_user_button);

        getDataBase();
        getUsers();

        AccountArrayAdapter adapter = new AccountArrayAdapter(this, R.layout.account_item, mAccounts);
        mMainDisplay = findViewById(R.id.userListView);
        mMainDisplay.setAdapter(adapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddAccountActivity.intentFactory(getApplicationContext());

                finish();
                startActivity(intent);
            }
        });
    }

    //Refreshes the display
    private void refreshDisplay() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    //Deletes a user from the table
    private void deleteItem(View view) {
        mAccountDAO.delete((Account)view.getTag());
        refreshDisplay();
    }

    //Sends admin to the edit user page
    public void editItem(View view) {

    }

    private void getUsers(){
        mAccounts = mAccountDAO.getAll();
    }

    public void getDataBase(){
        mAccountDAO = Room.databaseBuilder(this, AccountDatabase.class,AccountDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, ViewUsersActivity.class);
    }
}
