package edu.ucsb.cs.cs185.contenant.contenant;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.ucsb.cs.cs185.contenant.contenant.R;

/**
 * Activity for viewing a specific home.
 */
public class ViewHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");

        TextView title_view = (TextView) findViewById(R.id.home_title);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.home_address);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.home_price);
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.home_notes);
        title_view.setTypeface(face);
    }
}
