package at.sw2017.nodinero;

import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class TemplateAddInstrumentTest extends AbstractNoDineroInstrumentedTest {

    @Test
    public void templateValues()
    {
        addAccount();
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        String Validval = "-10.0";
        String invalidval = ".";

        onView(withText(R.string.template_overview)).perform(click());
        onView(withId(R.id.add_template)).perform(click());

        onView(withId(R.id.expense_name))
                .perform(typeText("test1"), closeSoftKeyboard());
        onView(withId(R.id.expense_value))
                .perform(typeText(Validval), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_template)).perform(click());
        onView(withId(R.id.expense_name))
                .perform(typeText("test1"), closeSoftKeyboard());
        onView(withId(R.id.expense_value))
                .perform(typeText(invalidval), closeSoftKeyboard());
        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_template_add)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cancel)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());


    }

    @Test
    public void addTemplate()
    {
        addAccount();

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());


        onView(withText(R.string.template_overview)).perform(click());
        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_template)).perform(click());
        onView(withId(R.id.fragment_template_add)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_account_type_spinner)).check(matches(isDisplayed()));
        //TODO onView(withId(R.id.expense_category_spinner)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.button_save)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cancel)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save_back)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));

        String template_test = "Beer";

        onView(withId(R.id.expense_name)).perform(typeText(template_test));
        onView(withId(R.id.expense_value)).perform(typeText("-10.0"));

        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed()));
        onView(withId(R.id.button_save_back)).perform(scrollTo()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));


        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.account_overview)).perform(click());

        onView(withText(template_test)).check((matches(isDisplayed())));

    }
    @Test
    public void checkTemplate()
    {
        addTemplate();


        onView(withText("Beer")).check((matches(isDisplayed()))).perform(click());
        onView(withText("-10.0")).check((matches(isDisplayed())));


    }
    @Test
    public void addTemplateAndBack() {
        addAccount();
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.template_overview)).perform(click());
        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_template)).perform(click());
        onView(withId(R.id.fragment_template_add)).check(matches(isDisplayed()));

        String template_name = "Beer123";
        String template_value = "2";


        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name))
                .perform(typeText(template_name), closeSoftKeyboard());

        onView(withId(R.id.expense_value))
                .perform(typeText(template_value), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save_back), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.template_list)).check(matches(isDisplayed()));

        onView(withText(template_name)).check(matches(isDisplayed()));
    }

    @Test
    public void addTemplateAndEdit() {
        addAccount();
        addCategory();
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText(R.string.template_overview)).perform(click());
        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.add_template)).perform(click());
        onView(withId(R.id.fragment_template_add)).check(matches(isDisplayed()));

        String template_name = "Beer123";
        String template_value = "2";


        onView(withId(R.id.expense_name)).check(matches(isDisplayed()));
        onView(withId(R.id.expense_value)).check(matches(isDisplayed()));

        onView(withId(R.id.expense_name))
                .perform(typeText(template_name), closeSoftKeyboard());

        onView(withId(R.id.expense_value))
                .perform(typeText(template_value), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_save_back), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.template_list)).check(matches(isDisplayed()));

        onView(withText(template_name)).check(matches(isDisplayed()));
        onView(withText("Beer123")).perform(click());
        onView(withId(R.id.fragment_template_add)).check(matches(isDisplayed()));

        // Check if Category is displayed correctly
        onView(withText("category test save")).check(matches(isDisplayed()));


        onView(withId(R.id.expense_name))
                .perform(typeText(" Special"), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_edit), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_template_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.template_list)).check(matches(isDisplayed()));

        onView(withText("Beer123 Special")).check(matches(isDisplayed()));
    }

    public void addAccount() {


        onView(withId(R.id.add_account)).perform(click());

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
    public void addCategory() {
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
        onView(withText(R.string.category_overview)).perform(click());

        onView(withId(R.id.fragment_category_overview)).check(matches(isDisplayed()));
        onView(withId(R.id.category_list)).check(matches(isDisplayed()));

        onView(withText(category_name)).check(matches(isDisplayed()));
    }
}
