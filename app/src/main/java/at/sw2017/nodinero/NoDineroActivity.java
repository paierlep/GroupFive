package at.sw2017.nodinero;

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
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import at.sw2017.nodinero.fragment.AccountFormFragment;
import at.sw2017.nodinero.fragment.AccountOverviewFragment;
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

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.getDatabase(Database.class).getWritableDatabase();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.menu_bar);
        setSupportActionBar(toolbar);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment;
        switch (id) {
            case R.id.add_account:
                fragment = AccountFormFragment.newInstance();
                break;
            case R.id.menu_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.account_overview:
                fragment = AccountOverviewFragment.newInstance();
                break;
            case R.id.menu_profile:
                Toast.makeText(this, "not implemented yet!", Toast.LENGTH_LONG).show();
            default:
                return false;
        }

        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
        return true;
    }

}
