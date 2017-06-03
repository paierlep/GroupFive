package at.sw2017.nodinero.fragment;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;

/**
 * Created by karin on 4/14/17.
 */

public class ReportAccountFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private TextView textView;
    private SeekBar seekBar;
    private LineChart lineChart;
    private BarChart barChart;
    private List<String> labels;

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
        barChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = barChart.getLegend();
        l.setTextSize(10f);
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

        // create a dataset and give it a type
        BarDataSet barSet = new BarDataSet(entries, "DataSet 1");
        barSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(barSet);
        data.setBarWidth(0.9f);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);

        barChart.setData(data);
        barChart.setFitBars(false);
    }


    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
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
}
