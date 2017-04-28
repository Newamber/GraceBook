package com.newamber.gracebook.model.entity;

import com.newamber.gracebook.model.GraceBookDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Calendar;

/**
 * Description: Account table in our Database.<p>
 *
 * Created by Newamber on 2017/4/27.
 */
@Table(database = GraceBookDatabase.class, allFields = true)
public class AccountTable {
    @PrimaryKey(autoincrement = true)
    int id;
    double amount;           // amount waited to be recorded
    String note;             // note: the usage of the money
    String moneyRepoType;    // bank card, wechat and so on
    String moneyType;        // for example, shopping, eating and so on
    boolean budget;          // income(true) or expense(false)
    Calendar date;           // the date of the money's record
}
