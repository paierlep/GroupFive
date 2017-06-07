package at.sw2017.nodinero;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GoogleMapInstrumentedTest extends AbstractNoDineroInstrumentedTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "at.sw2017.nodinero";

    private static final int TIMEOUT = 5000;
    private UiDevice mDevice;

    private void openApp() throws  Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                TIMEOUT);

        Context context = InstrumentationRegistry.getContext();
        System.out.println(context);
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                TIMEOUT);
    }

    private void openNavDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
    }

    @Test
    public void checkToolBarMenuClicked() throws Exception {
        openApp();

        onView(withId(R.id.add_account)).perform(click());

        String account_name = "edit test account";
        onView(withId(R.id.account_name)).perform(typeText(account_name), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_expense)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        String expense_name = "map expanse";
        String expense_value = "100.0";

        onView(withId(R.id.expense_name)).perform(typeText(expense_name), closeSoftKeyboard());
        onView(withId(R.id.expense_value)).perform(typeText(expense_value), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withText(expense_name)).check(matches(isDisplayed())).perform(click());
        onView(withText(expense_name)).check(matches(isDisplayed()));
        onView(withText(expense_value)).check(matches(isDisplayed()));


        openNavDrawer();
        onView(withText(R.string.view_map)).perform(click());

        final BySelector bySelector = By.clazz(Pattern.compile(".*")).desc("title_of_marker. snippet_of_marker.").pkg("at.sw2017.nodinero");
        mDevice.wait(Until.hasObject(bySelector), TIMEOUT);

        UiSelector selector = new UiSelector().descriptionContains(expense_name);
        UiObject marker = mDevice.findObject(selector);
        marker.click();
    }

}
