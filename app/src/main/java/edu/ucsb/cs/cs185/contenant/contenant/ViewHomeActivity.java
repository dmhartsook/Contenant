package edu.ucsb.cs.cs185.contenant.contenant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.ucsb.cs.cs185.contenant.contenant.R;

/**
 * Activity for viewing a specific home.
 */
public class ViewHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home);
    }
}
