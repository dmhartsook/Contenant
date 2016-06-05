package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Activity for viewing a specific room.
 */
public class ViewRoomActivity extends AppCompatActivity {

    private int myHomeId;
    private Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");

        TextView title_view = (TextView) findViewById(R.id.room_title);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_notes);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_types);
        title_view.setTypeface(face);

        myHomeId = getIntent().getIntExtra(Constants.HOME_ID, -1);
        if (myHomeId == -1) {
            Log.e("ViewRoom", "No home passed but rooms must be in houses");
        }

        int roomId = (int) getIntent().getLongExtra(Constants.ROOM_ID, -1);
        if (roomId == -1) {
            Log.e("ViewRoom", "Invalid room ID, " + roomId + ", passed. Uh oh");
        }
        room = HouseStorage.getHouse(myHomeId).getRoom(roomId);
        initializeFields(room);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit) {
            Intent intent = new Intent(ViewRoomActivity.this, AddRoomActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.ROOM, room);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        room = (Room) savedInstanceState.getSerializable(Constants.ROOM);
        initializeFields(room);

        super.onRestoreInstanceState(savedInstanceState);
    }

    /* Fills in the fields with the values in the room. */
    private void initializeFields(@NonNull Room room) {
        TextView notes = (TextView) findViewById(R.id.notes);
        notes.setText(room.getNotes());
    }
}
