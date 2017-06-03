package at.sw2017.nodinero.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Category;

/**
 * Created by karin on 4/14/17.
 */

public class ReportCategoryFragment extends Fragment {
    private PieChart spendingChart;
    private PieChart incomeChart;

    public static ReportCategoryFragment newInstance() {
        Bundle args = new Bundle();
        ReportCategoryFragment fragment = new ReportCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_tablayout, container, false);

        spendingChart = (PieChart) view.findViewById(R.id.spendingChart);
        incomeChart = (PieChart) view.findViewById(R.id.incomeChart);

        // set an alternative background color
        spendingChart.setBackgroundColor(Color.WHITE);

        setData();
        spendingChart.invalidate();


        return view;
    }

    private void setData()
    {
        List<PieEntry> incomeEntries = new ArrayList<>();
        /* allIncome = 0f;
        float allSpendings = 0f;
        for(Category cat : SQLite.select().from(Category.class).queryList())
        {
            allIncome += cat.getIncome();
            allSpendings += cat.getSpendings();
        }
        */

        for(Category cat : SQLite.select().from(Category.class).queryList())
        {
            incomeEntries.add(new PieEntry(cat.getIncome(), cat.name));
        }

        PieDataSet set = new PieDataSet(incomeEntries, "Income");
        PieData data = new PieData(set);
        incomeChart.setData(data);
    }
}
