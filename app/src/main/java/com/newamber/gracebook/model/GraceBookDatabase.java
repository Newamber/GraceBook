package com.newamber.gracebook.model;

import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Description: The whole Application's database.<p>
 *
 * Created by Newamber on 2017/4/27.
 */
@SuppressWarnings("all")
@Database(name = GraceBookDatabase.NAME, version = GraceBookDatabase.VERSION)
public class GraceBookDatabase {

    public static final String NAME = "GraceBook";

    public static final int VERSION = 2;

    @Migration(version = 2, database = GraceBookDatabase.class)
    public static class MigrationMoneyType extends AlterTableMigration<MoneyTypePO> {

        public MigrationMoneyType(Class<MoneyTypePO> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "id");
        }
    }

}
