package com.newamber.gracebook.view.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.model.adapter.MoneyTypeItemAdapter;
import com.newamber.gracebook.model.entity.MoneyTypeTable;
import com.newamber.gracebook.util.EditTypeItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/2.
 */

public class MoneyTypeFragment extends BaseFragment {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_money_type;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_money_type_card;

    private RecyclerView mRecyclerView;
    private MoneyTypeTable mMoneyTypeTable, mMoneyTypeTable1, mMoneyTypeTable2;
    private List<MoneyTypeTable> mMoneyTypeTableList;

    @Override
    public void initView() {
        // Test data.
        mMoneyTypeTable = new MoneyTypeTable();
        mMoneyTypeTable1 = new MoneyTypeTable();
        mMoneyTypeTable2 = new MoneyTypeTable();

        mMoneyTypeTable.moneyTypeImageID = R.drawable.ic_love_10;
        mMoneyTypeTable.moneyTypeName = "打杂";
        mMoneyTypeTable.save();

        mMoneyTypeTable1.moneyTypeImageID = R.drawable.ic_office_73;
        mMoneyTypeTable1.moneyTypeName = "网购";
        mMoneyTypeTable1.save();

        mMoneyTypeTable2.moneyTypeImageID = R.drawable.ic_office_28;
        mMoneyTypeTable2.moneyTypeName = "嘻嘻";
        mMoneyTypeTable2.save();

        mMoneyTypeTableList = new ArrayList<>();
        mMoneyTypeTableList.add(mMoneyTypeTable);
        mMoneyTypeTableList.add(mMoneyTypeTable1);
        mMoneyTypeTableList.add(mMoneyTypeTable2);

        mRecyclerView = (RecyclerView) getRootView().findViewById(R.id.recyclerView_moneyType);
        mRecyclerView.setHasFixedSize(true);
        MoneyTypeItemAdapter moneyTypeItemAdapter = new MoneyTypeItemAdapter(mMoneyTypeTableList, ITEM_LAYOUT_ID);

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
