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
import com.newamber.gracebook.model.entity.AccountPO;

import java.util.Calendar;
import java.util.List;

/**
 * Description: RecyclerView Adapter of AccountListItem.<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@SuppressWarnings("all")
public class AccountItemAdapter extends RecyclerView.Adapter<AccountItemAdapter.ViewHolder> {

    private List<AccountPO> mAccountPOList;
    private Context mContext;

    public AccountItemAdapter(List<AccountPO> accountTableslist) {
        mAccountPOList = accountTableslist;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewHourMin, mTextViewMoneyType, mTextViewNote, mTextViewAmount;
        ImageView mImageViewMoneyType;

        public ViewHolder(View v) {
            super(v);
            mTextViewHourMin = (TextView) v.findViewById(R.id.textview_hour_minute);
            mTextViewMoneyType = (TextView) v.findViewById(R.id.textView_moneyType);
            mTextViewNote = (TextView) v.findViewById(R.id.textView_note);
            mTextViewAmount = (TextView) v.findViewById(R.id.textView_amount);
            mImageViewMoneyType = (ImageView) v.findViewById(R.id.imageView_moneyType);
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
        AccountPO accountPO = mAccountPOList.get(position);

        int hour = accountPO.date.get(Calendar.HOUR);
        int minute = accountPO.date.get(Calendar.MINUTE);
        String hours = (hour < 10) ? "0" + hour : "" + hour;
        String minutes = (minute < 10) ? "0" + minute : "" + minute;

        holder.mTextViewHourMin.setText(hours + ":" +minutes);
        holder.mTextViewMoneyType.setText(accountPO.moneyType);
        holder.mTextViewNote.setText(accountPO.note);
        holder.mTextViewAmount.setText(accountPO.amount + "￥");
        Glide.with(mContext).load(accountPO.moneyTypeImageId).into(holder.mImageViewMoneyType);
    }

    @Override
    public int getItemCount() {
        return mAccountPOList.size();
    }

}
