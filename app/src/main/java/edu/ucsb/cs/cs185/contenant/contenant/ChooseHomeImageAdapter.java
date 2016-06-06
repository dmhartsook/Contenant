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
                Long lhsId = lhs.getId();
                Long rhsId = rhs.getId();
                return lhsId.compareTo(rhsId);
            }
        });
    }

    public int getCount() {
        return HouseStorage.getNumberOfHouses();
    }

    public House getItem(int position) {
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
        House home = getItem(position);
        if (home.getImage() == null) {
            Picasso.with(context)
                    .load(R.drawable.sample_house)
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.choose_image_width),
                            context.getResources().getDimensionPixelSize(R.dimen.choose_image_height))
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(Uri.parse(home.getImage()))
                    .resize(context.getResources().getDimensionPixelSize(R.dimen.choose_image_width),
                            context.getResources().getDimensionPixelSize(R.dimen.choose_image_height))
                    .centerCrop()
                    .placeholder(R.drawable.sample_house)
                    .error(R.drawable.sample_house)
                    .into(imageView);
        }
        TextView textView = (TextView) grid.findViewById(R.id.text);
        textView.setText(getItem(position).getName());

        return grid;
    }

}
