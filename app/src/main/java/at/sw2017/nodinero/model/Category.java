package at.sw2017.nodinero.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;


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



}