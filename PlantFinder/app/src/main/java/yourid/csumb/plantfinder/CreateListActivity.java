package yourid.csumb.plantfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.PlantList;
import yourid.csumb.plantfinder.model.PlantListDao;

public class CreateListActivity extends AppCompatActivity {

    //Empty Parameters such as ListName and the plant names
    private EditText tempNewPlantListName;
    private EditText tempNewPlantList;
    private String convertedList;

    private Button createPlantList;

    private PlantList mPlantList;
    private PlantListDao mPlantListDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plant_list);

        createPlantList();

    }

    public void createPlantList() {

        tempNewPlantListName = (EditText) findViewById(R.id.new_listName);
        tempNewPlantList = (EditText) findViewById(R.id.new_plants);


        createPlantList = findViewById(R.id.enter_button);
        createPlantList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertedList = new Gson().toJson(tempNewPlantList);



            }
        });
    }


}
