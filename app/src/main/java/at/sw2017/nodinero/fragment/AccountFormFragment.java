package at.sw2017.nodinero.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.Text;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.model.Account;

/**
 * Created by karin on 4/14/17.
 */

public class AccountFormFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton saveButton;
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

        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        accountName = (TextInputEditText) view.findViewById(R.id.account_name);
        accountBalance = (TextInputEditText) view.findViewById(R.id.account_init_balance);

        accountCurrency = (AppCompatSpinner) view.findViewById(R.id.account_currency);
        accountType = (AppCompatSpinner) view.findViewById(R.id.account_type);





        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_save) {


            Account account1 =  new Account();
            account1.name = accountName.getText().toString();

            account1.currency = accountCurrency.toString();
            account1.balance = Integer.parseInt(accountBalance.getText().toString());
            account1.type = accountType.toString();

            account1.save();
            Log.d("DB","Wrote Successful, ID: " + account1.id);

            ((NoDineroActivity)getActivity()).loadContent(R.id.account_overview);









        }

        else if (v.getId() == R.id.button_cancel) {
            ((NoDineroActivity)getActivity()).loadContent(R.id.account_overview);
        }
    }
}
