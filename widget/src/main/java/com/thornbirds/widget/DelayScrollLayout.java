package com.thornbirds.widget;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * This class implements a layout, which can delay the scroll of its content.
 *
 * @author YangLi yanglijd@gmail.com
 */
public class DelayScrollLayout extends FrameLayout {
    private static final String TAG = "DelayScrollLayout";

    private static final boolean DEBUG = false;

    private static final int SCROLL_STATE_IDLE = 0;
    private static final int SCROLL_STATE_DRAGGING = 1;
    private static final int SCROLL_STATE_SETTLING = 2;

    private int mScrollState = SCROLL_STATE_IDLE;
    private int mScrollPointerId = INVALID_POINTER;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mTouchSlop;

    private final DelayScroller mDelayScroller = new InterpolatorScroller(this);

    public DelayScrollLayout(@NonNull Context context) {
        this(context, null);
    }

    public DelayScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DelayScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
    }

    private void setScrollState(int state) {
        if (state == mScrollState) {
            return;
        }
        mScrollState = state;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        if (getChildCount() == 0) {
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointerId = e.getPointerId(0);
                mInitialTouchX = mLastTouchX = (int) (e.getX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY() + 0.5f);

                if (mScrollState == SCROLL_STATE_SETTLING) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    setScrollState(SCROLL_STATE_DRAGGING);
                }
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN:
                mScrollPointerId = e.getPointerId(actionIndex);
                mInitialTouchX = mLastTouchX = (int) (e.getX(actionIndex) + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY(actionIndex) + 0.5f);
                break;

            case MotionEvent.ACTION_MOVE: {
                final int index = e.findPointerIndex(mScrollPointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id " +
                            mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x = (int) (e.getX(index) + 0.5f);
                final int y = (int) (e.getY(index) + 0.5f);
                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    final int dx = x - mInitialTouchX;
                    final int dy = y - mInitialTouchY;
                    boolean startScroll = false;
                    if (Math.abs(dy) > mTouchSlop) {
                        mLastTouchY = mInitialTouchY + mTouchSlop * (dy < 0 ? -1 : 1);
                        startScroll = true;
                    }
                    if (startScroll) {
                        setScrollState(SCROLL_STATE_DRAGGING);
                    }
                }
            }
            break;

            case MotionEventCompat.ACTION_POINTER_UP: {
                onPointerUp(e);
            }
            break;

            case MotionEvent.ACTION_UP: {
            }
            break;

            case MotionEvent.ACTION_CANCEL: {
                cancelTouch();
            }
        }
        return mScrollState == SCROLL_STATE_DRAGGING;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (getChildCount() == 0) {
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mScrollPointerId = e.getPointerId(0);
                mInitialTouchX = mLastTouchX = (int) (e.getX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY() + 0.5f);
            }
            break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                mScrollPointerId = e.getPointerId(actionIndex);
                mInitialTouchX = mLastTouchX = (int) (e.getX(actionIndex) + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY(actionIndex) + 0.5f);
            }
            break;

            case MotionEvent.ACTION_MOVE: {
                final int index = e.findPointerIndex(mScrollPointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id " +
                            mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x = (int) (e.getX(index) + 0.5f);
                final int y = (int) (e.getY(index) + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;

                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    boolean startScroll = false;
                    if (Math.abs(dy) > mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop;
                        } else {
                            dy += mTouchSlop;
                        }
                        startScroll = true;
                    }
                    if (startScroll) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        setScrollState(SCROLL_STATE_DRAGGING);
                    }
                }

                if (mScrollState == SCROLL_STATE_DRAGGING) {
                    mLastTouchX = x;
                    mLastTouchY = y;

                    if (dy != 0) {
                        mDelayScroller.appendMoveStep(dy);
                    }
                }
            }
            break;

            case MotionEventCompat.ACTION_POINTER_UP: {
                onPointerUp(e);
            }
            break;

            case MotionEvent.ACTION_UP: {
                setScrollState(SCROLL_STATE_SETTLING);
            }
            break;

            case MotionEvent.ACTION_CANCEL: {
                cancelTouch();
            }
            break;
        }

        return true;
    }

    private void cancelTouch() {
        setScrollState(SCROLL_STATE_IDLE);
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mScrollPointerId = e.getPointerId(newIndex);
            mInitialTouchX = mLastTouchX = (int) (e.getX(newIndex) + 0.5f);
            mInitialTouchY = mLastTouchY = (int) (e.getY(newIndex) + 0.5f);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (getChildCount() > 0 && !mDelayScroller.isFinished()) {
            final View childTarget = getChildAt(0);
            final boolean canScroll = childTarget.canScrollVertically(mDelayScroller.getDirection());
            if (DEBUG) {
                Log.d(TAG, "computeScroll canScroll=" + canScroll);
            }
            if (!canScroll) {
                mDelayScroller.forwardMoveStage();
                if (DEBUG) {
                    Log.d(TAG, "computeScroll, move to next stage");
                }
            }
            if (mDelayScroller.forwardMoveStep()) {
                final int dy = mDelayScroller.getCurrForward();
                childTarget.scrollBy(0, dy);
                if (DEBUG) {
                    Log.d(TAG, "computeScroll dy=" + dy);
                }
            }
            if (mDelayScroller.isFinished()) {
                setScrollState(SCROLL_STATE_IDLE);
            } else {
                mDelayScroller.postInvalidateOnAnimation();
            }
        }
    }

    /**
     * Base definition for DelayScroller used in DelayScrollLayout
     */
    public static abstract class DelayScroller {

        private View mView;
        protected boolean mFinished = true;

        public DelayScroller(@NonNull View view) {
            mView = view;
        }

        protected final boolean isFinished() {
            return mFinished;
        }

        protected final void postInvalidateOnAnimation() {
            ViewCompat.postInvalidateOnAnimation(mView);
        }

        public abstract int getDirection();

        public abstract int getCurrForward();

        public abstract void appendMoveStep(final int dy);

        public abstract void forwardMoveStage();

        public abstract boolean forwardMoveStep();
    }

    /**
     * Implementation of DelayScroller using Interpolator for delay effect
     */
    protected static class InterpolatorScroller extends DelayScroller {

        //    private final LinkedList<Integer> mMoveStepQueue = new LinkedList<>();

        private int mTotalScroll;
        private int mDirection = 1;
        private int mLastScroll;
        private int mCurrScroll = 0;
        private long mStartTime = 0;
        private long mCurrTime = 0;
        private long mDuration = 300;

        private final Interpolator mInterpolator;

        public InterpolatorScroller(@NonNull View view) {
            super(view);
            mInterpolator = new DecelerateInterpolator2();
        }

        public InterpolatorScroller(@NonNull View view, Interpolator interpolator) {
            super(view);
            if (interpolator == null) {
                mInterpolator = new DecelerateInterpolator2();
            } else {
                mInterpolator = interpolator;
            }
        }

        @Override
        public int getDirection() {
            return mDirection;
        }

        @Override
        public int getCurrForward() {
            return mDirection * (mCurrScroll - mLastScroll);
        }

        @Override
        public void appendMoveStep(final int dy) {
            if (dy == 0) {
                return;
            }
            if (isFinished()) {
                mFinished = false;
                mDirection = dy > 0 ? 1 : -1;
                mTotalScroll = Math.abs(dy);
                postInvalidateOnAnimation();
                mLastScroll = mCurrScroll = 0;
                mCurrTime = mStartTime = AnimationUtils.currentAnimationTimeMillis();
                if (DEBUG) {
                    Log.d(TAG, "appendMoveStep new scroll dy=" + dy + ", mTotalScroll=" + mTotalScroll);
                }
            } else {
                if (dy < 0 && mDirection > 0 || dy > 0 && mDirection < 0) {
                    if (DEBUG) {
                        Log.d(TAG, "appendMoveStep illegal dy=" + dy + ", mTotalScroll=" + mTotalScroll);
                    }
                    return;
                }
                mTotalScroll = Math.max(0, mTotalScroll - mCurrScroll) + Math.abs(dy);
                final float ratio = (mCurrScroll - mLastScroll) / (float) mTotalScroll;
                mStartTime = Math.max(mStartTime, mCurrTime - (long) (mDuration * mInterpolator.inverse(ratio)));
                mLastScroll = mCurrScroll = 0;
                if (DEBUG) {
                    Log.d(TAG, "appendMoveStep dy=" + dy + ", mTotalScroll=" + mTotalScroll);
                }
            }
        }

        @Override
        public void forwardMoveStage() {
            mFinished = true;
        }

        @Override
        public boolean forwardMoveStep() {
            if (isFinished()) {
                return false;
            }
            mCurrTime = AnimationUtils.currentAnimationTimeMillis();
            final long elapsedTime = mCurrTime - mStartTime;
            if (elapsedTime <= mDuration) {
                mLastScroll = mCurrScroll;
                final float ratio = mInterpolator.interpolate(elapsedTime / (float) mDuration);
                mCurrScroll = (int) (mTotalScroll * ratio);
                if (mLastScroll < mCurrScroll || (mCurrScroll = mLastScroll + 1) <= mTotalScroll) {
                    if (DEBUG) {
                        Log.d(TAG, "forwardMoveStep mTotalScroll=" + mTotalScroll + ", ratio=" +
                                ratio + ", elapsedTime=" + elapsedTime + ", dy=" + (mCurrScroll - mLastScroll));
                    }
                    return true;
                }
                if (DEBUG) {
                    Log.d(TAG, "forwardMoveStep complete in advance");
                }
            } else {
                if (DEBUG) {
                    Log.d(TAG, "forwardMoveStep move to next stage");
                }
            }
            forwardMoveStage();
            return forwardMoveStep();
        }

        protected abstract static class Interpolator {

            public abstract float interpolate(float input);

            public abstract float inverse(float fraction);

        }

        protected static class DecelerateInterpolator extends Interpolator {
            private final float mInertia = 0.1f;

            public float interpolate(@FloatRange(from = 0, to = 1) float input) {
                return 1.0f - (1.0f - input) * (1.0f - input);
            }

            @Override
            public float inverse(@FloatRange(from = 0, to = 1) float fraction) {
                return 1.0f - (float) Math.sqrt(1.0 - mInertia * fraction);
            }
        }

        protected static class DecelerateInterpolator2 extends Interpolator {
            private final float mInertia = 0.1f;

            public float interpolate(@FloatRange(from = 0, to = 1) float input) {
                return (float) Math.sin(input * Math.PI / 2.0);
            }

            @Override
            public float inverse(@FloatRange(from = 0, to = 1) float fraction) {
                return (float) (2.0 * Math.asin(mInertia * fraction) / Math.PI);
            }
        }
    }
}
