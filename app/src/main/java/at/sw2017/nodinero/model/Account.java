package at.sw2017.nodinero.model;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


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

    public float getExpensesBetween(String currentDate, String otherDate, SimpleDateFormat sdf)
    {
        long filterDate = 0, filterDate2 = 0;
        try {
            filterDate = sdf.parse(currentDate).getTime();
            filterDate2 = sdf.parse(otherDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.accountId_id.eq(id)).queryList())
        {
            long date = 0;
            try {
                date = sdf.parse(exp.date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if((date >= filterDate2) && (date <= filterDate)) {
                res += (float) exp.value;
            }
        }
        return res;

    }


}
