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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * Activity for viewing a specific home.
 */
public class ViewHomeActivity extends AppCompatActivity {
    private long houseId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        houseId = getIntent().getLongExtra(Constants.HOME_ID, -1);
        if (houseId == -1) {
            Log.e("View Home Activity", "No house passed!");
        }

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");

        TextView textView = (TextView) findViewById(R.id.home_title);
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
                intent.putExtra(Constants.HOME_ID, houseId);
                startActivity(intent);
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        House house = HouseStorage.getHouse(houseId);
        initializeFields(house);
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

        ImageView imageView = (ImageView) findViewById(R.id.home_image);
        if (home.getImage() == null) {
            Picasso.with(this)
                    .load(R.drawable.sample_house)
                    .resize(getResources().getDimensionPixelSize(R.dimen.add_image_width),
                            getResources().getDimensionPixelSize(R.dimen.add_image_height))
                    .centerCrop()
                    .into(imageView);
        } else {
            House house = HouseStorage.getHouse(houseId);
            Picasso.with(this)
                    .load(Uri.parse(house.getImage()))
                    .resize(getResources().getDimensionPixelSize(R.dimen.add_image_width),
                            getResources().getDimensionPixelSize(R.dimen.add_image_height))
                    .centerCrop()
                    .into(imageView);
        }
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
            intent.putExtra(Constants.HOME_ID, houseId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
