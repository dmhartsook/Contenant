package edu.ucsb.cs.cs185.contenant.contenant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Deanna on 6/2/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public ImageAdapter(Context c) {
        context = c;
        layoutInflater = LayoutInflater.from(c);
    }

    public int getCount() {
        return 3;
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
