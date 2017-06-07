package at.sw2017.nodinero;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.v4.app.ActivityCompat;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import at.sw2017.nodinero.model.Database;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by cpaier on 04/05/2017.
 */

public abstract class AbstractNoDineroInstrumentedTest {

    Intent intent;
    protected SharedPreferences.Editor editor;

    UiDevice device;

    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class, true, false);

    @Before
    public void setUp() {

        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        intent = new Intent();
        Context context = getInstrumentation().getTargetContext();

        editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean("firstStart", false);
        editor.commit();

        mActivityTestRule.launchActivity(intent);
        FlowManager.getDatabase(Database.class).reset(mActivityTestRule.getActivity());

        if (Build.VERSION.SDK_INT >= 21) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.ACCESS_FINE_LOCATION");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.ACCESS_COARSE_LOCATION");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.CAMERA");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.WRITE_EXTERNAL_STORAGE");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.INTERNET");

            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.ACCESS_NETEWORK_STATE");
        }
    }

    @After
    public void tearDown() throws Exception {
        //FlowManager.getDatabase(Database.class).reset(mActivityTestRule.getActivity());
        FlowManager.destroy();
    }
}
