package com.newamber.gracebook.model.entity;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.model.GraceBookDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;

/**
 * Description: Account table in our Database.<p>
 *
 * Created by Newamber on 2017/4/27.
 */
@SuppressWarnings("all")
@Table(database = GraceBookDatabase.class, allFields = true)
public class AccountPO extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    public Double amount;              // amount waited to be recorded

    public String note;                // note: the usage of the money

    public String moneyRepoType;       // bank card, wechat and so on

    public String moneyType;           // for example, shopping, eating and so on

    public boolean isExpense;          // income(false) or expense(true)

    public Calendar calendar;          // the calendar of the money's record

    @DrawableRes
    public int moneyTypeImageId;       // the round icon of the money type

    @DrawableRes
    public int moneyRepoTypeImageId;   // the round icon of the money type
}
