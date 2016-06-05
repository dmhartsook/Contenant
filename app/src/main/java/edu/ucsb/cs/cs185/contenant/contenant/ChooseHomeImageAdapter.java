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
 * And adapter for displaying all the homes in a GridView.
 */
public class ChooseHomeImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<House> houses;

    public ChooseHomeImageAdapter(Context c) {
        context = c;
        layoutInflater = LayoutInflater.from(c);
        houses = new ArrayList<>(HouseStorage.getHousesCollection());
        Collections.sort(houses, new Comparator<House>() {
            @Override
            public int compare(House lhs, House rhs) {
                return Double.compare(lhs.getId(), rhs.getId());
            }
        });
    }

    public int getCount() {
        return HouseStorage.getNumberOfHouses();
    }

    public Object getItem(int position) {
        return houses.get(position);
    }

    public long getItemId(int position) {
        return houses.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        if(convertView==null){
            grid = layoutInflater.inflate(R.layout.choose_home_item, null);
        }else{
            grid = convertView;
        }

        ImageView imageView = (ImageView) grid.findViewById(R.id.home_image);
        imageView.setImageResource(R.drawable.sample_house);
        TextView textView = (TextView) grid.findViewById(R.id.text);
        textView.setText("Home " + String.valueOf(position));

        return grid;
    }

}
