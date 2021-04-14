package yourid.csumb.plantfinder;
import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.AccountDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    private Account account;
    private AccountDao accountDAO;
    List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getDataBase();
        getUsers();
        createAccount();
    }

    private void getUsers(){
        accounts = accountDAO.getAll();
    }

    public void getDataBase(){
        accountDAO = Room.databaseBuilder(this, AccountDatabase.class,AccountDatabase.name)
                .allowMainThreadQueries()
                .build()
                .account();
    }

    public void createAccount(){

        EditText tempNewUserName;
        EditText tempNewPassWord;
        String newUserName;
        String newPassWord;

        tempNewUserName = (EditText) findViewById(R.id.new_username);
        tempNewPassWord = (EditText) findViewById(R.id.new_password);

        newUserName = tempNewUserName.getText().toString();
        newPassWord = tempNewPassWord.getText().toString();

        Button enter = findViewById(R.id.enter_button);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;

                //Checking for Duplicate Usernames
                for(int i = 0; i < accountDAO.getAll().size(); i++){
                    if(accounts.get(i).getUserName().equals(newUserName)){
                        check = true;
                        break;
                    }
                }

                if(!check)
                {
                    //New Username
                    accountDAO.addAccount(new Account(newUserName, newPassWord));
                    Toast.makeText(getApplicationContext(), "Account Created!!!", Toast.LENGTH_SHORT).show();
                    //when account is created, it takes you back to the main activity
                    Intent i = new Intent(getApplicationContext(), SearchPage.class);
                    startActivity(i);
                }
                else
                {
                    //Duplicate Username
                    Toast.makeText(getApplicationContext(), "Duplicate Username Detected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
