package com.newamber.gracebook.base;

import java.lang.ref.WeakReference;

/**
 * Description: BasePresenter which extracts some common operations.<br>
 * {@code V} means a view interface.<p>
 *
 * Created by Newamber on 2017/4/24.
 */
@SuppressWarnings("all")
public abstract class BasePresenter<V> {

    private WeakReference<V> attachedView;

    public void attachView(V view) {
        attachedView = new WeakReference<>(view);
    }

    public void detachView() {
        if (attachedView != null) {
            attachedView.clear();
            attachedView = null;
        }
    }

    /**
     * Get attached View and then you can invoke relevant method in presenter.
     * Note, do not invoke this method to instantiate the BaseView reference field variable,
     * You should invoke it at specific method.
     *
     * @return the attched View we need
     */
    protected V getView() {
        return attachedView.get();
    }

    public boolean isViewAttached() {
        return attachedView != null && attachedView.get() != null;
    }
}
