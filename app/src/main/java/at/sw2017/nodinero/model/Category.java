package at.sw2017.nodinero.model;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import at.sw2017.nodinero.R;


@Table(database = Database.class)
public class Category extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String name;

    public float getSpendings()
    {
        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.categoryId_id.eq(id)).queryList())
        {
            if(exp.value < 0)
                res += (float)exp.value;
        }
        return res;
    }

    public float getIncome()
    {
        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.categoryId_id.eq(id)).queryList())
        {
            if(exp.value > 0)
                res += (float)exp.value;
        }
        return res;

    }


    public float getSpendingsBetween(String currentDate, String otherDate, SimpleDateFormat sdf)
    {
        long filterDate = 0, filterDate2 = 0;
        try {
            filterDate = sdf.parse(currentDate).getTime();
            filterDate2 = sdf.parse(otherDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.categoryId_id.eq(id)).queryList())
        {
            long date = 0;
            try {
               date = sdf.parse(exp.date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(exp.value > 0 && (date >= filterDate2) && (date < filterDate)) {
                res += (float) exp.value;
            }
        }

        return res;
    }

    public float getIncomeBetween(String currentDate, String otherDate, SimpleDateFormat sdf)
    {
        long filterDate = 0, filterDate2 = 0;
        try {
            filterDate = sdf.parse(currentDate).getTime();
            filterDate2 = sdf.parse(otherDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        float res = 0.0f;
        for(Expense exp : SQLite.select().from(Expense.class).where(Expense_Table.categoryId_id.eq(id)).queryList())
        {
            long date = 0;
            try {
               date = sdf.parse(exp.date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(exp.value > 0 && (date >= filterDate2) && (date < filterDate)) {
                res += (float) exp.value;
            }
        }
        return res;

    }


}