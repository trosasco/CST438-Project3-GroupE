package yourid.csumb.plantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPlant extends AppCompatActivity {

    private static final String TAG = "";
    EditText plantName;
    EditText plantLatin;
    EditText plantDifficulty;
    EditText plantLight;

    Button newPlant;
    Button cancelAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        plantName = findViewById(R.id.pName);
        plantLatin = findViewById(R.id.pLatin);
        plantDifficulty = findViewById(R.id.pDifficulty);
        plantLight = findViewById(R.id.pLight);

        newPlant = findViewById(R.id.addPlant);
        cancelAdd = findViewById(R.id.cancelAddition);

        newPlant.setOnClickListener(view -> addNewPlant());
        cancelAdd.setOnClickListener(view -> goBack());


    }


    public void addNewPlant(){
        String tempName = plantName.getText().toString();
        String tempLatin = plantLatin.getText().toString();
        String tempDiff = plantDifficulty.getText().toString();
        String tempLight = plantLight.getText().toString();

        PlantData newPlant = new PlantData(tempName, tempLatin, tempDiff, tempLight);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Create a new user with a first and last name
//        Map<String, Object> newPlant = new HashMap<>();
//        newPlant.put("first", "Ada");
//        newPlant.put("last", "Lovelace");
//        newPlant.put("born", 1815);

// Add a new document with a generated ID
        db.collection("Plants")
                .add(newPlant)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        Toast.makeText(this, "Plant has been successfully added", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void goBack(){
        finish();
    }

}