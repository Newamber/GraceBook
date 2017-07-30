package com.newamber.gracebook.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.newamber.gracebook.util.ColorUtil.getColor;

/**
 * Description: A encapsulated common ViewHolder of RecyclerView .<p>
 * <br>
 * Created by Newamber on 2017/5/2.
 */
@SuppressWarnings("all")
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mSubViewArray;

    // The context of relevant Activity.
    private Context mContext;

    /**
     * the constructor of ViewHolder
     *
     * @param itemView the outer layer layout of sub views of list item
     * @param context the context of parent ViewGroup of our ViewHolder
     */
    public ViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mSubViewArray = new SparseArray<>();
    }

    // Some auxiliary methods.
    public ViewHolder setText(@IdRes int viewId , CharSequence textContent) {
        View view = getSubView(viewId);
        if (view instanceof TextView) ((TextView) view).setText(textContent);
        if (view instanceof Button) ((Button) view).setText(textContent);
        return this;
    }

    public ViewHolder setTextColor(@IdRes int viewId, @ColorRes int colorId) {
        View view = getSubView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(getColor(colorId));
        }

        if (view instanceof Button) {
            ((Button) view).setTextColor(getColor(colorId));
        }
        return this;
    }

    public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorRes int colorId) {
        View view = getSubView(viewId);
        view.setBackgroundColor(getColor(colorId));
        return this;
    }

    public ViewHolder setImageResource(@IdRes int viewId , @DrawableRes int drawableId) {
        View view = getSubView(viewId);
        if (view instanceof ImageView) {
            Glide.with(mContext).load(drawableId).into((ImageView) view);
        }
        return this;
    }

    public ViewHolder setVisibility(@IdRes int viewId , int visibility) {
        getSubView(viewId).setVisibility(visibility);
        return this;
    }

    public ViewHolder saveTag(Object tag) {
        itemView.setTag(tag);
        return this;
    }

    public ViewHolder getTag() {
        itemView.getTag();
        return this;
    }

    public ViewHolder saveSubTag(@IdRes int viewId, Object tag) {
        getSubView(viewId).setTag(tag);
        return this;
    }

    public ViewHolder getSubTag(@IdRes int viewId) {
        getSubView(viewId).getTag();
        return this;
    }

    /**
     * Get our item view according to IdRes.<br>
     * The method can avoid redundant findViewById codes.
     *
     * @param viewId the id resource of view which we want to get
     * @param <V> certain view type depends on {@code viewId}
     * @return {@code view} which is converted to {@code T}
     */
    private <V extends View> V getSubView(@IdRes int viewId) {
        View view = mSubViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mSubViewArray.put(viewId, view);
        }
        return (V) view;
    }
}
