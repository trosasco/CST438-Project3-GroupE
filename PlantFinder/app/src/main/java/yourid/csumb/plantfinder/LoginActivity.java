package yourid.csumb.plantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

public class LoginActivity extends AppCompatActivity {

    private AccountDao accountDAO;
    List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getDataBase();
        getUsers();


        Button enterButton = findViewById(R.id.enter_button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tempNewUserName;
                EditText tempNewPassWord;
                String newUserName;
                String newPassWord;

                tempNewUserName = (EditText) findViewById(R.id.new_username);
                tempNewPassWord = (EditText) findViewById(R.id.new_password);

                newUserName = tempNewUserName.getText().toString();
                newPassWord = tempNewPassWord.getText().toString();

                //Verify that this isn't already a username

                Log.i("Check", "Checking!");

                //Move to Next Activity
                Intent newIntent = new Intent(getApplicationContext(), SearchPage.class);
                newIntent.putExtra("username", newUserName);
                //startActivity(newIntent);
            }
        });

    }

    private void getUsers(){
        accounts = accountDAO.getAll();
    }


    public void getDataBase(){
        accountDAO = Room.databaseBuilder(this, AccountDatabase.class, AccountDatabase.name)
                .allowMainThreadQueries()
                .build()
                .account();
    }

}
