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

public class SettingsFragment extends Fragment {

    public static ExpenseFormFragment newInstance() {
        Bundle args = new Bundle();
        ExpenseFormFragment fragment = new ExpenseFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }
}
