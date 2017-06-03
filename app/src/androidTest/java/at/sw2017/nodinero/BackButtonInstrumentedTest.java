package at.sw2017.nodinero;

import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class BackButtonInstrumentedTest extends AbstractNoDineroInstrumentedTest {

    public void backButtonTemplateFunction(int buttonText, int newFragment, int targetFragment) {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
        onView(withText(buttonText)).perform(click());

        onView(withId(newFragment)).check(matches(isDisplayed())).perform(pressBack());
        onView(withId(targetFragment)).check(matches(isDisplayed()));
    }


    @Test
    public void BackToAccountOverview() {

        onView(withId(R.id.add_account)).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed())).perform(pressBack());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        backButtonTemplateFunction(R.string.template_overview, R.id.fragment_template_overview, R.id.fragment_account_overview);

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
        onView(withText(R.string.template_overview)).perform(click());

        backButtonTemplateFunction(R.string.account_overview, R.id.fragment_account_overview, R.id.fragment_template_overview);

    }

    @Test
    public void CloseDrawerMenu() {

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open())
                .check(matches(isDisplayed()))
                .perform(pressBack());

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START)));
    }

    @Test
    public void OverviewToAddAndBack() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.category_overview))
                .perform(click());

        onView(withId(R.id.add_category)).perform(click());
        onView(withId(R.id.fragment_category_form))
                .check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.template_overview))
                .perform(click());

        onView(withId(R.id.fragment_template_overview))
                .check(matches(isDisplayed()))
                .perform(pressBack());

        onView(withId(R.id.fragment_category_overview))
                .check(matches(isDisplayed()));
    }

}
