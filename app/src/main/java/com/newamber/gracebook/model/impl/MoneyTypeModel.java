package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.model.TypeModel;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Description: The implementation of TypeModel.<p>
 *
 * Created by Newamber on 2017/5/8.
 */

public class MoneyTypeModel implements TypeModel<MoneyTypePO> {

    private String name;
    private @DrawableRes int imageId;

    public MoneyTypeModel(){}

    public MoneyTypeModel(String moneyTypeName, @DrawableRes int imageId) {
        this.name = moneyTypeName;
        this.imageId = imageId;
    }

    @Override
    public void saveData() {
        MoneyTypePO entity = new MoneyTypePO();
        entity.moneyTypeName = name;
        entity.moneyTypeImageId = imageId;
        entity.save();
    }

    @Override
    public void deleteAllData() {
        //getAllData().forEach(BaseModel::delete);
        for (BaseModel data : getAllData()) {
            data.delete();
        }
    }

    @Override
    public List<MoneyTypePO> getAllData() {
        return SQLite.select()
                .from(MoneyTypePO.class)
                .orderBy(MoneyTypePO_Table.id, true)
                .queryList();
    }

    // TODO: solve the id problems.
    public void deleteDataById(int id) {
        MoneyTypePO entity = SQLite
                .select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.is(id))
                .querySingle();

        // make the id be normal
        if (id == 1) {
            for (MoneyTypePO data : getAllData()) if (data != null) data.id -= 1;
        } else {
            List<MoneyTypePO> dataList = SQLite
                    .select()
                    .from(MoneyTypePO.class)
                    .where(MoneyTypePO_Table.id.greaterThan(id))
                    .queryList();
            //dataList.size()
            for (MoneyTypePO data : dataList) if (data != null) data.id -= 1;
        }
        if (entity != null) entity.delete();
    }

    public void exchangePostion() {

    }
}
