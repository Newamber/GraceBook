package com.newamber.gracebook.presenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.base.IBaseModel;
import com.newamber.gracebook.base.IBaseView;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.model.impl.AddAccountModel;
import com.newamber.gracebook.model.impl.MoneyRepoTypeModel;
import com.newamber.gracebook.model.impl.MoneyTypeModel;
import com.newamber.gracebook.ui.activity.AddAccountActivity;
import com.newamber.gracebook.ui.activity.MainActivity;
import com.newamber.gracebook.util.DateUtil.DateRange;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.newamber.gracebook.util.ColorUtil.getColor;
import static com.newamber.gracebook.util.DateUtil.getFirstDay;
import static com.newamber.gracebook.util.DateUtil.getLastDay;
import static com.newamber.gracebook.util.DateUtil.setEndOfDay;
import static com.newamber.gracebook.util.DateUtil.setStartOfDay;
import static com.newamber.gracebook.util.NumericUtil.formatCurrency;
import static com.newamber.gracebook.util.NumericUtil.formatCurrencyForChart;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/18.
 */
public class MainPresenter extends BasePresenter<IBaseView.MainView> {

    private static final int QUARTER_1 = 0;
    private static final int QUARTER_2 = 1;
    private static final int QUARTER_3 = 2;
    private static final int QUARTER_4 = 3;

    private IBaseModel.AccountModel mAccountModel = new AddAccountModel();
    private IBaseModel.TypeModel mTypeModel;

    public void newAccount() {
        IBaseModel.TypeModel moneyTypeModel = new MoneyTypeModel();
        IBaseModel.TypeModel repoTypeModel = new MoneyRepoTypeModel();
        int moneyTypeNum = moneyTypeModel.getAllRecords().size();
        int repoTypeNum = repoTypeModel.getAllRecords().size();

        if (moneyTypeNum != 0 && repoTypeNum != 0) {
            ((MainActivity) getView()).startTransitionActivity(AddAccountActivity.class);
        } else {
            getView().showErrorDialog();
        }
    }

    public void showAccountInfo(Object entity) {
        getView().showAccountInfoDialog(entity);
    }

    public void changeAccountBookName() {
        getView().showNewAccountNameDialog();
    }

    @SuppressWarnings("unchecked")
    public void deleteAll() {
        // recover balance
        mTypeModel = new MoneyRepoTypeModel();
        List<AccountPO> accountPOList = mAccountModel.getAllRecords();
        for(AccountPO record : accountPOList) {
            Double amount = record.isExpense ? record.amount : -record.amount;
            ((MoneyRepoTypeModel) mTypeModel).updateBalance(record.moneyRepoType, amount);
        }
        mAccountModel.deleteAllRecords();
    }

