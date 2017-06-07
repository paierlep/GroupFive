package at.sw2017.nodinero.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Space;
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
    private ViewGroup buttonsContainer;

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
        this.buttonsContainer = (ViewGroup) view.findViewById(R.id.fragment_quick_add_menu);


        for(Template templ : SQLite.select().from(Template.class).queryList())
        {
            Button myButton = (Button) inflater.inflate(R.layout.circularbutton_layout, buttonsContainer, false);
            myButton.setText(templ.name);
            myButton.setTextColor(Color.DKGRAY);
            myButton.setTextSize(16);
            myButton.setSingleLine();
            myButton.setEllipsize(TextUtils.TruncateAt.END);
            myButton.setClickable(true);
            myButton.setTag(templ);
            myButton.setClickable(true);
            myButton.setOnClickListener(this);
            myButton.setBackgroundResource(R.drawable.round_shape);

            buttonsContainer.addView(myButton);

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

        Toast.makeText(this.getActivity(), "Added 1 " + template.name, Toast.LENGTH_SHORT).show();
        ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
    }
}
