package edu.ucsb.cs.cs185.contenant.contenant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Allison on 5/29/2016.
 */
public class AddRoomActivity extends AppCompatActivity {

    TextView title_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        title_view = (TextView) findViewById(R.id.room_title);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face);

        title_view = (TextView) findViewById(R.id.room_notes);
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo-Regular.otf");
        title_view.setTypeface(face2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}