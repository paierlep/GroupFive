package at.sw2017.nodinero.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.sw2017.nodinero.NoDineroActivity;
import at.sw2017.nodinero.R;
import at.sw2017.nodinero.adapter.AccountAdapter;
import at.sw2017.nodinero.adapter.CategoryAdapter;
import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Expense_Table;

/**
 * Created by kosha on 21/04/2017.
 */

public class ExpenseFormFragment extends Fragment implements View.OnClickListener{
    final private String TAG = "AddExpenseFragement";
    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PHOTO     = 0;

    private AppCompatButton saveButton;
    private AppCompatButton editButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;
    private AppCompatButton takePictureButton;
    private ImageView imageView;

    private TextInputEditText expenseName;
    private TextInputEditText expenseValue;
    private AppCompatSpinner expenseCategory;
    private DatePicker expenseDate;
    private AppCompatSpinner expenseAccount;
    private int currentAccountId;
    private int currentCategoryId;
    private String currentPhotoPath;
    private ImageView userImagePreview;
    private Uri currentPhotoFile;

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

        List<Category> categories = SQLite.select().from(Category.class).queryList();
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expenseCategory.setAdapter(categoryAdapter);
        expenseCategory.setSelection(categoryAdapter.getPos(currentCategoryId));

        takePictureButton = (AppCompatButton) view.findViewById(R.id.button_image);
        imageView = (ImageView) view.findViewById(R.id.imageview);
      //  if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)) {
      //      takePictureButton.setEnabled(false);
      //      ActivityCompat.requestPermissions(this.getActivity(), new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
      //  }

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

            expenseCategory.setSelection(categoryAdapter.getPos(expense.categoryId.id));
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
        expense.categoryId = ((Category) expenseCategory.getSelectedItem());

        expense.update();
        Log.d(TAG, "Wrote Expense Successful, ID: " + expense.id);
    }

    private void saveExpense() {
        Account account = ((Account) expenseAccount.getSelectedItem());
        Category category = ((Category) expenseCategory.getSelectedItem());
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
        expense.categoryId = category;


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
        } else if (v.getId() == R.id.button_image) {
            takePhoto();
        }
        
    }

    private void takePhoto() {
        Log.e("PERMS", "take photo");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(this.getContext().getPackageManager()) != null) {
            Log.e("PERMS", "create File");
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("TAG", "exception" + ex);
            }

            if (photoFile != null) {
                Log.e(TAG, "calling photo app");
                currentPhotoFile = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        currentPhotoFile);
                startActivityForResult(takePictureIntent, 0);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.getActivity().RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                //Bundle extras = data.getExtras();
                //Bitmap imageBitmap = (Bitmap) extras.get("data");
                Log.e("HOT", "return from camera");
                Bitmap bitmap;
                try {/*
                    Image image = new Image(currentPhotoFile, getActivity().getContentResolver());
                    userImagePreview.setImageBitmap(image.getBitmap());
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bitmap = image.getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                    byte[] ba = bao.toByteArray();
                    String byteCode = Base64.encodeToString(ba, Base64.DEFAULT);
                    new ChildImageUploadRequest(
                            Utils.getDeviceId(context),
                            currentUser.getId(),
                            currentPerson.id,
                            byteCode).
                            send(handler, (ActivityHandler) getActivity());
                    // TODO */
                } catch (Exception e) {
                    Log.e("TAG", "exception " + e);
                    //TODO Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == SELECT_PICTURE) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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
