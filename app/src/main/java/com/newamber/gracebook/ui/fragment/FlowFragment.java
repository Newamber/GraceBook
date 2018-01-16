package com.newamber.gracebook.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.AccountFlowAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.presenter.MainPresenter;
import com.newamber.gracebook.util.DateUtil.DateRange;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.ToastUtil.ToastMode;
import com.newamber.gracebook.util.event.UpdateFlowIconEvent;
import com.newamber.gracebook.util.event.UpdateFlowItemEvent;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.newamber.gracebook.util.DateUtil.formatMonthDayForFlow;
import static com.newamber.gracebook.util.DateUtil.formatYearInCHS;
import static com.newamber.gracebook.util.DateUtil.formatYearMontDayWithWeekInCHS;
import static com.newamber.gracebook.util.DateUtil.formatYearMonthDay;
import static com.newamber.gracebook.util.DateUtil.formatYearMonthInCHS;
import static com.newamber.gracebook.util.DateUtil.formatYearQuarterInCHS;
import static com.newamber.gracebook.util.DateUtil.getFirstDay;
import static com.newamber.gracebook.util.DateUtil.getLastDay;
import static com.newamber.gracebook.util.DateUtil.setEndOfDay;
import static com.newamber.gracebook.util.DateUtil.setStartOfDay;
import static com.newamber.gracebook.util.DeviceUtil.isNormalClickSpeed;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class FlowFragment extends BaseFragment<MainPresenter> {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_flow;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_record_flow;
    private static final int WEEK    = 0;
    private static final int MONTH   = 1;
    private static final int QUARTER = 2;
    private static final int YEAR    = 3;

    private TextView mTextViewSectionHeader;
    private TextView mTextViewPeriodTitle;
    private TextView mTextViewDateRange;
    private CardView mCardViewSectionTop;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;

    private AccountFlowAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String[] periodArray;
    private int periodFlag;
    private int periodSelected = LocalStorage.getInt(GlobalConstant.FLOW_PERIOD_SELECTED_POSITION, 0);
    private DateRange range = DateRange.valueOf(periodSelected);
    private Calendar start = getFirstDay(range);
    private Calendar end = getLastDay(range);
    private Calendar customStart;
    private Calendar customEnd;
    private String dateRangeText;
    private boolean isCustomDate = false;


    @Override
    public void initView() {
        // findViewById
        mTextViewSectionHeader = findViewById(R.id.textView_header_flow);
        mCardViewSectionTop = findViewById(R.id.cardview_header_flow);
        mTextViewPeriodTitle = findViewById(R.id.textView_flow_period);
        mTextViewDateRange = findViewById(R.id.textView_dateRange);
        mRecyclerView = findViewById(R.id.recyclerview_flow);
        mButtonNext = findViewById(R.id.imageButton_next);
        mButtonPrevious = findViewById(R.id.imageButton_previous);

        // setOnClickListener
        bindOnClickListener(mTextViewPeriodTitle, mButtonNext, mButtonPrevious);

        // data source
        mAdapter = new AccountFlowAdapter(getPeriodRecord(), ITEM_LAYOUT_ID);
        mAdapter.setOnSubClickListener(R.id.cardview_flow_account, (view, entity, position) ->
                getHostPresenter().showAccountInfo(entity));

        View view = LayoutInflater.from(getHostActivity()).inflate(R.layout.flow_empty_view, mRecyclerView, false);
        mAdapter.setEmptyView(view);
        periodArray = getHostActivity().getResources().getStringArray(R.array.period_list);
        setEasyItemAdapter(mRecyclerView, mAdapter);
        setTopSection(mRecyclerView);

        mTextViewPeriodTitle.setText(periodArray[periodSelected]);
        startAnimator(mTextViewPeriodTitle, R.animator.anim_downward_show);
        updateUI();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.textView_flow_period:
                if (isNormalClickSpeed()) showPeriodDialog();
                break;
            case R.id.imageButton_previous:
                previousPeriod();
                break;
            case R.id.imageButton_next:
                nextPeriod();
                break;
            default:
                break;
        }
    }

    // --------------------------------------event--------------------------------------------------
    @Subscribe
    public void onClickSummary(String message) {
        if (message.equals(GlobalConstant.REQUEST_FLOW_DATE_AND_ACCOUNT)) {
            List<String> dateRangeTexts = new ArrayList<>();
            dateRangeTexts.add(dateRangeText);
            post(dateRangeTexts);
            post(mAdapter.getEntityList());
            cancelEventDelivery(message);
        }
    }

    @Subscribe
    public void onClickDeleteCurrentPage(String message) {
        if (message.equals(GlobalConstant.REQUEST_FLOW_DATE_RANGE)) {
            List<Calendar> calendars = new ArrayList<>();
            calendars.add(start);
            calendars.add(end);
            post(calendars);
            cancelEventDelivery(message);
        }
    }

    @Subscribe(sticky = true, priority = 1)
    public void onDeleteAccounts(String message) {
        if (message.equals(GlobalConstant.DELETE_ALL_ACCOUNT_ITEMS)
                || message.equals(GlobalConstant.DELETE_CURRENT_PAGE_ACCOUNT_ITEMS)) {
            mAdapter.removeAll();
            updateTopSection();
            removeStickyEvent(message);
        }
    }

    @Subscribe(sticky = true)
    public void onAccountAdded(String message) {
        if (message.equals(GlobalConstant.FLOW_ADD_NEW_ACCOUNT_ITEM)) {
            updateCurrentPage();
            updateTopSection();
            removeStickyEvent(message);
        }
    }

    @Subscribe(sticky = true)
    public void onAccountUpdate(UpdateFlowItemEvent message) {
        updateCurrentPage();
        updateTopSection();
        removeStickyEvent(message);
    }

    @Subscribe(sticky = true)
    public void onUpdateAccountIcon(UpdateFlowIconEvent event) {
        updateCurrentPage();
        removeStickyEvent(event);
    }

    @Subscribe
    public void onCurrentPageIsEmpty(String message) {
        if (message.equals(GlobalConstant.REQUEST_IS_CURRENT_PERIOD_EMPTY)) {
            if (mAdapter.isEmpty()) post(GlobalConstant.CURRENT_PAGE_IS_EMPTY);
            else post(GlobalConstant.CURRENT_PAGE_IS_NOT_EMPTY);
            cancelEventDelivery(message);
        }
    }

    @Subscribe
    public void onResumeCurrentPage(String message) {
        if (message.equals(GlobalConstant.REQUEST_RESUME_CURRENT_DATE_RANGE)) {
            resumeCurrent();
            cancelEventDelivery(message);
        }
    }
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected boolean isEventBusEnabled() {
        return true;
    }

    // --------------------------------private API--------------------------------------------------
    private void updateUI() {
        updateDateRangeText();
        updateTopSection();
        mRecyclerView.scrollToPosition(0);
    }

    private void nextPeriod() {
        mAdapter.setEntityList(getHostPresenter().changeDate(true, range, start, end));
        startAnimator(mTextViewDateRange, R.animator.anim_slide_to_right);
        updateUI();
    }

    private void previousPeriod() {
        // This is a bug of DBflow. In 1974 and 1970, it can not
        // show user's account items correctly.
        if (start.get(Calendar.YEAR) <= 1975) {
            ToastUtil.showShort(R.string.error_can_not_choose_year_before_1975, ToastMode.WARNING);
        } else {
            mAdapter.setEntityList(getHostPresenter().changeDate(false, range, start, end));
            startAnimator(mTextViewDateRange, R.animator.anim_slide_to_left);
            updateUI();
        }
    }

    private void showPeriodDialog() {
        new AlertDialog.Builder(getHostActivity())
                .setTitle(R.string.select_period)
                .setSingleChoiceItems(periodArray, periodSelected, (dialog, which) -> periodFlag = which)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    periodSelected = periodFlag;
                    mTextViewPeriodTitle.setText(periodArray[periodSelected]);
                    isCustomDate = false;
                    LocalStorage.put(GlobalConstant.FLOW_PERIOD_SELECTED_POSITION, periodSelected);
                    showSelectedButton();
                    resumeCurrent();
                })
                .setNegativeButton(R.string.cancel, null)
                .setNeutralButton(R.string.custom_date_range, (dialog, which) -> {
                    dialog.dismiss();
                    showCustomDatePicker();
                })
                .show();
    }

    private void updateDateRangeText() {
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
        mTextViewDateRange.setText(dateRangeText);
    }

    private void updateCurrentPage() {
        if (isCustomDate) {
            mAdapter.setEntityList(getHostPresenter().getRecordByDate(customStart, customEnd));
        } else {
            mAdapter.setEntityList(getHostPresenter().getRecordByDate(start, end));
        }
    }

    private void resumeCurrent() {
        if (isCustomDate) {
            ToastUtil.showShort(R.string.custom_date_is_current_date, ToastMode.INFO);
        } else {
            startAnimator(mTextViewDateRange, R.animator.anim_upward_show);
            startAnimator(mTextViewPeriodTitle, R.animator.anim_downward_show);
            range = DateRange.valueOf(periodSelected);
            start = getFirstDay(range);
            end = getLastDay(range);
            mAdapter.setEntityList(getPeriodRecord());
            updateUI();
        }
    }

    private void updateTopSection() {
        if (mAdapter.isEmpty()) {
            mCardViewSectionTop.setVisibility(View.INVISIBLE);
        } else {
            mCardViewSectionTop.setVisibility(View.VISIBLE);
            String headerText = formatMonthDayForFlow(mAdapter.getEntity(0).calendar);
            mTextViewSectionHeader.setText(headerText);
        }
        mCardViewSectionTop.setTranslationY(0);
    }

    private void setTopSection(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                View firstItemView = recyclerView.getChildAt(0);
                if (firstItemView != null && firstItemView.getContentDescription() != null)
                    mTextViewSectionHeader.setText(firstItemView.getContentDescription());

                View secondItemView = recyclerView.findChildViewUnder(mCardViewSectionTop.getMeasuredWidth() / 2,
                        mCardViewSectionTop.getMeasuredHeight() + 4);
                if (secondItemView != null && secondItemView.getTag() != null) {
                    int flag = (int) secondItemView.getTag();
                    int deltaY = secondItemView.getTop() - mCardViewSectionTop.getMeasuredHeight();

                    if (flag == AccountFlowAdapter.TYPE_CONTENT_WITH_SECTION)
                        mCardViewSectionTop.setTranslationY(secondItemView.getTop() > 0 ? deltaY : 0);
                    else if (flag == AccountFlowAdapter.TYPE_CONTENT_NO_SECTION)
                        mCardViewSectionTop.setTranslationY(0);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (pos == 0) mCardViewSectionTop.setTranslationY(0);
                }
            }
        });
    }

    private void checkCustomDate(boolean isSingleDay) {
        mTextViewPeriodTitle.setText(R.string.custom_date_range);
        startAnimator(mTextViewPeriodTitle, R.animator.anim_downward_show);
        if (isSingleDay) {
            customEnd.set(Calendar.YEAR, customStart.get(Calendar.YEAR));
            customEnd.set(Calendar.MONTH, customStart.get(Calendar.MONTH));
            customEnd.set(Calendar.DAY_OF_MONTH, customStart.get(Calendar.DAY_OF_MONTH));
            mAdapter.setEntityList(getHostPresenter().getRecordByDate(customStart, customEnd));
            dateRangeText = formatYearMonthDay(customStart);
        } else {
            mAdapter.setEntityList(getHostPresenter().getRecordByDate(customStart, customEnd));
            dateRangeText = formatYearMonthDay(customStart) + getString(R.string.to_with_space) + formatYearMonthDay(customEnd);
        }
        mTextViewDateRange.setText(dateRangeText);
        startAnimator(mTextViewPeriodTitle, R.animator.anim_downward_show);
        isCustomDate = true;
        hideSelectedButton();
        updateTopSection();
    }

    private void showCustomDatePicker() {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(getHostActivity()).inflate(R.layout.dialog_custom_date_picker, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getHostActivity());
        customStart = Calendar.getInstance();
        customEnd = Calendar.getInstance();
        // default end date
        customEnd.add(Calendar.DAY_OF_MONTH, 1);
        setStartOfDay(customStart);
        setEndOfDay(customEnd);

        SwitchCompat switchDate = dialogView.findViewById(R.id.switch_enable_single_date);
        TextView startDateText = dialogView.findViewById(R.id.textView_start_date_info);
        TextView startDateTile = dialogView.findViewById(R.id.textView_start_date);
        TextView endDateText = dialogView.findViewById(R.id.textView_end_date_info);
        TextView endDateTitle = dialogView.findViewById(R.id.textView_end_date);

        startDateText.setText(formatYearMontDayWithWeekInCHS(customStart));
        endDateText.setText(formatYearMontDayWithWeekInCHS(customEnd));
        startDateText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        startDateText.getPaint().setAntiAlias(true);
        endDateText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        endDateText.getPaint().setAntiAlias(true);

        switchDate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                endDateText.setClickable(false);
                startDateTile.setText(R.string.single_day);
                startAnimator(startDateTile, R.animator.anim_slide_to_right);
                startAnimators(R.animator.anim_end_date_hide, endDateText, endDateTitle);
                startAnimators(R.animator.anim_single_day_downward, startDateText, startDateTile);
            } else {
                endDateText.setClickable(true);
                startDateTile.setText(R.string.start_date);
                startAnimator(startDateTile, R.animator.anim_slide_to_left);
                startAnimators(R.animator.anim_end_date_show, endDateText, endDateTitle);
                startAnimators(R.animator.anim_single_day_upward, startDateText, startDateTile);
            }
        });

        builder.setTitle(R.string.custom_date_range)
                .setView(dialogView)
                .setPositiveButton(R.string.query, (dialog, which) -> {
                    checkCustomDate(switchDate.isChecked());
                    if (LocalStorage.getBoolean(GlobalConstant.IS_FIRST_QUERY_SINGLE_DAY, true)) {
                        ToastUtil.showLong(R.string.query_single_day_hint, ToastMode.INFO);
                        LocalStorage.put(GlobalConstant.IS_FIRST_QUERY_SINGLE_DAY, false);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();

        startDateText.setOnClickListener(v -> showStartDatePicker(startDateText, endDateText, switchDate.isChecked()));
        endDateText.setOnClickListener(v -> showEndDatePicker(endDateText));
    }

    private void showStartDatePicker(TextView start, TextView end, boolean isSingleDay) {
        DatePickerDialog dialog = new DatePickerDialog(getHostActivity(), (view, year, month, dayOfMonth) -> {
            if (year < 1975) {
                ToastUtil.showShort(R.string.error_can_not_choose_year_before_1975, ToastUtil.ToastMode.WARNING);
            } else {
                boolean isEarlier = (year < customEnd.get(Calendar.YEAR))
                        || (year == customEnd.get(Calendar.YEAR) && month < customEnd.get(Calendar.MONTH))
                        || (year == customEnd.get(Calendar.YEAR) && month == customEnd.get(Calendar.MONTH)
                        && dayOfMonth < customEnd.get(Calendar.DAY_OF_MONTH));
                if (isSingleDay) {
                    customStart.set(Calendar.YEAR, year);
                    customStart.set(Calendar.MONTH, month);
                    customStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    customEnd.set(Calendar.YEAR, year);
                    customEnd.set(Calendar.MONTH, month);
                    customEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    customEnd.add(Calendar.DAY_OF_MONTH, 1);
                    start.setText(formatYearMontDayWithWeekInCHS(customStart));
                    end.setText(formatYearMontDayWithWeekInCHS(customEnd));
                    startAnimator(start, R.animator.anim_single_downward);
                } else {
                    if (isEarlier) {
                        customStart.set(Calendar.YEAR, year);
                        customStart.set(Calendar.MONTH, month);
                        customStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        start.setText(formatYearMontDayWithWeekInCHS(customStart));
                        startAnimator(start, R.animator.anim_downward_show);
                    } else {
                        ToastUtil.showShort(R.string.start_date_earlier_than_end_date, ToastMode.WARNING);
                    }
                }
            }
        }, customStart.get(Calendar.YEAR), customStart.get(Calendar.MONTH), customStart.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showEndDatePicker(TextView textView) {
        DatePickerDialog dialog = new DatePickerDialog(getHostActivity(), (view, year, month, dayOfMonth) -> {
            if (year < 1975) {
                ToastUtil.showShort(R.string.error_can_not_choose_year_before_1975, ToastUtil.ToastMode.WARNING);
            } else {
                boolean isLater = (year > customStart.get(Calendar.YEAR))
                        || (year == customStart.get(Calendar.YEAR) && month > customStart.get(Calendar.MONTH))
                        || (year == customStart.get(Calendar.YEAR) && month == customStart.get(Calendar.MONTH)
                        && dayOfMonth > customStart.get(Calendar.DAY_OF_MONTH));

                if (isLater) {
                    customEnd.set(Calendar.YEAR, year);
                    customEnd.set(Calendar.MONTH, month);
                    customEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textView.setText(formatYearMontDayWithWeekInCHS(customEnd));
                    startAnimator(textView, R.animator.anim_downward_show);
                } else {
                    ToastUtil.showShort(R.string.end_date_later_than_start_date, ToastMode.WARNING);
                }
            }
        }, customEnd.get(Calendar.YEAR), customEnd.get(Calendar.MONTH), customEnd.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void hideSelectedButton() {
        mButtonPrevious.setClickable(false);
        mButtonNext.setClickable(false);
        startAnimator(mButtonPrevious, R.animator.anim_previous_hide);
        startAnimator(mButtonNext, R.animator.anim_next_hide);
    }

    private void showSelectedButton() {
        mButtonPrevious.setClickable(true);
        mButtonNext.setClickable(true);
        startAnimator(mButtonPrevious, R.animator.anim_previous_show);
        startAnimator(mButtonNext, R.animator.anim_next_show);
    }

    private <M extends BaseModel> List<M> getPeriodRecord() {
        return  getHostPresenter().getRecordByDateRange(range);
    }
}