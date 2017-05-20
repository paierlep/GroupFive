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
import android.widget.DatePicker;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.adapter.AccountAdapter;
import at.sw2017.nodinero.adapter.CategoryAdapter;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Account_Table;
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Expense_Table;

/**
 * Created by kosha on 21/04/2017.
 */

public class ExpenseFormFragment extends Fragment implements View.OnClickListener{
    final private String TAG = "AddExpenseFragement";

    private AppCompatButton saveButton;
    private AppCompatButton editButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;

    private TextInputEditText expenseName;
    private TextInputEditText expenseValue;
    private AppCompatSpinner expenseCategory;
    private DatePicker expenseDate;
    private AppCompatSpinner expenseAccount;
    private int currentAccountId;
    private int currentCategoryId;

    private Expense expense;

    public static ExpenseFormFragment newInstance(int accountId) {
        Bundle args = new Bundle();
        ExpenseFormFragment fragment = new ExpenseFormFragment();
        args.putInt("accountId", accountId);
        fragment.setArguments(args);
        return fragment;
    }

    public static ExpenseFormFragment newInstance(int accountId, int expenseId) {
        Bundle args = new Bundle();
        ExpenseFormFragment fragment = new ExpenseFormFragment();
        args.putInt("accountId", accountId);
        args.putInt("expenseId", expenseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_add, container, false);

        currentAccountId = getArguments().getInt("accountId", 0);
        currentCategoryId = getArguments().getInt("categoryId", 0);

        int expenseId = getArguments().getInt("expenseId", 0);

        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        expenseName = (TextInputEditText) view.findViewById(R.id.expense_name);
        expenseValue = (TextInputEditText) view.findViewById(R.id.expense_value);
        expenseDate = (DatePicker) view.findViewById(R.id.expense_date_picker);

        expenseAccount = (AppCompatSpinner) view.findViewById(R.id.expense_account_type_spinner);
        expenseCategory = (AppCompatSpinner) view.findViewById(R.id.expense_category_spinner);

        saveButton = (AppCompatButton) view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        saveAndBackButton = (AppCompatButton) view.findViewById(R.id.button_save_back);
        saveAndBackButton.setOnClickListener(this);

        editButton = (AppCompatButton) view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(this);

        if(expenseId != 0)
        {
            //todo add edit button
            expense = SQLite.select().from(Expense.class).where(Expense_Table.id.eq(expenseId)).querySingle();
            expenseName.setText(expense.name);
            expenseValue.setText(Integer.toString(expense.value));

            //toDo
            //expenseCategory.setText();
            //expenseDate.updateDate();

            saveButton.setVisibility(View.GONE);
            saveAndBackButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
        } else
        {
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            saveAndBackButton.setVisibility(View.VISIBLE);
        }


        List<Account> accounts = SQLite.select().from(Account.class).queryList();
        Log.e(TAG, "size: "+accounts.size());

        AccountAdapter accountAdapter = new AccountAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
      //  ArrayAdapter accountAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
        accountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expenseAccount.setAdapter(accountAdapter);
        expenseAccount.setSelection(accountAdapter.getPos(currentAccountId));

        List<Category> categories = SQLite.select().from(Category.class).queryList();
        Log.e(TAG, "size: "+categories.size());

        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expenseCategory.setAdapter(categoryAdapter);
        expenseCategory.setSelection(categoryAdapter.getPos(currentCategoryId));

        return view;
    }


    private void editExpense() {

        //Expense expense =  new Expense();

        expense.name = expenseName.getText().toString();
        expense.date = expenseDate.toString();

        int value;
        if (expenseValue.getText() == null || expenseValue.getText().toString().equals("")) {
            value = 0;
        } else {
            value = Integer.parseInt(expenseValue.getText().toString());
        }
        expense.value = value;

        expense.accountId = ((Account)expenseAccount.getSelectedItem());

        expense.update();
        Log.d(TAG, "Wrote Expense Successful, ID: " + expense.id);
    }

    private void saveExpense() {
        Account account = ((Account) expenseAccount.getSelectedItem());
        if (account == null) {
            return;
        }

        expense = new Expense();
        expense.name = expenseName.getText().toString();
        expense.date = expenseDate.toString();

        int value;
        if (expenseValue.getText() == null || expenseValue.getText().toString().equals("")) {
            value = 0;
        } else {
            value = Integer.parseInt(expenseValue.getText().toString());
        }
        expense.value = value;

        expense.accountId = account;


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
        } else if (v.getId() == R.id.button_edit) {
            editExpense();
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

    public void loadContent(int expenseId){

    }

}
