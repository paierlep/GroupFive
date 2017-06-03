package at.sw2017.nodinero;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActionBarInstrumentedTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void checkToolBarExists() {
        onView(withId(R.id.menu_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void checkToolBarMenuClicked() {
        onView(withText("Settings")).perform(click());
        onView(withId(R.id.menu_profile)).perform(click());
    }

    @Test
    public void checkToolBarTitles() {
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.START)))
            .perform(open());

        onView(withText(R.string.account_overview)).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.account_overview_title), withParent(withId(R.id.menu_bar))))
                .check(matches(isDisplayed()));


        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.START)))
            .perform(open());

        onView(withText(R.string.template_overview)).perform(click());
        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));
        onView(allOf(withText(R.string.template_overview_title), withParent(withId(R.id.menu_bar))))
                .check(matches(isDisplayed()));
    }
}