package com.newamber.gracebook.presenter;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.model.MoneyRepoTypeModel;
import com.newamber.gracebook.model.MoneyTypeModel;
import com.newamber.gracebook.model.TypeModel;
import com.newamber.gracebook.view.TypeEditView;

import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/5.
 */

public class TypeEditPresenter extends BasePresenter<TypeEditView> {
    private TypeModel mTypeModel;
    public boolean isMoneyType;
    public void newTypeDialog(int position) {
        if (position == 0) getView().showMoneyTypeDialog();
        else getView().showMoneyRepoTypeDialog();
    }

    public void saveData(String name, @DrawableRes int imageID, double balance) {
        mTypeModel = (isMoneyType) ?
                new MoneyTypeModel(name, imageID) : new MoneyRepoTypeModel(name, imageID, balance);
        mTypeModel.saveData();
    }

    public List<?> getAllData() {
        mTypeModel = (isMoneyType) ? new MoneyTypeModel() : new MoneyRepoTypeModel();
        return mTypeModel.getAllData();
    }
}
