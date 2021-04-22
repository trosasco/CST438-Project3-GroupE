package yourid.csumb.plantfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //Enter in Key word
        //Hit Button to Search
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText keyWords = findViewById(R.id.search_entry);

            //Redirect to another page or display posts on the same page?
                TextView results = (TextView) findViewById(R.id.results);
                results.setText(keyWords.getText().toString());

            }

        });
    }
}
