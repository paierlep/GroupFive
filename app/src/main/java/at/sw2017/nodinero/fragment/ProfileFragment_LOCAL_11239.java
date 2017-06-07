package at.sw2017.nodinero.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Locale;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Profile;

/**
 * Created by karin on 4/14/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ProfileFragment";

    private AppCompatSpinner currencySpinner;
    private AppCompatSpinner languageSpinner;
    private TextInputEditText nameEditText;
    private TextInputEditText passwordEditText;
    private AppCompatCheckBox showIntro;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);


        view.findViewById(R.id.button_save).setOnClickListener(this);

        nameEditText = (TextInputEditText) view.findViewById(R.id.profile_name);
        passwordEditText = (TextInputEditText) view.findViewById(R.id.profile_password);
        currencySpinner = (AppCompatSpinner) view.findViewById(R.id.profile_default_currency);
        languageSpinner = (AppCompatSpinner) view.findViewById(R.id.profile_default_language);

        showIntro = (AppCompatCheckBox) view.findViewById(R.id.intro_enabled);

        ((NoDineroActivity)getActivity()).setToolbarTitle(R.string.menu_profile);
        fillData();
        return view;
    }

    @Override
    public void onClick(View v) {
        NoDineroActivity.hideKeyboard(this.getActivity());

        if (v.getId() == R.id.button_save) {
            saveProfile();
        }
    }

    private void saveProfile() {
        Profile.storeByName("name", nameEditText.getText().toString());

        if (passwordEditText.getText().length() > 0) {
            Profile.storeByName("password", passwordEditText.getText().toString());
        }

        Profile.storeByName("currency", Integer.toString(currencySpinner.getSelectedItemPosition()));
        Profile.storeByName("language", Integer.toString(languageSpinner.getSelectedItemPosition()));
        Profile.storeByName("show_intro", Boolean.toString(showIntro.isChecked()));

        ((NoDineroActivity) getActivity()).checkLocale();
        ((NoDineroActivity) getActivity()).setUsername();
        Toast.makeText(getActivity(), R.string.profile_saved, Toast.LENGTH_SHORT).show();

    }

    private void fillData() {
        nameEditText.setText(Profile.getByName("name"));
        String currency = Profile.getByName("currency");
        if (currency != null) {
            currencySpinner.setSelection(Integer.parseInt(Profile.getByName("currency")));
        }
        String language = Profile.getByName("language");
        if (language != null) {
            languageSpinner.setSelection(Integer.parseInt(Profile.getByName("language")));
        }

        String showIntro = Profile.getByName("show_intro");
        if (showIntro != null) {
            this.showIntro.setChecked(Boolean.parseBoolean(showIntro));
        }
    }

}
