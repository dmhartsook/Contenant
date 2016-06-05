package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Activity for the page showing all the homes, allowing the user to choose one.
 */
public class ChooseHomeActivity extends AppCompatActivity {

    private ChooseHomeImageAdapter houseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_home);

        GridView gridview = (GridView) findViewById(R.id.choose_home_gridview);
        houseAdapter = new ChooseHomeImageAdapter(this);
        gridview.setAdapter(houseAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(ChooseHomeActivity.this, ViewHomeActivity.class);
                House house = houseAdapter.getItem(position);
                intent.putExtra(Constants.HOME, house);
                startActivity(intent);
            }
        });
    }
}
