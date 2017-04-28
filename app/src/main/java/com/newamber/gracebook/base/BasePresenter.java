package com.newamber.gracebook.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Description: BasePresenter which extracts some common operations.<br>
 * {@code V} means a View interface.<p>
 *
 * Created by Newamber on 2017/4/24.
 */

@SuppressWarnings("all")
public abstract class BasePresenter<V> {

    private Reference<V> mViewWeakReference;

    public void attachView(V view) {
        mViewWeakReference = new WeakReference<>(view);
    }

    public void detachView() {
        if (mViewWeakReference != null) {
            mViewWeakReference.clear();
            mViewWeakReference = null;
        }
    }

    protected V getView() {
        return  mViewWeakReference.get();
    }

    protected boolean isViewAttached() {
        return mViewWeakReference != null && mViewWeakReference.get() != null;
    }

}
