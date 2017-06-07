package at.sw2017.nodinero.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Account_Table;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Expense_Table;

import static com.raizlabs.android.dbflow.sql.language.Method.count;
import static com.raizlabs.android.dbflow.sql.language.Method.sum;

/**
 * Created by karin on 4/14/17.
 */

public class ReportAccountFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    private TextView textView;
    private SeekBar seekBar;
    private LineChart lineChart;
    private BarChart barChart;
    private List<String> labels;

    private AppCompatSpinner dateFilter;

    public static ReportAccountFragment newInstance() {
        Bundle args = new Bundle();
        ReportAccountFragment fragment = new ReportAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_accounts, container, false);

        dateFilter = (AppCompatSpinner)view.findViewById(R.id.date_filter_spinner);
        dateFilter.setOnItemSelectedListener(this);

        labels = new ArrayList<String>();


        barChart = (BarChart) view.findViewById(R.id.chart1);

        // no description text
        barChart.getDescription().setEnabled(false);

        // enable touch gestures
        barChart.setTouchEnabled(true);

        barChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDrawGridBackground(false);
        barChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        barChart.setBackgroundColor(Color.WHITE);
        barChart.setViewPortOffsets(0f, 0f, 0f, 0f);



        // add data
        setData();

        // get the legend (only possible after setting data)
        Legend l = barChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextSize(20f);
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);



        YAxis yAxis = barChart.getAxisRight();
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        yAxis.setDrawLabels(true);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawZeroLine(true);
        barChart.getAxisLeft().setEnabled(false);

        return view;
    }

    private void setData() {

        List<Account> accounts = SQLite.select().from(Account.class).queryList();
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int x = 0; x < accounts.size(); x++) {
            labels.add(accounts.get(x).name);
            entries.add(new BarEntry(x, accounts.get(x).getBalance())); // add one entry per hour
        }
        drawData(entries);
    }

    private void drawData(List<BarEntry> entries) {
        BarDataSet barSet = new BarDataSet(entries, "DataSet 1");
        barSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(barSet);
        data.setBarWidth(0.9f);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        barChart.setData(data);
        barChart.setFitBars(false);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.simple_date_format));
        String currentDate = sdf.format(calNow.getTime());
        String otherDate = sdf.format(cal.getTime());

        List<Account> accounts = SQLite.select().from(Account.class).queryList();
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int x = 0; x < accounts.size(); x++) {
            int currentAccountID = accounts.get(x).id;
            /*
            String sql = SQLite.select(sum(Expense_Table.value).as("sum")).from(Expense.class).
                    where(Expense_Table.accountId_id.eq(accounts.get(x).id)).
                    and(Expense_Table.date.between(otherDate).and(currentDate)).getQuery();
            */

            //Cursor query = SQLite.select(sum(Expense_Table.value).as("sum")).from(Expense.class).
            //        where(Expense_Table.accountId_id.eq(accounts.get(x).id)).query()
                    //and(Expense_Table.date.between(otherDate).and(currentDate)).query();

            float res = accounts.get(x).getExpensesBetween(currentDate, otherDate, sdf);

            labels.add(accounts.get(x).name);
            entries.add(new BarEntry(x, res));



            /*
            long am = SQLite.select(count(Expense_Table.accountId_id)).from(Expense.class).
                    where(Expense_Table.accountId_id.eq(accounts.get(x).id)).
                    and(Expense_Table.date.between(otherDate).and(currentDate)).count();

            Log.e("TAGSQL", "sql " + am);

            List<Expense> ams = SQLite.select().from(Expense.class).
                    where(Expense_Table.date.between(otherDate).and(currentDate)).
                    queryList();
            */

            //if (query != null && query.moveToFirst()) {

            //    float amount = query.getFloat(0);

           //     labels.add(accounts.get(x).name);
           //     entries.add(new BarEntry(x, amount));
           //     query.close();
            //}

        }

        drawData(entries);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
