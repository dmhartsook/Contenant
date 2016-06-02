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

/**
 * Activity for viewing a specific home.
 */
public class ViewHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        initializeFields(getIntent());

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
                Intent intent = new Intent(ViewHomeActivity.this, ViewRoomActivity.class);
                startActivity(intent);
            }
        });
    }

    /* Sets all the TextView fields with the values passed in the intent. */
    private void initializeFields(Intent intent) {
        String address = intent.getStringExtra(Constants.HOME_ADDRESS);
        String price = intent.getStringExtra(Constants.HOME_PRICE);
        String notes = intent.getStringExtra(Constants.HOME_NOTES);

        TextView addressView = (TextView) findViewById(R.id.address);
        addressView.setText(address);
        TextView priceView = (TextView) findViewById(R.id.price);
        priceView.setText(price);
        TextView notesView = (TextView) findViewById(R.id.notes);
        notesView.setText(notes);
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
            Intent intent = new Intent(ViewHomeActivity.this, AddHomeActivity.class);

            TextView address = (TextView) findViewById(R.id.address);
            TextView price = (TextView) findViewById(R.id.price);
            TextView notes = (TextView) findViewById(R.id.notes);

            intent.putExtra(Constants.HOME_ADDRESS, address.getText().toString());
            intent.putExtra(Constants.HOME_PRICE, price.getText().toString());
            intent.putExtra(Constants.HOME_NOTES, notes.getText().toString());

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
