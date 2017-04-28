package com.newamber.gracebook.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newamber.gracebook.model.entity.AccountTable;

import java.util.List;

/**
 * Description: RecyclerView Adapter.<p>
 *
 * Created by Newamber on 2017/4/28.
 */
@SuppressWarnings("all")
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private List<AccountTable> mAccountTableList;

    public AccountListAdapter(List<AccountTable> accountTableslist) {
        mAccountTableList = accountTableslist;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewHourMin, mTextViewMoneyType, mTextViewNote, mTextViewAmount;
        ImageView mImageViewMoneyType;

        public ViewHolder(View v) {
            super(v);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mAccountTableList.size();
    }

}
