package at.sw2017.nodinero.model;

@com.raizlabs.android.dbflow.annotation.Database(name = Database.NAME, version = Database.VERSION)
public class Database {
    public static final String NAME = "Database";
    public static final int VERSION = 1;

    /*
    @Migration(version = 2, database = Database.class)
    public static class InitMigration extends AlterTableMigration<Account> {

        public InitMigration(Class<Account> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "id");
        }
    }
    */
}