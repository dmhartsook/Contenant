package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Allison on 5/29/2016.
 */
public class AddRoomActivity extends AppCompatActivity {

    TextView title_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        title_view = (TextView) findViewById(R.id.room_title);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_notes);
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face2);

        title_view = (TextView) findViewById(R.id.room_type);
        Typeface face3 = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face3);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String text = dropdown.getSelectedItem().toString();
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
            Intent intent = new Intent(AddRoomActivity.this, AddHomeActivity.class);
            this.finish();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.ROOM, createRoom());

        super.onSaveInstanceState(outState);
    }

    @NonNull
    private Room createRoom() {
        EditText notes = (EditText) findViewById(R.id.edit_room_notes);
        return new Room(notes.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Room room = (Room) savedInstanceState.getSerializable(Constants.ROOM);
        initializeFields(room);

        super.onRestoreInstanceState(savedInstanceState);
    }

    /* Fills in the fields with the values in the room. */
    private void initializeFields(@NonNull Room room) {
        EditText notes = (EditText) findViewById(R.id.edit_room_notes);
        notes.setText(room.getNotes());
    }
}

