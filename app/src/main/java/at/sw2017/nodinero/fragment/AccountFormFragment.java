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
import at.sw2017.nodinero.model.Account;

/**
 * Created by karin on 4/14/17.
 */

public class AccountFormFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton saveButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;

    private TextInputEditText accountName;
    private TextInputEditText accountBalance;
    private AppCompatSpinner  accountType;
    private AppCompatSpinner accountCurrency;

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

        saveAndBackButton = (AppCompatButton) view.findViewById(R.id.button_save_back);
        saveAndBackButton.setOnClickListener(this);

        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        accountName = (TextInputEditText) view.findViewById(R.id.account_name);
        accountBalance = (TextInputEditText) view.findViewById(R.id.account_init_balance);

        accountCurrency = (AppCompatSpinner) view.findViewById(R.id.account_currency);
        accountType = (AppCompatSpinner) view.findViewById(R.id.account_type);

        return view;
    }

    private void saveAccount()
    {
        Account account =  new Account();
        account.name = accountName.getText().toString();

        account.currency = accountCurrency.getSelectedItem().toString();
        if(accountBalance.getText() == null || accountBalance.getText().toString().equals("")) {
            account.initialBalance = 0;
        } else {
            account.initialBalance = Integer.parseInt(accountBalance.getText().toString());
        }

        account.type = accountType.getSelectedItem().toString();

        account.save();
        Log.d("DB","Wrote Successful, ID: " + account.id);
    }

    @Override
    public void onClick(View v) {
        NoDineroActivity.hideKeyboard(this.getActivity());
        if (v.getId() == R.id.button_save) {
            saveAccount();
        } else if (v.getId() == R.id.button_save_back) {
            saveAccount();
            ((NoDineroActivity) getActivity()).loadAccountOverviewFragment();
        } else if (v.getId() == R.id.button_cancel) {
            ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
        }
    }
}
