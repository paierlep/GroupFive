package at.sw2017.nodinero.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Category_Table;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Expense_Table;

import static com.raizlabs.android.dbflow.sql.language.Method.sum;

/**
 * Created by karin on 4/14/17.
 */

public class ReportCategoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private PieChart spendingChart;
    private PieChart incomeChart;

    private AppCompatSpinner dateFilter;

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

        dateFilter = (AppCompatSpinner)view.findViewById(R.id.date_filter_spinner_cat);
        dateFilter.setOnItemSelectedListener(this);

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

        drawData(incomeEntries, spendingEntries);
    }

    private void drawData(List<PieEntry> incomeEntries, List<PieEntry> spendingEntries) {

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

        spendingChart.notifyDataSetChanged();
        spendingChart.invalidate();

        incomeChart.notifyDataSetChanged();
        incomeChart.invalidate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int filter = dateFilter.getSelectedItemPosition();
        Calendar cal = Calendar.getInstance();
        switch (filter) {
            default:
            case 0:
                setData();
                return;
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
        Calendar calNow = Calendar.getInstance();
        SimpleDateFormat sdf =
                new SimpleDateFormat(getResources().getString(R.string.simple_date_format));
        String currentDate = sdf.format(calNow.getTime());
        String otherDate = sdf.format(cal.getTime());

        List<PieEntry> incomeEntries = new ArrayList<>();
        List<PieEntry> spendingEntries = new ArrayList<>();

        for(Category cat : SQLite.select().from(Category.class).queryList())
        {
            incomeEntries.add(new PieEntry(cat.getIncomeBetween(currentDate, otherDate, sdf), cat.name));
            spendingEntries.add(new PieEntry(cat.getSpendingsBetween(currentDate, otherDate, sdf)*-1f, cat.name));
        }

        drawData(incomeEntries, spendingEntries);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
