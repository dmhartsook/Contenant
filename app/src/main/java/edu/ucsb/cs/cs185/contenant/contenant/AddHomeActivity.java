package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by Allison on 5/29/2016.
 */
public class AddHomeActivity extends AppCompatActivity{

    TextView title_view;

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

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_home_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddHomeActivity.this, AddRoomActivity.class);
                startActivity(intent);
            }
        });
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
}
