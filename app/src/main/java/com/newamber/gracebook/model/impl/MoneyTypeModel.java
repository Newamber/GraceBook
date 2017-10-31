package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.IBaseModel;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

/**
 * Description: The implementation of
 * {@link IBaseModel.TypeModel}.<p>
 *
 * Created by Newamber on 2017/5/8.
 */
public class MoneyTypeModel implements IBaseModel.TypeModel<MoneyTypePO> {

    private String name;
    private @DrawableRes int imageId;

    public MoneyTypeModel() {}

    public MoneyTypeModel(String moneyTypeName, @DrawableRes int imageId) {
        this.name = moneyTypeName;
        this.imageId = imageId;
    }

    @Override
    public void saveRecord() {
        if (isExist(name)) {
            SQLite.update(MoneyTypePO.class)
                    .set(MoneyTypePO_Table.moneyTypeImageId.is(imageId))
                    .where(MoneyTypePO_Table.moneyTypeName.is(name))
                    .execute();
        } else {
            MoneyTypePO record = new MoneyTypePO();
            record.id = getAllRecords().size() + 1;
            record.moneyTypeName = name;
            record.moneyTypeImageId = imageId;
            record.save();
        }
    }

    @Override
    public void deleteAllRecords() {
        SQLite.delete(MoneyTypePO.class).execute();
    }

    @Override
    public List<MoneyTypePO> getAllRecords() {
        return SQLite.select()
                .from(MoneyTypePO.class)
                .orderBy(MoneyTypePO_Table.id, true)
                .queryList();
    }

    @Override
    public void deleteRecordById(int id) {
        SQLite.delete(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.is(id))
                .execute();

        List<MoneyTypePO> lists = select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.greaterThan(id))
                .queryList();
        if (!lists.isEmpty()) {
            for (MoneyTypePO record : lists) {
                record.id -= 1;
                record.update();
            }
        }
    }

    @Override
    public void dragToSwap(int fromId, int toId) {
        MoneyTypePO fromData = queryById(fromId);
        assert fromData != null;

        if (fromId > toId) {
            // I don't know why, the SQLite.update(...).set(...minus or plus )
            // doesn't work... So I use this clumsy method.
            List<MoneyTypePO> recordList = SQLite.select()
                    .from(MoneyTypePO.class)
                    .where(MoneyTypePO_Table.id.greaterThanOrEq(toId))
                    .and(MoneyTypePO_Table.id.lessThan(fromId))
                    .queryList();
            for (MoneyTypePO record : recordList) {
                record.id += 1;
                record.update();
            }
        } else {
            List<MoneyTypePO> recordList = SQLite.select()
                    .from(MoneyTypePO.class)
                    .where(MoneyTypePO_Table.id.greaterThan(fromId))
                    .and(MoneyTypePO_Table.id.lessThanOrEq(toId))
                    .queryList();
            for (MoneyTypePO record : recordList) {
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
    public MoneyTypePO queryByName(String typeName) {
        return SQLite.select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.moneyTypeName.is(typeName))
                .querySingle();
    }

    private MoneyTypePO queryById(int id) {
        return SQLite.select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.is(id))
                .querySingle();
    }
}
