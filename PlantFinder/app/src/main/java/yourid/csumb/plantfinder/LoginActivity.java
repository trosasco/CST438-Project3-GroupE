package yourid.csumb.plantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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

                Intent newIntent = new Intent(getApplicationContext(), SearchPage.class);
                newIntent.putExtra("username", newUserName);
                startActivity(newIntent);
            }
        });

    }
}
