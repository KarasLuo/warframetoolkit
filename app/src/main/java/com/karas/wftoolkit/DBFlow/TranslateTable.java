package com.karas.wftoolkit.DBFlow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Hongliang Luo on 2019/6/12.
 **/
@Table(database = JKDataBase.class)
public class TranslateTable extends BaseModel {

    @PrimaryKey
    public int id;

    @Column
    public String enString;

    @Column
    public String cnString;
}
