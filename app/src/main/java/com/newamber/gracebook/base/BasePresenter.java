package com.newamber.gracebook.base;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Description: BasePresenter which extracts some common operations.<br>
 * {@code V} means a view interface type.<p>
 *
 * Created by Newamber on 2017/4/24.
 */
public abstract class BasePresenter<V> {

    private Reference<V> attachedView;

    /**
     * Get attached View and then you can invoke relevant method in presenter.
     * Note that do not invoke this method to instantiate the BaseView reference field variable,
     * You should invoke it at specific method and then you can ensure the existence of presenter's
     * attached View interface.
     *
     * @return the attached View we need
     */
    protected V getView() {
        return attachedView.get();
    }

    void attachView(V view) {
        attachedView = new WeakReference<>(view);
    }

    void detachView() {
        if (isAttached()) {
            attachedView.clear();
            attachedView = null;
        }
    }

    // ---------------------------------EventBus----------------------------------------------------
    protected void post(Object event) {
        EventBus.getDefault().post(event);
    }

    protected void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    // --------------------------------private API--------------------------------------------------
    private boolean isAttached() {
        return attachedView != null && attachedView.get() != null;
    }
}
