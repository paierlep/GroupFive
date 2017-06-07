package at.sw2017.nodinero;

import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class ReportInstrumentedTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void checkifExists() throws Exception {

        onView(withId(R.id.add_account)).perform(click());
        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));
        String account_name = "account test";
        onView(withId(R.id.account_name))
                .perform(typeText(account_name), closeSoftKeyboard());
        onView(allOf(withId(R.id.button_save_back), isDisplayed())).perform(click());

        onView(withId(R.id.drawer_layout))
                    .check(matches(isClosed(Gravity.START)))
                    .perform(open());

        onView(withText(R.string.view_report)).perform(click());

        onView(withText(R.string.report_account)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.chart1)).check(matches(isDisplayed()));
        onView(withText(R.string.report_category)).check(matches(isDisplayed())).perform(click());
    }
}




