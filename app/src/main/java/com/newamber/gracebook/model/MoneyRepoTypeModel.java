package com.newamber.gracebook.model;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/8.
 */

public class MoneyRepoTypeModel implements TypeModel<MoneyRepoTypePO> {
    private String name;
    private @DrawableRes int imageID;
    private double balance;

    public MoneyRepoTypeModel() {}

    public MoneyRepoTypeModel(String moneyTypeName, @DrawableRes int imageID, double balance) {
        this.name = moneyTypeName;
        this.imageID =  imageID;
        this.balance = balance;
    }

    @Override
    public void saveData() {
        MoneyRepoTypePO entity = new MoneyRepoTypePO();
        entity.moneyRepoTypeName = name;
        entity.moneyRepoTypeImageID = imageID;
        entity.balance = balance;
        entity.save();
    }

    @Override
    public List<MoneyRepoTypePO> getAllData() {
        return SQLite.select().from(MoneyRepoTypePO.class).queryList();
    }
}
