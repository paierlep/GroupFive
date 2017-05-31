package at.sw2017.nodinero.fragment;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
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
import java.io.InputStream;
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
    final private int CAM_PERM = 2;
    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PHOTO     = 0;

    private AppCompatButton saveButton;
    private AppCompatButton editButton;
    private AppCompatButton saveAndBackButton;
    private AppCompatButton cancelButton;
    private AppCompatButton takePictureButton;
    private AppCompatButton selectPictureButton;
    private ImageView imageView;

    private TextInputEditText expenseName;
    private TextInputEditText expenseValue;
    private AppCompatSpinner expenseCategory;
    private DatePicker expenseDate;
    private AppCompatSpinner expenseAccount;
    private int currentAccountId;
    private int currentCategoryId;
    private Uri currentPhotoFile;
    private String currentPhoto;

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
        takePictureButton.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.imageview);
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
            takePictureButton.setEnabled(false);
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAM_PERM);
        } else {
            takePictureButton.setEnabled(true);
        }

        selectPictureButton = (AppCompatButton) view.findViewById(R.id.button_image_gallery);
        selectPictureButton.setOnClickListener(this);


        saveButton = (AppCompatButton) view.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);

        saveAndBackButton = (AppCompatButton) view.findViewById(R.id.button_save_back);
        saveAndBackButton.setOnClickListener(this);

        editButton = (AppCompatButton) view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(this);

        if(expenseId != 0)
        {
            expense = SQLite.select().from(Expense.class).where(Expense_Table.id.eq(expenseId)).querySingle();
            expenseName.setText(expense.name);
            expenseValue.setText(Integer.toString(expense.value));

            if(expense.categoryId != null) {
                expenseCategory.setSelection(categoryAdapter.getPos(expense.categoryId.id));
            }

            if (expense.photo != null) {
                displayImage(expense.photo);
            }

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

        if(currentPhoto != null && currentPhoto.length() > 0) {
            expense.photo = currentPhoto;
        }

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

        if(currentPhoto != null && currentPhoto.length() > 0) {
            expense.photo = currentPhoto;
        }

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
        } else if (v.getId() == R.id.button_image_gallery) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        Log.e(TAG, "i am in " + requestCode + " " + grantResults);
        if (requestCode == CAM_PERM) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(false);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(this.getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }

            if (photoFile != null) {
                currentPhotoFile = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        currentPhotoFile);
                startActivityForResult(takePictureIntent, 0);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                displayImage("file://" + currentPhotoFile.getPath());
            } else if (requestCode == SELECT_PICTURE) {
                displayImage(data.getData().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayImage(String imageUri) {
        Log.e(TAG, "==> " + imageUri);
        try {
            InputStream is = getContext().getContentResolver().openInputStream(Uri.parse(imageUri));
            Bitmap d = new BitmapDrawable(is).getBitmap();
            int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
            imageView.setImageBitmap(scaled);
            currentPhoto = imageUri;
        } catch (Exception e) {
            e.printStackTrace();
            // Do nothing
        }
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
        return image;
    }


    private void loadExpanseCorrectView(){
        if (currentAccountId > 0) {
            ((NoDineroActivity)getActivity()).loadExpensesOverviewFragment(currentAccountId);
        } else {
            ((NoDineroActivity)getActivity()).loadAccountOverviewFragment();
        }
    }

}
