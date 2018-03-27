package com.newamber.gracebook.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.AnimatorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.newamber.gracebook.util.ActivityUtil;
import com.newamber.gracebook.util.DeviceUtil;

import org.greenrobot.eventbus.EventBus;

import static com.newamber.gracebook.util.ActivityUtil.addActivity;
import static com.newamber.gracebook.util.DeviceUtil.aboveAndroid_5;

/**
 * Description: BaseActivity which extracts some common operations. <br>
 * {@code V} means a view interface which implemented by this Activity. <br>
 * {@code T} means a sub presenter of BasePresenter. <p>
 *
 * Created by Newamber on 2017/4/24.
 */
@SuppressWarnings("unused")
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity
        implements View.OnClickListener {

    private T mPresenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (aboveAndroid_5()) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            setTransitionAnim();
        }
        setContentView(getLayoutId());

        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView((V) this);
        addActivity(this);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isEventBusEnabled()) EventBus.getDefault().register(this);
    }

    public void startTransitionActivity(Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        if (aboveAndroid_5()) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    // startActivity if need pass data
    public void startTransitionActivity(Intent intent) {
        if (aboveAndroid_5()) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void startTransitionActivity(Class<? extends Activity> targetActivity, int timeDelay) {
        Intent intent = new Intent(this, targetActivity);
        if (aboveAndroid_5()) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            new Handler().postDelayed(() -> startActivity(intent), timeDelay);
        }
    }

    // startActivity if need postpone
    public void startTransitionActivity(Intent intent, int timeDelay) {
        if (aboveAndroid_5()) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            new Handler().postDelayed(() -> startActivity(intent), timeDelay);
        }
    }

    /**
     * Initializing Views here.
     */
    public abstract void initViews();

    /**
     * Get the presenter attaching to relevant View.
     *
     * @return the presenter
     */
    protected T getPresenter() {
        return mPresenter;
    }

    /**
     * Write like <br>
     * {@code return R.layout.activity_main;}.<br>
     *
     * @return a LayoutRes id.
     */
    protected abstract @LayoutRes int getLayoutId();

    /**
     * Processing click events here.
     *
     * @param v view waited to be clicked.
     */
    protected void processClick(View v) {}

    /**
     * Create a presenter that implements BasePresenter.
     *
     * @return Certain presenter that implements BasePresenter.
     */
    protected T createPresenter() {
        return null;
    }

    protected void bindOnClickListener(View... views) {
        for (View v : views) v.setOnClickListener(this);
    }

    /**
     * Override this method in specific sub class if you need different transition effect.
     * Following is default.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setTransitionAnim() {
        int duration = 500;
        Interpolator interpolator = new OvershootInterpolator();

        // default
        Explode explode = new Explode();
        explode.setDuration(duration);
        explode.setInterpolator(interpolator);

        Slide slide = new Slide(Gravity.END);
        slide.setDuration(duration);
        slide.setInterpolator(interpolator);

        Fade fade = new Fade();
        fade.setDuration(duration);
        fade.setInterpolator(interpolator);

        // A.startActivity() -> B
        // B's transition.
        getWindow().setEnterTransition(slide);
        // A's transition.
        getWindow().setExitTransition(slide);

        // B.onBackPressed() -> A
        // B's transition.
        getWindow().setReturnTransition(slide);
        // A's transition.
        getWindow().setReenterTransition(slide);
    }

    /**
     * Auxiliary method which help show press physical effect.
     *
     * @param isUpward true show the upward physical effect like Button behave above API 21
     *                 and false show the compression physical effect just is alike real life
     * @param views view array
     */
    @SuppressLint("ClickableViewAccessibility")
    protected void setCompressEffect(boolean isUpward, View... views) {
        Interpolator aInterpolator = new AccelerateInterpolator();
        Interpolator dInterpolator = new DecelerateInterpolator();
        for (View v : views) {
            if (isUpward) {
                v.setOnTouchListener((view, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", DeviceUtil.dp2Px(8));
                            downAnim.setDuration(200);
                            downAnim.setInterpolator(dInterpolator);
                            downAnim.start();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                            upAnim.setDuration(200);
                            upAnim.setInterpolator(aInterpolator);
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
                            downAnim.setInterpolator(dInterpolator);
                            downAnim.start();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", DeviceUtil.dp2Px(8));
                            upAnim.setDuration(200);
                            upAnim.setInterpolator(aInterpolator);
                            upAnim.start();
                            break;
                    }
                    return false;
                });
            }
        }
    }

    protected void startAnimator(View target, @AnimatorRes int animId) {
        Animator animator = AnimatorInflater.loadAnimator(this, animId);
        animator.setTarget(target);
        animator.start();
    }

    protected void startAnimator(View target, @AnimatorRes int animId, int delay) {
        Animator animator = AnimatorInflater.loadAnimator(this, animId);
        animator.setStartDelay(delay);
        animator.setTarget(target);
        animator.start();
    }

    protected void startAnimators(@AnimatorRes int animId, View... targets) {
        for (View v : targets) {
            Animator animator = AnimatorInflater.loadAnimator(this, animId);
            animator.setTarget(v);
            animator.start();
        }
    }

    protected void setImageByGlide(ImageView view, @DrawableRes int drawableId) {
        Glide.with(this).load(drawableId).into(view);
    }

    // --------------------------------EventBus-----------------------------------------------------
    protected void post(Object event) {
        EventBus.getDefault().post(event);
    }

    protected void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    protected void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    protected void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    protected boolean isEventBusEnabled() {
        return false;
    }
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onStop() {
        super.onStop();
        if (isEventBusEnabled()) EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this);

        // Detach the presenter from this Activity.
        if (mPresenter != null) mPresenter.detachView();
    }
}