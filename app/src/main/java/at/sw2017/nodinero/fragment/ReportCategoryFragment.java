package at.sw2017.nodinero.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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
        View view = inflater.inflate(R.layout.fragment_report_categories, container, false);

        spendingChart = (PieChart) view.findViewById(R.id.spendingsChart);
        incomeChart = (PieChart) view.findViewById(R.id.incomeChart);

        // set an alternative background color
        spendingChart.setBackgroundColor(Color.WHITE);
        incomeChart.setBackgroundColor(Color.WHITE);

        spendingChart.getDescription().setEnabled(false);
        incomeChart.getDescription().setEnabled(false);

        spendingChart.setTouchEnabled(false);
        incomeChart.setTouchEnabled(false);

        setData();
        spendingChart.invalidate();
        incomeChart.invalidate();

        Legend spendingLegend =spendingChart.getLegend();
        spendingLegend.setTextSize(15f);
        spendingLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);

        Legend incomeLegend =incomeChart.getLegend();
        incomeLegend.setTextSize(15f);
        incomeLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);


        return view;
    }

    private void setData()
    {
        List<PieEntry> incomeEntries = new ArrayList<>();
        List<PieEntry> spendingEntries = new ArrayList<>();


        for(Category cat : SQLite.select().from(Category.class).queryList())
        {
            incomeEntries.add(new PieEntry(cat.getIncome(), cat.name));
            spendingEntries.add(new PieEntry(cat.getSpendings()*-1f, cat.name));
        }


        PieDataSet set = new PieDataSet(incomeEntries, "Income");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextSize(15f);
        set.setDrawValues(true);
        PieData data = new PieData(set);


        PieDataSet set2 = new PieDataSet(spendingEntries, "Spendings");
        set2.setColors(ColorTemplate.COLORFUL_COLORS);
        set2.setValueTextSize(15f);
        set2.setDrawValues(true);
        PieData data2 = new PieData(set2);

        spendingChart.setData(data2);
        incomeChart.setData(data);

    }
}
