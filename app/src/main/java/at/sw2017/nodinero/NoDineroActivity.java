package at.sw2017.nodinero;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import at.sw2017.nodinero.model.Database;

public class NoDineroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public final String TAG = "NoDineroActivity";

    private NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_overview);

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.getDatabase(Database.class).getWritableDatabase();

        navigation = (NavigationView) findViewById(R.id.nav_view);

        navigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "pushed menu button " + item.getTitle());
        return true;
    }
}
