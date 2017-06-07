package at.sw2017.nodinero.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by cpaier on 05/06/2017.
 */

@Table(database = Database.class)
public class Profile extends BaseModel {
    @Column
    @PrimaryKey
    @Unique
    public String name;

    @Column
    public String value;

    // This is slightly more complicated - because profile acts as a key-value store
    public static String getByName(String name) {
         Profile p = SQLite.select()
                .from(Profile.class)
                .where(Profile_Table.name.eq(name))
                .querySingle();

        if (p != null) {
            return p.value;
        }

        return null;
    }

    public static void storeByName(String name, String value) {
        Profile profile_name = new Profile();
        profile_name.name = name;
        profile_name.value = value;
        profile_name.save();
    }
}