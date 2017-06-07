package at.sw2017.nodinero;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;


import at.sw2017.nodinero.fragment.AccountFormFragment;
import at.sw2017.nodinero.fragment.AccountOverviewFragment;
import at.sw2017.nodinero.fragment.CategoryFormFragment;
import at.sw2017.nodinero.fragment.CategoryOverviewFragment;
import at.sw2017.nodinero.fragment.ExpenseFormFragment;
import at.sw2017.nodinero.fragment.ExpenseOverviewFragment;
import at.sw2017.nodinero.fragment.MapFragment;
import at.sw2017.nodinero.fragment.ReportFragment;
import at.sw2017.nodinero.fragment.PasswordFragment;
import at.sw2017.nodinero.fragment.ProfileFragment;
import at.sw2017.nodinero.fragment.TemplateFormFragment;
import at.sw2017.nodinero.fragment.TemplateOverviewFragment;
import at.sw2017.nodinero.model.Database;
import at.sw2017.nodinero.model.Profile;

public class NoDineroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public final String TAG = "NoDineroActivity";

    private NavigationView navigation;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_overview);
        initDb();
        checkLocale();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isfirstStart = getPrefs.getBoolean("firstStart", true);

                if (isfirstStart) {
                    startActivity(new Intent(NoDineroActivity.this,WelcomeActivity.class));
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart",false);
                    e.apply();
                }

            }
        });

        thread.start();

        //FlowManager.getDatabase("Database").reset(getContext());

        toolbar = (Toolbar) findViewById(R.id.menu_bar);

        String password = Profile.getByName("password");
        if (!loggedIn && password != null && password.length() > 0) {
            loadPasswordFragment();
            return;
        }

        //FlowManager.getDatabase("Database").reset(getContext());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

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
            case R.id.menu_profile:
                loadProfileFragment();
                break;
            case R.id.account_overview:
                loadAccountOverviewFragment();
                break;
            case R.id.template_overview:
                loadTemplateOverviewFragment();
                break;
            default:
                return false;
        }

        return true;
    }

    private void loadFragment(Fragment fragment) {
        loadFragment(fragment, getResources().getString(R.string.no_back_stack));
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_content, fragment);

        //if(!tag.equals(getResources().getString(R.string.no_back_stack))) {
            addToStackTree(tag);
        //}

        fragmentTransaction.addToBackStack(tag);

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        int amount = getSupportFragmentManager().getBackStackEntryCount();
        Log.e(TAG, "current back support: " + amount + " || " + tag);
    }

    Deque<String> backStack = new ArrayDeque<String>();

    private void clearBackStack()
    {
        while(!backStack.isEmpty())
        {
            backStack.pop();
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void addToStackTree(String tag)
    {
        //first entry
        if(backStack.isEmpty() /*&& tag.equals(AccountOverviewFragment.TAG)*/)
        {
            backStack.push(tag);
            return;
        }

        if(backStack.peek().equals(getResources().getString(R.string.no_back_stack)))
        {
            getSupportFragmentManager().popBackStackImmediate();
            backStack.pop();
        }

        if(backStack.peek().equals(tag))
        {
            getSupportFragmentManager().popBackStackImmediate();
            backStack.pop();
        }
        backStack.push(tag);
    }


    public void loadProfileFragment() {
        loadFragment(ProfileFragment.newInstance(), ProfileFragment.TAG);
    }

    public void loadAccountOverviewFragment() {
        loadFragment(AccountOverviewFragment.newInstance(), AccountOverviewFragment.TAG);
    }

    public void loadCategoryOverviewFragment() {
        loadFragment(CategoryOverviewFragment.newInstance(), CategoryOverviewFragment.TAG);
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
        loadFragment(ExpenseOverviewFragment.newInstance(accountId), ExpenseOverviewFragment.TAG);
    }
    public void loadTemplateOverviewFragment() {
        loadFragment(TemplateOverviewFragment.newInstance(), TemplateOverviewFragment.TAG);
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

    public void loadPasswordFragment() {
        loadFragment(PasswordFragment.newInstance(), PasswordFragment.TAG);
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

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawers();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStackImmediate();
                backStack.pop();
        } else {
            finish();
        }
    }

    public boolean checkLocale() {

        String userLanguage = Profile.getByName("language");

        if (userLanguage == null || userLanguage.length() == 0) {
            return false;
        }

        String currentLang = getResources().getConfiguration().locale.getLanguage();
        String[] languages = getResources().getStringArray(R.array.languages_short);
        String language = languages[Integer.parseInt(userLanguage)];

        if (!currentLang.equals(new Locale(language).getLanguage())) {
            setLocale(language);
            return true;
        }
        return false;
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getBaseContext().getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());

        /*Intent refresh = new Intent(this, NoDineroActivity.class);
        startActivity(refresh);
        finish();*/
        //restartActivity();
    }

    public void restartActivity()
    {
        Intent refresh = new Intent(this, NoDineroActivity.class);
        startActivity(refresh);
        finish();
    }
    public void setIsLoggedIn() {
        this.loggedIn = true;

        clearBackStack();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(toolbar);

        loadAccountOverviewFragment();
    }
}
