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
import android.widget.Spinner;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.adapter.AccountAdapter;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Account_Table;
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Category_Table;
import at.sw2017.nodinero.model.Template;
import at.sw2017.nodinero.model.Template_Table;


public class TemplateFormFragment extends Fragment implements View.OnClickListener{
    final private String TAG = "AddTemplateFragment";

    private AppCompatButton saveButton;
    private AppCompatButton editButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;

    private TextInputEditText templateName;
    private TextInputEditText templateValue;
    private TextInputEditText templateCategory;
    private AppCompatSpinner teplateAccount;
    private int currentAccountId;
    private int templateID;

    private Template template;

    public static TemplateFormFragment newInstance() {
        Bundle args = new Bundle();
        TemplateFormFragment fragment = new TemplateFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TemplateFormFragment newInstance(int id) {
        Bundle args = new Bundle();
        TemplateFormFragment fragment = new TemplateFormFragment();
        args.putInt("templateID", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_template_add, container, false);
        this.getArguments();
        cancelButton = (AppCompatButton) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(this);

        templateName = (TextInputEditText) view.findViewById(R.id.expense_name);
        templateValue = (TextInputEditText) view.findViewById(R.id.expense_value);
        templateCategory = (TextInputEditText) view.findViewById(R.id.expense_category);


        teplateAccount = (AppCompatSpinner) view.findViewById(R.id.expense_account_type_spinner);

        saveButton = (AppCompatButton) view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        saveAndBackButton = (AppCompatButton) view.findViewById(R.id.button_save_back);
        saveAndBackButton.setOnClickListener(this);

        editButton = (AppCompatButton) view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(this);

        editButton.setVisibility(View.GONE);

        int expenseId = getArguments().getInt("templateID", 0);
        if (expenseId != 0)
        {
            Template t1 = new Template();
            t1.id = expenseId;
            List<Template> t2 = SQLite.select().from(Template.class).where(Template_Table.id.is(t1.id)).queryList();
            t1.value = t2.get(0).value;
            t1.name = t2.get(0).name;
            saveButton.setVisibility(View.GONE);
            saveAndBackButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            templateName.setText(t1.name);
            templateValue.setText(String.valueOf(t1.value));

            // TO DO: Set Account and Category Spinner
            List<Account> a1 = SQLite.select().from(Account.class).where(Account_Table.id.is(t1.id)).queryList();
            //List<Category> c1 = SQLite.select().from(Category.class).where(Category_Table.id.is(t1.categoryId)).queryList();





        }

        List<Account> accounts = SQLite.select().from(Account.class).queryList();
        Log.e(TAG, "size: "+accounts.size());

        AccountAdapter accountAdapter = new AccountAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
      //  ArrayAdapter accountAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
        accountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teplateAccount.setAdapter(accountAdapter);


        return view;
    }


    private void editTemplate() {

        Template template = new Template();
        int expenseId = getArguments().getInt("templateID", 0);
        template.id = expenseId;

        template.name = templateName.getText().toString();

        int value;
        if (templateValue.getText() == null || templateValue.getText().toString().equals("")) {
            value = 0;
        } else {
            value = Integer.parseInt(templateValue.getText().toString());
        }
        template.value = value;

        template.accountId = ((Account) teplateAccount.getSelectedItem());

        template.save();
        Log.d(TAG, "Wrote template Successful, ID: " + template.id);
    }

    private void saveTemplate() {
        Account account = ((Account) teplateAccount.getSelectedItem());
        if (account == null) {
            return;
        }

        template = new Template();
        template.name = templateName.getText().toString();

        int value;
        if (templateValue.getText() == null || templateValue.getText().toString().equals("")) {
            value = 0;
        } else {
            value = Integer.parseInt(templateValue.getText().toString());
        }
        template.value = value;

        template.accountId = account;

        template.save();
        Log.d(TAG, "Wrote template Successful, ID: " + template.id);
    }

    @Override
    public void onClick(View v) {
        NoDineroActivity.hideKeyboard(this.getActivity());

        if(v.getId() == R.id.button_save) {
            saveTemplate();
        } else if (v.getId() == R.id.button_save_back) {
            saveTemplate();
            ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
        } else if (v.getId() == R.id.button_cancel) {
            ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
        } else if (v.getId() == R.id.button_edit) {
            editTemplate();
            ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
        }
    }

    public void loadContent(){

    }

    private int getSpinnerIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

}
