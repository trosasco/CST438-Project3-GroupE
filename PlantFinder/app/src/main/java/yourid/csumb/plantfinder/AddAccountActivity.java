package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class AddAccountActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;

    Button mAddButton;

    AccountDao mAccountDAO;

    List<Account> mAccounts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);

        setTitle("Add an Account");

        getDataBase();
        getUsers();

        //Find user input fields
        mUsername = findViewById(R.id.editText_enterUsername);
        mPassword = findViewById(R.id.editText_enterPassword);

        //Wire up add user button
        mAddButton = findViewById(R.id.add_new_user_button);
        mAddButton.setOnClickListener(view -> {
            Account account = getValuesFromDisplay();

            //Insert user into the user database
            mAccountDAO.insert(account);

            //Reset user input fields
            mUsername.setText("");
            mPassword.setText("");

            //Go back to Show users activity
            Intent intent = ViewUsersActivity.intentFactory(getApplicationContext());
            String add = "added";
            intent.putExtra("added", add);
            finish();
            startActivity(intent);
        });
    }

    //Gets user input from the display
    private Account getValuesFromDisplay() {
        String name = "No name found.";
        String password = "password";

        name = mUsername.getText().toString();
        password = mPassword.getText().toString();

        //Create new user object
        return new Account(name, password, false);
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
        return new Intent(context, AddAccountActivity.class);
    }
}
