package com.newamber.gracebook.ui.fragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.newamber.gracebook.R;
import com.newamber.gracebook.adapter.MoneyTypeItemAdapter;
import com.newamber.gracebook.base.BaseFragment;
import com.newamber.gracebook.model.entity.MoneyTypePO;
import com.newamber.gracebook.presenter.TypeEditPresenter;
import com.newamber.gracebook.util.GlobalConstant;
import com.newamber.gracebook.util.LocalStorage;
import com.newamber.gracebook.util.ToastUtil;
import com.newamber.gracebook.util.other.EditTypeItemCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 import java.util.List;

 * Description: Fragment which shows the user custom money type.<p>
 * Created by Newamber on 2017/5/2.
 */
@SuppressWarnings("unused")
public class MoneyTypeFragment extends BaseFragment<TypeEditPresenter> {

    private static final @LayoutRes int LAYOUT_ID = R.layout.fragment_money_type;
    private static final @LayoutRes int ITEM_LAYOUT_ID = R.layout.recyclerview_money_type_card;

    private MoneyTypeItemAdapter mAdapter;

    @Override
    public void initView() {
        getHostPresenter().isMoneyType = true;
        // data source
        List<MoneyTypePO> POList = getHostPresenter().getAll();
        // TODO: set empty view.
        RecyclerView recyclerView = (RecyclerView) getRootView().findViewById(R.id.recyclerView_moneyType);
        mAdapter = new MoneyTypeItemAdapter(POList, ITEM_LAYOUT_ID);

        // item animator
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new EditTypeItemCallback(mAdapter, true));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        mAdapter.setOnClickListener((view, entity, position) -> {
            int pos = position + 1;
            if (entity instanceof MoneyTypePO)
            ToastUtil.showShort("你点击了位置为"+ pos + "的ViewHolder，" + "其id为 " +
                            ((MoneyTypePO) entity).id, ToastUtil.ToastMode.WARNING);
        });
        setEasyItemAnimatorAdapter(recyclerView, mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    protected boolean isEnabledEventBus() {
        return true;
    }

    @Subscribe
    public void onNewMoneyType(MoneyTypePO record) {
        if (LocalStorage.getBoolean(GlobalConstant.IS_EXIST_TYPE_NAME, false)) {
            mAdapter.replace(record.id - 1, record);
        } else {
            mAdapter.add(record);
        }
        EventBus.getDefault().cancelEventDelivery(record);
    }

    @Subscribe
    public void onDeleteMoneyType(String deleteMessage) {
        if (deleteMessage.equals(GlobalConstant.DELETE_ALL_MONEY_TYPE)) {
            mAdapter.removeAll();
            EventBus.getDefault().cancelEventDelivery(deleteMessage);
        }
    }
}