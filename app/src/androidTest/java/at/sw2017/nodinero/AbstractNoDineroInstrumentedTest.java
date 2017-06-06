package at.sw2017.nodinero;

import android.support.test.rule.ActivityTestRule;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import at.sw2017.nodinero.model.Database;

/**
 * Created by cpaier on 04/05/2017.
 */

public abstract class AbstractNoDineroInstrumentedTest {

    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class);

    @Before
    public void setUp() {
        FlowManager.getDatabase(Database.class).reset(mActivityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        FlowManager.destroy();

    }
}
