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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.adapter.AccountAdapter;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Expense;

/**
 * Created by kosha on 21/04/2017.
 */

public class ExpenseFormFragment extends Fragment implements View.OnClickListener{
    final private String TAG = "AddExpenseFragement";

    private AppCompatButton saveButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;

    private TextInputEditText expenseName;
    private TextInputEditText expenseValue;
    private TextInputEditText expenseCategory;
    private DatePicker expenseDate;
    private AppCompatSpinner expenseAccount;
    private int currentAccountId;

    public static ExpenseFormFragment newInstance(int accountId) {
        Bundle args = new Bundle();
        ExpenseFormFragment fragment = new ExpenseFormFragment();
        args.putInt("accountId", accountId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_add, container, false);

        currentAccountId = getArguments().getInt("accountId", 0);

        saveButton = (AppCompatButton) view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        saveAndBackButton = (AppCompatButton) view.findViewById(R.id.button_save_back);
        saveAndBackButton.setOnClickListener(this);

        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        expenseName = (TextInputEditText) view.findViewById(R.id.expense_name);
        expenseValue = (TextInputEditText) view.findViewById(R.id.expense_value);
        expenseCategory = (TextInputEditText) view.findViewById(R.id.expense_category);
        expenseDate = (DatePicker) view.findViewById(R.id.expense_date_picker);

        expenseAccount = (AppCompatSpinner) view.findViewById(R.id.expense_account_type_spinner);

        List<Account> accounts = SQLite.select().from(Account.class).queryList();
        Log.e(TAG, "size: "+accounts.size());
        AccountAdapter accountAdapter = new AccountAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
      //  ArrayAdapter accountAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
        accountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expenseAccount.setAdapter(accountAdapter);


        return view;
    }

    private void saveExpense() {
        Expense expense =  new Expense();
        expense.name = expenseName.getText().toString();
        expense.date = expenseDate.toString();

        int value;
        if (expenseValue.getText() == null || expenseValue.getText().toString().equals("")) {
            value = 0;
        } else {
            value = Integer.parseInt(expenseValue.getText().toString());
        }
        expense.value = value;

        Account account = ((Account)expenseAccount.getSelectedItem());
        expense.accountId = account;

        account.balance += value;
        account.save();

        expense.save();
        Log.d(TAG, "Wrote Expense Successful, ID: " + expense.id);
    }

    @Override
    public void onClick(View v) {
        NoDineroActivity.hideKeyboard(this.getActivity());

        if(v.getId() == R.id.button_save) {
            saveExpense();
        } else if (v.getId() == R.id.button_save_back) {
            saveExpense();
            loadExpanseCorrectView();
        } else if (v.getId() == R.id.button_cancel) {
            loadExpanseCorrectView();
        }
    }

    private void loadExpanseCorrectView(){
        if (currentAccountId > 0) {
            ((NoDineroActivity)getActivity()).loadExpensesOverviewFragment(currentAccountId);
        } else {
            ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
        }
    }

}
