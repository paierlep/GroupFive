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
        backButtonTemplateFunction(R.string.add_account, R.id.fragment_account_form, R.id.fragment_account_overview);
        backButtonTemplateFunction(R.string.template_overview, R.id.fragment_template_overview, R.id.fragment_account_overview);
        backButtonTemplateFunction(R.string.add_template, R.id.fragment_template_add, R.id.fragment_account_overview);
    }

}
