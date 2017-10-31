package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.IBaseModel;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import static com.newamber.gracebook.util.NumericUtil.add;
import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

/**
 * Description: The implementation of {@link IBaseModel.TypeModel}.<p>
 *
 * Created by Newamber on 2017/5/8.
 */

public class MoneyRepoTypeModel implements IBaseModel.TypeModel<MoneyRepoTypePO> {
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
    public void saveRecord() {
        if (isExist(name)) {
            SQLite.update(MoneyRepoTypePO.class)
                    .set(MoneyRepoTypePO_Table.moneyRepoTypeImageId.eq(imageId))
                    .where(MoneyRepoTypePO_Table.moneyRepoTypeName.is(name))
                    .execute();
        } else {
            MoneyRepoTypePO record = new MoneyRepoTypePO();
            record.id = getAllRecords().size() + 1;
            record.moneyRepoTypeName = name;
            record.moneyRepoTypeImageId = imageId;
            record.balance = balance;
            record.save();
        }
    }

    @Override
    public void deleteAllRecords() {
        SQLite.delete(MoneyRepoTypePO.class).execute();
    }

    @Override
    public List<MoneyRepoTypePO> getAllRecords() {
        return SQLite.select()
                .from(MoneyRepoTypePO.class)
                .orderBy(MoneyRepoTypePO_Table.id, true)
                .queryList();
    }

    @Override
    public void deleteRecordById(int id) {
        SQLite.delete(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.id.is(id))
                .execute();

        List<MoneyRepoTypePO> lists = select()
                        .from(MoneyRepoTypePO.class)
                        .where(MoneyRepoTypePO_Table.id.greaterThan(id))
                        .queryList();
        if (!lists.isEmpty()) {
            for (MoneyRepoTypePO record : lists) {
                record.id -= 1;
                record.update();
            }
        }
    }

    @Override
    public void dragToSwap(int fromId, int toId) {
        MoneyRepoTypePO fromData = queryById(fromId);
        assert fromData != null;

        if (fromId > toId) {
            List<MoneyRepoTypePO> recordList = SQLite.select()
                    .from(MoneyRepoTypePO.class)
                    .where(MoneyRepoTypePO_Table.id.greaterThanOrEq(toId))
                    .and(MoneyRepoTypePO_Table.id.lessThan(fromId))
                    .queryList();
            for (MoneyRepoTypePO record : recordList) {
                record.id += 1;
                record.update();
            }
        } else {
            List<MoneyRepoTypePO> recordList = SQLite.select()
                    .from(MoneyRepoTypePO.class)
                    .where(MoneyRepoTypePO_Table.id.greaterThan(fromId))
                    .and(MoneyRepoTypePO_Table.id.lessThanOrEq(toId))
                    .queryList();
            for (MoneyRepoTypePO record : recordList) {
                record.id -= 1;
                record.update();
            }
        }

        fromData.id = toId;
        fromData.update();
    }

    @Override
    public boolean isExist(String typeName) {
        return queryByName(typeName) != null;
    }

    @Override
    public MoneyRepoTypePO queryByName(String typeName) {
        return SQLite.select()
                .from(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.moneyRepoTypeName.is(typeName))
                .querySingle();
    }

    public void updateBalance(String name, Double amount) {
        MoneyRepoTypePO record = queryByName(name);
        if (record != null) {
            record.balance = add(record.balance, amount);
            record.update();
        }
    }

    // ----------------------------------private API------------------------------------------------
    private MoneyRepoTypePO queryById(int id) {
        return SQLite.select()
                .from(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.id.is(id))
                .querySingle();
    }
}