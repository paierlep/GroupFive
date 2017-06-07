package at.sw2017.nodinero;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onData;
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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
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

        onView(withText(R.string.category_overview)).perform(click());

        onView(withId(R.id.add_category)).perform(click());

        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));

        onView(withId(R.id.category_name)).check(matches(isDisplayed()));

        String category_name = "category test save";
        onView(withId(R.id.category_name)).perform(typeText(category_name), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save), isDisplayed())).perform(click());
        onView(withId(R.id.fragment_category_form)).check(matches(isDisplayed()));


        onView(withId(R.id.drawer_layout))
                    .check(matches(isClosed(Gravity.START)))
                    .perform(open());

        onView(withText(R.string.view_report)).perform(click());


        android.content.res.Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        String[] filter_name = resources.getStringArray(R.array.expense_filter_array);

        onView(withText(R.string.report_account)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.date_filter_spinner_acc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[0]))).perform(click());
        onView(withId(R.id.date_filter_spinner_acc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[1]))).perform(click());
        onView(withId(R.id.date_filter_spinner_acc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[2]))).perform(click());
        onView(withId(R.id.date_filter_spinner_acc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[3]))).perform(click());
        onView(withId(R.id.date_filter_spinner_acc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[4]))).perform(click());

        onView(withText(R.string.report_category)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.date_filter_spinner_cat)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[0]))).perform(click());
        onView(withId(R.id.date_filter_spinner_cat)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[1]))).perform(click());
        onView(withId(R.id.date_filter_spinner_cat)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[2]))).perform(click());
        onView(withId(R.id.date_filter_spinner_cat)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[3]))).perform(click());
        onView(withId(R.id.date_filter_spinner_cat)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(filter_name[4]))).perform(click());
    }
}




