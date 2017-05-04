package at.sw2017.nodinero;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.robolectric.RuntimeEnvironment;

import at.sw2017.nodinero.model.Database;

/**
 * Created by cpaier on 04/05/2017.
 */

public class AbstractNoDineroUnitTest {

    @Before
    public void setUp() {
        FlowManager.init(new FlowConfig.Builder(RuntimeEnvironment.application).build());
        FlowManager.getDatabase(Database.class).getWritableDatabase();
    }

    @After
    public void tearDown() {
        FlowManager.destroy();
    }
}
