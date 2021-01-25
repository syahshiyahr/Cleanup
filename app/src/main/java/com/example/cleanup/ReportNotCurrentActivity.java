package com.example.cleanup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ReportNotCurrentActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST = 1;
    private static final int PICK_IMAGE = 1;
    int PLACE_PICKER_REQUEST = 999;
    private TextInputLayout edtLevel, edtLocation;
    private FusedLocationProviderClient fusedLocationClient;
    MaterialButton btnTakePhoto, btnSend, btnGallery;
    ImageView ivPhotos, btnBack;
    ProgressBar progressBar;

    String loc;
    String dateTime;
    double latitude, longitude;

    Uri image_uri;
    private StorageReference referense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_current);

        edtLevel = findViewById(R.id.til_level_polution);
        edtLocation = findViewById(R.id.til_location);

        btnSend = findViewById(R.id.btn_send_report);
        btnBack = findViewById(R.id.ic_back);

        btnTakePhoto = findViewById(R.id.btn_take_picture);
        btnGallery = findViewById(R.id.btn_gallery);
        ivPhotos = findViewById(R.id.iv_photos);

        edtLevel = findViewById(R.id.til_level_polution);
        progressBar = findViewById(R.id.progress);

        referense = FirebaseStorage.getInstance().getReference();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, 1000);


                    }else {
                        openCamera();
                    }
                }else {

                }
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, 1000);


                    }else {
                        openGallery();
                    }
                }else {

                }
            }
        });
        

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, LEVEL);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.et_level_polution);
        textView.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        dateTime = simpleDateFormat.format(calendar.getTime());

        sendToDatabase();

        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_place_api));

        edtLocation.getEditText().setFocusable(false);
        edtLocation.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(ReportNotCurrentActivity.this);
                startActivityForResult(intent, 100);
            }
        });
    }


    private void openGallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);


    }

    private void sendToDatabase() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empty = "Please fill this field";

                String levelPolution = edtLevel.getEditText().getText().toString().trim();

                if (levelPolution.isEmpty()){
                    edtLevel.setError(empty);
                }else if(image_uri == null){
                    Toast.makeText(ReportNotCurrentActivity.this, "Please take a picture first", Toast.LENGTH_SHORT).show();
                }else if(!levelPolution.isEmpty() && image_uri != null){
                    progressBar.setVisibility(View.VISIBLE);
                    StorageReference filepath = referense.child("report_image").child(image_uri.getLastPathSegment());
                    filepath.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloaduri = uri;
                                    String id = FirebaseDatabase.getInstance().getReference().push().getKey();
                                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("timestamp", dateTime);
                                    map.put("latLng", latitude+","+longitude);
                                    map.put("location", loc);
                                    map.put("levelPolution", levelPolution);
                                    map.put("image", downloaduri.toString());
                                    map.put("idReport", id);
                                    map.put("idUser", user_id);
                                    map.put("status","Reported");

                                    FirebaseDatabase.getInstance().getReference().child("report").push().setValue(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ReportNotCurrentActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressBar.setVisibility(View.GONE);
                                            Intent in = new Intent(ReportNotCurrentActivity.this, SuccessActivity.class);
                                            startActivity(in);
                                            finish();
                                        }
                                    });

                                }
                            });
                        }
                    }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ReportNotCurrentActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1001) {
            ivPhotos.setImageURI(image_uri);
        }else if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            image_uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                ivPhotos.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            edtLocation.getEditText().setText(place.getAddress());
            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;
            loc = place.getAddress();
        }else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private static final String[] LEVEL = new String[]{
            "Type 1", "Type 2", "Type 3"
    };

    private static final String[] LOCATION = new String[]{
            "Current Location", "Search Location .."
    };

}