package com.newamber.gracebook.ui.fragment;


import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.presenter.MainPresenter;
import com.newamber.gracebook.util.DateUtil.DateRange;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;

import java.util.Calendar;
import java.util.List;

import static com.newamber.gracebook.util.ColorUtil.getColor;
import static com.newamber.gracebook.util.DateUtil.formatYearInCHS;
import static com.newamber.gracebook.util.DateUtil.formatYearMonthDay;
import static com.newamber.gracebook.util.DateUtil.formatYearMonthInCHS;
import static com.newamber.gracebook.util.DateUtil.formatYearQuarterInCHS;
import static com.newamber.gracebook.util.DateUtil.getFirstDay;
import static com.newamber.gracebook.util.DateUtil.getLastDay;

/**
 *
 */
public class ChartFragment extends BaseFragment<MainPresenter> {

    private static final int LAYOUT_ID = R.layout.fragment_chart;
    private static final int WEEK    = 0;
    private static final int MONTH   = 1;
    private static final int QUARTER = 2;
    private static final int YEAR    = 3;

    private LineChart mLineChart;
    private TextView mTextViewTile;
    private TextView mTextViewDateRangeText;

    private int periodSelected = LocalStorage.getInt(GlobalConstant.CHART_PERIOD_SELECTED_POSITION, 0);
    private DateRange mDateRange = DateRange.valueOf(periodSelected);
    private String[] periodArray;
    private Calendar start = getFirstDay(mDateRange);
    private Calendar end = getLastDay(mDateRange);
    private int periodFlag;

    @Override
    public void initView() {
        mLineChart = findViewById(R.id.lineChart_trend);
        mTextViewTile = findViewById(R.id.textView_chart_period_title);
        mTextViewDateRangeText = findViewById(R.id.textView_chart_dateRange);
        ImageButton buttonPrevious = findViewById(R.id.imageButton_chart_previous);
        ImageButton buttonNext = findViewById(R.id.imageButton_chart_next);

        bindOnClickListener(mTextViewTile, buttonPrevious, buttonNext);

        periodArray = getHostActivity().getResources().getStringArray(R.array.period_list);
        mTextViewTile.setText(periodArray[periodSelected]);
        startAnimator(mTextViewDateRangeText, R.animator.anim_upward_show);
        startAnimator(mTextViewTile, R.animator.anim_downward_show);
        mDateRange = DateRange.valueOf(periodSelected);
        updateDateRangeText();
        initLineChart();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.imageButton_chart_previous:
                previousPeriod();
                break;
            case R.id.imageButton_chart_next:
                nextPeriod();
                break;
            case R.id.textView_chart_period_title:
                showPeriodDialog();
            default:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }


