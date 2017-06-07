package at.sw2017.nodinero;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import at.sw2017.nodinero.model.Database;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
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
public class PasswordInstrumentTest extends AbstractNoDineroInstrumentedTest {

    @Override
    public void tearDown() throws Exception {
        FlowManager.getDatabase(Database.class).reset(mActivityTestRule.getActivity());
        FlowManager.destroy();
    }

    @Test
    public void saveProfile() throws Exception {
        String password = "1234";
        onView(withId(R.id.menu_profile)).perform(click());
        onView(withId(R.id.profile_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.button_save)).check(matches(isDisplayed())).perform(click());

        //pressBack();

        onView(withText(R.string.login));
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.button_login)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.menu_profile)).perform(click());
    }
}

