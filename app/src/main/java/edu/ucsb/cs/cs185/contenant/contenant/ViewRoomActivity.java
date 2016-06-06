package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Activity for viewing a specific room.
 */
public class ViewRoomActivity extends AppCompatActivity {
    private long myHomeId;
    private int roomId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");

        TextView title_view = (TextView) findViewById(R.id.room_title);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_notes);
        title_view.setTypeface(face);

        myHomeId = getIntent().getLongExtra(Constants.HOME_ID, -1);
        if (myHomeId == -1) {
            Log.e("ViewRoom", "No home passed but rooms must be in houses");
        }

        roomId = (int) getIntent().getLongExtra(Constants.ROOM_ID, -1);
        if (roomId == -1) {
            Log.e("ViewRoom", "Invalid room ID, " + roomId + ", passed. Uh oh");
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        initializeFields();
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
            Room room = HouseStorage.getHouse(myHomeId).getRoom(roomId);
            intent.putExtra(Constants.ROOM, room);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /* Fills in the fields with the values in the room. */
    private void initializeFields() {
        Room room = HouseStorage.getHouse(myHomeId).getRoom(roomId);
        TextView notes = (TextView) findViewById(R.id.notes);
        notes.setText(room.getNotes());
        String[] roomTypes = getResources().getStringArray(R.array.room_array);
        TextView titleView = (TextView)findViewById(R.id.room_title);
        titleView.setText(roomTypes[room.getTypeIndex()]);

        ImageView imageView = (ImageView) findViewById(R.id.room_image);
        if (room.getImage() == null) {
            Picasso.with(this)
                    .load(R.drawable.sample_room)
                    .resize(imageView.getWidth(), imageView.getHeight())
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(this)
                    .load(Uri.parse(room.getImage()))
                    .resize(imageView.getWidth(), imageView.getHeight())
                    .centerCrop()
                    .into(imageView);
        }
    }
}
