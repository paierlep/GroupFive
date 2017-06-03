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
import at.sw2017.nodinero.fragment.CategoryFormFragment;
import at.sw2017.nodinero.fragment.CategoryOverviewFragment;
import at.sw2017.nodinero.fragment.ExpenseFormFragment;
import at.sw2017.nodinero.fragment.ExpenseOverviewFragment;
import at.sw2017.nodinero.fragment.MapFragment;
import at.sw2017.nodinero.fragment.ReportFragment;
import at.sw2017.nodinero.fragment.SettingsFragment;
import at.sw2017.nodinero.fragment.TemplateFormFragment;
import at.sw2017.nodinero.fragment.TemplateOverviewFragment;
import at.sw2017.nodinero.model.Database;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

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

        //FlowManager.getDatabase("Database").reset(getContext());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(toolbar);

        loadAccountOverviewFragment();
    }

    public void setToolbarTitle(int stringRes) {
        toolbar.setTitle(stringRes);
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
            case R.id.view_map:
                loadMapFragment();
                break;
            case R.id.view_report:
                loadReportFragment();
                break;
            case R.id.category_overview:
                loadCategoryOverviewFragment();
                break;
            //toolbar
            case R.id.menu_settings:
                loadSettingsFragment();
                break;
            case R.id.account_overview:
                loadAccountOverviewFragment();
                break;
            case R.id.template_overview:
                loadTemplateOverviewFragment();
                break;
            case R.id.menu_profile:
                Toast.makeText(this, "not implemented yet!", Toast.LENGTH_LONG).show();
                break;
            default:
                return false;
        }

        return true;
    }

    private void loadFragment(Fragment fragment) {
        loadFragment(fragment, "no-history");
    }

    private void loadFragment(Fragment fragment, String history) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.addToBackStack(history);

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        int amount = getSupportFragmentManager().getBackStackEntryCount();
        Log.e(TAG, "current back support: " + amount);
    }

    public void loadSettingsFragment() {
        loadFragment(SettingsFragment.newInstance());
    }

    public void loadAccountOverviewFragment() {
        loadFragment(AccountOverviewFragment.newInstance(), "history");
    }

    public void loadCategoryOverviewFragment() {
        loadFragment(CategoryOverviewFragment.newInstance());
    }

    public void loadTemplateFormFragment() {
        loadFragment(TemplateFormFragment.newInstance());
    }

    public void loadTemplateUpdateFormFragment(int id) {
        loadFragment(TemplateFormFragment.newInstance(id));
    }

    public void loadAccountFormFragment() {
        loadFragment(AccountFormFragment.newInstance());
    }

    public void loadCategoryFormFragment() {loadFragment(CategoryFormFragment.newInstance()); }

    public void loadExpensesOverviewFragment(int accountId) {
        loadFragment(ExpenseOverviewFragment.newInstance(accountId), "history");
    }
    public void loadTemplateOverviewFragment() {
        loadFragment(TemplateOverviewFragment.newInstance(), "history");
    }
    public void loadExpensesFormFragment(int accountId) {
        loadFragment(ExpenseFormFragment.newInstance(accountId));
    }
    public void loadExpensesFormFragment(int accountId, int expenseId) {
        loadFragment(ExpenseFormFragment.newInstance(accountId, expenseId));
    }
    public void loadMapFragment() {
        loadFragment(MapFragment.newInstance());
    }

    public void loadReportFragment() {
        loadFragment(ReportFragment.newInstance());
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
        Log.e (TAG, getSupportFragmentManager().getBackStackEntryCount() + "");

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            if(getSupportFragmentManager().getBackStackEntryAt(0).getName().equals("history")) {
                getSupportFragmentManager().popBackStackImmediate();
                getSupportFragmentManager().popBackStackImmediate("history", 0);
            } else {
                getSupportFragmentManager().popBackStackImmediate("history", 0);
            }
        } else {
            finish();
        }
    }
}
