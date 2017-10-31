package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.IBaseModel;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.model.entity.AccountPO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.Property;

import java.util.Calendar;
import java.util.List;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/16.
 */
public class AddAccountModel implements IBaseModel.AccountModel<AccountPO> {

    private double amount;
    private String note;
    private String moneyRepoType;
    private String moneyType;
    private boolean isExpense;
    private Calendar calendar;
    private @DrawableRes int moneyTypeImageId;
    private @DrawableRes int moneyRepoTypeImageId;

    public AddAccountModel() {}

    public AddAccountModel(double amount, String note, String moneyRepoType, String moneyType,
                           boolean isExpense, Calendar calendar, @DrawableRes int moneyTypeImageId,
                           @DrawableRes int moneyRepoTypeImageId) {
        this.amount = amount;
        this.note = note;
        this.moneyRepoType = moneyRepoType;
        this.moneyType = moneyType;
        this.isExpense = isExpense;
        this.calendar = calendar;
        this.moneyTypeImageId = moneyTypeImageId;
        this.moneyRepoTypeImageId = moneyRepoTypeImageId;
    }

    @Override
    public void saveRecord() {
        AccountPO record = new AccountPO();
        record.amount = amount;
        record.note = note;
        record.moneyRepoType = moneyRepoType;
        record.moneyType = moneyType;
        record.isExpense = isExpense;
        record.calendar = calendar;
        record.moneyTypeImageId = moneyTypeImageId;
        record.moneyRepoTypeImageId = moneyRepoTypeImageId;
        record.save();
    }

    @Override
    public List<AccountPO> getAllRecords() {
        return SQLite.select().from(AccountPO.class).queryList();
    }

    @Override
    public void updateImage(boolean isMoneyType, @DrawableRes int imageId, String name) {
        Property<Integer> PropertyImageId = isMoneyType ?
                AccountPO_Table.moneyTypeImageId : AccountPO_Table.moneyRepoTypeImageId;
        Property<String> PropertyMoneyType = isMoneyType ?
                AccountPO_Table.moneyType : AccountPO_Table.moneyRepoType;

        SQLite.update(AccountPO.class)
                .set(PropertyImageId.is(imageId))
                .where(PropertyMoneyType.is(name))
                .execute();

    }

    @Override
    public void deleteAllRecords() {
        SQLite.delete().from(AccountPO.class).execute();
    }

    @Override
    public void deleteRecordByDate(Calendar start, Calendar end) {
        SQLite.delete()
                .from(AccountPO.class)
                .where(AccountPO_Table.calendar.greaterThanOrEq(start))
                .and(AccountPO_Table.calendar.lessThanOrEq(end))
                .execute();
    }

    @Override
    public List<AccountPO> getRecordByDate(Calendar start, Calendar end) {
        return SQLite.select()
                .from(AccountPO.class)
                .where(AccountPO_Table.calendar.greaterThanOrEq(start))
                .and(AccountPO_Table.calendar.lessThanOrEq(end))
                .orderBy(AccountPO_Table.calendar, true)
                .queryList();
    }
}
