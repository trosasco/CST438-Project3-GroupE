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

public class EditUsersActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    Button mEditButton;

    AccountDao mAccountDAO;
    Account mAcc;
    List<Account> mAccounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editusers);

        setTitle("Edit Users");

        getDataBase();
        getUsers();

        mAcc = (Account) getIntent().getSerializableExtra("view");

        //Wire up edit text fields
        mUsername = findViewById(R.id.editText_enterNewUsername);
        mPassword = findViewById(R.id.editText_enterNewPassword);

        //Set username and password
        mUsername.setText(mAcc.getUserName());
        mPassword.setText(mAcc.getUserPassword());

        //Wire up edit user button
        mEditButton = findViewById(R.id.edit_users_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcc.setUserName(mUsername.getText().toString());
                mAcc.setUserPassword(mPassword.getText().toString());

                editUser();

                //Add extra to indicate that the user was edited
                Intent intent = ViewUsersActivity.intentFactory(getApplicationContext());
                String edit = "edited";
                intent.putExtra("edited", edit);
                finish();
                startActivity(intent);
            }
        });
    }

    private void editUser() { mAccountDAO.update(mAcc); }

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
        return new Intent(context, EditUsersActivity.class);
    }
}
