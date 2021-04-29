package yourid.csumb.plantfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        setTitle("Administrator Home");

        Button plantButton = (Button) findViewById(R.id.inventory_button);
        plantButton.setOnClickListener(view -> addPlant());

        Button userButton = (Button) findViewById(R.id.user_button);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewUsersActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    public void addPlant(){
        Intent intent = new Intent(this, AddPlant.class);
        startActivity(intent);
    }
}
