package at.sw2017.nodinero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Database;

public class AccountOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.getDatabase(Database.class).getWritableDatabase();
    }
}
