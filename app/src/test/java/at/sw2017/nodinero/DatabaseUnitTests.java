package at.sw2017.nodinero;


import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Database;
import at.sw2017.nodinero.model.Expenses;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseUnitTests {

    @Before
    public void setUp() {
        FlowManager.init(new FlowConfig.Builder(RuntimeEnvironment.application).build());
        FlowManager.getDatabase(Database.class).getWritableDatabase();
    }

    @After
    public void tearDown() {
        FlowManager.destroy();
    }

    @Test
    public void testMe() throws Exception {
        Account account1 = new Account();
        account1.name = "test";
        account1.save();

        List<Account> accs = SQLite.select().from(Account.class).queryList();
        Account acc2 = accs.get(0);
        assertEquals(account1.name, acc2.name);
    }

    @Test
    public void createExpenseTest() throws Exception {
        Account account1 = new Account();
        account1.name = "test";
        account1.save();

        Expenses expenses = new Expenses();
        expenses.name = "test expense";
        expenses.value = 42;
        expenses.date = "2017-04-21";
        expenses.accountId = account1;
        expenses.save();

        List<Expenses> listExpenses = SQLite.select().from(Expenses.class).queryList();
        Expenses testExpense = listExpenses.get(0);

        assertEquals(expenses.name, testExpense.name);
        assertEquals(expenses.accountId, account1);
    }

    @Test
    public void categoryTest() throws Exception {
        Account account1 = new Account();
        account1.name = "test";
        account1.save();

        Category catgeory1 = new Category();
        catgeory1.name = "test category";
        catgeory1.save();

        Expenses expenses = new Expenses();
        expenses.name = "test expense";
        expenses.value = 42;
        expenses.date = "2017-04-21";
        expenses.accountId = account1;
        expenses.categoryId = catgeory1;
        expenses.save();

        List<Expenses> listExpenses = SQLite.select().from(Expenses.class).queryList();
        Expenses testExpense = listExpenses.get(0);


        assertEquals(expenses.name, testExpense.name);
        assertEquals(expenses.categoryId, catgeory1);
    }
}