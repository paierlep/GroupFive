package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import at.sw2017.nodinero.R;

/**
 * Created by cpaier on 05/05/2017.
 */

public class QuickAddNavigationFragment extends Fragment implements View.OnClickListener{

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
        Button qb = (Button) view.findViewById(R.id.quickAdd);

        qb.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this.getActivity(), "Added 1 Beer", Toast.LENGTH_LONG).show();
    }
}
