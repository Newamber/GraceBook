package com.newamber.gracebook.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.base.ViewHolder;
import com.newamber.gracebook.model.entity.AccountPO;

import java.util.List;

import static com.newamber.gracebook.util.DateUtil.getHourMin;
import static com.newamber.gracebook.util.DateUtil.getYearMonthDay;
import static com.newamber.gracebook.util.NumericUtil.formatCurrency;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/29.
 */

public class AccountFlowAdapter extends BaseRecyclerViewAdapter<AccountPO> {

    private static final int TYPE_FIRST_ITEM = 1;
    public static final int TYPE_CONTENT_WITH_SECTION = 2;
    public static final int TYPE_CONTENT_NO_SECTION = 3;

    public AccountFlowAdapter(@NonNull List<AccountPO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    protected void convertView(ViewHolder holder, AccountPO entity) {
        int position = getPosition(entity);
        holder.setText(R.id.textview_item_flow_hourMinute, getHourMin(entity.calendar))
                .setText(R.id.textView_header_flow, getYearMonthDay(entity.calendar))
                .setText(R.id.textView_item_flow_moneyType, entity.moneyType)
                .setText(R.id.textView_item_flow_amount, formatCurrency(entity.amount))
                .setImageResource(R.id.imageView_item_flow_moneyType, entity.moneyTypeImageId);

        if (entity.budget)
            holder.setTextColor(R.id.textView_item_flow_amount, R.color.colorExpense);
        else
            holder.setTextColor(R.id.textView_item_flow_amount, R.color.colorIncome);

        if (position == 0) {
            holder.setVisibility(R.id.cardview_header_flow, View.VISIBLE);
            holder.saveTag(TYPE_FIRST_ITEM);
        } else {
            AccountPO preEntity = getEntity(position - 1);
            boolean isEqual = TextUtils.equals(getYearMonthDay(entity.calendar), getYearMonthDay(preEntity.calendar));
            if (isEqual) {
                holder.setVisibility(R.id.cardview_header_flow, View.GONE);
                holder.saveTag(TYPE_CONTENT_NO_SECTION);
            } else {
                holder.setVisibility(R.id.cardview_header_flow, View.VISIBLE);
                holder.saveTag(TYPE_CONTENT_WITH_SECTION);
            }
        }

        holder.itemView.setContentDescription(getYearMonthDay(entity.calendar));
    }

}
