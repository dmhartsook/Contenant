package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import edu.ucsb.cs.cs185.contenant.contenant.R;

/**
 * Activity for viewing a specific home.
 */
public class ViewRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");

        TextView title_view = (TextView) findViewById(R.id.room_title);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_notes);
        title_view.setTypeface(face);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_home) {
            Intent intent = new Intent(ViewRoomActivity.this, AddRoomActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
