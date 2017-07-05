package com.newamber.gracebook.model.entity;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.model.GraceBookDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Description: Save moneyRepo types which defined by user<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@SuppressWarnings("all")
@Table(database = GraceBookDatabase.class, allFields = true)
public class MoneyRepoTypePO extends BaseModel {

    public int id;

    @PrimaryKey
    public String moneyRepoTypeName;    // defined by user

    public @DrawableRes
    int moneyRepoTypeImageId;           // defined by user

    public Double balance;              // the balance of MoneyRepo initializd by user

}
