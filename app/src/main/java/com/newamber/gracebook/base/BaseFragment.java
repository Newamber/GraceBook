package com.newamber.gracebook.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

    private T mPresenter;
    // A reference points to an RootView attached by this Fragment.
    private View mRootView;

    // Host Activity
    private AppCompatActivity mHostActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHostActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) mRootView = inflater.inflate(getLayoutId(), container, false);
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView((V) this);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * Initialize and bind views here.
     *
     */
    public abstract void initView();

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
    protected abstract T createPresenter();

    /**
     * Get the presenter attaching to relevant View.
     *
     * @return the presenter
     */
    protected T getPresenter() {
        return mPresenter;
    }

    public AppCompatActivity getHostActivity() {
        return mHostActivity;
    }

    protected View getRootView() {
        return mRootView;
    }

    protected abstract  @LayoutRes int getLayoutId();

    @Override
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
