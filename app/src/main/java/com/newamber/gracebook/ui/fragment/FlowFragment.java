package com.newamber.gracebook.ui.fragment;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.AccountFlowAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.presenter.MainPresenter;
import com.newamber.gracebook.util.DateUtil;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;

import java.util.List;

import static com.newamber.gracebook.util.DateUtil.getFirstDayThisYear;
import static com.newamber.gracebook.util.DateUtil.getLastDayThisYear;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlowFragment extends BaseFragment<MainPresenter> {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_flow;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_record_flow;

    private TextView mTextViewSectionHeader;
    private TextView mTextViewPeriod;
    private CardView mCardViewSectionContainer;
    private AccountFlowAdapter mAdapter;

    private String[] periodArray;
    private int periodFlag;
    private int periodSelected = LocalStorage.getInt(GlobalConstant.FLOW_PERIOD_SELECTED_POSITION, 0);

    @Override
    public void initView() {
        // findViewById
        mTextViewSectionHeader = findView(R.id.textView_header_flow);
        mCardViewSectionContainer = findView(R.id.cardview_header_flow);
        mTextViewPeriod = findView(R.id.textView_flow_period);
        ImageButton buttonNext = findView(R.id.imageButton_next);
        ImageButton buttonPrevious = findView(R.id.imageButton_previous);
        RecyclerView recyclerView = findView(R.id.recyclerview_flow);

        // setOnClickListener
        mTextViewPeriod.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);

        // data source
        List<AccountPO> POList = getHostPresenter().getAll();
        mAdapter = new AccountFlowAdapter(POList, ITEM_LAYOUT_ID);
        periodArray = getHostActivity().getResources().getStringArray(R.array.flow_period_list);
        setEasyItemAnimatorAdapter(recyclerView, mAdapter);
        setSectionEffect(recyclerView);

        mTextViewPeriod.setText(periodArray[periodSelected]);

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.textView_flow_period:
                showPeriodDialog();
                break;
            case R.id.imageButton_previous:
                ToastUtil.showShort(DateUtil.getYearMonthDay(getFirstDayThisYear()),
                        ToastUtil.ToastMode.INFO);
                break;
            case R.id.imageButton_next:
                ToastUtil.showShort(DateUtil.getYearMonthDay(getLastDayThisYear()),
                        ToastUtil.ToastMode.INFO);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    private void setSectionEffect(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                View firstItemView = recyclerView.getChildAt(0);
                if (firstItemView != null && firstItemView.getContentDescription() != null) {
                    mTextViewSectionHeader.setText(firstItemView.getContentDescription());
                }

                View otherItemView = recyclerView.findChildViewUnder(mCardViewSectionContainer.getMeasuredWidth() / 2,
                        mCardViewSectionContainer.getMeasuredHeight() + 1);
                if (otherItemView != null && otherItemView.getTag() != null) {
                    int flag = (int) otherItemView.getTag();
                    int deltaY = otherItemView.getTop() - mCardViewSectionContainer.getMeasuredHeight();
                    if (flag == AccountFlowAdapter.TYPE_CONTENT_WITH_SECTION) {
                        if (otherItemView.getTop() > 0) mCardViewSectionContainer.setTranslationY(deltaY);
                        else mCardViewSectionContainer.setTranslationY(0);
                    } else if (flag == AccountFlowAdapter.TYPE_CONTENT_NO_SECTION) {
                        mCardViewSectionContainer.setTranslationY(0);
                    }
                }
            }
        });
    }

    private void showPeriodDialog() {
        new AlertDialog.Builder(getHostActivity())
                .setTitle(R.string.select_period)
                .setSingleChoiceItems(periodArray, periodSelected, (dialog, which) -> periodFlag = which)
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    periodSelected = periodFlag;
                    mTextViewPeriod.setText(periodArray[periodSelected]);
                    LocalStorage.put(GlobalConstant.FLOW_PERIOD_SELECTED_POSITION, periodSelected);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

}
