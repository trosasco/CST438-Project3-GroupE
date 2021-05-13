package yourid.csumb.plantfinder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import yourid.csumb.plantfinder.model.Account;
import yourid.csumb.plantfinder.model.AccountDao;
import yourid.csumb.plantfinder.model.PlantListDatabase;

public class SearchPage extends AppCompatActivity {
    public static final String TAG = "";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String USER_ID_KEY = "yourid.csumb.plantfinder.model.userIdKey";

    private SharedPreferences mPreference = null;

    Button search;
    AccountDao mAccountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search = findViewById(R.id.search_button);
        search.setOnClickListener(view -> AddResults());
        //Enter in Key word
        //Hit Button to Search

    }








    public void AddResults() {
        EditText keywords = findViewById((R.id.search_entry));
        PlantData newPlant = new PlantData(keywords.toString(),  "", "", "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Plants").document(keywords.toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        Log.d(TAG, "found!" + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public static Intent intentFactory(Context context, int userId) {
        return new Intent(context, SearchPage.class);
    }
}
