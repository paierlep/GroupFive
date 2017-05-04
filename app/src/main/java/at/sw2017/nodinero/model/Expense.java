package at.sw2017.nodinero.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;


@Table(database = Database.class)
public class Expense extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    @ForeignKey
    public Account accountId;

    @Column
    @ForeignKey
    public Category categoryId;

    @Column
    public String name;
    @Column
    public int value;
    @Column
    public String date;

    //placeholder
    @Column
    public String place;
}