package at.sw2017.nodinero.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Category;

/**
 * Created by karin on 5/5/17.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(@NonNull Context context, @LayoutRes int resource, List<Category> categories) {
        super(context, resource);
        this.categories = categories;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("Spinner View", "---- " +position);
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(categories.get(position).name);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        Log.e("Spinner", "---- " +position);
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(categories.get(position).name);
        return label;
    }

    public int getCount(){
        return categories.size();
    }

    public int getPos(int categoryid)
    {
        for(int i = 0 ; i < categories.size(); i++)
        {
            if(categories.get(i).id ==  categoryid)
                return i;
        }
        return -1;
    }

    public Category getItem(int position){
        return categories.get(position);
    }

    public long getItemId(int position){
        return position;
    }







}
