package at.sw2017.nodinero.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = Database.class)
public class Account extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public int currencyId;

    @Column
    public String name;

    @Column
    public float initialBalance;

    @Column
    public String type;

    @Column
    public String currency;


    public float getBalance()
    {
        float res = initialBalance;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.accountId_id.eq(id)).queryList())
        {
            res += exp.value;
        }
        return res;
    }
    public float getSpendings()
    {
        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.accountId_id.eq(id)).queryList())
        {
            if(exp.value < 0)
                res += (float)exp.value;
        }
        return res;
    }

    public float getIncome()
    {
        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.accountId_id.eq(id)).queryList())
        {
            if(exp.value > 0)
                res += (float)exp.value;
        }
        return res;

    }

}
