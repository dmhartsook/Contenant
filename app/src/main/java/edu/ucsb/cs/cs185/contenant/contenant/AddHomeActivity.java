package edu.ucsb.cs.cs185.contenant.contenant;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Allison on 5/29/2016.
 */
public class AddHomeActivity extends AppCompatActivity{

    TextView title_view;
    ImageView home_image;

    private static final String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int PERMISSIONS_REQUEST = 1;
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 2;
    private static final int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 0;
    private String userChosenTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);

        House home = (House) getIntent().getSerializableExtra(Constants.HOME);
        if (home != null) {
            initializeFields(home);
        }

        title_view=(TextView)findViewById(R.id.home_title);
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

        home_image = (ImageView) findViewById(R.id.home_image);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_home_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddHomeActivity.this, AddRoomActivity.class);
                startActivity(intent);
            }
        });

        Button cameraButton = (Button) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddHomeActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (ContextCompat.checkSelfPermission(builder.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(AddHomeActivity.this,
                                permissions,
                                PERMISSIONS_REQUEST);
                    }
                } else {
                    if (items[item].equals("Take Photo")) {
                        userChosenTask="Take Photo";
                        openCamera();

                    } else if (items[item].equals("Choose from Library")) {
                        userChosenTask="Choose from Library";
                        openGallery();

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        File image = null;
        String filename;

        File imageFolder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        filename = "home_image001.jpg";

        imageFolder.mkdirs();
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

    private void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        home_image.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        home_image.setImageBitmap(thumbnail);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    /*
     * Sets all the EditView fields with the values in the home.
     */
    private void initializeFields(@NonNull House home) {
        EditText addressView = (EditText) findViewById(R.id.edit_address);
        String address = home.getAddress();
        addressView.setText(address);

        EditText priceView = (EditText) findViewById(R.id.edit_price);
        String price = home.getPrice();
        priceView.setText(price);

        EditText notesView = (EditText) findViewById(R.id.edit_home_notes);
        String notes = home.getNotes();
        notesView.setText(notes);
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
            Intent intent = new Intent(AddHomeActivity.this, ViewHomeActivity.class);

            intent.putExtra(Constants.HOME, createHouse());

            this.finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /* Creates a House object from all the fields. */
    @NonNull
    private House createHouse() {
        EditText address = (EditText) findViewById(R.id.edit_address);
        EditText price = (EditText) findViewById(R.id.edit_price);
        EditText notes = (EditText) findViewById(R.id.edit_home_notes);

        return new House(
                address.getText().toString(),
                price.getText().toString(),
                notes.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.HOME, createHouse());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        House home = (House) savedInstanceState.getSerializable(Constants.HOME);
        initializeFields(home);
        
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) { // camera, read/write external
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChosenTask.equals("Take Photo"))
                        openCamera();
                    else if(userChosenTask.equals("Choose from Library"))
                        openGallery();
                } else {
                    // Permission denied so exit
                    new AlertDialog.Builder(this)
                            .setTitle("Need Permission")
                            .setMessage("We don't have the necessary permissions.")
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    finish();
                                    System.exit(-1);
                                }
                            })
                            .show();
                }

        }
    }

}
