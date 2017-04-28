package com.newamber.gracebook.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description: BaseFragment which extracts some common operations. <br>
 * {@code V} means a view interface which implemented by this Fragment. <br>
 * {@code T} means a sub presenter of BasePresenter. <p>
 *
 * Created by Newamber on 2017/4/24.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment
        implements View.OnClickListener {

    protected T mPresenter;

    // A reference points to an Activity attached by this Fragment.
    protected View mRootView;


    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) mRootView = inflater.inflate(getLayoutRes(), container, false);
        mPresenter = getPresenter();
        if (mPresenter != null) mPresenter.attachView((V) this);
        return mRootView;
    }

    /**
     * Processing click events at here.
     *
     * @param v view
     */
    public abstract void processClick(View v);

    /**
     * Get a presenter that implements BasePresenter.
     *
     * @return A presenter that implements BasePresenter.
     */
    protected abstract T getPresenter();

    protected abstract  @LayoutRes int getLayoutRes();

    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Detach the presenter from this Activity.
        if (mPresenter != null) mPresenter.detachView();
        mRootView = null;
    }
}
