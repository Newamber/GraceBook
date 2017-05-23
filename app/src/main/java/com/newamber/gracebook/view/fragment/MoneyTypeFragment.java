package com.newamber.gracebook.view.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.model.MoneyTypeModel;
import com.newamber.gracebook.model.adapter.MoneyTypeItemAdapter;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.util.EditTypeItemTouchHelperCallback;

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

    private RecyclerView mRecyclerView;
    public List<MoneyTypePO> mMoneyTypePOList;

    public MoneyTypeItemAdapter moneyTypeItemAdapter;

    @Override
    public void initView() {
        mMoneyTypePOList = new ArrayList<>();

        //TypeEditActivity activity = (TypeEditActivity) getActivity();
        mMoneyTypePOList = new MoneyTypeModel().getAllData();

        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.recyclerView_moneyType);
        mRecyclerView.setHasFixedSize(true);

        moneyTypeItemAdapter = new MoneyTypeItemAdapter(mMoneyTypePOList, ITEM_LAYOUT_ID);

        EditTypeItemTouchHelperCallback itemTouchHelperCallback = new EditTypeItemTouchHelperCallback(moneyTypeItemAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(moneyTypeItemAdapter);
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
    protected int getLayoutRes() {
        return LAYOUT_ID;
    }
}
