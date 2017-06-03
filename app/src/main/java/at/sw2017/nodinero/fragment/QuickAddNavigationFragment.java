package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Template;

/**
 * Created by cpaier on 05/05/2017.
 */

public class QuickAddNavigationFragment extends Fragment implements View.OnClickListener{
    public final String TAG = "QuickAddNavigationFr";

    private Account acc;

    public static QuickAddNavigationFragment newInstance() {
        Bundle args = new Bundle();
        QuickAddNavigationFragment fragment = new QuickAddNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_add_nav, container, false);



        for(Template templ : SQLite.select().from(Template.class).queryList())
        {
            Button myButton = new Button(getContext());
            myButton.setText(templ.name);

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.fragment_quick_add_menu);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                   ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            myButton.setClickable(true);
            myButton.setTag(templ);
            myButton.setClickable(true);
            myButton.setOnClickListener(this);

            ll.addView(myButton);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        Template template = (Template) v.getTag();


        Expense expense =  new Expense();
        expense.name = template.name;
        expense.date = new Date().toString();
        expense.value = template.value;
        expense.accountId = template.accountId;
        expense.categoryId = template.categoryId;
        expense.save();


        Toast.makeText(this.getActivity(), "Added 1 " + template.name, Toast.LENGTH_LONG).show();
        ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
    }
}
