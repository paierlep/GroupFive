package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Category;

/**
 * Created by karin on 4/14/17.
 */

public class CategoryFormFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton saveButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;


    public static CategoryFormFragment newInstance() {
        Bundle args = new Bundle();
        CategoryFormFragment fragment = new CategoryFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_form, container, false);

        saveButton = (AppCompatButton) view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        saveAndBackButton = (AppCompatButton) view.findViewById(R.id.button_save_back);
        saveAndBackButton.setOnClickListener(this);

        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);


        return view;
    }

    private void saveCategory()
    {

    }

    @Override
    public void onClick(View v) {
        NoDineroActivity.hideKeyboard(this.getActivity());
        if (v.getId() == R.id.button_save) {
            saveCategory();
        } else if (v.getId() == R.id.button_save_back) {
            saveCategory();
            ((NoDineroActivity) getActivity()).loadCategoryOverviewFragment();
        } else if (v.getId() == R.id.button_cancel) {
            ((NoDineroActivity)getActivity()).loadCategoryOverviewFragment();
        }
    }
}