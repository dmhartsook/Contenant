package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Allison on 6/4/2016.
 */
public class ChooseRoomImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Room> rooms;

    public ChooseRoomImageAdapter(Context c, int house_id) {
        context = c;
        layoutInflater = LayoutInflater.from(c);

        House house = HouseStorage.getHouse(house_id);
        rooms = new ArrayList<>(house.getRooms());
        Collections.sort(rooms, new Comparator<Room>() {
            @Override
            public int compare(Room lhs, Room rhs) {
                return Double.compare(lhs.getId(), rhs.getId());
            }
        });
    }

    public int getCount() {
        return rooms.size();
    }

    public Room getItem(int position) {
        return rooms.get(position);
    }

    public long getItemId(int position) {
        return rooms.get(position).getId();
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

