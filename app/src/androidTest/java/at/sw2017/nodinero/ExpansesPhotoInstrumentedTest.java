package at.sw2017.nodinero;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.view.Gravity;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.sw2017.nodinero.model.Database;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class ExpansesPhotoInstrumentedTest {

    @Rule
    public IntentsTestRule<NoDineroActivity> mActivity =
            new IntentsTestRule<NoDineroActivity>(NoDineroActivity.class);
    Intent intent;
    protected SharedPreferences.Editor editor;

    UiDevice device;


    @Before
    public void setUp() {
        Context context = getInstrumentation().getTargetContext();

        editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean("firstStart", false);
        editor.commit();

        FlowManager.getDatabase(Database.class).reset(mActivity.getActivity());

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

    @Test
    public void addExpensePhoto()
    {
        onView(withId(R.id.add_account)).perform(click());
        onView(withId(R.id.account_name)).perform(typeText("testaccount"), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withText("testaccount")).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.add_expense)).perform(click());

        onView(withText(R.string.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera2")).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.button_image)).perform(click());

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage("com.android.camera2"));

    }
}
