package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;

/**
 * Created by karin on 4/14/17.
 */

public class AccountFormFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton saveButton;
    private AppCompatButton cancelButton;

    public static AccountFormFragment newInstance() {
        Bundle args = new Bundle();
        AccountFormFragment fragment = new AccountFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form, container, false);

        saveButton = (AppCompatButton) view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_save) {
            //TODO save
        }
        ((NoDineroActivity)getActivity()).loadContent(R.id.account_overview);
    }
}
