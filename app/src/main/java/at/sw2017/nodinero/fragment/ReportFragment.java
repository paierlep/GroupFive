package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.adapter.ReportPageAdapter;
import at.sw2017.nodinero.model.Account;

/**
 * Created by karin on 4/14/17.
 */

public class ReportFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static ReportFragment newInstance() {
        Bundle args = new Bundle();
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_tablayout, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.pager_frame);
        viewPager.setAdapter(new ReportPageAdapter(getFragmentManager(), getContext()));

        tabLayout = (TabLayout) view.findViewById(R.id.report_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
