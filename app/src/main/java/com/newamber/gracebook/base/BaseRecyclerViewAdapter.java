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

import java.util.Collections;
import java.util.List;

/**
 * Description: Common Adapter for RecyclerView. <br>
 * {@code E} means certain entity(like {@code String}) type depends on data source. <p>
 *
 * Created by Newamber on 2017/5/2.
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerViewAdapter<E> extends RecyclerView.Adapter<ViewHolder>
        implements ItemTouchActionHelper {

    private Context mContext;
    private List<E> mEntityList;
    private @LayoutRes int mLayoutId;
    private @IdRes int mSubViewId, mSubViewLongId;

    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private OnSubItemClickListener mSubClickListener;
    private OnSubItemLongClickListener mSubLongClickListener;


    protected BaseRecyclerViewAdapter(@NonNull List<E> entityList, @LayoutRes int layoutId) {
        mEntityList = entityList;
        mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);

        // The instance of ViewHolder is passed to parameter "holder" at onBindViewHolder method
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // init OnItemClickListener
        initOnItemClickListener(holder);

        // init sub itemClickListener
        initSubItemClickListener(holder);

        convertView(holder, mEntityList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    @Override
    public void onItemDragMove(int fromPosition, int toPosition) {
        Collections.swap(mEntityList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwipeDismiss(int position) {
        mEntityList.remove(position);
        notifyItemRemoved(position);
    }

    public void setEntityList(@NonNull List<E> entityList) {
        this.mEntityList = entityList;
        notifyDataSetChanged();
    }

    public List<E> getEntityList() {
        return mEntityList;
    }

    public E getItem(int position) {
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

    public void remove(int position) {
        mEntityList.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(E entity) {
        int position = mEntityList.indexOf(entity);
        mEntityList.remove(entity);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        for (int i = getItemCount() - 1; i >= 0; i--) {
            mEntityList.remove(i);
            notifyItemRemoved(i);
        }
    }

    public void replace(int position, E entity) {
        mEntityList.set(position, entity);
        notifyItemChanged(position);
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    protected int getPosition(E entity) {
        return mEntityList.indexOf(entity);
    }

    protected E getEntity(int position) {
        return mEntityList.get(position);
    }

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

    /**
     * Extract our operations about different data binding.<br>
     * This is a specific job of our sub adapters according to the show of different item view.<br>
     * You can set text, color or drawable resource in the method.
     *
     * @param holder the outer layer holder of sub items
     * @param entity the data source
     */
    protected abstract void convertView(ViewHolder holder, E entity);

    // A auxiliary method to initialize (long) click listener.
    private void initOnItemClickListener(ViewHolder holder) {
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

    // A auxiliary method to initialize (long) sub item click listener.
    private void initSubItemClickListener(ViewHolder holder) {
        if (mSubClickListener != null) {
            holder.itemView.findViewById(mSubViewId).setOnClickListener(v ->
                    mSubClickListener.onSubItemClick(v, holder.getLayoutPosition()));
        }

        if (mSubLongClickListener != null) {
            holder.itemView.findViewById(mSubViewLongId).setOnLongClickListener(v -> {
                mSubLongClickListener.onSubItemLongClick(v, holder.getLayoutPosition());
                return true;
            });
        }
    }


    /**
     * The item click listener interface of RecyclerView
     * {@code T} means the type of entity, it should be the same with {@code E}
     */
    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(View view, Object entity, int position) ;
    }

    @FunctionalInterface
    @SuppressWarnings("all")
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Object entity, int position) ;
    }

    @FunctionalInterface
    @SuppressWarnings("all")
    public interface OnSubItemClickListener {
        void onSubItemClick(View view, int position) ;
    }

    @FunctionalInterface
    @SuppressWarnings("all")
    public interface OnSubItemLongClickListener {
        void onSubItemLongClick(View view, int position) ;
    }
}