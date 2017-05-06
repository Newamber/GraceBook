package com.newamber.gracebook.model;

import android.content.Context;
import android.os.Build;
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

/**
 * Description: A encapsulated common ViewHolder.<p>
 *
 * Created by Newamber on 2017/5/2.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mSubViewArray;

    // The context of relevant Activity.
    private Context mContext;

    /**
     * the constructor of ViewHolder
     *
     * @param itemView the outer layer layout of sub views of list item
     * @param context the context of parent ViewGroup(like Activity) of our ViewHolder
     */
    public ViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mSubViewArray = new SparseArray<>();
    }

    /**
     * Encapsulated method used to set text of view.
     *
     * @param viewID  the ID resource of view which is set text
     * @param textContent the text we want to set
     * @return {@code this} the instance of ViewHolder
     */
    public ViewHolder setText(@IdRes int viewID , CharSequence textContent) {
        View view = getSubView(viewID);
        if (view instanceof TextView) ((TextView) view).setText(textContent);
        if (view instanceof Button) ((Button) view).setText(textContent);
        return this;
    }

    /**
     * Encapsulated method used to set color of view.
     *
     * @param viewID the ID resource of view which is set color
     * @param colorID the color we want to set
     * @return {@code this} the instance of ViewHolder
     */
    public ViewHolder setColor(@IdRes int viewID, @ColorRes int colorID) {
        View view = getSubView(viewID);
        if (view instanceof TextView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ((TextView) view).setTextColor(mContext.getResources().getColor(colorID, null));
            else
                ((TextView) view).setTextColor(mContext.getResources().getColor(colorID));
        }

        if (view instanceof Button) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ((Button) view).setTextColor(mContext.getResources().getColor(colorID, null));
            else
                ((Button) view).setTextColor(mContext.getResources().getColor(colorID));
        }
        return this;
    }

    /**
     * Encapsulated method used to set background color of view.
     *
     * @param viewID the ID resource of view which is set background color
     * @param colorID he color we want to set
     * @return {@code this} the instance of ViewHolder
     */
    public ViewHolder setBackgroundColor(@IdRes int viewID, @ColorRes int colorID) {
        View view = getSubView(viewID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            view.setBackgroundColor(mContext.getResources().getColor(colorID, null));
        else
            view.setBackgroundColor(mContext.getResources().getColor(colorID));

        return this;
    }

    /**
     * Encapsulated method used to set drawable of view.
     *
     * @param viewID the ID resource of view which is set image
     * @param drawableID the ID drawable resource image which we want to set
     * @return {@code this} the instance of ViewHolder
     */
    public ViewHolder setImageResource(@IdRes int viewID , @DrawableRes int drawableID) {
        View view = getSubView(viewID);
        if (view instanceof ImageView) {
            Glide.with(mContext).load(drawableID).into((ImageView) view);
        }
        return this ;
    }

    public ViewHolder setOnClickListener(@IdRes int viewID , View.OnClickListener clickListener) {
        getSubView(viewID).setOnClickListener(clickListener);
        return this;
    }

    public ViewHolder setOnLongClickListener(@IdRes int viewID , View.OnLongClickListener longClickListener) {
        getSubView(viewID).setOnLongClickListener(longClickListener);
        return this;
    }

    /**
     * Get our item view according to IDRes.<br>
     * The method can avoid redundant findViewById codes.
     *
     * @param viewID the ID resource of view which we want to get
     * @param <T> certain view type depends on {@code viewID}
     * @return {@code (T) view} which is converted to {@code T}
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getSubView(@IdRes int viewID) {
        View view = mSubViewArray.get(viewID);
        if (view == null) {
            view = itemView.findViewById(viewID);
            mSubViewArray.put(viewID, view);
        }
        return (T) view;
    }

}
