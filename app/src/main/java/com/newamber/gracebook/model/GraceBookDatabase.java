package com.newamber.gracebook.model;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Description: The whole Application's database.<p>
 *
 * Created by Newamber on 2017/4/27.
 */
@SuppressWarnings("all")
@Database(name = GraceBookDatabase.NAME, version = GraceBookDatabase.VERSION)
public class GraceBookDatabase extends BaseModel {

    public static final String NAME = "GraceBook";

    public static final int VERSION = 1;
}
