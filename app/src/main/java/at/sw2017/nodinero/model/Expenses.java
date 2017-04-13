package at.sw2017.nodinero.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.annotation.Table;

import java.sql.Date;


@Table( database = Database.class )
public class Expenses extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public Date date;
    public int amount;
    public String category;








}