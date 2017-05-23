package com.newamber.gracebook.base;

import android.content.Context;
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
    private int mLayoutRes;

    protected BaseListViewAdapter(List<T> mEntityList, int mLayoutRes) {
        this.mEntityList = mEntityList;
        this.mLayoutRes = mLayoutRes;
    }

    @Override
    public int getCount() {
        return (mEntityList != null) ? mEntityList.size() : 0;
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
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes,
                position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    @SuppressWarnings("all")
    protected abstract void bindView(ViewHolder holder, T entity);

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

        //绑定ViewHolder与item
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

        /**
         * 获取当前条目
         */
        private View getItemView() {
            return item;
        }

        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                Glide.with(context).load(drawableRes).into((ImageView) view);
            }
            return this;
        }

        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }
    }
}
