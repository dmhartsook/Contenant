package edu.ucsb.cs.cs185.contenant.contenant;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Allison on 5/29/2016.
 */
public class AddHomeActivity extends AppCompatActivity{
    private enum ImageOption {
        NONE,
        CAMERA,
        GALLERY
    }
    private static final String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String IMAGE_NAME = "home_image";
    private static final int PERMISSIONS_REQUEST = 1;
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 2;
    private static final int SELECT_FILE = 1;

    private ImageOption chosenImageOption = ImageOption.NONE;
    private House house;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);

        long houseId = getIntent().getLongExtra(Constants.HOME_ID, -1);
        if (houseId == -1) {
            house = new House();
        }
        else {
            house = HouseStorage.getHouse(houseId);
        }

        setTitleFonts();

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_home_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHouse();
                Intent intent = new Intent(AddHomeActivity.this, AddRoomActivity.class);
                intent.putExtra(Constants.HOME_ID, house.getId());
                startActivity(intent);
            }
        });

        ImageView cameraButton = (ImageView) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        initializeFields(house);
    }

    /* Sets all the title TextViews to use the font. */
    private void setTitleFonts() {
        TextView title_view=(TextView)findViewById(R.id.home_title);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face);

        title_view=(TextView)findViewById(R.id.home_address);
        Typeface face2= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face2);

        title_view=(TextView)findViewById(R.id.home_price);
        Typeface face3= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face3);

        title_view=(TextView)findViewById(R.id.home_notes);
        Typeface face4= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face4);

        title_view=(TextView)findViewById(R.id.home_name);
        Typeface face5= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face5);
    }

    private void selectImage() {

        final Dialog dialog = new Dialog(AddHomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment2_diag);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView title_view=(TextView)dialog.findViewById(R.id.frag_title);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setText(R.string.add_photo);
        title_view.setTypeface(face);

        Button cameraButton = (Button) dialog.findViewById(R.id.frag_details1);
        cameraButton.setText(R.string.take_photo);

        Button galleryButton = (Button) dialog.findViewById(R.id.frag_details2);
        galleryButton.setText(R.string.gal_photo);

        Button mButton = (Button) dialog.findViewById(R.id.ok);
        mButton.setText(R.string.cancel);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenImageOption = ImageOption.CAMERA;
                if (ContextCompat.checkSelfPermission(dialog.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        ActivityCompat.requestPermissions(AddHomeActivity.this,
                                permissions,
                                PERMISSIONS_REQUEST);
                } else {
                    openCamera();
                }
                dialog.dismiss();
            }
        });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenImageOption = ImageOption.GALLERY;
                if (ContextCompat.checkSelfPermission(dialog.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    ActivityCompat.requestPermissions(AddHomeActivity.this,
                            permissions,
                            PERMISSIONS_REQUEST);
                } else {
                    openGallery();
                }

                dialog.dismiss();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openCamera() {
        File image = null;
        String filename;

        File imageFolder = getImageStorageFolder();
        if (! imageFolder.exists()){
            if (! imageFolder.mkdirs()){
                Log.e("AddHomeActivity", "failed to create directory");
                return;
            }
        }

        filename = getImageFilename();
        image = new File(imageFolder, filename);
        if (image.exists()) {
            image.delete();
        }
        try {
            image.createNewFile();
        } catch (IOException e) {
            Log.e("AddHome", "Failed to create new file.");
            e.printStackTrace();
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_REQUEST_CODE);
    }

    @NonNull
    private File getImageStorageFolder() {
        return new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getResources().getString(R.string.app_name));
    }

    @NonNull
    private String getImageFilename() {
        return IMAGE_NAME + house.getId() + ".jpg";
    }

    private File getImageFile() {
        File imageFolder = getImageStorageFolder();
        if (! imageFolder.exists()){
            Log.e("AddHomeActivity", "Image folder isn't created...");
        }
        String filename = getImageFilename();
        return new File(imageFolder, filename);
    }

    private void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.home_image);
            if (requestCode == SELECT_FILE) {

                house.setImage(data.getData().toString());
                Picasso.with(this)
                        .load(Uri.parse(house.getImage()))
                        .resize(imageView.getWidth(), imageView.getHeight())
                        .centerCrop()
                        .into(imageView);
            }
            else if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
                // Trigger media scanner, so that the new file will show up in the gallery the next time it's opened:
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[] {getImageFile().getAbsolutePath()},
                        null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        // Auto-generated method stub, nothing to do here.
                    }
                });

                house.setImage(Uri.fromFile(getImageFile()).toString());
                Picasso.with(this)
                        .load(Uri.parse(house.getImage()))
                        .resize(imageView.getWidth(), imageView.getHeight())
                        .centerCrop()
                        .into(imageView);
            }
        } else {
            Log.d("AddHome", "not okay result");
        }
    }


    /*
     * Sets all the EditView fields with the values in the home.
     * Sets the house parameter to home.
     */
    private void initializeFields(@NonNull House home) {
        house = home;

        EditText nameView = (EditText) findViewById(R.id.edit_name);
        String name = home.getName();
        nameView.setText(name);

        EditText addressView = (EditText) findViewById(R.id.edit_address);
        String address = home.getAddress();
        addressView.setText(address);

        EditText priceView = (EditText) findViewById(R.id.edit_price);
        String price = home.getPrice();
        priceView.setText(price);

        EditText notesView = (EditText) findViewById(R.id.edit_home_notes);
        String notes = home.getNotes();
        notesView.setText(notes);

        ImageView imageView = (ImageView) findViewById(R.id.home_image);
        if (home.getImage() == null) {
            Picasso.with(this)
                    .load(R.drawable.sample_house)
                    .resize(imageView.getWidth(), imageView.getHeight())
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(this)
                    .load(Uri.parse(house.getImage()))
                    .resize(imageView.getWidth(), imageView.getHeight())
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            saveHouse();
            if (getIntent().getBooleanExtra(Constants.OPEN_VIEW_ON_SAVE, false)) {
                Intent intent = new Intent(AddHomeActivity.this, ViewHomeActivity.class);
                intent.putExtra(Constants.HOME_ID, house.getId());
                finish();
                startActivity(intent);
            } else {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveHouse() {
        updateHouse();
        HouseStorage.addHouse(house);
    }

    /* Updates the house field from all the fields. */
    @NonNull
    private void updateHouse() {
        EditText name = (EditText) findViewById(R.id.edit_name) ;
        EditText address = (EditText) findViewById(R.id.edit_address);
        EditText price = (EditText) findViewById(R.id.edit_price);
        EditText notes = (EditText) findViewById(R.id.edit_home_notes);


        if (name.getText().toString().isEmpty()) {
            house.setName("My Home");
        } else {
            house.setName(name.getText().toString());
        }

        house.setAddress(address.getText().toString());
        house.setPrice(price.getText().toString());
        house.setNotes(notes.getText().toString());
    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) { // camera, read/write external
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(chosenImageOption == ImageOption.CAMERA)
                        openCamera();
                    else if(chosenImageOption == ImageOption.GALLERY)
                        openGallery();
                } else {
                    // Permission denied so exit
                    new AlertDialog.Builder(this)
                            .setTitle("Need Permission")
                            .setMessage("We don't have the necessary permissions to choose a photo.")
                            .setPositiveButton("Okay.", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .show();
                }

        }
    }

}