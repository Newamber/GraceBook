package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.ViewHolder;
import com.newamber.gracebook.model.entity.AccountPO;

import java.util.List;

import static com.newamber.gracebook.util.DateUtil.getHourMin;
import static com.newamber.gracebook.util.NumericUtil.formatCurrency;

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

        holder.setText(R.id.textview_item_flow_hourMinute, getHourMin(entity.calendar))
                .setText(R.id.textView_item_flow_moneyType, entity.moneyType)
                .setText(R.id.textView_item_flow_amount, formatCurrency(entity.amount))
                .setImageResource(R.id.imageView_item_flow_moneyType, entity.moneyTypeImageId);

        if (entity.budget)
            holder.setTextColor(R.id.textView_item_flow_amount, R.color.colorExpense);
        else
            holder.setTextColor(R.id.textView_item_flow_amount, R.color.colorIncome);
    }
}