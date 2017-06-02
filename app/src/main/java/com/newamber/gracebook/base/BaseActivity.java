package com.newamber.gracebook.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.newamber.gracebook.util.ActivityCollectorUtil;

/**
 * Description: BaseActivity which extracts some common operations. <br>
 * {@code V} means a view interface which implemented by this Activity. <br>
 * {@code T} means a sub presenter of BasePresenter. <p>
 *
 * Created by Newamber on 2017/4/24.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity
        implements View.OnClickListener {

    private T mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView((V) this);
        ActivityCollectorUtil.addActivity(this);
        initView();
    }

    /**
     * Processing click events here.
     *
     * @param v view waited to be clicked.
     */
    public abstract void processClick(View v);

    /**
     * Initializing Views here.
     *
     */
    public abstract void initView();

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    /**
     * Create a presenter that implements BasePresenter.
     *
     * @return Certain presenter that implements BasePresenter.
     */
    protected abstract T createPresenter();

    protected abstract @LayoutRes int getLayoutId();

    /**
     * Get the presenter attaching to relevant View.
     *
     * @return the presenter
     */
    public T getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);

        // Detach the presenter from this Activity.
        if (mPresenter != null) mPresenter.detachView();
    }
}
