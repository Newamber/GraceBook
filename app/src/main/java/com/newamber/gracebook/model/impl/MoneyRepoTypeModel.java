package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;
import android.util.Log;

import com.newamber.gracebook.base.BaseModels;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO_Table;
import com.newamber.gracebook.util.ToastUtil;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

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
        if (isExist(name)) {
            SQLite.update(MoneyRepoTypePO.class)
                    .set(MoneyRepoTypePO_Table.moneyRepoTypeImageId.is(imageId))
                    .where(MoneyRepoTypePO_Table.moneyRepoTypeName.is(name))
                    .execute();
        } else {
            MoneyRepoTypePO entity = new MoneyRepoTypePO();
            entity.id = getAllData().size() + 1;
            entity.moneyRepoTypeName = name;
            entity.moneyRepoTypeImageId = imageId;
            entity.balance = balance;
            entity.save();
        }
    }

    @Override
    public void deleteAllData() {
        SQLite.delete(MoneyRepoTypePO.class).execute();
    }

    @Override
    public List<MoneyRepoTypePO> getAllData() {
        return select().from(MoneyRepoTypePO.class).queryList();
    }

    @Override
    public void deleteDataById(int id) {
        SQLite.delete(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.id.is(id))
                .execute();
        ToastUtil.showShort("现在的删除的id是" + id, ToastUtil.ToastMode.INFO);

        List<MoneyRepoTypePO> lists = select()
                        .from(MoneyRepoTypePO.class)
                        .where(MoneyRepoTypePO_Table.id.greaterThan(id))
                        .queryList();
        if (!lists.isEmpty()) {
            for (MoneyRepoTypePO data : lists) {
                data.id -= 1;
                data.update();
                Log.d("删除成功", "后面的id依次减一，分别是" + data.id);
            }
        }
    }

    @Override
    public void dragSwap(int fromId, int toId) {

    }

    private boolean isExist(String typeName) {
        MoneyRepoTypePO data =
                select()
                .from(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.moneyRepoTypeName.is(typeName))
                .querySingle();
        return data != null;
    }

    private MoneyRepoTypePO queryById(int id) {
        return SQLite.select()
                .from(MoneyRepoTypePO.class)
                .where(MoneyRepoTypePO_Table.id.is(id))
                .querySingle();
    }
}
