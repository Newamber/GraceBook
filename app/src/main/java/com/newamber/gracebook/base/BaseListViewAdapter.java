package com.newamber.gracebook.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: .<p>
 * Created by Newamber on 2017/5/8.
 */
@SuppressWarnings("unused")
public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    private List<T> mEntityList;
    private int mLayoutResId;

    protected BaseListViewAdapter(@NonNull List<T> entityList, int layoutId) {
        this.mEntityList = entityList;
        this.mLayoutResId = layoutId;
    }

    @Override
    public int getCount() {
        return mEntityList.size();
    }

    @Override
    public T getItem(int position) {
        return mEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutResId,
                position);
        convertView(holder, getItem(position));
        return holder.getItemView();
    }

    @SuppressWarnings("all")
    protected abstract void convertView(ViewHolder holder, T entity);

    public void add(T data) {
        if (mEntityList == null) {
            mEntityList = new ArrayList<>();
        }
        mEntityList.add(data);
        notifyDataSetChanged();
    }

    public void insert(int position, T data) {
        if (mEntityList == null) {
            mEntityList = new ArrayList<>();
        }
        mEntityList.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (mEntityList != null) {
            mEntityList.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mEntityList != null) {
            mEntityList.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mEntityList != null) {
            mEntityList.clear();
        }
        notifyDataSetChanged();
    }

    protected static class ViewHolder {
        private SparseArray<View> mViewSparseArray;
        private View item;
        private int position;
        private Context context;

        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViewSparseArray = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        static ViewHolder bind(Context context, View convertView, ViewGroup parent, int layoutRes,
                               int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        @SuppressWarnings("unchecked")
        <T extends View> T getView(int id) {
            View view = mViewSparseArray.get(id);
            if (view == null) {
                view = item.findViewById(id);
                mViewSparseArray.put(id, view);
            }
            return (T) view;
        }

        private View getItemView() {
            return item;
        }

        public int getItemPosition() {
            return position;
        }

        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                Glide.with(context).load(drawableRes).into((ImageView) view);
            }
            return this;
        }

        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }
    }
}
