package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

    public ChooseRoomImageAdapter(Context c, long house_id) {
        context = c;
        layoutInflater = LayoutInflater.from(c);

        House house = HouseStorage.getHouse(house_id);
        if (house == null) {
            Log.e("ChooseRoom", "House is null - invalid house id: " + house_id);
        }
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
        Room room = getItem(position);
        if (room.getImage() == null) {
            Picasso.with(context)
                    .load(R.drawable.sample_room)
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.choose_image_width),
                            context.getResources().getDimensionPixelSize(R.dimen.choose_image_height))
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(Uri.parse(room.getImage()))
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.choose_image_width),
                            context.getResources().getDimensionPixelSize(R.dimen.choose_image_height))
                    .centerCrop()
                    .placeholder(R.drawable.sample_room)
                    .error(R.drawable.sample_room)
                    .into(imageView);
        }


        TextView textView = (TextView) grid.findViewById(R.id.text);
        String[] roomTypes = context.getResources().getStringArray(R.array.room_array);
        textView.setText(roomTypes[getItem(position).getTypeIndex()]);

        return grid;
    }

}

