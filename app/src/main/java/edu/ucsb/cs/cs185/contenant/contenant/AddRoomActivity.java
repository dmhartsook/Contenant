package edu.ucsb.cs.cs185.contenant.contenant;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Allison on 5/29/2016.
 */
public class AddRoomActivity extends AppCompatActivity {
    private enum ImageOption {
        NONE,
        CAMERA,
        GALLERY
    }

    private Room room;
    private long myHouseId;
    private ImageOption chosenImageOption = ImageOption.NONE;

    private static final String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String IMAGE_NAME = "room_image";
    private static final int PERMISSIONS_REQUEST = 1;
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 2;
    private static final int SELECT_FILE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        room = (Room) getIntent().getSerializableExtra(Constants.ROOM);
        if (room != null) {
            myHouseId = room.getHouseId();
        } else {
            myHouseId = getIntent().getLongExtra(Constants.HOME_ID, -1);
            room = new Room(myHouseId);
        }

        TextView title_view = (TextView) findViewById(R.id.room_title);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_notes);
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face2);

        title_view = (TextView) findViewById(R.id.room_type);
        Typeface face3 = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face3);


        ImageView cameraButton = (ImageView) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        initializeFields();
    }


//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        initializeFields();
//    }

    @Override
    protected void onResume() {
        super.onResume();

        initializeFields();
    }

    private void selectImage() {

        final Dialog dialog = new Dialog(AddRoomActivity.this);
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

                    ActivityCompat.requestPermissions(AddRoomActivity.this,
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

                    ActivityCompat.requestPermissions(AddRoomActivity.this,
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

    private void openCamera() {
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
        try {
            image = new File(imageFolder, filename);
            image.createNewFile();
        } catch (IOException e) {
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
        return IMAGE_NAME + room.getId() + ".jpg";
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
            ImageView imageView = (ImageView) findViewById(R.id.room_image);
            if (requestCode == SELECT_FILE) {
                room.setImage(data.getData().toString());
                Picasso.with(this)
                        .load(Uri.parse(room.getImage()))
                        .resize(getResources().getDimensionPixelSize(R.dimen.add_image_width),
                                getResources().getDimensionPixelSize(R.dimen.add_image_height))
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

                room.setImage(Uri.fromFile(getImageFile()).toString());
                Picasso.with(this)
                        .load(Uri.parse(room.getImage()))
                        .resize(getResources().getDimensionPixelSize(R.dimen.add_image_width),
                                getResources().getDimensionPixelSize(R.dimen.add_image_height))
                        .centerCrop()
                        .into(imageView);
            }
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
            saveRoom();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveRoom() {
        updateRoom();
        HouseStorage.addRoomToHouse(room, myHouseId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        updateRoom();
        outState.putSerializable(Constants.ROOM, room);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        room = (Room) savedInstanceState.getSerializable(Constants.ROOM);

        super.onRestoreInstanceState(savedInstanceState);
    }

    /* Updates the fields in this.room with the values in the form. */
    @NonNull
    private void updateRoom() {
        EditText notes = (EditText) findViewById(R.id.edit_room_notes);
        room.setNotes(notes.getText().toString());
        Spinner roomTypeDropdown = (Spinner) findViewById(R.id.spinner1);
        room.setTypeIndex(roomTypeDropdown.getSelectedItemPosition());
    }


    /*
     * Fills in the fields with the values in the room.
     */
    private void initializeFields() {
        EditText notes = (EditText) findViewById(R.id.edit_room_notes);
        notes.setText(room.getNotes());
        Spinner roomTypeDropdown = (Spinner) findViewById(R.id.spinner1);
        roomTypeDropdown.setSelection(room.getTypeIndex());

        ImageView imageView = (ImageView) findViewById(R.id.room_image);
        if (room.getImage() == null) {
            Picasso.with(this)
                    .load(R.drawable.sample_room)
                    .resize(getResources().getDimensionPixelSize(R.dimen.add_image_width),
                            getResources().getDimensionPixelSize(R.dimen.add_image_height))
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(this)
                    .load(Uri.parse(room.getImage()))
                    .resize(getResources().getDimensionPixelSize(R.dimen.add_image_width),
                            getResources().getDimensionPixelSize(R.dimen.add_image_height))
                    .centerCrop()
                    .into(imageView);
        }
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
                        .setMessage("We don't have the necessary permissions.")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                        .show();
            }

        }
    }
}

