package com.newamber.gracebook.view.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.MoneyTypeItemAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.base.BasePresenter;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.helper.EditTypeItemCallback;
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
        mAdapter = new MoneyTypeItemAdapter(mPOList, ITEM_LAYOUT_ID);

        // item animator
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new EditTypeItemCallback(mAdapter, true));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.ItemClickListener() {
            @Override
            public <E> void onItemClick(View view, E entity, int position) {
                int id = 0;
                int pos = position + 1;
                if (entity instanceof MoneyTypePO) id = ((MoneyTypePO) entity).id;
                ToastUtil.showShort("你点击了位置为"+ pos + "的ViewHolder" + "其id为 " + id,
                        ToastUtil.ToastMode.WARNING);
            }
        });
        setEasyItemAnimatorAdapter(recyclerView, mAdapter);
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
