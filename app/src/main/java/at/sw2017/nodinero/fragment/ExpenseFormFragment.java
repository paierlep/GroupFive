package at.sw2017.nodinero.fragment;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
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
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

public class ExpenseFormFragment extends Fragment implements View.OnClickListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DatePicker.OnDateChangedListener {
    final private String TAG = "AddExpenseFragement";
    final private int CAM_PERM = 2;
    private static final int SELECT_PICTURE = 1;
    private static final int TAKE_PHOTO     = 0;

    final private int GEOLOC_PERM = 1;

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

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng latLng;

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
        Log.e(TAG, "my expense: " + expenseId);

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
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) ||
                (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                takePictureButton.setEnabled(false);
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAM_PERM);
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
            expenseValue.setText(Float.toString(expense.value));

            if(expense.categoryId != null) {
                expenseCategory.setSelection(categoryAdapter.getPos(expense.categoryId.id));
            }

            if (expense.photo != null) {
                displayImage(expense.photo);
            }

            if (expense.date != null) {
                Log.e("TAG", "my date" + expense.date);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.simple_date_format));
                try {
                    cal.setTime(sdf.parse(expense.date));
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    expenseDate.init(year, month, day, this);
                } catch (Exception e) {
                    // nothing to do here
                }
            }

            saveButton.setVisibility(View.GONE);
            saveAndBackButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            saveAndBackButton.setVisibility(View.VISIBLE);
        }

        List<Account> accounts = SQLite.select().from(Account.class).queryList();
        Log.e(TAG, "size: " + accounts.size());

        AccountAdapter accountAdapter = new AccountAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
        //  ArrayAdapter accountAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, accounts);
        accountAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expenseAccount.setAdapter(accountAdapter);
        expenseAccount.setSelection(accountAdapter.getPos(currentAccountId));

        if (expenseId == 0) {
            ((NoDineroActivity) getActivity()).setToolbarTitle(R.string.expense_add_title);
        } else {
            ((NoDineroActivity) getActivity()).setToolbarTitle(R.string.expense_edit_title);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getGeolocationPermission();
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

    private void editExpense() {

        //Expense expense =  new Expense();
        expense.name = expenseName.getText().toString();
        //expense.date = expenseDate.toString();
        expense.date = getDatePickerFormatedString();
        if (latLng != null) {
            expense.latitude = latLng.latitude;
            expense.longitude = latLng.longitude;
        }
        expense.accountId = ((Account) expenseAccount.getSelectedItem());

        if (expenseValue.getText() == null || expenseValue.getText().toString().equals("")) {
            expense.value = 0.0f;
        } else {
            try {
                expense.value = Float.parseFloat(expenseValue.getText().toString());
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_LONG).show();
                return;
            }
        }

        if(currentPhoto != null && currentPhoto.length() > 0) {
            expense.photo = currentPhoto;
        }

        expense.accountId = ((Account)expenseAccount.getSelectedItem());
        expense.categoryId = ((Category) expenseCategory.getSelectedItem());

        expense.update();
        loadExpanseCorrectView();
    }

    private void saveExpense(boolean stay) {
        Account account = ((Account) expenseAccount.getSelectedItem());
        Category category = ((Category) expenseCategory.getSelectedItem());
        if (account == null) {
            return;
        }

        expense = new Expense();
        expense.name = expenseName.getText().toString();
        //expense.date = expenseDate.toString();
        expense.date = getDatePickerFormatedString();
        expense.accountId = account;

        if (latLng != null) {
            expense.latitude = latLng.latitude;
            expense.longitude = latLng.longitude;
        }

        if (expenseValue.getText() == null || expenseValue.getText().toString().equals("")) {
            expense.value = 0.0f;
        } else {
            try {
                expense.value = Float.parseFloat(expenseValue.getText().toString());
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_LONG).show();
                return;
            }
        }

        expense.categoryId = category;
        if(currentPhoto != null && currentPhoto.length() > 0) {
            expense.photo = currentPhoto;
        }
        expense.save();
        if(!stay)
            loadExpanseCorrectView();
    }

    @Override
    public void onClick(View v) {
        NoDineroActivity.hideKeyboard(this.getActivity());

        if (v.getId() == R.id.button_save) {
            saveExpense(true);
        } else if (v.getId() == R.id.button_save_back) {
            saveExpense(false);
        } else if (v.getId() == R.id.button_cancel) {
            loadExpanseCorrectView();
        } else if (v.getId() == R.id.button_edit) {
            editExpense();
        } else if (v.getId() == R.id.button_image) {
            takePhoto();
        } else if (v.getId() == R.id.button_image_gallery) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
        
    }

    private void loadExpanseCorrectView(){
        if (currentAccountId > 0) {
            ((NoDineroActivity) getActivity()).loadExpensesOverviewFragment(currentAccountId);
        } else {
            ((NoDineroActivity) getActivity()).loadAccountOverviewFragment();
        }
    }

    public void getGeolocationPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, GEOLOC_PERM);
        } else {
            enableGeolocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == GEOLOC_PERM) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableGeolocation();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void enableGeolocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation != null) {
            latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)
                .setFastestInterval(1000);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void onStop() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
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
    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Log.e("TAG", "in takePhoto");
        if (takePictureIntent.resolveActivity(this.getContext().getPackageManager()) != null) {
            Log.e("TAG", "in takePhoto 2");
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                currentPhotoFile = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        currentPhotoFile);
                startActivityForResult(takePictureIntent, 0);
            }
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

    private String getDatePickerFormatedString()
    {
        String finalDateTime = "";

        int year = expenseDate.getYear();
        int month = expenseDate.getMonth();
        int day = expenseDate.getDayOfMonth();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(getResources().getString(R.string.simple_date_format));
        finalDateTime = simpleDateFormat.format(cal.getTime());

        return finalDateTime;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }
}
