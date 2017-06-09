package com.newamber.gracebook.view.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.adapter.MoneyTypeItemAdapter;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.helper.EditTypeItemTouchHelperCallback;
import com.newamber.gracebook.view.activity.TypeEditActivity;

import java.util.ArrayList;
import java.util.List;

/**
 import java.util.List;

 * Description: .<p>
 * Created by Newamber on 2017/5/2.
 */

public class MoneyTypeFragment extends BaseFragment {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_money_type;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_money_type_card;

    public List<MoneyTypePO> mPOList;
    public MoneyTypeItemAdapter mAdapter;

    @Override
    public void initView() {
        mPOList = new ArrayList<>();
        TypeEditActivity activity = (TypeEditActivity) getHostActivity();
        activity.getPresenter().isMoneyType = true;

        // data source
        mPOList = activity.getPresenter().getAllData();
        RecyclerView recyclerView = (RecyclerView) getRootView().findViewById(R.id.recyclerView_moneyType);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MoneyTypeItemAdapter(mPOList, ITEM_LAYOUT_ID);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new EditTypeItemTouchHelperCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void processClick(View v) {
        // do something
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
