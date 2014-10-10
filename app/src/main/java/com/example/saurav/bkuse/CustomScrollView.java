package com.example.saurav.bkuse;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by saurav on 9/25/14.
 * Created for implementing parallax and fading action bar effect on scrolling .
 * @see <a href="http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level/">Pushing Action Bar Next Level</a>
 * @see <a href="https://github.com/nirhart/ParallaxScroll/">Parallax ScrollView For Android</a>
 */
public class CustomScrollView extends ScrollView {

    private Context mContext;

    private static final int DEFAULT_PARALLAX_VIEWS = 1;
    private static final float DEFAULT_INNER_PARALLAX_FACTOR = 1.9F;
    private static final float DEFAULT_PARALLAX_FACTOR = 1.9F;
    private static final float DEFAULT_ALPHA_FACTOR = -1F;

    private int mNumOfParallaxViews = DEFAULT_PARALLAX_VIEWS;

    private float mInnerParallaxFactor = DEFAULT_PARALLAX_FACTOR;
    private float mParallaxFactor = DEFAULT_PARALLAX_FACTOR;
    private float mAlphaFactor = DEFAULT_ALPHA_FACTOR;

    private ArrayList<ParallaxedView> mParallaxedViews = new ArrayList<ParallaxedView>();

    private Drawable mBackgroundDrawable;
    private ActionBar mActionBar;
    private RelativeLayout mRelativeLayout;
    private View mFooterView;


    public CustomScrollView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ParallaxScroll);
        this.mParallaxFactor = typeArray.getFloat(R.styleable.ParallaxScroll_parallaxFactor, DEFAULT_PARALLAX_FACTOR);
        this.mAlphaFactor = typeArray.getFloat(R.styleable.ParallaxScroll_alphaFactor, DEFAULT_ALPHA_FACTOR);
        this.mInnerParallaxFactor = typeArray.getFloat(R.styleable.ParallaxScroll_innerParallaxFactor, DEFAULT_INNER_PARALLAX_FACTOR);
        this.mNumOfParallaxViews = typeArray.getInt(R.styleable.ParallaxScroll_parallaxViewsNum, DEFAULT_PARALLAX_VIEWS);
        typeArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        makeViewsParallax();
    }

    private void makeViewsParallax() {
        if (getChildCount() > 0 && getChildAt(0) instanceof ViewGroup) {
            ViewGroup viewsHolder = (ViewGroup) getChildAt(0);
            int numOfParallaxViews = Math.min(this.mNumOfParallaxViews, viewsHolder.getChildCount());
            for (int i = 0; i < numOfParallaxViews; i++) {
                ParallaxedView parallaxedView = new ScrollViewParallaxedItem(viewsHolder.getChildAt(i));
                mParallaxedViews.add(parallaxedView);
            }
        }
    }

    public void setmLinearLayout(View mLinearLayout) {
        this.mFooterView = mLinearLayout;
    }

    public void setmRelativeLayout(RelativeLayout mRelativeLayout) {
        this.mRelativeLayout = mRelativeLayout;
    }

    public void setmActionBar(ActionBar mActionBar) {
        this.mActionBar = mActionBar;
    }

    public void setmBackgroundDrawable(Drawable mBackgroundDrawable) {
        this.mBackgroundDrawable = mBackgroundDrawable;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //For Fading ActionBar

        final int headerHeight = mRelativeLayout.getHeight() - mActionBar.getHeight();
        final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
        final int newAlpha = (int) (ratio * 255);
        if(t<=5){
            mActionBar.setTitle("");
        }else {
            mActionBar.setTitle("ActionBar");
        }

        mBackgroundDrawable.setAlpha(newAlpha);

        //For Showing FooterView

        if (t - oldt > 10) {
            if (mFooterView.getVisibility() == VISIBLE) {
                mFooterView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down));
                mFooterView.setVisibility(GONE);
            }
        } else if (oldt - t > 10) {
            if (mFooterView.getVisibility() == GONE) {
                mFooterView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up));
                mFooterView.setVisibility(VISIBLE);
            }
        }

        //For giving Parallax Effect

        float parallax = mParallaxFactor;
        float alpha = mAlphaFactor;
        for (ParallaxedView parallaxedView : mParallaxedViews) {
            parallaxedView.setOffset((float) t / parallax);
            parallax *= mInnerParallaxFactor;
            if (alpha != DEFAULT_ALPHA_FACTOR) {
                float fixedAlpha = (t <= 0) ? 1 : (100 / ((float) t * alpha));
                parallaxedView.setAlpha(fixedAlpha);
                alpha /= mAlphaFactor;

            }
            parallaxedView.animateNow();
        }
    }

    protected class ScrollViewParallaxedItem extends ParallaxedView {
        public ScrollViewParallaxedItem(View view) {
            super(view);
        }

        @Override
        protected void translatePreIcs(View view, float offset) {
            view.offsetTopAndBottom((int) offset - lastOffset);
            lastOffset = (int) offset;
        }
    }
}
