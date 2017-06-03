package at.sw2017.nodinero;

import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExpanseEditInstrumentTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void loadExpenseFromAccountDetail() {


        onView(withId(R.id.add_account)).check(matches(isDisplayed())).perform(click());

        String account_name = "edit test account";
        onView(withId(R.id.account_name)).perform(typeText(account_name), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_expense)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        String expense_name = "very important expense";
        String expense_value = "100.0";
        String expense_cat = "miau";

        onView(withId(R.id.expense_name)).perform(typeText(expense_name), closeSoftKeyboard());
        onView(withId(R.id.expense_value)).perform(typeText(expense_value), closeSoftKeyboard());
        //onView(withId(R.id.expense_category_spinner)).perform(typeText(expense_cat), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withText(expense_name)).check(matches(isDisplayed())).perform(click());

        //check predefined text
        onView(withText(expense_name)).check(matches(isDisplayed()));
        onView(withText(expense_value)).check(matches(isDisplayed()));

        //TODO category is not implemented
        //onView(withText(expense_cat)).check(matches(isDisplayed()));
    }

    @Test
    public void editExpenseFromAccountDetail() {

        onView(withId(R.id.add_account)).check(matches(isDisplayed())).perform(click());

        String account_name = "edit test account";
        onView(withId(R.id.account_name)).perform(typeText(account_name), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withText(account_name)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_expense)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_expense_add)).check(matches(isDisplayed()));

        String expense_name = "very important expense";
        String expense_value = "100.0";
        String expense_cat = "miau";

        onView(withId(R.id.expense_name)).perform(typeText(expense_name), closeSoftKeyboard());
        onView(withId(R.id.expense_value)).perform(typeText(expense_value), closeSoftKeyboard());
        //onView(withId(R.id.expense_category)).perform(typeText(expense_cat), closeSoftKeyboard());

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withText(expense_name)).check(matches(isDisplayed())).perform(click());

        //check predefined text
        onView(withText(expense_name)).check(matches(isDisplayed()));
        onView(withText(expense_value)).check(matches(isDisplayed()));

        //TODO category is not implemented
        //onView(withText(expense_cat)).check(matches(isDisplayed()));

        String expense_name_new = "most important expense";
        String expense_value_new = "150.0";
        String expense_cat_new = "wuff";

        onView(withId(R.id.expense_name)).perform(replaceText(expense_name_new), closeSoftKeyboard());
        onView(withId(R.id.expense_value)).perform(replaceText(expense_value_new), closeSoftKeyboard());
        //onView(withId(R.id.expense_category)).perform(replaceText(expense_cat_new), closeSoftKeyboard());
        onView(withText(R.string.select_picture)).check(matches(isDisplayed()));
        onView(withText(R.string.take_picture)).check(matches(isDisplayed()));

        onView(withId(R.id.button_edit)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fragment_expense_overview)).check(matches(isDisplayed()));

        onView(withText(expense_name)).check(doesNotExist());
        onView(withText(expense_name_new)).check(matches(isDisplayed()));
        onView(withText(expense_value_new)).check(matches(isDisplayed()));
        //TODO category
        //TODO photo
    }
}
