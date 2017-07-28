package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.ViewHolder;
import com.newamber.gracebook.model.entity.AccountPO;
import com.newamber.gracebook.util.DateUtil;
import com.newamber.gracebook.util.NumericUtil;

import java.util.List;

/**
 * Description: RecyclerView Adapter of AccountItem.<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@SuppressWarnings("all")
public class AccountTodayAdapter extends BaseRecyclerViewAdapter<AccountPO> {

    public AccountTodayAdapter(@NonNull List<AccountPO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    protected void convertView(ViewHolder holder, AccountPO entity) {
        holder.setText(R.id.textview_item_hourMinute, DateUtil.getHourMin(entity.calendar))
                .setText(R.id.textView_item_moneyType, entity.moneyType)
                .setText(R.id.textView_item_amount, NumericUtil.getCurrencyFormat(entity.amount))
                .setImageResource(R.id.imageView_item_moneyType, entity.moneyTypeImageId);

        if (entity.budget)
            holder.setTextColor(R.id.textView_item_amount, R.color.colorExpense);
        else
            holder.setTextColor(R.id.textView_item_amount, R.color.colorIncome);
    }
}