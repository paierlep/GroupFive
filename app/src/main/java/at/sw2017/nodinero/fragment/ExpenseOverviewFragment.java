package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Expense_Table;

import static at.sw2017.nodinero.model.Account_Table.id;

/**
 * Created by karin on 4/14/17.
 */
public class ExpenseOverviewFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ExpenseOverviewFragment";
    private int currentAccountId;
    private Spinner filterSpinner;

    public static ExpenseOverviewFragment newInstance(int accountId) {
        Bundle args = new Bundle();
        ExpenseOverviewFragment fragment = new ExpenseOverviewFragment();
        args.putInt("accountId", accountId);
        fragment.setArguments(args);
        return fragment;
    }
    //public View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_expense_overview, container, false);

        currentAccountId = getArguments().getInt("accountId", 0);
        view.findViewById(R.id.add_expense).setOnClickListener(this);

        filterSpinner = (Spinner)view.findViewById(R.id.expense_filter_spinner);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                createOverviewTable(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Log.e(TAG, "my account: " + currentAccountId);

        ((NoDineroActivity)getActivity()).setToolbarTitle(R.string.expense_overview_title);

        return view;
    }

    @Override
    public void onClick(View v) {
        //ExpenseFormFragment


        if(v.getId() == R.id.add_expense) {
            ((NoDineroActivity) getActivity()).loadExpensesFormFragment(currentAccountId);
        } else if (v.getId() == R.id.overview_expanse_delete) {
            SQLite.delete(Expense.class)
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
        TableLayout expanse_table = (TableLayout) view.findViewById(R.id.expanse_list);
        expanse_table.removeAllViews();

        Calendar cal = Calendar.getInstance();

        long expenseDate = 0, filterDate = 0;
        long currentDate = cal.getTimeInMillis();

        boolean filterAll = false;
        switch (filterSpinner.getSelectedItemPosition())
        {
            default:
                filterAll = true;
                break;
            case 1:
                cal.add(Calendar.MONTH, -1);
                break;
            case 2:
                cal.add(Calendar.MONTH, -3);
                break;
            case 3:
                cal.add(Calendar.MONTH, -6);
                break;
            case 4:
                cal.add(Calendar.YEAR, -1);
                break;
        }

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(getResources().getString(R.string.simple_date_format));
        String filterDateString = simpleDateFormat.format(cal.getTime());
        //Date filterDate = cal.getTime();


        //DateFormat dateFormat = DateFormat.getDateInstance();

        for (final Expense expense : SQLite.select().from(Expense.class)
                .where(Expense_Table.accountId_id.eq(currentAccountId)).queryList()) {
            TableRow row = (TableRow) View.inflate(getContext(), R.layout.table_row_expanse_overview, null);
            ((TextView) row.findViewById(R.id.expanse_name)).setText(expense.name);
            ((TextView) row.findViewById(R.id.expanse_value)).setText(String.valueOf(expense.value));

            row.findViewById(R.id.overview_expanse_delete).setTag(expense.id);
            row.findViewById(R.id.overview_expanse_delete).setOnClickListener(this);



            row.setTag(expense.id);
            //row.setId(expense.id);

            row.setClickable(true);
            //row.setOnClickListener(this);
            row.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ((NoDineroActivity) getActivity()).loadExpensesFormFragment(currentAccountId, expense.id);

                }
            });


            if(!filterAll) {
                try {
                    filterDate = simpleDateFormat.parse(filterDateString).getTime();
                    expenseDate = simpleDateFormat.parse(expense.date).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //long filterDateLong = filterDate.getTime();
                if ((expenseDate >= filterDate) && (expenseDate < currentDate)) {
                    expanse_table.addView(row);
                }
            } else {
                expanse_table.addView(row);
            }
        }
    }
}