    @SuppressWarnings("unchecked")
    public void deleteRecordByDate(Calendar start, Calendar end) {
        // recover balance
        mTypeModel = new MoneyRepoTypeModel();
        List<AccountPO> accountPOList = mAccountModel.getRecordByDate(start, end);
        for(AccountPO record : accountPOList) {
            Double amount = record.isExpense ? record.amount : -record.amount;
            ((MoneyRepoTypeModel) mTypeModel).updateBalance(record.moneyRepoType, amount);
        }
        mAccountModel.deleteRecordByDate(start, end);
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getAll() {
        return (List<M>) mAccountModel.getAllRecords();
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> changeDate(boolean isNext, DateRange dateRange,
                                                    Calendar start, Calendar end) {
        final int ONE_WEEK = isNext ? 7 : -7;
        final int ONE_MONTH = isNext ? 1 : -1;
        final int ONE_QUARTER = isNext ? 3 : -3;
        final int ONE_YEAR = isNext ? 1 : -1;

        switch (dateRange) {
            case WEEK:
                start.add(Calendar.DAY_OF_MONTH, ONE_WEEK);
                end.add(Calendar.DAY_OF_MONTH, ONE_WEEK);
                break;
            case MONTH:
                start.add(Calendar.MONTH, ONE_MONTH);
                end.add(Calendar.MONTH, ONE_MONTH);
                end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
                break;
            case QUARTER:
                start.add(Calendar.MONTH, ONE_QUARTER);
                end.add(Calendar.MONTH, ONE_QUARTER);
                end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
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
    public <M extends BaseModel> List<M> getRecordOfToday() {
        Calendar todayMorning = Calendar.getInstance();
        Calendar todayNight = Calendar.getInstance();
        setStartOfDay(todayMorning);
        setEndOfDay(todayNight);
        return (List<M>) mAccountModel.getRecordByDate(todayMorning, todayNight);
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getRecordByDate(Calendar start, Calendar end) {
        return (List<M>) mAccountModel.getRecordByDate(start, end);
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel> List<M> getRecordByDateRange(DateRange dateRange) {
        return (List<M>) mAccountModel.getRecordByDate(getFirstDay(dateRange), getLastDay(dateRange));
    }

    public LineData getTrendDataByDateRange(DateRange dateRange, Context context) {
        // this week
        List<AccountPO> poList = getRecordByDateRange(dateRange);
        LineDataSet dataSetExpense, dataSetIncome;
        switch (dateRange) {
            case WEEK:
                dataSetExpense = new LineDataSet(getWeekEntries(poList, true),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getWeekEntries(poList, false),
                        context.getString(R.string.income));
                break;
            case MONTH:
                dataSetExpense = new LineDataSet(getMonthEntries(poList, true, dateRange),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getMonthEntries(poList, false, dateRange),
                        context.getString(R.string.income));
                break;
            case QUARTER:
                dataSetExpense = new LineDataSet(getQuarterEntries(true, dateRange),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getQuarterEntries(false, dateRange),
                        context.getString(R.string.income));
                break;
            case YEAR:
                dataSetExpense = new LineDataSet(getYearEntries(poList, true),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getYearEntries(poList, false),
                        context.getString(R.string.income));
                break;
            default:
                dataSetExpense = new LineDataSet(getWeekEntries(poList, true),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getWeekEntries(poList, false),
                        context.getString(R.string.income));
                break;
        }
        initLineDataSet(dataSetExpense, true);
        initLineDataSet(dataSetIncome, false);

        List<ILineDataSet> resultList = new ArrayList<>();
        resultList.add(dataSetExpense);
        resultList.add(dataSetIncome);
        return new LineData(resultList);
    }

    public LineData updateTrendDataByDateRange(DateRange dateRange,  List<AccountPO> poList,  Context context) {
        // this week
        LineDataSet dataSetExpense, dataSetIncome;
        switch (dateRange) {
            case WEEK:
                dataSetExpense = new LineDataSet(getWeekEntries(poList, true),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getWeekEntries(poList, false),
                        context.getString(R.string.income));
                break;
            case MONTH:
                dataSetExpense = new LineDataSet(getMonthEntries(poList, true, dateRange),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getMonthEntries(poList, false, dateRange),
                        context.getString(R.string.income));
                break;
            case QUARTER:
                dataSetExpense = new LineDataSet(getQuarterEntries(true, dateRange),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getQuarterEntries(false, dateRange),
                        context.getString(R.string.income));
                break;
            case YEAR:
                dataSetExpense = new LineDataSet(getYearEntries(poList, true),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getYearEntries(poList, false),
                        context.getString(R.string.income));
                break;
            default:
                dataSetExpense = new LineDataSet(getWeekEntries(poList, true),
                        context.getString(R.string.expense));
                dataSetIncome = new LineDataSet(getWeekEntries(poList, false),
                        context.getString(R.string.income));
                break;
        }
        initLineDataSet(dataSetExpense, true);
        initLineDataSet(dataSetIncome, false);

        List<ILineDataSet> resultList = new ArrayList<>();
        resultList.add(dataSetExpense);
        return new LineData(resultList);
    }

    public int getMaxXCount(DateRange dateRange) {
        int count = 4;
        switch (dateRange) {
            case WEEK:
                count = 7;
                break;
            case MONTH:
                count = 31;
                break;
            case QUARTER:
                count = 4;
                break;
            case YEAR:
                count = 12;
                break;
            default:
                break;
        }
        return count;
    }

    public IAxisValueFormatter getTrendXFormatter(DateRange dateRange) {
        IAxisValueFormatter formatter;
        switch (dateRange) {
            case WEEK:
                String[] xValues = new String[] {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
                formatter = (value, axis) -> xValues[(int) value];
                break;
            case MONTH:
                int count = getFirstDay(dateRange).getActualMaximum(Calendar.DAY_OF_MONTH);
                String[] xValues1 = new String[count];
                for (int i = 0; i <= count - 1; i++) {
                    int temp = i + 1;
                    xValues1[i] = temp + "日";
                }
                formatter = (value, axis) -> xValues1[(int) value];
                break;
            case QUARTER:
                String[] xValues2 = new String[] {"第一季度", "第二季度", "第三季度", "第四季度"};
                formatter = (value, axis) -> xValues2[(int) value];
                break;
            case YEAR:
                String[] xValues3 = new String[] {"1月", "2月", "3月", "4月", "5月", "6月", "7月"
                        , "8月", "9月", "10月", "11月", "12月"};
                formatter = (value, axis) -> xValues3[(int) value];
                break;
            default:
                String[] xValues4 = new String[] {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
                formatter = (value, axis) -> xValues4[(int) value];
                break;
        }
        return formatter;
    }

    public IAxisValueFormatter getTrendYFormatter() {
        return (value, axis) -> formatCurrencyForChart((double) value);
    }

    public int getIncomeCount(List<AccountPO> list) {
        int sum = 0;
        for (AccountPO record : list) {
            if (!record.isExpense) sum += 1;
        }
        return sum;
    }

    public int getExpenseCount(List<AccountPO> list) {
        int sum = 0;
        for (AccountPO record : list) {
            if (record.isExpense) sum += 1;
        }
        return sum;
    }

    public Double getTotalIncome(List<AccountPO> list) {
        Double sum = 0d;
        for (AccountPO record : list) {
            if (!record.isExpense) sum += record.amount;
        }
        return sum;
    }

    public Double getTotalExpense(List<AccountPO> list) {
        Double sum = 0d;
        for (AccountPO record : list) {
            if (record.isExpense) sum += record.amount;
        }
        return sum;
    }

    public Double getSurplus(List<AccountPO> list) {
        return getTotalIncome(list) - getTotalExpense(list);
    }

    public void showSummaryReport() {
        post(GlobalConstant.REQUEST_FLOW_DATE_AND_ACCOUNT);
        getView().showSummaryDialog();
    }

    public void deleteAllRecords() {
        if (isAccountsEmpty()) {
            ToastUtil.showShort(R.string.all_accounts_deleted, ToastMode.INFO);
        } else {
            getView().showDeleteAllDialog();
        }
    }

    public void deleteCurrent(boolean isEmpty) {
        if (isEmpty) {
            ToastUtil.showShort(R.string.current_page_no_accounts, ToastMode.INFO);
        } else {
            getView().showDeleteCurrentPageDialog();
        }
    }

    public void resumeCurrent() {
        post(GlobalConstant.REQUEST_RESUME_CURRENT_DATE_RANGE);
    }

    public boolean isMoneyTypeExist(String name) {
        mTypeModel = new MoneyTypeModel();
        return mTypeModel.isExist(name);
    }

    public boolean isRepoTypeExist(String name) {
        mTypeModel= new MoneyRepoTypeModel();
        return mTypeModel.isExist(name);
    }

    @SuppressWarnings("unchecked")
    public String getTotalRepoBalance() {
        mTypeModel = new MoneyRepoTypeModel();
        List<MoneyRepoTypePO> poList = mTypeModel.getAllRecords();
        Double balance = 0d;
        for (MoneyRepoTypePO record : poList) {
            balance += record.balance;
        }
        return formatCurrency(balance);
    }


    // --------------------------------------private API--------------------------------------------
    private boolean isAccountsEmpty() {
        return mAccountModel.getAllRecords().isEmpty();
    }

    private void initLineDataSet(LineDataSet lineDataSet, boolean isExpense) {
        YAxis.AxisDependency dependency = isExpense ? YAxis.AxisDependency.LEFT : YAxis.AxisDependency.RIGHT;
        @ColorInt int lineColor = isExpense ? getColor(R.color.colorExpense) : getColor(R.color.colorIncome);
        @ColorInt int circleColor = isExpense ? Color.RED : Color.GREEN;
        float lineWidth = isExpense ? 2.5f : 1f;

        lineDataSet.setAxisDependency(dependency);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(lineColor);
        lineDataSet.setLineWidth(lineWidth);
        lineDataSet.setCircleColor(circleColor);
        lineDataSet.setCircleColorHole(circleColor);
        lineDataSet.setCircleRadius(2.5f);
        lineDataSet.setCircleHoleRadius(1f);
        lineDataSet.setHighLightColor(Color.TRANSPARENT);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
    }

    private List<Entry> getWeekEntries(List<AccountPO> list, boolean isExpense) {
        List<Entry> entries = new ArrayList<>();
        entries.add(getEntryByWeek(Calendar.MONDAY, list, isExpense));
        entries.add(getEntryByWeek(Calendar.TUESDAY, list, isExpense));
        entries.add(getEntryByWeek(Calendar.WEDNESDAY, list, isExpense));
        entries.add(getEntryByWeek(Calendar.THURSDAY, list, isExpense));
        entries.add(getEntryByWeek(Calendar.FRIDAY, list, isExpense));
        entries.add(getEntryByWeek(Calendar.SATURDAY, list, isExpense));
        entries.add(getEntryByWeek(Calendar.SUNDAY, list, isExpense));

        return entries;
    }

    private List<Entry> getMonthEntries(List<AccountPO> list, boolean isExpense, DateRange dateRange) {
        int count = getLastDay(dateRange).getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i <= count - 1; i++) entries.add(i, getEntryByMonth(i, list, isExpense));

        return entries;
    }

    private List<Entry> getQuarterEntries(boolean isExpense, DateRange dateRange) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i <= 3; i++) entries.add(i, getEntryByQuarter(i, dateRange, isExpense));

        return entries;
    }

    private List<Entry> getYearEntries(List<AccountPO> list, boolean isExpense) {
        List<Entry> entries = new ArrayList<>();
        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++)
            entries.add(getEntryByYear(i, list, isExpense));

        return entries;
    }

    @NonNull
    private Entry getEntryByWeek(int week, List<AccountPO> list, boolean isExpense) {
        List<AccountPO> poList = new ArrayList<>();
        for (AccountPO record : list) {
            if (record.calendar.get(Calendar.DAY_OF_WEEK) == week) {
                if (record.isExpense == isExpense) poList.add(record);
            }
        }

        double totalAmount = 0d;
        if (!poList.isEmpty()) {
            for (AccountPO record : poList) totalAmount += record.amount;
        }

        if (week == Calendar.SUNDAY) week = 6;
        else week -= 2;

        return new Entry(week, (float) totalAmount);
    }

    @NonNull
    private Entry getEntryByMonth(int dayOfMonth, List<AccountPO> list, boolean isExpense) {
        List<AccountPO> poList = new ArrayList<>();
        for (AccountPO record : list) {
            if (record.calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                if (record.isExpense == isExpense) poList.add(record);
            }
        }

        double totalAmount = 0d;
        if (!poList.isEmpty()) {
            for (AccountPO record : poList) totalAmount += record.amount;
        }
        return new Entry(dayOfMonth, (float) totalAmount);
    }

    @NonNull
    private Entry getEntryByQuarter(int quarter, DateRange dateRange, boolean isExpense) {
        Calendar start = getFirstDay(dateRange);
        Calendar end = getLastDay(dateRange);
        switch (quarter) {
            case QUARTER_1:
                start.set(Calendar.MONTH, Calendar.JANUARY);
                end.set(Calendar.MONTH, Calendar.MARCH);
                break;
            case QUARTER_2:
                start.set(Calendar.MONTH, Calendar.APRIL);
                end.set(Calendar.MONTH, Calendar.JUNE);
                break;
            case QUARTER_3:
                start.set(Calendar.MONTH, Calendar.JULY);
                end.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            case QUARTER_4:
                start.set(Calendar.MONTH, Calendar.OCTOBER);
                end.set(Calendar.MONTH, Calendar.DECEMBER);
                break;
            default:
                break;
        }

        List<AccountPO> poList = getRecordByDate(start, end);
        List<AccountPO> newList = new ArrayList<>();

        for (AccountPO record : poList) {
            if (record.isExpense == isExpense) newList.add(record);
        }
        double totalAmount = 0d;
        if (!newList.isEmpty()) {
            for (AccountPO record : newList) totalAmount += record.amount;
        }

        return new Entry(quarter, (float) totalAmount);
    }

    @NonNull
    private Entry getEntryByYear(int month, List<AccountPO> list, boolean isExpense) {
        List<AccountPO> poList = new ArrayList<>();
        for (AccountPO record : list) {
            if (record.calendar.get(Calendar.MONTH) == month) {
                if (record.isExpense == isExpense) poList.add(record);
            }
        }

        double totalAmount = 0d;
        if (!poList.isEmpty()) {
            for (AccountPO record : poList) totalAmount += record.amount;
        }

        return new Entry(month, (float) totalAmount);
    }

}