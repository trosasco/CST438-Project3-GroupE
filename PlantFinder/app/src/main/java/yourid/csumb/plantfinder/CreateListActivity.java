package yourid.csumb.plantfinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.PlantList;
import yourid.csumb.plantfinder.model.PlantListDao;

public class CreateListActivity extends AppCompatActivity {

    //Empty Parameters such as ListName and the plant names
    private EditText tempNewPlantListName;
    private EditText tempNewPlantList;
    private String PlantListName;
    private String convertedList;


    private Button createPlantList;

    private PlantList mPlantList;
    private PlantListDao mPlantListDao;
    //How to get userID?

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";

    private AccountDao mAccountDAO;
    private Account mAccount;
    List<Account> mAccounts;
    private String mUsername;
    private int mUserID;

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
                PlantListName = tempNewPlantListName.getText().toString();
                convertedList = new Gson().toJson(tempNewPlantList);

                //Set shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                String user = sharedPreferences.getString(USERNAME, "");
                mUsername = user;
                getUserByUsername();

                mUserID = mAccount.getUserId();

                PlantList plantList = new PlantList(mUserID, PlantListName, convertedList);

                mPlantList = plantList;
                mPlantListDao.insert(mPlantList);
                Toast.makeText(getApplicationContext(), "Plant List Created!", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void getUserByUsername() {mAccount = mAccountDAO.getUserByUsername(mUsername); }




}
