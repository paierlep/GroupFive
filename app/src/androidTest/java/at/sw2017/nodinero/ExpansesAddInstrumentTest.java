package at.sw2017.nodinero;

import android.support.design.widget.TextInputEditText;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import at.sw2017.nodinero.model.Database;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ExpansesAddInstrumentTest {
    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class);

    @Test
    public void addExpense()
    {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_account_type_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_category_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_date_picker)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.button_save)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cancel)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));

        String expense_test = "Beer";

        onView(withId(R.id.expense_name)).perform(typeText(expense_test));

        onView(withText(R.id.button_save)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.account_overview)).perform(click());
        onView(withId(R.id.account_overview)).check(matches(isDisplayed()));

        //TODO check account form
    }

    @Test
    public void addExpenseAndBack() {

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_account_type_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_category_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_date_picker)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.button_save)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cancel)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));

        String expense_test = "Beer";

        onView(withId(R.id.expense_name)).perform(typeText(expense_test));

        onView(withText(R.id.button_save_back)).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        //TODO check account form
    }

    @Test
    public void addExpenseCancel() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_account_type_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_category_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_date_picker)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.button_save)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cancel)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));

        onView(withId(R.id.button_cancel)).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

    }
}
