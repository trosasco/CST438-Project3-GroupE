package yourid.csumb.plantfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button edit;
    Button search;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.editAcc);
        search = findViewById(R.id.search);
        logout = findViewById(R.id.logout);

        edit.setOnClickListener(view -> editAccount());
        search.setOnClickListener(view -> searchPlants());
        logout.setOnClickListener(view -> signOut());
    }

    public void editAccount(){

    }

    public void searchPlants(){

    }

    public void signOut(){

    }

}