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

    private Reference<V> attachedView;

    public void attachView(V view) {
        attachedView = new WeakReference<>(view);
    }

    public void detachView() {
        if (attachedView != null) {
            attachedView.clear();
            attachedView = null;
        }
    }

    protected V getView() {
        return  attachedView.get();
    }

    protected boolean isViewAttached() {
        return attachedView != null && attachedView.get() != null;
    }

}
