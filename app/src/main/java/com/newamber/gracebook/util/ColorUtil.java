package com.newamber.gracebook.util;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.newamber.gracebook.app.GraceBookApplication;

import static com.newamber.gracebook.util.DeviceUtil.aboveAndroid_6;

/**
 * Description:.<br>
 * <p>more specific...</p>
 * <p>
 * Created by Newamber at 2017/7/20.
 */

public class ColorUtil {

    @SuppressWarnings("deprecation")
    public static @ColorInt int getColor(@ColorRes int colorId) {
        return aboveAndroid_6() ?
                GraceBookApplication.getContext().getResources().getColor(colorId, null):
                GraceBookApplication.getContext().getResources().getColor(colorId);
    }

    public static void setGradientColor(View v, @ColorRes int startColor, @ColorRes int endColor,
                                        int duration) {
        ValueAnimator animator = ValueAnimator.ofObject(ColorEvaluator.getInstance()
                , getColor(startColor), getColor(endColor));
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.addUpdateListener(animation -> {
            if (v instanceof CardView)
                ((CardView) v).setCardBackgroundColor((Integer) animation.getAnimatedValue());
            else
                v.setBackgroundColor((Integer) animation.getAnimatedValue());
        });
    }

    // --------------------------------private API--------------------------------------------------
    /**
     * This evaluator can be used to perform type interpolation between integer
     * values that represent ARGB colors.
     *
     * NOTE: This is a copy of {@code ArgbEvaluator.java}
     */
    private static class ColorEvaluator implements TypeEvaluator {
        private static final ColorEvaluator sInstance = new ColorEvaluator();

        /**
         * Returns an instance of <code>ColorEvaluator</code> that may be used in
         * {@link ValueAnimator#setEvaluator(TypeEvaluator)}. The same instance may
         * be used in multiple <code>Animator</code>s because it holds no state.
         * @return An instance of <code>ColorEvaluator</code>.
         *
         */
        static ColorEvaluator getInstance() {
            return sInstance;
        }

        /**
         * This function returns the calculated in-between value for a color
         * given integers that represent the start and end values in the four
         * bytes of the 32-bit int. Each channel is separately linearly interpolated
         * and the resulting calculated values are recombined into the return value.
         *
         * @param fraction The fraction from the starting to the ending values
         * @param startValue A 32-bit int value representing colors in the
         * separate bytes of the parameter
         * @param endValue A 32-bit int value representing colors in the
         * separate bytes of the parameter
         * @return A value that is calculated to be the linearly interpolated
         * result, derived by separating the start and end values into separate
         * color channels and interpolating each one separately, recombining the
         * resulting values in the same way.
         */
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            int startInt = (Integer) startValue;
            int startA = (startInt >> 24) & 0xff;
            int startR = (startInt >> 16) & 0xff;
            int startG = (startInt >> 8)  & 0xff;
            int startB = startInt & 0xff;

            int endInt = (Integer) endValue;
            int endA = (endInt >> 24) & 0xff;
            int endR = (endInt >> 16) & 0xff;
            int endG = (endInt >> 8)  & 0xff;
            int endB = endInt & 0xff;

            return  (startA + (int)(fraction * (endA - startA))) << 24 |
                    (startR + (int)(fraction * (endR - startR))) << 16 |
                    (startG + (int)(fraction * (endG - startG))) << 8  |
                    (startB + (int)(fraction * (endB - startB)));
        }
    }
}
