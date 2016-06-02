package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        initializeFields(getIntent());

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
     * Sets all the EditView fields with the values passed in the intent.
     * Leaves the default (empty) values if nothing was passed.
     */
    private void initializeFields(Intent intent) {
        String address = intent.getStringExtra(Constants.HOME_ADDRESS);
        String price = intent.getStringExtra(Constants.HOME_PRICE);
        String notes = intent.getStringExtra(Constants.HOME_NOTES);

        if (address != null) {
            EditText addressView = (EditText) findViewById(R.id.edit_address);
            addressView.setText(address);
        }
        if (price != null) {
            EditText priceView = (EditText) findViewById(R.id.edit_price);
            priceView.setText(price);
        }
        if (notes != null) {
            EditText notesView = (EditText) findViewById(R.id.edit_home_notes);
            notesView.setText(notes);
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

        if (id == R.id.save_home) {
            Intent intent = new Intent(AddHomeActivity.this, ViewHomeActivity.class);
            EditText address = (EditText) findViewById(R.id.edit_address);
            EditText price = (EditText) findViewById(R.id.edit_price);
            EditText notes = (EditText) findViewById(R.id.edit_home_notes);

            intent.putExtra(Constants.HOME_ADDRESS, address.getText().toString());
            intent.putExtra(Constants.HOME_PRICE, price.getText().toString());
            intent.putExtra(Constants.HOME_NOTES, notes.getText().toString());

            this.finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
