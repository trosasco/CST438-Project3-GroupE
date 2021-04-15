package yourid.csumb.plantfinder;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewProfile extends AppCompatActivity {

    private TextView profileName;
    private TextView profileBio;
    private TextView posts;
    private Button editProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Plant Finder - Profile Page");
        wireupDisplay();
    }

    public void wireupDisplay() {
        profileName = findViewById(R.id.topTextView);
        profileBio = findViewById(R.id.textViewBio);
        posts = findViewById(R.id.text_view_result);
        editProfile = findViewById(R.id.editProfileButton);
    }
}
