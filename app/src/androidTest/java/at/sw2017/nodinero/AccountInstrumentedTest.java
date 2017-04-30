package at.sw2017.nodinero;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.TimeUnit;

import at.sw2017.nodinero.model.Account;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by cpaier on 18/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class AccountInstrumentedTest {
    @Rule
    public ActivityTestRule<NoDineroActivity> mActivityTestRule =
            new ActivityTestRule<>(NoDineroActivity.class);

    @Test
    public void addAccountAndBack() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText("Add Account")).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.button_save_back), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
        //TODO in overview nachschaun ob er angezeigt wird
    }

    @Test
    public void addAccount() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText("Add Account")).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));

        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.button_save), isDisplayed())).perform(click());

        //TODO in overview nachschaun ob er angezeigt wird
        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));
    }

    @Test
    public void addAccountCancel() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText("Add Account")).perform(click());

        onView(withId(R.id.fragment_account_form)).check(matches(isDisplayed()));


        onView(withId(R.id.account_name)).check(matches(isDisplayed()));
        onView(withId(R.id.account_type)).check(matches(isDisplayed()));
        onView(withId(R.id.account_init_balance)).check(matches(isDisplayed()));
        onView(withId(R.id.account_currency)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.button_cancel), isDisplayed())).perform(click());

        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));
    }

    @Test
    public void showAccounts() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText("Overview")).perform(click());


        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.account_scroll)).check(matches(isDisplayed()));
        onView(withId(R.id.account_list)).check(matches(isDisplayed()));


        List<Account> accs = SQLite.select().from(Account.class).queryList();
        for (Account account: accs) {
            onView(withText(account.name)).check(matches(isDisplayed()));
            if(account.currency.equals("EUR"))
                onView(withText("Balance: " + String.valueOf(account.balance) + ".-")).check(matches(isDisplayed()));
            else
                onView(withText("Balance: " +String.valueOf(account.balance) + "$")).check(matches(isDisplayed()));
        }



    }


    @Test
    public void deleteAccount() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(open());

        onView(withText("Overview")).perform(click());


        onView(withId(R.id.fragment_account_overview)).check(matches(isDisplayed()));

        onView(withId(R.id.account_scroll)).check(matches(isDisplayed()));
        onView(withId(R.id.account_list)).check(matches(isDisplayed()));


        List<Account> accs = SQLite.select().from(Account.class).queryList();

        Account acc1 = accs.get(0);

        onView(allOf(withId(acc1.id), isDisplayed())).perform(click());
        onView(withText(acc1.name)).check(doesNotExist());



    }




}
