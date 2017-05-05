package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;

import static at.sw2017.nodinero.model.Account_Table.id;

/**
 * Created by karin on 4/14/17.
 */
public class AccountOverviewFragment extends Fragment implements View.OnClickListener{
    public final String TAG = "AccountOverviewFragment";

    public static AccountOverviewFragment newInstance() {
        Bundle args = new Bundle();
        AccountOverviewFragment fragment = new AccountOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_overview, container, false);
        createOverviewTable(view);
        loadQuickAddNavigation();
        //TODO createQuickAddMenu
        return view;
    }

    private void loadQuickAddNavigation() {
        Fragment fragment = QuickAddNavigationFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.add_quick_fragment, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        //TODO better swipe ui for delete button
        if (v.getId() == R.id.overview_table_row) {
            ((NoDineroActivity) getActivity()).loadExpensesOverviewFragment(v.getId());
        } else if (v.getId() == R.id.overview_delete) {
            SQLite.delete(Account.class)
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
        TableLayout account_table = (TableLayout) view.findViewById(R.id.account_list);

        for (Account account : SQLite.select().from(Account.class).queryList()) {
            TableRow row = (TableRow) View.inflate(getContext(), R.layout.table_row_account_overview, null);
            ((TextView) row.findViewById(R.id.overview_name)).setText(account.name);
            ((TextView) row.findViewById(R.id.overview_balance)).setText(String.valueOf(account.balance)); // TODO replace with localss
            ((TextView) row.findViewById(R.id.overview_currency)).setText(account.currency);

            row.findViewById(R.id.overview_delete).setTag(account.id);
            row.findViewById(R.id.overview_delete).setOnClickListener(this);

            row.setTag(account.id);
            row.setClickable(true);
            row.setOnClickListener(this);
            account_table.addView(row);
        }
    }
}
