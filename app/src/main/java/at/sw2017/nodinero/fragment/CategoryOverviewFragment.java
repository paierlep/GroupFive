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
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Template;
import at.sw2017.nodinero.model.Template_Table;

import static at.sw2017.nodinero.model.Account_Table.id;

/**
 * Created by karin on 4/14/17.
 */
public class CategoryOverviewFragment extends Fragment implements View.OnClickListener{
    public final String TAG = "AccountOverviewFragment";

    public static CategoryOverviewFragment newInstance() {
        Bundle args = new Bundle();
        CategoryOverviewFragment fragment = new CategoryOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_overview, container, false);
        createOverviewTable(view);
        loadQuickAddNavigation();

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
        if (v.getId() == R.id.overview_category_table_row) {
            ((NoDineroActivity) getActivity()).loadCategoryOverviewFragment();
        } else if (v.getId() == R.id.overview_category_delete) {
            SQLite.delete(Category.class)
                    .where(id.is((int) v.getTag()))
                    .async()
                    .execute();

            TableRow row = (TableRow) v.getParent();
            row.setVisibility(View.INVISIBLE);

            TableLayout table = (TableLayout) row.getParent();
            table.removeView(row);
            loadQuickAddNavigation();
        }
    }

    public void createOverviewTable(View view)
    {
        TableLayout category_table = (TableLayout) view.findViewById(R.id.category_list);


        for (Category category : SQLite.select().from(Category.class).queryList()) {

            TableRow row = (TableRow) View.inflate(getContext(), R.layout.table_row_category_overview, null);
            ((TextView) row.findViewById(R.id.overview_category_name)).setText(category.name);


            row.findViewById(R.id.overview_category_delete).setTag(category.id);
            row.findViewById(R.id.overview_category_delete).setOnClickListener(this);

            row.setTag(category.id);
            row.setClickable(true);
            row.setOnClickListener(this);
            category_table.addView(row);

        }
    }


}
