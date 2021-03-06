package com.newamber.gracebook.model.entity;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.model.GraceBookDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Description: Save money types which defined by user.<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@Table(database = GraceBookDatabase.class, allFields = true)
public class MoneyTypePO extends BaseModel {

    public int id;

    @PrimaryKey
    public String moneyTypeName;                 // defined by user

    public @DrawableRes int moneyTypeImageId;    // the same...
}
