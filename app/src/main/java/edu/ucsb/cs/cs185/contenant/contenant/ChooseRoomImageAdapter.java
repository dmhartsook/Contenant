package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Allison on 6/4/2016.
 */
public class ChooseRoomImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public ChooseRoomImageAdapter(Context c) {
        context = c;
        layoutInflater = LayoutInflater.from(c);
    }

    public int getCount() {
        return 5;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        if(convertView==null){
            grid = layoutInflater.inflate(R.layout.choose_room_item, null);
        }else{
            grid = convertView;
        }

        ImageView imageView = (ImageView) grid.findViewById(R.id.room_image);
        imageView.setImageResource(R.drawable.sample_room);
        TextView textView = (TextView) grid.findViewById(R.id.text);
        textView.setText("Room " + String.valueOf(position));

        return grid;
    }

}

