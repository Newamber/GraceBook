package com.newamber.gracebook.presenter;

import android.os.Handler;
import android.support.annotation.DrawableRes;

import com.newamber.gracebook.base.IBaseModel;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.base.IBaseView;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.model.impl.AddAccountModel;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;
import com.newamber.gracebook.model.impl.MoneyTypeModel;
import com.newamber.gracebook.ui.activity.AddAccountActivity;
import com.newamber.gracebook.ui.activity.MainActivity;

import java.util.Calendar;
import java.util.List;

/**
 * Description: The presenter of {@link com.newamber.gracebook.ui.activity.AddAccountActivity}.<p>
 *
 * Created by Newamber on 2017/4/29.
 */
public class AddAccountPresenter extends BasePresenter<IBaseView.AddAccountView> {

    private static final int TIME_DELAY = 60;

    //private IBaseView.AddAccountView mView;
    private IBaseModel.TypeModel typeModel;

    public void saveInDB(double amount,
                         String note,
                         String moneyRepoType,
                         String moneyType,
                         boolean isExpense,
                         Calendar calendar,
                         @DrawableRes int moneyTypeImageId,
                         @DrawableRes int moneyRepoTypeImageId) {

        IBaseModel.AccountModel accountModel = new AddAccountModel(amount, note, moneyRepoType,
                moneyType, isExpense, calendar, moneyTypeImageId, moneyRepoTypeImageId);
        accountModel.saveRecord();
    }

    public void pickDate() {
        new Handler().postDelayed(() -> getView().showDatePicker(), TIME_DELAY);
    }

    public void pickMoneyRepo() {
        new Handler().postDelayed(() -> getView().showMoneyRepoPicker(), TIME_DELAY);
    }

    public void pickMoneyType() {
        new Handler().postDelayed(() ->  getView().showMoneyTypePicker(), TIME_DELAY);
    }

    public void saveRecord() {
        AddAccountActivity activity = (AddAccountActivity) getView();
        getView().checkInput();
        if (activity.canSave) {
            activity.saveRecord();
            activity.startTransitionActivity(MainActivity.class);
            activity.finish();
        } else {
            getView().showErrorHint();
        }
    }

    @SuppressWarnings("unchecked")
    public String[] getArrayOfTypeName(boolean isMoneyType) {
        String[] nameArray;
        typeModel = isMoneyType ? new MoneyTypeModel() : new MoneyRepoTypeModel();
        if (isMoneyType) {
            List<MoneyTypePO> list1 = typeModel.getAllRecords();
            nameArray = new String[list1.size()];
            for (int i = 0; i < list1.size(); i++) nameArray[i] = list1.get(i).moneyTypeName;
            return nameArray;
        } else {
            List<MoneyRepoTypePO> list2 = typeModel.getAllRecords();
            nameArray = new String[list2.size()];
            for (int i = 0; i < list2.size(); i++) nameArray[i] = list2.get(i).moneyRepoTypeName;
            return nameArray;
        }
    }

    public int getTypePosition(String typeName, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (typeName.equals(array[i])) return i;
        }
        return 0;
    }

    public @DrawableRes int getMoneyImageByName(String typeName) {
        typeModel = new MoneyTypeModel();
        MoneyTypePO record = (MoneyTypePO) typeModel.queryByName(typeName);
        return record.moneyTypeImageId;
    }

    public @DrawableRes int getMoneyRepoImageByName(String typeName) {
        typeModel = new MoneyRepoTypeModel();
        MoneyRepoTypePO record = (MoneyRepoTypePO) typeModel.queryByName(typeName);
        assert record != null;
        return record.moneyRepoTypeImageId;
    }

    public Double getBalanceByName(String typeName) {
        typeModel = new MoneyRepoTypeModel();
        MoneyRepoTypePO record = (MoneyRepoTypePO) typeModel.queryByName(typeName);
        assert record != null;
        return record.balance;
    }

    public void updateRepoBalance(String repoName, Double expense) {
        typeModel = new MoneyRepoTypeModel();
        ((MoneyRepoTypeModel) typeModel).updateBalance(repoName, expense);
    }

    public void resetPage() {
        getView().resetContent();
    }
}