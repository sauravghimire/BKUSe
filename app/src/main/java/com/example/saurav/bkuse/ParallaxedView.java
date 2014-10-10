package com.example.saurav.bkuse;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurav on 9/24/14.
 * Used by CustomScrollViewForProductDetail for giving parallax and fading actionbar effect to scroll view.
 * @see <a href="https://github.com/nirhart/ParallaxScroll/">Parallax ScrollView For Android</a>
 */
public abstract class ParallaxedView {

    public static  boolean sIsApi11 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    protected WeakReference<View> view;
    protected int lastOffset;
    protected List<Animation> animations;

    abstract protected void translatePreIcs(View view, float offset);

    public ParallaxedView(View view) {
        this.lastOffset = 0;
        this.animations = new ArrayList<Animation>();
        this.view = new WeakReference<View>(view);
    }

    public boolean is(View v) {
        return (v != null && view != null && view.get() != null && view.get().equals(v));
    }

    @SuppressLint("NewApi")
    public void setOffset(float offset) {
        View view = this.view.get();
        if (view != null) {
            if (sIsApi11) {
                view.setTranslationY(offset);
            } else {
                translatePreIcs(view, offset);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setAlpha(float alpha) {
        View view = this.view.get();
        if (view != null) {
            if (sIsApi11) {
                view.setAlpha(alpha);
            } else {
                alphaPreIcs(view, alpha);
            }
        }
    }

    protected synchronized void addAnimation(Animation animation) {
        animations.add(animation);
    }

    protected void alphaPreIcs(View view, float alpha) {
        addAnimation(new AlphaAnimation(alpha, alpha));
    }

    protected synchronized void animateNow() {
        View view = this.view.get();
        if (view != null) {
            AnimationSet set = new AnimationSet(true);
            for (Animation animation : animations) {
                if (animation != null) {
                    set.addAnimation(animation);
                }
            }
            set.setDuration(0);
            set.setFillAfter(true);
            view.setAnimation(set);
            set.start();
            animations.clear();
        }
    }

    public void setView(View view) {
        this.view = new WeakReference<View>(view);
    }
}