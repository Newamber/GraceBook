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
public class AccountTable extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    public double amount;           // amount waited to be recorded

    public String note;             // note: the usage of the money

    public String moneyRepoType;    // bank card, wechat and so on

    public String moneyType;        // for example, shopping, eating and so on

    public boolean budget;          // income(true) or expense(false)

    public Calendar date;           // the date of the money's record

    public @DrawableRes
    int moneyTypeImageId;           // the round icon of the money type

    public @DrawableRes
    int moneyRepoTypeImageId;       // the round icon of the money type
}
