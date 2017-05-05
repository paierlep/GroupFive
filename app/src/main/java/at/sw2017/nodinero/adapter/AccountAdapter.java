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

/**
 * Created by karin on 5/5/17.
 */

public class AccountAdapter extends ArrayAdapter<Account> {
    private List<Account> accounts;
    private Context context;

    public AccountAdapter(@NonNull Context context, @LayoutRes int resource, List<Account> accounts) {
        super(context, resource);
        this.accounts = accounts;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("Spinner View", "---- " +position);
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(accounts.get(position).name);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        Log.e("Spinner", "---- " +position);
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(accounts.get(position).name);
        return label;
    }

    public int getCount(){
        return accounts.size();
    }

    public Account getItem(int position){
        return accounts.get(position);
    }

    public long getItemId(int position){
        return position;
    }







}
