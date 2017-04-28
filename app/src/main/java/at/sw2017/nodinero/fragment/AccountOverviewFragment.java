package at.sw2017.nodinero.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.w3c.dom.Text;

import java.util.List;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Account_Table;

import static at.sw2017.nodinero.model.Account_Table.id;

/**
 * Created by karin on 4/14/17.
 */

public class AccountOverviewFragment extends Fragment implements View.OnClickListener{

    public final String TAG = "AccountOverviewFragment";
    private View account_view;
    private int count_accounts = 0;
    public static AccountOverviewFragment newInstance() {
        Bundle args = new Bundle();
        AccountOverviewFragment fragment = new AccountOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        account_view=inflater.inflate(R.layout.fragment_account_overview, container, false);
        createAccount();
        return account_view;
    }

    @Override
    public void onClick(View v){
        for(Account accounts: SQLite.select().from(Account.class).queryList()){
            if(v.getId() == accounts.name.hashCode())
            {
                Toast.makeText(this.getActivity(), "not implemented yet!", Toast.LENGTH_LONG).show();
            }
            else if(v.getId() ==  accounts.id)
            {
                SQLite.delete(Account.class)
                        .where(id.is(accounts.id))
                        .async()
                        .execute();


                TableRow row = (TableRow) v.getParent();
                TableLayout table = (TableLayout) row.getParent();
                Animation fadeout = AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_out);
                fadeout.setDuration(1000);
                row.startAnimation(fadeout);
                row.setVisibility(View.INVISIBLE);


                table.removeView(row);
            }
        }

    }

    public void createAccount()
    {

        ScrollView scroll = (ScrollView) account_view.findViewById(R.id.account_scroll);
        scroll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        TableLayout account_table = (TableLayout) account_view.findViewById(R.id.account_list);
        account_table.setVerticalScrollBarEnabled(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;
        double factor = 0.02;

        Log.d("Test", Integer.toString((int)(height*factor)));
        for (Account accounts : SQLite.select().from(Account.class).queryList()) {

            TableRow append_row = new TableRow(this.getContext());
            append_row.setGravity(Gravity.CENTER);

            Button myButton = new Button(this.getContext());
            myButton.setId(accounts.id);
            myButton.setText(R.string.account_delete);
            myButton.setOnClickListener(this);
            TableRow.LayoutParams params_view = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);


            TextView view_name = new TextView(this.getContext());
            view_name.setLayoutParams(params_view);
            view_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            view_name.setPadding((int)(width*factor),(int)(height*factor), (int)(width*factor), (int)(height*factor));

            TextView view_balance = new TextView(this.getContext());
            view_balance.setLayoutParams(params_view);
            view_balance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            view_balance.setPadding((int)(width*factor),(int)(height*factor), (int)(width*factor), (int)(height*factor));



            TextView view_currency = new TextView(this.getContext());
            view_currency.setLayoutParams(params_view);
            view_currency.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            view_currency.setPadding((int)(width*factor),(int)(height*factor), (int)(width*factor), (int)(height*factor));


            view_name.setText(accounts.name);
            Log.d("AccountOverviewFragment", "Currency: " + accounts.currency);
            if(accounts.currency.equals("EUR"))
                view_currency.setText("Balance: " + String.valueOf(accounts.balance) + ".-");
            else
                view_currency.setText("Balance " + String.valueOf(accounts.balance) + "$");


            append_row.setLayoutParams(params_view);
            append_row.setPadding((int)(width*factor),(int)(height*factor), (int)(width*factor), (int)(height*factor));

            append_row.addView(view_name);
            append_row.addView(view_balance);
            append_row.addView(view_currency);
            append_row.addView(myButton);
            append_row.setBackgroundResource(R.drawable.account_row_border);
            append_row.setId(accounts.name.hashCode());
            append_row.setClickable(true);
            append_row.setOnClickListener(this);

            account_table.addView(append_row);
        }

    }

    public String getTAG() {
        return TAG;
    }
}
