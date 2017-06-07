package at.sw2017.nodinero;

import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ProfileInstrumentTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void saveProfile() {
        String profile_name = "test profile";
        String currency = "EUR";
        String language = "English";

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.account_overview)).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.menu_profile)).perform(click());
        onView(withId(R.id.profile_name)).perform(clearText(), typeText(profile_name), closeSoftKeyboard());

        onView(withId(R.id.profile_default_currency)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(currency))).perform(click());
        onView(withId(R.id.profile_default_currency)).check(matches(withSpinnerText(containsString(currency))));

        onView(withId(R.id.profile_default_language)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(language))).perform(click());
        onView(withId(R.id.profile_default_language)).check(matches(withSpinnerText(containsString(language))));

        onView(withId(R.id.button_save)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
        onView(withText(R.string.template_overview)).perform(click());

        onView(withId(R.id.menu_profile)).perform(click());
        //onView(withId(R.id.profile_name)).perform(clearText(), typeText(profile_name), closeSoftKeyboard());

        onView(withText(currency)).check(matches(isDisplayed()));
        onView(withText(language)).check(matches(isDisplayed()));
        onView(withText(profile_name)).check(matches(isDisplayed()));



        //pressBack();

    }

    @Test
    public void changeLanguage() {
        String language = "German";

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.account_overview)).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.menu_profile)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.profile_default_language)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(language))).perform(click());
        onView(withId(R.id.profile_default_language)).check(matches(withSpinnerText(containsString(language))));

        onView(withId(R.id.button_save)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.menu_profile)).perform(click());

        onView(withText(language)).check(matches(isDisplayed()));
        onView(withText("Speichern")).check(matches(isDisplayed())); // explicitly german version!
    }
}
