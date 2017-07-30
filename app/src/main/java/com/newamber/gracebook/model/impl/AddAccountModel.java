package com.newamber.gracebook.model.impl;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.BaseDataModel;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.model.entity.AccountPO_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.List;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/16.
 */
public class AddAccountModel implements BaseDataModel.AccountModel<AccountPO> {

    private double amount;
    private String note;
    private String moneyRepoType;
    private String moneyType;
    private boolean budget;
    private Calendar calendar;
    private @DrawableRes int moneyTypeImageId;
    private @DrawableRes int moneyRepoTypeImageId;

    public AddAccountModel() {}

    public AddAccountModel(double amount, String note, String moneyRepoType, String moneyType,
                           boolean budget, Calendar calendar, @DrawableRes int moneyTypeImageId,
                           @DrawableRes int moneyRepoTypeImageId) {
        this.amount = amount;
        this.note = note;
        this.moneyRepoType = moneyRepoType;
        this.moneyType = moneyType;
        this.budget = budget;
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
        record.budget = budget;
        record.calendar = calendar;
        record.moneyTypeImageId = moneyTypeImageId;
        record.moneyRepoTypeImageId = moneyRepoTypeImageId;
        record.save();
    }

    @Override
    public List<AccountPO> getAllRecord() {
        return SQLite.select().from(AccountPO.class).queryList();
    }

    @Override
    public void deleteAllRecord() {
        SQLite.delete().from(AccountPO.class).execute();
    }

    @Override
    public List<AccountPO> getRecordByDate(Calendar startDate, Calendar endDate) {
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);

        return SQLite.select()
                .from(AccountPO.class)
                .where(AccountPO_Table.calendar.greaterThanOrEq(startDate))
                .and(AccountPO_Table.calendar.lessThanOrEq(endDate))
                .orderBy(AccountPO_Table.calendar, true)
                .queryList();
    }
}
