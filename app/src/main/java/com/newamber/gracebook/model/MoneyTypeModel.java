package com.newamber.gracebook.model;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

/**
 * Description: The implementation of TypeModel.<p>
 *
 * Created by Newamber on 2017/5/8.
 */

public class MoneyTypeModel implements TypeModel<MoneyTypePO> {

    private String name;
    private @DrawableRes int imageID;

    public MoneyTypeModel(){}

    public MoneyTypeModel(String moneyTypeName, @DrawableRes int imageID) {
        this.name = moneyTypeName;
        this.imageID =  imageID;
    }

    @Override
    public void saveData() {
        MoneyTypePO entity = new MoneyTypePO();
        entity.moneyTypeName = name;
        entity.moneyTypeImageID = imageID;
        entity.save();
    }

    public void deleteDataByPosition(int position) {
        MoneyTypePO entity = SQLite
                .select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.is(position))
                .querySingle();
        if (entity != null) entity.delete();
    }

    public void deleteAllData() {
        List<MoneyTypePO> record = getAllData();
        for (MoneyTypePO item : record) {
            item.delete();
        }
    }

    @Override
    public List<MoneyTypePO> getAllData() {
        return select().from(MoneyTypePO.class).queryList();
    }
}
