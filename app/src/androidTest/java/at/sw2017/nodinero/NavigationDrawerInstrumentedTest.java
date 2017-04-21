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

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NavigationDrawerInstrumentedTest {

    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class);
    
    @Test
    public void checkIfMenuDrawerAddAccountExistsAndCanBeClicked() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_account)).perform(click());
        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfMenuDrawerAddExpenseExistsAndCanBeClicked() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));
    }
}
