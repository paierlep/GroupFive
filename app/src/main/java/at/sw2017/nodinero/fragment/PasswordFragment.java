package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Profile;

/**
 * Created by cpaier on 05/06/2017.
 */

public class PasswordFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "PasswordFragment";
    private TextInputEditText passwordField;

    public static PasswordFragment newInstance() {
        Bundle args = new Bundle();
        PasswordFragment fragment = new PasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        passwordField = (TextInputEditText) view.findViewById(R.id.password);
        view.findViewById(R.id.button_login).setOnClickListener(this);
        ((NoDineroActivity)getActivity()).setToolbarTitle(R.string.password_protected);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_login) {
            String originPassword = Profile.getByName("password");
            String inputPassword = passwordField.getText().toString();
            if (originPassword != null && originPassword.equals(inputPassword)) {
                ((NoDineroActivity)getActivity()).setIsLoggedIn();
            } else {
                Toast.makeText(getActivity(), R.string.error_no_login, Toast.LENGTH_LONG).show();
            }
        }
    }
}
