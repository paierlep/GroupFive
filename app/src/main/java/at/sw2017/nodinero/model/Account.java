package at.sw2017.nodinero.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = Database.class)
public class Account extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String name;
}
