package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.BaseModels;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Description: The implementation of {@link com.newamber.gracebook.base.BaseModels.TypeModel}.<p>
 *
 * Created by Newamber on 2017/5/8.
 */

public class MoneyRepoTypeModel implements BaseModels.TypeModel<MoneyRepoTypePO> {
    private String name;
    private @DrawableRes int imageId;
    private double balance;

    public MoneyRepoTypeModel() {}

    public MoneyRepoTypeModel(String moneyTypeName, @DrawableRes int imageId, double balance) {
        this.name = moneyTypeName;
        this.imageId = imageId;
        this.balance = balance;
    }

    @Override
    public void saveData() {
        MoneyRepoTypePO entity = new MoneyRepoTypePO();
        entity.moneyRepoTypeName = name;
        entity.moneyRepoTypeImageId = imageId;
        entity.balance = balance;
        entity.save();
    }

    @Override
    public void deleteAllData() {
        //getAllData().forEach(BaseModels::delete);
        for (BaseModel data : getAllData()) {
            data.delete();
        }
    }

    @Override
    public List<MoneyRepoTypePO> getAllData() {
        return SQLite.select().from(MoneyRepoTypePO.class).queryList();
    }

    public void deleteDataById(int id) {
        MoneyRepoTypePO entity = SQLite
                .select()
                .from(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.id.is(id))
                .querySingle();
        if (entity != null) entity.delete();
        for (BaseModel data : getAllData()) {
            data.update();
        }
    }

}
