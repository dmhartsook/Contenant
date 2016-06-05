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
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Activity for viewing a specific home.
 */
public class ViewHomeActivity extends AppCompatActivity {
    private House house;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        house = (House) getIntent().getSerializableExtra(Constants.HOME);
        if (house == null) {
            Log.e("View Home Activity", "No house passed!");
        } else {
            initializeFields(house);
        }

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");

        TextView textView = (TextView) findViewById(R.id.home_title);
        textView.setText("My Home");
        textView.setTypeface(face);

        textView = (TextView) findViewById(R.id.home_address);
        textView.setTypeface(face);

        textView = (TextView) findViewById(R.id.home_price);
        textView.setTypeface(face);

        textView = (TextView) findViewById(R.id.home_notes);
        textView.setTypeface(face);

        FloatingActionButton fabView = (FloatingActionButton) findViewById(R.id.fab_home_view);
        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewHomeActivity.this, ChooseRoomActivity.class);
                intent.putExtra(Constants.HOME_ID, house.getId());
                startActivity(intent);
            }
        });
    }

    /* Sets all the TextView fields with the values passed in the house. */
    private void initializeFields(@NonNull House home) {
        TextView textView = (TextView) findViewById(R.id.home_title);
        textView.setText(home.getName());
        TextView addressView = (TextView) findViewById(R.id.address);
        addressView.setText(home.getAddress());
        TextView priceView = (TextView) findViewById(R.id.price);
        priceView.setText(home.getPrice());
        TextView notesView = (TextView) findViewById(R.id.notes);
        notesView.setText(home.getNotes());
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
            Intent intent = new Intent(ViewHomeActivity.this, AddHomeActivity.class);
            intent.putExtra(Constants.HOME_ID, house.getId());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.HOME, house);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        House home = (House) savedInstanceState.getSerializable(Constants.HOME);
        initializeFields(home);

        super.onRestoreInstanceState(savedInstanceState);
    }
}
