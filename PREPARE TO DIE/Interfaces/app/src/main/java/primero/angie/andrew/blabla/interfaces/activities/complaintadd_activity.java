package primero.angie.andrew.blabla.interfaces.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.tumblr.remember.Remember;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import okhttp3.ResponseBody;
import primero.angie.andrew.blabla.interfaces.API.Api;
import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;
import primero.angie.andrew.blabla.interfaces.clases.Location;
import primero.angie.andrew.blabla.interfaces.clases.Picture;
import primero.angie.andrew.blabla.interfaces.clases.RandomS;
import primero.angie.andrew.blabla.interfaces.clases.createComplaint;
import primero.angie.andrew.blabla.interfaces.clases.receiveComplaint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static primero.angie.andrew.blabla.interfaces.clases.Complaint.complaint;

/**
 * Created by Dell on 22/10/2017.
 */public class complaintadd_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText title;
    private EditText description;
    private Button create;
    private ImageButton location;
    private ImageView imagePicker;
    private GoogleApiClient mGoogleApiClient;
    private String locationName;
    private Uri fileUri;
    private ArrayList<Uri> cameraUris;
    private ArrayList<Uri> globalUri = null;
    private ArrayList<String> firebaseUrl;
    private StorageReference mStorage;
    private ArrayList<String> generateUrls;

    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        requestPermissions();
        Log.i("1", "onCreate");
        cameraUris = new ArrayList<>();

        Log.i("2", "onCreate");
        mStorage = FirebaseStorage.getInstance().getReference();
        Log.i("3", "onCreate");
        initViews();
        initActions();

    }

    /**
     * To init views on variables
     */
    private void requestPermissions() {
        Log.i("1", "requestPermissions");
        TedPermission.with(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Log.i("2", "requestPermissions");
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Log.i("3", "requestPermissions");
                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
                Log.i("4", "requestPermissions");
    }

    public void cleanObjects(){
        firebaseUrl = null;
        globalUri = null;
    }

    private void initViews() {
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        create = (Button) findViewById(R.id.create);
        location = (ImageButton) findViewById(R.id.location);
        imagePicker = (ImageView) findViewById(R.id.imagePicker);
    }

    private void initImagePicker (){
        Log.i("1", "initImagePicker");
        ArrayList<Image> images = new ArrayList<>();
        Log.i("2", "initImagePicker");
        ImagePicker.with(this)                         //  Initialize ImagePicker with activity or fragment context
                .setToolbarColor("#212121")         //  Toolbar color
                .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                .setProgressBarColor("#4CAF50")     //  ProgressBar color
                .setBackgroundColor("#212121")      //  Background color
                .setCameraOnly(false)               //  Camera mode
                .setMultipleMode(true)              //  Select multiple images or single image
                .setFolderMode(true)                //  Folder mode
                .setShowCamera(true)                //  Show camera button
                .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
                .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
                .setDoneTitle("Done")               //  Done button title
                .setLimitMessage("You have reached selection limit")    // Selection limit message
                .setMaxSize(5)                     //  Max images can be selected
                .setSavePath("ImagePicker")         //  Image capture folder name
                .setSelectedImages(images)          //  Selected images
                .setKeepScreenOn(true)              //  Keep screen on when selecting images
                .start();                           //  Start ImagePicker
        Log.i("3", "initImagePicker");
        }

    /**
     * Logic button action
     */
    private void initActions() {

        Log.i("1", "initActions");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.name_required , Toast.LENGTH_LONG).show();
                } else if (description.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.description_required , Toast.LENGTH_LONG).show();
                } else if (globalUri != null) {
                    //To create a Dialog with circular progress
                    upload(globalUri);

                } else {
                    Toast.makeText(complaintadd_activity.this, "No olvides elegir una imagen", Toast.LENGTH_SHORT).show();
                }

            }
        });

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImagePicker();
            }
        });
    }



    private void upload(final ArrayList<Uri> file) {
        Log.i("1", "upload");
        StorageReference riversRef = null;
        generateUrls = new ArrayList<>();
        Log.i("2", "upload");
        //To iterate the ArrayList
        ListIterator<Uri> iterator = file.listIterator();
        Log.i("3", "upload");

        //To advance a position
        while (iterator.hasNext()){
            //set error to false

            //To generate a random string and build a route
            riversRef = mStorage.child("images/" + RandomS.generateString() + ".jpg");
            Log.i("4", "upload");

            //Upload a file in Firebase for each iteration
            riversRef.putFile(iterator.next())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            generateUrls.add(taskSnapshot.getDownloadUrl().toString());
                            Log.i("5", "upload");
                            //Validate the size of the uri and the publics urls  of firebase
                            if(generateUrls.size() == file.size()){
                                Log.i("6", "upload");
                                create(generateUrls);
                                Log.i("7", "upload");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //To create a Dialog with circular progress
                            Log.i("Failure", "upload");
                        }
                    });
        }
    }


    private void create(final ArrayList<String> url) {

        //Ubicación estática
        Location location = new Location();
        location.setLng(15.8);
        location.setLat(15.4);

        //Objeto con los datos necesarios para crear una denuncia
        createComplaint complaint = new createComplaint();
        complaint.setTitle(title.getText().toString());
        complaint.setDescription(description.getText().toString());
        complaint.setCategoryId(1);
        complaint.setLocation(location);
        complaint.setCreatedAt("2017-11-08T05:49:16.827Z");
        complaint.setEnabled(true);

        Log.i("Hasta aquí llegué", Remember.getString("access_token","U62AKJWJcZD8cPW8TCZ3lmc0h8hOmSyLQrAWxmAMqWQMsmrg5659GnVaKhfuU8Eu"));

        /**
         * The complaint is created by accessing the createComplaint method found in the API class,
         * This method receives 2 parameters that were sent at the end through the HTTP POST request.
         * The first is the access token, and the second is the Complaint object that contains all the data of
         * the complaint.
         */

        Call<receiveComplaint> call = Api.instance().createComplaint( Remember.getString("access_token","U62AKJWJcZD8cPW8TCZ3lmc0h8hOmSyLQrAWxmAMqWQMsmrg5659GnVaKhfuU8Eu"), complaint);
        call.enqueue(new Callback<receiveComplaint>() {
            @Override
            public void onResponse(Call<receiveComplaint> call, Response<receiveComplaint> response) {
                if (response.body() != null) {
                    Log.i("Debug: ", "ID" + response.body().getId());
                    uploadPictures(url, response.body().getId());
                    Toast.makeText(getApplicationContext(), "Denuncia subida!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("Error del codigo", response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<receiveComplaint> call, Throwable t) {
            }
        });
    }

    private void addImageView(Uri file) {
        imagePicker.setImageURI(file);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "@string/Error", Toast.LENGTH_SHORT).show();
    }

    //To open places picker
    private void displayPlacePicker() throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(complaintadd_activity.this), PLACE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            // do your logic here...
            ArrayList<Uri> uri = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                uri.add(Uri.fromFile(new File(images.get(i).getPath())));
                addImageView(uri.get(0));
                globalUri = uri;
            }
        }

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                locationName = String.format("%s", place.getName());
            }
        }
    }

    private void uploadPictures(ArrayList<String> url, final int id) {
        //set error to false
        error = false;
        //To  iterate the ArrayList of urls
        ListIterator<String> iterator = url.listIterator();
        while (iterator.hasNext()) {
            final Picture picture = new Picture();
            picture.setTitle("Imagen de la denuncia");
            picture.setUrl(iterator.next());
            picture.setComplaintId(id);
            picture.setEnabled(true);

            Call<Picture> call = Api.instance().createPictures(Remember.getString("access_token", "U62AKJWJcZD8cPW8TCZ3lmc0h8hOmSyLQrAWxmAMqWQMsmrg5659GnVaKhfuU8Eu"), picture);
            call.enqueue(new Callback<Picture>() {
                @Override
                public void onResponse(Call<Picture> call, Response<Picture> response) {
                    if (response.body() != null) {
                        Log.i("Debug:", "Exito");
                        Toast.makeText(complaintadd_activity.this, "Denuncia creada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(complaintadd_activity.this, "fail..", Toast.LENGTH_SHORT).show();
                        Log.e("Error del codigo", response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Picture> call, Throwable t) {
                    Call<ResponseBody> callDelete = Api.instance().deleteComplaint(Remember.getString("access_token", "U62AKJWJcZD8cPW8TCZ3lmc0h8hOmSyLQrAWxmAMqWQMsmrg5659GnVaKhfuU8Eu"), id);
                    callDelete.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() != null) {
                                Log.i("Debug:", "Denuncia eliminada");
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("Debug:", "Denuncia array error");
                        }
                    });
                    cleanObjects();
                }
            });
        }
        cleanObjects();
    }
}