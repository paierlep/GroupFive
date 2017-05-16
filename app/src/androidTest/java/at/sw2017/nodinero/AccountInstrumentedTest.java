package at.sw2017.nodinero;

import android.os.IBinder;
import android.os.SystemClock;
import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;
import android.view.WindowManager;

import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.Description;

import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.core.deps.guava.base.Predicates.not;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by cpaier on 18/04/2017.
 */



@RunWith(AndroidJUnit4.class)
public class AccountInstrumentedTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void addAccountAndBack() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_account)).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        String account_name = "account test save and back";

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name))
                .perform(typeText(account_name), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save_back), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.account_list)).check(matches(isDisplayed()));

        onView(withText(account_name)).check(matches(isDisplayed()));
    }

    @Test
    public static void addAccount() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_account)).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        String account_name = "account test save";
        onView(withId(R.id.account_name)).perform(typeText(account_name), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save), isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
        onView(withText(R.string.account_overview)).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.account_list)).check(matches(isDisplayed()));

        onView(withText(account_name)).check(matches(isDisplayed()));
    }

    @Test
    public void addAccountCancel() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_account)).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.button_cancel), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
    }

    @Test
    public void deleteAccount() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_account)).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        String account_name = "account test";
        onView(withId(R.id.account_name)).perform(typeText(account_name), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.account_list)).check(matches(isDisplayed()));
        onView(withId(R.id.account_scroll)).check(matches(isDisplayed()));

        onView(withText(account_name)).check(matches(isDisplayed()));

        onView(allOf(withText(R.string.account_delete), isDisplayed())).perform(click());

        onView(withText(account_name)).check(doesNotExist());
    }

    @Test
    public void quickAdd() {

        onView(withId(R.id.menu_profile)).perform(click());


        //onView(withText(R.string.toast)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));


        //onView(withText("Quick")).perform(click());
        //onView(withText("1 Beer added")).check(matches(isDisplayed()));

    }


}
