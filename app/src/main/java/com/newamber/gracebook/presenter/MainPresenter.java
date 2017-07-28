package com.newamber.gracebook.presenter;

import com.newamber.gracebook.base.BaseDataModel;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.base.BaseView;
import com.newamber.gracebook.model.impl.AddAccountModel;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;
import com.newamber.gracebook.model.impl.MoneyTypeModel;
import com.newamber.gracebook.ui.activity.AddAccountActivity;
import com.newamber.gracebook.ui.activity.MainActivity;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;
import java.util.List;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/18.
 */
public class MainPresenter extends BasePresenter<BaseView.MainView> {

    private BaseDataModel.AccountModel mAccountModel = new AddAccountModel();

    public void clickRecordButton() {
        BaseDataModel.TypeModel moneyTypeModel = new MoneyTypeModel();
        BaseDataModel.TypeModel repoTypeModel = new MoneyRepoTypeModel();
        int moneyTypeNum = moneyTypeModel.getAllRecord().size();
        int repoTypeNum = repoTypeModel.getAllRecord().size();

        if (moneyTypeNum != 0 && repoTypeNum != 0) {
            ((MainActivity) getView()).startTransitionActivity(AddAccountActivity.class);
        } else {
            getView().showErrorDialog();
        }
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getAll() {
        return (List<M>) mAccountModel.getAllRecord();
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getRecordToday() {
        return (List<M>) mAccountModel.getRecordByDate(Calendar.getInstance(), Calendar.getInstance());
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getRecord(DateRange dateRange) {
        switch (dateRange) {
            case TODAY:
                return (List<M>) mAccountModel.getRecordByDate(Calendar.getInstance(), Calendar.getInstance());
            default:
                return null;
        }
    }

    public enum DateRange {
        TODAY,
        WEEK,       // default, this week
        MONTH,      // default, this month
        QUARTER,    // default, this quarter
        YEAR,       // default, this year
        ALL
    }
}
