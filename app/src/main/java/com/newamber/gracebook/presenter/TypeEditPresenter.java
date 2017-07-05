package com.newamber.gracebook.presenter;

import android.support.annotation.DrawableRes;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseModels;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;
import com.newamber.gracebook.model.impl.MoneyTypeModel;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.newamber.gracebook.view.activity.TypeEditActivity;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Description: The presenter of {@link com.newamber.gracebook.view.activity.TypeEditActivity}.<p>
 * Created by Newamber on 2017/5/5.
 */
public class TypeEditPresenter extends BasePresenter<BaseView.TypeEditView> {

    public boolean isMoneyType;

    // Only if this presenter is initialized, the mView can be got.
    // So do not invoke getView() here.
    private BaseView.TypeEditView mView;
    private BaseModels.TypeModel typeModel;

    public void showNewTypeDialog(int position) {
        mView = getView();
        if (position == 0) mView.showMoneyTypeDialog();
        else mView.showMoneyRepoTypeDialog();
    }

    public void showDeleteAllDialog(int position) {
        mView = getView();
        TypeEditActivity activity = (TypeEditActivity) mView;
        if (position == 0) {
            if (activity.mMoneyTypeFragment.mPOList.isEmpty())
                    ToastUtil.showShort(R.string.there_is_no_money_type, ToastMode.INFO);
            else mView.showTypeDeleteDialog();
        } else {
            if (activity.mMoneyRepoTypeFragment.mPOList.isEmpty())
                ToastUtil.showShort(R.string.there_is_no_money_repo_type, ToastMode.INFO);
            else mView.showRepoTypeDeleteDialog();
        }
    }

    /**
     * Save method for data.
     *
     * @param name type name
     * @param imageId image id.
     * @param balance balance can be random if type is MoneyType and defined by user if not.
     */
    public void saveData(String name, @DrawableRes int imageId, double balance) {
        typeModel = isMoneyType ?
                new MoneyTypeModel(name, imageId) : new MoneyRepoTypeModel(name, imageId, balance);
        typeModel.saveData();
    }

    public void deleteAllData() {
        typeModel = isMoneyType ? new MoneyTypeModel() : new MoneyRepoTypeModel();
        typeModel.deleteAllData();
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getAllData() {
        typeModel = isMoneyType ? new MoneyTypeModel() : new MoneyRepoTypeModel();
        return (List<M>) typeModel.getAllData();
    }
}
