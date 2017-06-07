package at.sw2017.nodinero.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import at.sw2017.nodinero.R;
import at.sw2017.nodinero.fragment.ReportAccountFragment;
import at.sw2017.nodinero.fragment.ReportCategoryFragment;

/**
 * Created by cpaier on 31/05/2017.
 */

public class ReportPageAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public ReportPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 1:
                return categoryReport();
            //case 2:
            //    return categoryReport();
            case 0:
            default:
                return accountReport();
        }
    }

    private Fragment categoryReport() {
        return ReportCategoryFragment.newInstance();
    }

    private Fragment accountReport() {
        return ReportAccountFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int stringId = 0;
        switch(position) {
            case 1:
                stringId = R.string.report_category;
                break;
            //case 2:
            //    stringId = R.string.report_other;
            //    break;
            case 0:
            default:
                stringId = R.string.report_account;
        }

        return context.getResources().getString(stringId);
    }
}
