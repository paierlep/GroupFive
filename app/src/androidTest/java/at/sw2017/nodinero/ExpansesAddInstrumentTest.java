package at.sw2017.nodinero;

import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import at.sw2017.nodinero.model.Database;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ExpansesAddInstrumentTest extends AbstractNoDineroInstrumentedTest {

    public void setUpAccount(String account_name)
    {
        onView(withId(R.id.add_account)).perform(click());
        onView(withId(R.id.account_name)).perform(typeText(account_name), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
    }



    public void validExpenseAdd(String name, String value)
    {
        onView(withId(R.id.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name))
                .perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.expense_value))
                .perform(typeText(value), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));
    }
    public void invalidExpenseAdd(String name, String value)
    {
        onView(withId(R.id.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name))
                .perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.expense_value))
                .perform(typeText(value), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cancel)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
    }


    @Test
    public void addExpenseInputTests()
    {
        String account_name ="testaccount";
        setUpAccount(account_name);
        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        validExpenseAdd("test1", "2.0");
        validExpenseAdd("test2", "-10.0");
        validExpenseAdd("test3", "2");
        validExpenseAdd("test4", "-10");

        invalidExpenseAdd("test5", ".");
    }


    @Test
    public void addExpense()
    {
        String account_name = "test";
        setUpAccount(account_name);
        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.add_expense)).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));
        onView(withText(R.string.expense_add_title)).check(matches(isDisplayed()));

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

        onView(withId(R.id.button_save)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());
        onView(withText(R.string.account_overview)).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        //TODO check account form
    }

    @Test
    public void addExpenseAndBack() {

        String account_name = "test";
        setUpAccount(account_name);
        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.add_expense)).perform(click());
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

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        //TODO check account form
    }

    @Test
    public void addExpenseCancel() {

        String account_name = "test";
        setUpAccount(account_name);
        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.add_expense)).check(matches(isDisplayed())).perform(click());

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

        onView(withId(R.id.button_cancel)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));
    }

    @Test
    public void addExpenseFromAccountDetail() {

        String account_name = "test acoount";

        setUpAccount(account_name);

        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_expense)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        String expense_name = "very important expense";
        //TODO other fields
        onView(withId(R.id.expense_name)).perform(typeText(expense_name), closeSoftKeyboard());
        //onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));
        onView(withText(expense_name)).check(matches(isDisplayed()));


    }

    @Test
    public void ExpenseFilterDetail() {

        String account_name = "test acoount";
        String[] expense_name = {"todays expense",
                "last month expense",
                "for 3 month expense",
                "expense from last year"};

        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        String[] filter_name = resources.getStringArray(R.array.expense_filter_array);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DAY_OF_MONTH);

        setUpAccount(account_name);

        //String[] filterArray = R.array.expense_filter_array;
        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_expense)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).perform(clearText(), typeText(expense_name[0]), closeSoftKeyboard());
        onView(withId(R.id.button_save)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.expense_name)).perform(clearText(), typeText(expense_name[1]), closeSoftKeyboard());


        onView(withId(R.id.expense_date_picker))
                .perform(PickerActions.setDate(year, month, day));
        onView(withId(R.id.button_save)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());


        onView(withId(R.id.expense_name)).perform(clearText(), typeText(expense_name[2]), closeSoftKeyboard());


        onView(withId(R.id.expense_date_picker))
                .perform(PickerActions.setDate(year, month - 2, day));
        onView(withId(R.id.button_save)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.expense_name)).perform(clearText(), typeText(expense_name[3]), closeSoftKeyboard());

        onView(withId(R.id.expense_date_picker))
                .perform(PickerActions.setDate(year - 1, month + 1, day));
        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        for(int i = 0; i < expense_name.length; i++)
        {
            onView(withText(expense_name[i])).check(matches(isDisplayed()));
        }

        onView(withId(R.id.expense_filter_spinner))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is(filter_name[1])))
                .perform(click());

        onView(withText(expense_name[0])).check(matches(isDisplayed()));
        onView(withText(expense_name[1])).check(matches(isDisplayed()));
        onView(withText(expense_name[2])).check(doesNotExist());
        onView(withText(expense_name[3])).check(doesNotExist());

        onView(withId(R.id.expense_filter_spinner))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is(filter_name[2])))
                .perform(click());

        onView(withText(expense_name[0])).check(matches(isDisplayed()));
        onView(withText(expense_name[1])).check(matches(isDisplayed()));
        onView(withText(expense_name[2])).check(matches(isDisplayed()));
        onView(withText(expense_name[3])).check(doesNotExist());

        onView(withId(R.id.expense_filter_spinner))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is(filter_name[4])))
                .perform(click());

        onView(withText(expense_name[0])).check(matches(isDisplayed()));
        onView(withText(expense_name[1])).check(matches(isDisplayed()));
        onView(withText(expense_name[2])).check(matches(isDisplayed()));
        onView(withText(expense_name[3])).check(matches(isDisplayed()));
    }
}
