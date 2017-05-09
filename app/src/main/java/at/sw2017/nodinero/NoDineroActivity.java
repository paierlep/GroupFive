package at.sw2017.nodinero;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import at.sw2017.nodinero.fragment.AccountFormFragment;
import at.sw2017.nodinero.fragment.AccountOverviewFragment;
import at.sw2017.nodinero.fragment.ExpenseFormFragment;
import at.sw2017.nodinero.fragment.ExpenseOverviewFragment;
import at.sw2017.nodinero.fragment.SettingsFragment;
import at.sw2017.nodinero.model.Database;

public class NoDineroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public final String TAG = "NoDineroActivity";

    private NavigationView navigation;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_overview);

        initDb();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(toolbar);

        loadAccountOverviewFragment();
    }

    private void initDb() {
        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.getDatabase(Database.class).getWritableDatabase();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "pushed menu button " + item.getTitle());
        mDrawerLayout.closeDrawers();
        return loadContent(item.getItemId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "pushed top bar menu button " + item.getTitle());
        return loadContent(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    public boolean loadContent(int id) {
        switch (id) {
            //Side menu
            case R.id.add_account:
                loadAccountFormFragment();
                break;
            case R.id.add_expense:
                loadExpensesFormFragment(0);
                break;
            //toolbar
            case R.id.menu_settings:
                loadSettingsFragment();
                break;
            case R.id.account_overview:
                loadAccountOverviewFragment();
                break;
            case R.id.menu_profile:
                Toast.makeText(this, "not implemented yet!", Toast.LENGTH_LONG).show();
            default:
                return false;
        }

        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.addToBackStack("tag");

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    public void loadSettingsFragment() {
        loadFragment(SettingsFragment.newInstance());
    }

    public void loadAccountOverviewFragment() {
        loadFragment(AccountOverviewFragment.newInstance());
    }

    public void loadAccountFormFragment() {
        loadFragment(AccountFormFragment.newInstance());
    }

    public void loadExpensesOverviewFragment(int accountId) {
        loadFragment(ExpenseOverviewFragment.newInstance(accountId));
    }

    public void loadExpensesFormFragment(int accountId) {
        loadFragment(ExpenseFormFragment.newInstance(accountId));
    }
    public void loadExpensesFormFragment(int accountId, int expenseId) {
        loadFragment(ExpenseFormFragment.newInstance(accountId, expenseId));
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
