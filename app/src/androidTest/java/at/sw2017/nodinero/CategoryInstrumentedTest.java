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
 * Created by ko on 20.05.17.
 */

@RunWith(AndroidJUnit4.class)
public class CategoryInstrumentedTest extends AbstractNoDineroInstrumentedTest {

    public void addCategoryAndBack() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_category)).perform(click());

        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));

        String category_name = "category test save and back";

        onView(withId(R.id.category_name)).check(matches(isDisplayed()));

        onView(withId(R.id.category_name))
                .perform(typeText(category_name), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save_back), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_category_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.category_list)).check(matches(isDisplayed()));

        onView(withText(category_name)).check(matches(isDisplayed()));
    }

    @Test
    public static void addCategory() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_category)).perform(click());

        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));

        onView(withId(R.id.category_name)).check(matches(isDisplayed()));

        String account_name = "category test save";
        onView(withId(R.id.category_name)).perform(typeText(category_name), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save), isDisplayed())).perform(click());
        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
        onView(withText(R.string.category_overview)).perform(click());

        onView(withId(R.id.fragment_category_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.category_list)).check(matches(isDisplayed()));

        onView(withText(category_name)).check(matches(isDisplayed()));
    }

    @Test
    public void addCategoryCancel() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_category)).perform(click());

        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));

        onView(withId(R.id.category_name)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.button_cancel), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_category_overview)).check(matches(isDisplayed()));
    }

    @Test
    public void deleteCategory() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_category)).perform(click());

        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));

        onView(withId(R.id.category_name)).check(matches(isDisplayed()));

        String account_name = "category test";
        onView(withId(R.id.category_name)).perform(typeText(account_name), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_category_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.category_list)).check(matches(isDisplayed()));
        onView(withId(R.id.category_scroll)).check(matches(isDisplayed()));

        onView(withText(category_name)).check(matches(isDisplayed()));

        onView(allOf(withText(R.string.category_delete), isDisplayed())).perform(click());

        onView(withText(category_name)).check(doesNotExist());
    }
}
