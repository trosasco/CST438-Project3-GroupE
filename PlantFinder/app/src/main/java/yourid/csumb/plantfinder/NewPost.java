package yourid.csumb.plantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class NewPost extends AppCompatActivity {

    EditText caption;
    TextView imgName;

    Button selectImg;
    Button cancel;
    Button upload;

    ImageView IVPrevImg;

    private Uri filePath;

    int SELECT_PICTURE = 200;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore fsdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        fsdb = FirebaseFirestore.getInstance();

        caption = findViewById(R.id.caption);
        imgName = findViewById(R.id.imageName);

        selectImg = findViewById(R.id.selectImage);
        IVPrevImg = findViewById(R.id.imageView);
        upload = findViewById(R.id.addPlant);
        cancel = findViewById(R.id.cancelAddition);

        selectImg.setOnClickListener(view -> selectImage());
        cancel.setOnClickListener(view -> goBack());
        upload.setOnClickListener(view -> uploadToFirebase());
    }
    public void goBack(){
        finish();
    }

    public void uploadToFirebase(){
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            // Progress Listener for loading
// percentage on the dialog box
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {

                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast
                                        .makeText(NewPost.this,
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            })

                    .addOnFailureListener(e -> {

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast
                                .makeText(NewPost.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int)progress + "%");
                            });
        }
    }

    void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                filePath = data.getData();
                imgName.setText(filePath.toString());
                if (null != filePath) {
                    // update the preview image in the layout
                    IVPrevImg.setImageURI(filePath);
                }
            }
        }
    }
}