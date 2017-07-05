package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;
import android.util.Log;

import com.newamber.gracebook.base.BaseModels;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO_Table;
import com.newamber.gracebook.util.ToastUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

/**
 * Description: The implementation of
 * {@link com.newamber.gracebook.base.BaseModels.TypeModel}.<p>
 *
 * Created by Newamber on 2017/5/8.
 */

public class MoneyTypeModel implements BaseModels.TypeModel<MoneyTypePO> {

    private String name;
    private @DrawableRes int imageId;

    public MoneyTypeModel() {}

    public MoneyTypeModel(String moneyTypeName, @DrawableRes int imageId) {
        this.name = moneyTypeName;
        this.imageId = imageId;
    }

    @Override
    public void saveData() {
        if (isExist(name)) {
            SQLite.update(MoneyTypePO.class)
                    .set(MoneyTypePO_Table.moneyTypeImageId.is(imageId))
                    .where(MoneyTypePO_Table.moneyTypeName.is(name))
                    .execute();
        } else {
            MoneyTypePO entity = new MoneyTypePO();
            entity.id = getAllData().size() + 1;
            entity.moneyTypeName = name;
            entity.moneyTypeImageId = imageId;
            entity.save();
        }
    }

    @Override
    public void deleteAllData() {
        SQLite.delete(MoneyTypePO.class).execute();
    }

    @Override
    public List<MoneyTypePO> getAllData() {
        return select()
                .from(MoneyTypePO.class)
                .orderBy(MoneyTypePO_Table.id, true)
                .queryList();
    }

    // TODO: solve the id problems.
    @Override
    public void deleteDataById(int id) {
        SQLite.delete(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.is(id))
                .execute();
        ToastUtil.showShort("现在的删除的id是" + id, ToastUtil.ToastMode.INFO);

        List<MoneyTypePO> lists = select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.greaterThan(id))
                .queryList();
        if (!lists.isEmpty()) {
            for (MoneyTypePO data : lists) {
                data.id -= 1;
                data.update();
                Log.d("删除成功", "后面的id依次减一，分别是" + data.id);
            }
        }
    }

    @Override
    public void dragSwap(int fromId, int toId) {
        // TODO: ...solve
        /*MoneyTypePO fromData = queryById(fromId);
        MoneyTypePO toData = queryById(toId);

        if (fromData != null && toData != null) {
            fromData.id = toId;
            fromData.update();
            if (fromId > toId) {
                SQLite.update(MoneyTypePO.class)
                        .set(MoneyTypePO_Table.id.plus(1))
                        .where(MoneyTypePO_Table.id.greaterThanOrEq(toId))
                        .and(MoneyTypePO_Table.id.lessThan(fromId))
                        .execute();
            } else {
                SQLite.update(MoneyTypePO.class)
                        .set(MoneyTypePO_Table.id.minus(1))
                        .where(MoneyTypePO_Table.id.greaterThan(fromId))
                        .and(MoneyTypePO_Table.id.lessThanOrEq(toId))
                        .execute();
            }
        }*/
    }

    private boolean isExist(String typeName) {
        MoneyTypePO data = SQLite
                .select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.moneyTypeName.is(typeName))
                .querySingle();
        return data != null;
    }

    private MoneyTypePO queryById(int id) {
        return SQLite.select()
                .from(MoneyTypePO.class)
                .where(MoneyTypePO_Table.id.is(id))
                .querySingle();
    }
}
