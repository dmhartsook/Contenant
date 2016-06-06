package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Allison on 6/4/2016.
 */
public class ChooseRoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_room);

        Intent intent = getIntent();
        final long houseId = intent.getLongExtra(Constants.HOME_ID, -1);
        if (houseId == -1) {
            Log.e("ChooseRoom", "No house ID passed");
        }

        GridView gridview = (GridView) findViewById(R.id.choose_room_gridview);
        gridview.setAdapter(new ChooseRoomImageAdapter(this, houseId));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(ChooseRoomActivity.this, ViewRoomActivity.class);
                intent.putExtra(Constants.ROOM_ID, id);
                intent.putExtra(Constants.HOME_ID, houseId);
                startActivity(intent);
            }
        });
    }
}