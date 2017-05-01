package com.newamber.gracebook.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newamber.gracebook.R;
import com.newamber.gracebook.model.entity.AccountTable;

import java.util.Calendar;
import java.util.List;

/**
 * Description: RecyclerView Adapter.<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@SuppressWarnings("all")
public class AccountListItemAdapter extends RecyclerView.Adapter<AccountListItemAdapter.ViewHolder> {

    private List<AccountTable> mAccountTableList;
    private Context mContext;

    public AccountListItemAdapter(List<AccountTable> accountTableslist) {
        mAccountTableList = accountTableslist;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewHourMin, mTextViewMoneyType, mTextViewNote, mTextViewAmount;
        ImageView mImageViewMoneyType;

        public ViewHolder(View v) {
            super(v);
            mTextViewHourMin = (TextView) v.findViewById(R.id.textview_hour_minute);
            mTextViewMoneyType = (TextView) v.findViewById(R.id.textView_moneyType);
            mTextViewNote = (TextView) v.findViewById(R.id.textview_note);
            mTextViewAmount = (TextView) v.findViewById(R.id.textview_amount);
            mImageViewMoneyType = (ImageView) v.findViewById(R.id.imageview_money_type);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.recyclerview_record_card, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountTable accountTable = mAccountTableList.get(position);

        int hour = accountTable.date.get(Calendar.HOUR);
        int minute = accountTable.date.get(Calendar.MINUTE);
        String hours = (hour < 10) ? "0" + hour : ""+hour;
        String minutes = (minute < 10) ? "0" + minute : ""+minute;

        holder.mTextViewHourMin.setText(hours + ":" +minutes);
        holder.mTextViewMoneyType.setText(accountTable.moneyType);
        holder.mTextViewNote.setText(accountTable.note);
        holder.mTextViewAmount.setText(accountTable.amount + "￥");
        Glide.with(mContext).load(accountTable.moneyTypeImageId).into(holder.mImageViewMoneyType);
    }

    @Override
    public int getItemCount() {
        return mAccountTableList.size();
    }

}
