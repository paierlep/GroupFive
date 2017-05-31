package at.sw2017.nodinero;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import at.sw2017.nodinero.model.Account;
import at.sw2017.nodinero.model.Category;
import at.sw2017.nodinero.model.Expense;
import at.sw2017.nodinero.model.Template;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseUnitTests extends AbstractNoDineroUnitTest {

    @Test
    public void addAccount() throws Exception {
        Account account1 = new Account();
        account1.name = "test";
        account1.initialBalance = 100;
        account1.currency = "EUR";
        account1.type = "Cash";
        account1.save();

        List<Account> accs = SQLite.select().from(Account.class).queryList();
        Account acc2 = accs.get(0);
        assertEquals(account1.name, acc2.name);
        assertEquals(account1.initialBalance, acc2.initialBalance, 0.01f);
        assertEquals(account1.currency,acc2.currency);
        assertEquals(account1.type,acc2.type);
    }

    @Test
    public void createExpenseTest() throws Exception {
        Account account = new Account();
        account.name = "test";
        account.save();

        Expense expense = new Expense();
        expense.name = "test expense";
        expense.value = 42;
        expense.date = "2017-04-21";
        expense.accountId = account;
        expense.save();

        List<Expense> listExpenses = SQLite.select().from(Expense.class).queryList();
        Expense testExpense = listExpenses.get(0);

        assertEquals(expense.name, testExpense.name);
        assertEquals(expense.accountId, account);
    }

    @Test
    public void createTemplateTest() throws Exception {
        Account account = new Account();
        account.name = "test";
        account.save();

        Template template = new Template();
        template.name = "test template";
        template.value = 42;
        template.accountId = account;
        template.save();

        List<Template> listTemplate = SQLite.select().from(Template.class).queryList();
        Template testTemplate = listTemplate.get(0);

        assertEquals(template.name, testTemplate.name);
        assertEquals(template.accountId, account);
    }


    @Test
    public void getBalanceTest() throws Exception {
        Account account = new Account();
        account.name = "test";
        account.save();

        Expense expense = new Expense();
        expense.name = "test expense";
        expense.value = 42;
        expense.date = "2017-04-21";
        expense.accountId = account;

        Expense expense2 = new Expense();
        expense.name = "test expense2";
        expense.value = 40;
        expense.date = "2017-04-21";
        expense.accountId = account;
        expense.save();
        ;

        assertEquals(account.getBalance(), expense.value + expense2.value + account.initialBalance, 0.01f);
    }


    @Test
    public void categoryTest() throws Exception {
        Account account1 = new Account();
        account1.name = "test";
        account1.save();

        Category catgeory1 = new Category();
        catgeory1.name = "test category";
        catgeory1.save();

        Expense expense = new Expense();
        expense.name = "test expense";
        expense.value = 42;
        expense.date = "2017-04-21";
        expense.accountId = account1;
        expense.categoryId = catgeory1;
        expense.save();

        List<Expense> listExpenses = SQLite.select().from(Expense.class).queryList();
        Expense testExpense = listExpenses.get(0);


        assertEquals(expense.name, testExpense.name);
        assertEquals(expense.categoryId, catgeory1);
    }

}