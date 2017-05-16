package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by karin on 4/14/17.
 */
public class ExpenseOverviewFragment extends Fragment implements View.OnClickListener {
    public final String TAG = "ExpenseOverviewFragment";
    private int currentAccountId;

    public static ExpenseOverviewFragment newInstance(int accountId) {
        Bundle args = new Bundle();
        ExpenseOverviewFragment fragment = new ExpenseOverviewFragment();
        args.putInt("accountId", accountId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_overview, container, false);

        currentAccountId = getArguments().getInt("accountId", 0);
        view.findViewById(R.id.add_expense).setOnClickListener(this);

        createOverviewTable(view);

        //TODO get account from database
        //redirect to account overview if account id does not exist
        //create table of the expenses
        return view;
    }

    @Override
    public void onClick(View v) {
        //ExpenseFormFragment


        if(v.getId() == R.id.add_expense) {
            ((NoDineroActivity) getActivity()).loadExpensesFormFragment(currentAccountId);
        } else {


            //loadContent((int)v.getTag());
        }
    }


    public void createOverviewTable(View view)
    {
        TableLayout expanse_table = (TableLayout) view.findViewById(R.id.expanse_list);

        for (final Expense expense : SQLite.select().from(Expense.class)
                .where(Expense_Table.accountId_id.eq(currentAccountId)).queryList()) {
            TableRow row = (TableRow) View.inflate(getContext(), R.layout.table_row_expanse_overview, null);
            ((TextView) row.findViewById(R.id.expanse_name)).setText(expense.name);
            ((TextView) row.findViewById(R.id.expanse_value)).setText(String.valueOf(expense.value));

            row.setTag(expense.id);
            //row.setId(expense.id);

            row.setClickable(true);
            //row.setOnClickListener(this);
            row.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //Toast toast = Toast.makeText(getApplicationContext(), "Tabele row clicked "+ Integer.toString(v.getId()) , Toast.LENGTH_LONG);
                    ((NoDineroActivity) getActivity()).loadExpensesFormFragment(currentAccountId, expense.id);

                }
            });
            expanse_table.addView(row);
        }
    }
}
