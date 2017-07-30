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

import static com.newamber.gracebook.util.DateUtil.getFirstDayThisMonth;
import static com.newamber.gracebook.util.DateUtil.getFirstDayThisQuarter;
import static com.newamber.gracebook.util.DateUtil.getFirstDayThisWeek;
import static com.newamber.gracebook.util.DateUtil.getLastDayThisMonth;
import static com.newamber.gracebook.util.DateUtil.getLastDayThisQuarter;
import static com.newamber.gracebook.util.DateUtil.getLastDayThisWeek;

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
    public <M extends BaseModel> List<M> changeDate(boolean isNext, DateRange dateRange, Calendar start, Calendar end) {
        final int ONE_WEEK = isNext ? 7 : - 7;
        final int ONE_MONTH = isNext ? 1 : - 1;
        final int ONE_QUARTER = isNext ? 3 : - 3;
        final int ONE_YEAR = isNext ? 1 : - 1;

        switch (dateRange) {
            case WEEK:
                start.add(Calendar.DAY_OF_MONTH, ONE_WEEK);
                end.add(Calendar.DAY_OF_MONTH, ONE_WEEK);
                break;
            case MONTH:
                start.add(Calendar.MONTH, ONE_MONTH);
                end.add(Calendar.MONTH, ONE_MONTH);
                break;
            case QUARTER:
                start.add(Calendar.MONTH, ONE_QUARTER);
                end.add(Calendar.MONTH, ONE_QUARTER);
                break;
            case YEAR:
                start.add(Calendar.YEAR, ONE_YEAR);
                end.add(Calendar.YEAR, ONE_YEAR);
                break;
            default:
                break;
        }
        return mAccountModel.getRecordByDate(start, end);
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
    public <M extends BaseModel> List<M> getRecordByDateRange(DateRange dateRange) {
        switch (dateRange) {
            case WEEK:
                return (List<M>) mAccountModel.getRecordByDate(getFirstDayThisWeek(), getLastDayThisWeek());
            case MONTH:
                return (List<M>) mAccountModel.getRecordByDate(getFirstDayThisMonth(), getLastDayThisMonth());
            case QUARTER:
                return (List<M>) mAccountModel.getRecordByDate(getFirstDayThisQuarter(), getLastDayThisQuarter());
            case YEAR:

            default:
                return null;
        }
    }



    public enum DateRange {
        WEEK,       // default, this week
        MONTH,      // default, this month
        QUARTER,    // default, this quarter
        YEAR,       // default, this year
    }
}
