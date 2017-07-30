package com.newamber.gracebook.presenter;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseDataModel;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;
import com.newamber.gracebook.model.impl.MoneyTypeModel;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Description: The presenter of {@link com.newamber.gracebook.ui.activity.TypeEditActivity}.<p>
 *
 * Created by Newamber on 2017/5/5.
 */
public class TypeEditPresenter extends BasePresenter<BaseView.TypeEditView> {

    public boolean isMoneyType;

    // Only if this presenter is initialized, the mView can be got.
    // So do not invoke getView() here(after "mView").
    private BaseView.TypeEditView mView;
    private BaseDataModel.TypeModel typeModel;

    public void showNewTypeDialog(int position) {
        mView = getView();
        if (position == 0) mView.showMoneyTypeDialog();
        else mView.showMoneyRepoTypeDialog();
    }

    public void showDeleteAllDialog(int position) {
        mView = getView();
        if (position == 0) {
            if (isMoneyTypeEmpty())
                ToastUtil.showShort(R.string.there_is_no_money_type, ToastMode.INFO);
            else
                mView.showTypeDeleteDialog();
        } else {
            if (isRepoTypeEmpty())
                ToastUtil.showShort(R.string.there_is_no_money_repo_type, ToastMode.INFO);
            else
                mView.showRepoTypeDeleteDialog();
        }
    }

    /**
     * Save method to save money type or money repository type in DB.
     *
     * @param name type name
     * @param imageId image id.
     * @param balance balance can be random if type is MoneyType and defined by user if not.
     */
    public void saveInDB(String name, @DrawableRes int imageId, double balance) {
        typeModel = isMoneyType ? new MoneyTypeModel(name, imageId) : new MoneyRepoTypeModel(name, imageId, balance);
        typeModel.saveRecord();
    }

    public boolean isExist(String typeName) {
        typeModel = getTypeModel();
        return typeModel.isExist(typeName);
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> M getRecordByName(String typeName) {
        typeModel = getTypeModel();
        return (M) typeModel.queryByName(typeName);
    }

    public void deleteAll() {
        typeModel = getTypeModel();
        typeModel.deleteAllRecord();
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getAll() {
        typeModel = getTypeModel();
        return (List<M>) typeModel.getAllRecord();
    }

    // -----------------------------------private API-----------------------------------------------
    private BaseDataModel.TypeModel getTypeModel() {
        return isMoneyType ? new MoneyTypeModel() : new MoneyRepoTypeModel();
    }

    private boolean isMoneyTypeEmpty() {
        typeModel = new MoneyTypeModel();
        return typeModel.getAllRecord().isEmpty();
    }

    private boolean isRepoTypeEmpty() {
        typeModel = new MoneyRepoTypeModel();
        return typeModel.getAllRecord().isEmpty();
    }
}
