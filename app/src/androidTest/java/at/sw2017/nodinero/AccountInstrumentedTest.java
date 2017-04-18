package at.sw2017.nodinero;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by cpaier on 18/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class AccountInstrumentedTest {
    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class);

    @Test
    public void addAccount() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText("Add Account")).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.button_save), isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
    }
}
