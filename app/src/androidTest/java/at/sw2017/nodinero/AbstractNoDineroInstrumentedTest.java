package at.sw2017.nodinero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import at.sw2017.nodinero.model.Database;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by cpaier on 04/05/2017.
 */

public abstract class AbstractNoDineroInstrumentedTest {

    Intent intent;
    protected SharedPreferences.Editor editor;

    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class, true, false);

    @Before
    public void setUp() {
        intent = new Intent();
        Context context = getInstrumentation().getTargetContext();

        editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean("firstStart", false);
        editor.commit();


        mActivityTestRule.launchActivity(intent);
        FlowManager.getDatabase(Database.class).reset(mActivityTestRule.getActivity());

    }

    @After
    public void tearDown() throws Exception {
        //FlowManager.getDatabase(Database.class).reset(mActivityTestRule.getActivity());
        FlowManager.destroy();
    }
}
