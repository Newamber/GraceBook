package com.newamber.gracebook.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.newamber.gracebook.util.DeviceUtil;

import org.greenrobot.eventbus.EventBus;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Description: BaseFragment which extracts some common operations. <br>
 * {@code T} means a sub presenter of BasePresenter. <p>
 *
 * Created by Newamber on 2017/4/24.
 */
@SuppressWarnings("unused")
public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements View.OnClickListener {

    // A reference which points to an RootView attached by this Fragment.
    private View mRootView;

    // Host Activity
    private BaseActivity mHostActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHostActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) mRootView = inflater.inflate(getLayoutId(), container, false);
        /*mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView((V) this);*/
        initView();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isEventBusEnabled()) EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHostActivity = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isEventBusEnabled()) EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detach the presenter from this Activity.
       // if (mPresenter != null) mPresenter.detachView();
        mRootView = null;
    }

    /**
     * Initialize and bind views here.
     *
     */
    protected void initView() {}

    /**
     * Initialize and bind data here.
     *
     */
    protected void initData() {}

    @SuppressWarnings("unused")
    protected BaseActivity getHostActivity() {
        return mHostActivity;
    }

    protected boolean isEventBusEnabled() {
        return false;
    }

    /**
     * Processing click events at here.
     *
     * @param v view
     */
    protected void processClick(View v) {}

    protected void bindOnClickListener(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    @SuppressWarnings("unchecked")
    protected T getHostPresenter() {
        return (T) mHostActivity.getPresenter();
    }

    protected View getRootView() {
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    protected abstract  @LayoutRes int getLayoutId();

    protected void setImageByGlide(ImageView view, @DrawableRes int drawableId) {
        Glide.with(this).load(drawableId).into(view);
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findViewById(@IdRes int viewId) {
        return getRootView().findViewById(viewId);
    }

    protected void startAnimator(View target, @AnimatorRes int animId) {
        Animator animator = AnimatorInflater.loadAnimator(mHostActivity, animId);

        animator.setTarget(target);
        animator.start();
    }

    protected void startAnimators(@AnimatorRes int animId, View... targets) {
        for (View v : targets) {
            Animator animator = AnimatorInflater.loadAnimator(mHostActivity, animId);
            animator.setTarget(v);
            animator.start();
        }
    }

    protected void post(Object event) {
        EventBus.getDefault().post(event);
    }

    protected void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    protected  void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    protected void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    protected void setEasyItemAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        int duration = 500;
        recyclerView.setHasFixedSize(true);

        AlphaInAnimationAdapter alphaInAdapter = new AlphaInAnimationAdapter(adapter);
        alphaInAdapter.setDuration(duration);
        alphaInAdapter.setFirstOnly(false);

        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaInAdapter);
        scaleAdapter.setDuration(duration);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(false);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        animator.setAddDuration(duration);
        animator.setRemoveDuration(150);

        recyclerView.setAdapter(scaleAdapter);
    }

    /**
     * Auxiliary method which help show press physical effect.
     *
     * @param isUpward true show the upward physical effect like Button behave above API 21
     *                 and false show the compression physical effect just is alike real life
     * @param views view array
     */
    protected void setCompressEffect(boolean isUpward, View... views) {
        for (View v : views) {
            if (isUpward) {
                v.setOnTouchListener((view, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", DeviceUtil.dp2Px(8));
                            downAnim.setDuration(200);
                            downAnim.setInterpolator(new DecelerateInterpolator());
                            downAnim.start();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                            upAnim.setDuration(200);
                            upAnim.setInterpolator(new AccelerateInterpolator());
                            upAnim.start();
                            break;
                    }
                    return false;
                });
            } else {
                v.setOnTouchListener((view, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                            downAnim.setDuration(200);
                            downAnim.setInterpolator(new DecelerateInterpolator());
                            downAnim.start();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", DeviceUtil.dp2Px(8));
                            upAnim.setDuration(200);
                            upAnim.setInterpolator(new AccelerateInterpolator());
                            upAnim.start();
                            break;
                    }
                    return false;
                });
            }
        }
    }
}