    // --------------------------------private API--------------------------------------------------
    private void showPeriodDialog() {
        new AlertDialog.Builder(getHostActivity())
                .setTitle(R.string.select_period)
                .setSingleChoiceItems(periodArray, periodSelected, (dialog, which) -> periodFlag = which)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    periodSelected = periodFlag;
                    mDateRange = DateRange.valueOf(periodSelected);
                    start = getFirstDay(mDateRange);
                    end = getLastDay(mDateRange);
                    LocalStorage.put(GlobalConstant.CHART_PERIOD_SELECTED_POSITION, periodSelected);
                    mTextViewTile.setText(periodArray[periodSelected]);
                    startAnimator(mTextViewDateRangeText, R.animator.anim_upward_show);
                    startAnimator(mTextViewTile, R.animator.anim_downward_show);
                    updateDateRangeText();

                    mLineChart.clearValues();
                    updateXAxis();
                    mLineChart.setData(getHostPresenter().getTrendDataByDateRange(mDateRange, getHostActivity()));
                    mLineChart.animateY(1000, Easing.EasingOption.EaseOutBounce);
                    mLineChart.animateX(2000, Easing.EasingOption.EaseOutBounce);
                    mLineChart.invalidate();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void previousPeriod() {
        // This is a bug of DBflow. In 1974 and 1970, it can not
        // show user's account items correctly.
        if (start.get(Calendar.YEAR) <= 1975) {
            ToastUtil.showShort(R.string.error_can_not_choose_year_before_1975, ToastMode.WARNING);
        } else {
            List<AccountPO> poList = getHostPresenter().changeDate(false, mDateRange, start, end);
            mLineChart.clearValues();
            updateXAxis();
            mLineChart.setData(getHostPresenter().updateTrendDataByDateRange(mDateRange, poList, getHostActivity()));
            startAnimators(R.animator.anim_slide_to_left, mTextViewDateRangeText, mLineChart);
            updateDateRangeText();
        }
    }

    private void nextPeriod() {
        List<AccountPO> poList = getHostPresenter().changeDate(true, mDateRange, start, end);
        mLineChart.clearValues();
        updateXAxis();
        mLineChart.setData(getHostPresenter().updateTrendDataByDateRange(mDateRange, poList, getHostActivity()));
        startAnimators(R.animator.anim_slide_to_right, mTextViewDateRangeText, mLineChart);
        updateDateRangeText();
    }

    private void updateDateRangeText() {
        String dateRangeText;
        switch (periodSelected) {
            case WEEK:
                dateRangeText = formatYearMonthDay(start) + getString(R.string.to_with_space) + formatYearMonthDay(end);
                break;
            case MONTH:
                dateRangeText = formatYearMonthInCHS(start);
                break;
            case QUARTER:
                dateRangeText = formatYearQuarterInCHS(start);
                break;
            case YEAR:
                dateRangeText = formatYearInCHS(start);
                break;
            default:
                dateRangeText = formatYearMonthDay(start) + getString(R.string.to_with_space) + formatYearMonthDay(end);
                break;
        }
        mTextViewDateRangeText.setText(dateRangeText);
    }

    private void initLineChart() {
        // label, description...
        Description description = new Description();
        description.setText("");
        mLineChart.setDescription(description);
        mLineChart.setNoDataText(getString(R.string.current_page_no_accounts));

        // interaction mode
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.setDoubleTapToZoomEnabled(false);

        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(getColor(R.color.colorWhiteTextOrIcon));

        // set X and Y axis
        updateXAxis();
        final float axisLineWidth = 1f;
        YAxis yAxisExpense = mLineChart.getAxisLeft();
        yAxisExpense.setTextColor(getColor(R.color.colorExpense));
        yAxisExpense.setGridColor(getColor(R.color.colorWhiteTextOrIcon));
        yAxisExpense.setAxisLineColor(getColor(R.color.colorWhiteTextOrIcon));
        yAxisExpense.setDrawZeroLine(true);
        yAxisExpense.setAxisLineWidth(axisLineWidth);
        yAxisExpense.setValueFormatter(getHostPresenter().getTrendYFormatter());
        yAxisExpense.enableGridDashedLine(8, 8, 4);

        YAxis yAxisIncome = mLineChart.getAxisRight();
        yAxisIncome.setTextColor(getColor(R.color.colorIncome));
        yAxisIncome.setGridColor(getColor(R.color.colorWhiteSecondaryText));
        yAxisIncome.setAxisLineColor(getColor(R.color.colorWhiteTextOrIcon));
        yAxisIncome.setDrawZeroLine(true);
        yAxisIncome.setAxisLineWidth(axisLineWidth);
        yAxisIncome.setValueFormatter(getHostPresenter().getTrendYFormatter());
        yAxisIncome.enableGridDashedLine(8, 8, 4);

        // set data
        mLineChart.setData(getHostPresenter().getTrendDataByDateRange(mDateRange, getHostActivity()));
        mLineChart.setVisibleXRangeMaximum(getHostPresenter().getMaxXCount(mDateRange));
        mLineChart.animateY(2000, Easing.EasingOption.EaseOutBounce);
        mLineChart.animateX(2000, Easing.EasingOption.EaseOutBounce);
        mLineChart.invalidate();
    }

    private void updateXAxis() {
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getColor(R.color.colorWhiteTextOrIcon));
        xAxis.setAxisLineColor(getColor(R.color.colorWhiteTextOrIcon));
        xAxis.setAxisLineWidth(2);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(getHostPresenter().getTrendXFormatter(mDateRange));
    }
}