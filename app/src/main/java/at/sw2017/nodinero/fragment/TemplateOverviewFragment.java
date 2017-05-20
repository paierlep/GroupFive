package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Expense_Table;
import at.sw2017.nodinero.model.Template;
import at.sw2017.nodinero.model.Template_Table;

import static at.sw2017.nodinero.model.Account_Table.id;
import static at.sw2017.nodinero.model.Template_Table.value;

/**
 * Created by karin on 4/14/17.
 */
public class TemplateOverviewFragment extends Fragment implements View.OnClickListener{
    public final String TAG = "TemplateOverviewFragment";

    public static TemplateOverviewFragment newInstance() {
        Bundle args = new Bundle();
        TemplateOverviewFragment fragment = new TemplateOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template_overview, container, false);
        createOverviewTable(view);

        ((NoDineroActivity)getActivity()).setToolbarTitle(R.string.template_overview_title);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.template_table_row) {
            ((NoDineroActivity) getActivity()).loadTemplateUpdateFormFragment((Integer) v.getTag());
        } else if (v.getId() == R.id.template_delete)
        {
            SQLite.delete(Template.class)
                    .where(id.is((int) v.getTag()))
                    .async()
                    .execute();
            TableRow row = (TableRow) v.getParent();
            row.setVisibility(View.INVISIBLE);
            TableLayout table = (TableLayout) row.getParent();
            table.removeView(row);
        }

    }

    public void createOverviewTable(View view)
    {
        TableLayout template_table = (TableLayout) view.findViewById(R.id.template_list);

        for (Template template : SQLite.select().from(Template.class).queryList()) {
            TableRow row = (TableRow) View.inflate(getContext(), R.layout.table_row_template_overview, null);
            ((TextView) row.findViewById(R.id.template_name)).setText(template.name);
            ((TextView) row.findViewById(R.id.template_value)).setText(String.format("Value: %s", Integer.toString(template.value)));


            row.findViewById(R.id.template_delete).setTag(template.id);
            row.findViewById(R.id.template_delete).setOnClickListener(this);

            row.setTag(template.id);
            row.setClickable(true);
            row.setOnClickListener(this);
            template_table.addView(row);
        }
    }


}
