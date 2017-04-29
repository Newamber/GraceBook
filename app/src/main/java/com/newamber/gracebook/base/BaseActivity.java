package com.newamber.gracebook.base;

import android.os.Bundle;
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

    protected T attachedPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ActivityCollectorUtil.addActivity(this);
        attachedPresenter = getAttachedPresenter();
        if (attachedPresenter != null) attachedPresenter.attachView((V) this);
    }

    /**
     * Processing click events at here.
     *
     * @param v view waited to be clicked.
     */
    public abstract void processClick(View v);

    /**
     * Initializing views here.
     *
     */
    public abstract void initView();

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    /**
     * Get a presenter that implements BasePresenter.
     *
     * @return A presenter that implements BasePresenter.
     */
    protected abstract T getAttachedPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
        // Detach the presenter from this Activity.
        if (attachedPresenter != null) attachedPresenter.detachView();
    }
}
