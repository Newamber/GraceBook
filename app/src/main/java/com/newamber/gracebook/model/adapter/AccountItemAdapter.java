package com.newamber.gracebook.model.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.newamber.gracebook.R;
import com.newamber.gracebook.base.BaseRecyclerViewAdapter;
import com.newamber.gracebook.model.ViewHolder;
import com.newamber.gracebook.model.entity.AccountPO;

import java.util.Calendar;
import java.util.List;

/**
 * Description: RecyclerView Adapter of AccountItem.<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@SuppressWarnings("all")
public class AccountItemAdapter extends BaseRecyclerViewAdapter<AccountPO> {

    public AccountItemAdapter(@NonNull List<AccountPO> entityList, @LayoutRes int layoutId) {
        super(entityList, layoutId);
    }

    @Override
    public void onItemDismiss(int position) {
        super.onItemDismiss(position);
    }

    @Override
    protected void convertView(ViewHolder holder, AccountPO entity) {
        int hour = entity.date.get(Calendar.HOUR);
        int minute = entity.date.get(Calendar.MINUTE);
        String hours = (hour < 10) ? "0" + hour : "" + hour;
        String minutes = (minute < 10) ? "0" + minute : "" + minute;

        // TODO: Solve the display way of note and amount.
        holder.setText(R.id.textview_hour_minute, hours + ":" + minutes);
        holder.setText(R.id.textView_moneyType, entity.moneyType);

        if (entity.note.length() <= 8) {
            holder.setText(R.id.textView_note, entity.note);
        } else {
            holder.setText(R.id.textView_note, entity.note.substring(0, 7) + "...");
        }

        holder.setText(R.id.textView_amount, entity.amount + "￥");
        holder.setImageResource(R.id.imageView_moneyType, entity.moneyTypeImageId);
    }

    @Override
    protected void initSubItemClickListener(ViewHolder holder) {
        // TODO: Initialize sub item click listener here if needed.
    }
}