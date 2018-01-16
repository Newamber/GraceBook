package com.newamber.gracebook.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newamber.gracebook.util.other.ItemTouchActionHelper;

import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;

/**
 * Description: Common Adapter for RecyclerView. <br>
 * {@code E} means certain entity(like {@code String}) type depends on data source. <p>
 *
 * Created by Newamber on 2017/5/2.
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerViewAdapter<E> extends RecyclerView.Adapter<BaseViewHolder>
        implements ItemTouchActionHelper {

    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int ITEM_TYPE_HEADER = 2;
    private static final int ITEM_TYPE_FOOTER = 3;
    private static final int ITEM_TYPE_EMPTY  = 4;

    private Context mContext;
    private List<E> mEntityList;
    private @LayoutRes int mLayoutId;
    private @IdRes int mSubViewId, mSubViewLongId;

    private View mHeaderView, mFooterView, mEmptyView;

    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private OnSubItemClickListener mSubClickListener;
    private OnSubItemLongClickListener mSubLongClickListener;


    protected BaseRecyclerViewAdapter(@NonNull List<E> entityList, @LayoutRes int layoutId) {
        mEntityList = entityList;
        mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        switch (viewType) {
            case ITEM_TYPE_HEADER:
                return new BaseViewHolder(mHeaderView, mContext);
            case ITEM_TYPE_FOOTER:
                return new BaseViewHolder(mFooterView, mContext);
            case ITEM_TYPE_EMPTY:
                return new BaseViewHolder(mEmptyView, mContext);
            default:
                View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
                return new BaseViewHolder(view, mContext);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_HEADER
                || itemType == ITEM_TYPE_FOOTER
                || itemType == ITEM_TYPE_EMPTY) return;

        // init OnItemClickListener
        initOnItemClickListener(holder);
        // init sub itemClickListener
        initSubItemClickListener(holder);
        convertView(holder, mEntityList.get(getRealItemPosition(position)));
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) return ITEM_TYPE_HEADER;
        if (mFooterView != null && position == getItemCount() - 1) return ITEM_TYPE_FOOTER;
        if (mEmptyView != null && isEmpty()) return ITEM_TYPE_EMPTY;

        return ITEM_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        int itemCount = mEntityList.size();
        if (mEmptyView != null && itemCount == 0) itemCount++;
        if (mHeaderView != null) itemCount++;
        if (mFooterView != null) itemCount++;
        return itemCount;
    }

    // ----------------------------------header footer empty----------------------------------------
    public void setHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }

    // 0 is the position of HeaderView in list if it exists.
    public void removeHeaderView() {
        if (mHeaderView != null) {
            mHeaderView = null;
            notifyItemRemoved(0);
        }
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFooterView(View view) {
        mFooterView = view;
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeFooterView() {
        if (mFooterView != null) {
            mFooterView = null;
            notifyItemRemoved(getItemCount() - 1);
        }
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setEmptyView(View view) {
        mEmptyView = view;
        notifyDataSetChanged();
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    // ------------------------------------entity operations----------------------------------------
    public void setEntityList(@NonNull List<E> entityList) {
        if (!isEmpty()) mEntityList.clear();
        this.mEntityList = entityList;
        notifyDataSetChanged();
    }

    public List<E> getEntityList() {
        return mEntityList;
    }

    public E getEntity(int position) {
        return isEmpty() ? null : mEntityList.get(position);
    }

    public void add(E entity) {
        mEntityList.add(entity);
        notifyItemInserted(getItemCount());
    }

    public void insert(int position, E entity) {
        mEntityList.add(position, entity);
        notifyItemInserted(position);
    }

    @SuppressWarnings("all")
    public void remove(int position) {
        try {
            mEntityList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void remove(E entity) {
        int position = mEntityList.indexOf(entity);
        mEntityList.remove(entity);
        notifyItemRemoved(position);
    }

    // with animation
    public void removeAll() {
        for (int i = getItemCount() - 1; i >= 0; i--) {
            mEntityList.remove(i);
            notifyItemRemoved(i);
        }
    }

    public void replace(int position, E newEntity) {
        mEntityList.set(position, newEntity);
        notifyItemChanged(position);
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isEmpty() {
        return mEntityList.size() == 0;
    }

    @SuppressWarnings("all")
    public int getPosition(E entity) {
        return mEntityList.indexOf(entity);
    }

    // --------------------------------setOnClickListener-------------------------------------------
    public void setOnClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener ;
    }

    public void setOnLongClickListener(OnItemLongClickListener longClickListener) {
        mLongClickListener = longClickListener ;
    }

    public void setOnSubClickListener(@IdRes int subItemId, OnSubItemClickListener subClickListener) {
        mSubViewId = subItemId;
        mSubClickListener = subClickListener;
    }

    public void setOnSubLongClickListener(@IdRes int subItemId, OnSubItemLongClickListener
            subLongClickListener) {
        mSubViewLongId = subItemId;
        mSubLongClickListener = subLongClickListener;
    }

    @Override
    public void onItemDragMove(int fromPosition, int toPosition) {
        Collections.swap(mEntityList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwipeDismiss(int position) {
        remove(position);
    }

    /**
     * Extract our operations about different data binding.<br>
     * This is a specific job of our sub adapters according to the show of different item view.<br>
     * You can set text, color or drawable resource in the method.
     *
     * @param holder the outer layer holder of sub items
     * @param entity the data source
     */
    protected abstract void convertView(BaseViewHolder holder, E entity);

    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(View view, Object entity, int position) ;
    }

    @FunctionalInterface
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Object entity, int position) ;
    }

    @FunctionalInterface
    public interface OnSubItemClickListener {
        void onSubItemClick(View view, Object entity, int position) ;
    }

    @FunctionalInterface
    public interface OnSubItemLongClickListener {
        void onSubItemLongClick(View view, Object entity, int position) ;
    }

    // ----------------------------------private API------------------------------------------------
    // An auxiliary method to initialize (long) click listener.
    private void initOnItemClickListener(BaseViewHolder holder) {
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                E entity = mEntityList.get(holder.getLayoutPosition());
                mClickListener.onItemClick(v, entity, holder.getLayoutPosition());
            });
        }

        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                E entity = mEntityList.get(holder.getLayoutPosition());
                mLongClickListener.onItemLongClick(v, entity, holder.getLayoutPosition());
                return true;
            });
        }
    }

    // An auxiliary method to initialize (long) sub item click listener.
    private void initSubItemClickListener(BaseViewHolder holder) {
        if (mSubClickListener != null) {
            holder.itemView.findViewById(mSubViewId).setOnClickListener(v -> {
                E entity = mEntityList.get(holder.getLayoutPosition());
                mSubClickListener.onSubItemClick(v, entity, holder.getLayoutPosition());
            });
        }

        if (mSubLongClickListener != null) {
            holder.itemView.findViewById(mSubViewLongId).setOnLongClickListener(v -> {
                E entity = mEntityList.get(holder.getLayoutPosition());
                mSubLongClickListener.onSubItemLongClick(v, entity, holder.getLayoutPosition());
                return true;
            });
        }
    }

    @Contract(pure = true)
    private int getRealItemPosition(int position) {
        return mHeaderView != null ? position - 1 : position;
    }
}