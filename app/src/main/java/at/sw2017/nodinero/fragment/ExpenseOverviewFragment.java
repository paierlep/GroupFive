package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.sw2017.nodinero.R;

/**
 * Created by karin on 4/14/17.
 */
public class ExpenseOverviewFragment extends Fragment {
    public final String TAG = "ExpenseOverviewFragment";

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

        int currentAccountId = getArguments().getInt("accountId", 0);
        //TODO get account from database
        //redirect to account overview if account id does not exist
        //create table of the expenses
        return view;
    }
}
