package com.newamber.gracebook.view.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.MoneyRepoTypeItemAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.model.entity.MoneyRepoTypePO;
import com.newamber.gracebook.util.helper.EditTypeItemCallback;
import com.newamber.gracebook.view.activity.TypeEditActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/2.
 */

public class MoneyRepoTypeFragment extends BaseFragment {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_money_repo_type;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_money_repo_type_card;

    public List<MoneyRepoTypePO> mPOList;
    public MoneyRepoTypeItemAdapter mAdapter;

    @Override
    public void initView() {
        mPOList = new ArrayList<>();
        TypeEditActivity activity = (TypeEditActivity) getHostActivity();
        activity.getPresenter().isMoneyType = false;

        // data source
        mPOList = activity.getPresenter().getAllData();
        RecyclerView recyclerView = (RecyclerView) getRootView().findViewById(R.id.recyclerView_moneyRepoType);
        mAdapter = new MoneyRepoTypeItemAdapter(mPOList, ITEM_LAYOUT_ID);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new EditTypeItemCallback(mAdapter, false));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        setEasyItemAnimatorAdapter(recyclerView, mAdapter);
    }

    @Override
    public void processClick(View v) {

    }

    // The Fragment has no business logic so there is no presenter
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

}
