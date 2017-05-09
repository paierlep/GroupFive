package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.sw2017.nodinero.R;

/**
 * Created by cpaier on 05/05/2017.
 */

public class QuickAddNavigationFragment extends Fragment {

    public static QuickAddNavigationFragment newInstance() {
        Bundle args = new Bundle();
        QuickAddNavigationFragment fragment = new QuickAddNavigationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_quick_add_nav, container, false);
        return view;
    }
}
