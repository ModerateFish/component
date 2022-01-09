package com.thornbirds.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.annotation.FloatRange
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.thornbirds.component.utils.DebugUtils
import kotlin.math.*

/**
 * This class implements a layout, which can delay the scroll of its content.
 *
 * @author YangLi yanglijd@gmail.com
 */
class DelayScrollLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val mDelayScroller: DelayScroller = InterpolatorScroller(this)

    private var mScrollState = SCROLL_STATE_IDLE
    private var mScrollPointerId = ViewDragHelper.INVALID_POINTER
    private var mInitialTouchX = 0
    private var mInitialTouchY = 0
    private var mLastTouchX = 0
    private var mLastTouchY = 0

    private fun setScrollState(state: Int) {
        if (state == mScrollState) {
            return
        }
        mScrollState = state
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        if (childCount == 0) {
            return false
        }
        val action = e.action
        val actionIndex = e.actionIndex
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = e.getPointerId(0)
                mLastTouchX = (e.x + 0.5f).toInt()
                mLastTouchY = (e.y + 0.5f).toInt()
                mInitialTouchX = mLastTouchX
                mInitialTouchY = mLastTouchY
                if (mScrollState == SCROLL_STATE_SETTLING) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    setScrollState(SCROLL_STATE_DRAGGING)
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mScrollPointerId = e.getPointerId(actionIndex)
                mLastTouchX = (e.getX(actionIndex) + 0.5f).toInt()
                mLastTouchY = (e.getY(actionIndex) + 0.5f).toInt()
                mInitialTouchX = mLastTouchX
                mInitialTouchY = mLastTouchY
            }
            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(mScrollPointerId)
                if (index < 0) {
                    Log.e(
                        TAG, "Error processing scroll; pointer index for id $mScrollPointerId " +
                                "not found. Did any MotionEvents get skipped?"
                    )
                    return false
                }
                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()
                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    val dx = x - mInitialTouchX
                    val dy = y - mInitialTouchY
                    val startScroll = if (abs(dy) > mTouchSlop) {
                        mLastTouchY = mInitialTouchY + mTouchSlop * if (dy < 0) -1 else 1
                        true
                    } else false
                    if (startScroll) {
                        setScrollState(SCROLL_STATE_DRAGGING)
                    }
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onPointerUp(e)
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_CANCEL -> {
                cancelTouch()
            }
        }
        return mScrollState == SCROLL_STATE_DRAGGING
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (childCount == 0) {
            return false
        }
        val action = e.action
        val actionIndex = e.actionIndex
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = e.getPointerId(0)
                mLastTouchX = (e.x + 0.5f).toInt()
                mLastTouchY = (e.y + 0.5f).toInt()
                mInitialTouchX = mLastTouchX
                mInitialTouchY = mLastTouchY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mScrollPointerId = e.getPointerId(actionIndex)
                mLastTouchX = (e.getX(actionIndex) + 0.5f).toInt()
                mLastTouchY = (e.getY(actionIndex) + 0.5f).toInt()
                mInitialTouchX = mLastTouchX
                mInitialTouchY = mLastTouchY
            }
            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(mScrollPointerId)
                if (index < 0) {
                    Log.e(
                        TAG, "Error processing scroll; pointer index for id $mScrollPointerId " +
                                "not found. Did any MotionEvents get skipped?"
                    )
                    return false
                }
                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()
                val dx = mLastTouchX - x
                var dy = mLastTouchY - y
                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    var startScroll = false
                    if (abs(dy) > mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop
                        } else {
                            dy += mTouchSlop
                        }
                        startScroll = true
                    }
                    if (startScroll) {
                        parent.requestDisallowInterceptTouchEvent(true)
                        setScrollState(SCROLL_STATE_DRAGGING)
                    }
                }
                if (mScrollState == SCROLL_STATE_DRAGGING) {
                    mLastTouchX = x
                    mLastTouchY = y
                    if (dy != 0) {
                        mDelayScroller.appendMoveStep(dy)
                    }
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onPointerUp(e)
            }
            MotionEvent.ACTION_UP -> {
                setScrollState(SCROLL_STATE_SETTLING)
            }
            MotionEvent.ACTION_CANCEL -> {
                cancelTouch()
            }
        }
        return true
    }

    private fun cancelTouch() {
        setScrollState(SCROLL_STATE_IDLE)
    }

    private fun onPointerUp(e: MotionEvent) {
        val actionIndex = e.actionIndex
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            val newIndex = if (actionIndex == 0) 1 else 0
            mScrollPointerId = e.getPointerId(newIndex)
            mLastTouchX = (e.getX(newIndex) + 0.5f).toInt()
            mLastTouchY = (e.getY(newIndex) + 0.5f).toInt()
            mInitialTouchX = mLastTouchX
            mInitialTouchY = mLastTouchY
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (childCount > 0 && !mDelayScroller.isFinished) {
            val childTarget = getChildAt(0)
            val canScroll = childTarget.canScrollVertically(mDelayScroller.direction)
            if (DEBUG) {
                Log.d(TAG, "computeScroll canScroll=$canScroll")
            }
            if (!canScroll) {
                mDelayScroller.forwardMoveStage()
                if (DEBUG) {
                    Log.d(TAG, "computeScroll, move to next stage")
                }
            }
            if (mDelayScroller.forwardMoveStep()) {
                val dy = mDelayScroller.currForward
                childTarget.scrollBy(0, dy)
                if (DEBUG) {
                    Log.d(TAG, "computeScroll dy=$dy")
                }
            }
            if (mDelayScroller.isFinished) {
                setScrollState(SCROLL_STATE_IDLE)
            } else {
                mDelayScroller.postInvalidateOnAnimation()
            }
        }
    }

    /**
     * Base definition for DelayScroller used in DelayScrollLayout
     */
    abstract class DelayScroller(private val mView: View) {
        var isFinished = true

        fun postInvalidateOnAnimation() {
            ViewCompat.postInvalidateOnAnimation(mView)
        }

        abstract val direction: Int
        abstract val currForward: Int

        abstract fun appendMoveStep(dy: Int)
        abstract fun forwardMoveStage()
        abstract fun forwardMoveStep(): Boolean
    }

    /**
     * Implementation of DelayScroller using Interpolator for delay effect
     */
    protected class InterpolatorScroller(view: View) : DelayScroller(view) {

        private val mInterpolator = DecelerateInterpolator2()

        private var mTotalScroll = 0
        override var direction = 1
            private set
        private var mLastScroll = 0
        private var mCurrScroll = 0
        private var mStartTime = 0L
        private var mCurrTime = 0L
        private val mDuration = 300L

        override val currForward: Int
            get() = direction * (mCurrScroll - mLastScroll)

        override fun appendMoveStep(dy: Int) {
            if (dy == 0) {
                return
            }
            if (isFinished) {
                isFinished = false
                direction = if (dy > 0) 1 else -1
                mTotalScroll = abs(dy)
                postInvalidateOnAnimation()
                mCurrScroll = 0
                mLastScroll = mCurrScroll
                mStartTime = AnimationUtils.currentAnimationTimeMillis()
                mCurrTime = mStartTime
                if (DEBUG) {
                    Log.d(TAG, "appendMoveStep new scroll dy=$dy, mTotalScroll=$mTotalScroll")
                }
            } else {
                if (dy < 0 && direction > 0 || dy > 0 && direction < 0) {
                    if (DEBUG) {
                        Log.d(TAG, "appendMoveStep illegal dy=$dy, mTotalScroll=$mTotalScroll")
                    }
                    return
                }
                mTotalScroll = max(0, mTotalScroll - mCurrScroll) + abs(dy)
                val ratio = (mCurrScroll - mLastScroll) / mTotalScroll.toFloat()
                mStartTime = max(
                    mStartTime,
                    mCurrTime - (mDuration * mInterpolator.inverse(ratio)).toLong()
                )
                mCurrScroll = 0
                mLastScroll = mCurrScroll
                if (DEBUG) {
                    Log.d(TAG, "appendMoveStep dy=$dy, mTotalScroll=$mTotalScroll")
                }
            }
        }

        override fun forwardMoveStage() {
            isFinished = true
        }

        override fun forwardMoveStep(): Boolean {
            if (isFinished) {
                return false
            }
            mCurrTime = AnimationUtils.currentAnimationTimeMillis()
            val elapsedTime = mCurrTime - mStartTime
            if (elapsedTime <= mDuration) {
                mLastScroll = mCurrScroll
                val ratio = mInterpolator.interpolate(elapsedTime / mDuration.toFloat())
                mCurrScroll = (mTotalScroll * ratio).toInt()
                if (mLastScroll < mCurrScroll || mLastScroll + 1.also {
                        mCurrScroll = it
                    } <= mTotalScroll) {
                    if (DEBUG) {
                        Log.d(
                            TAG, "forwardMoveStep mTotalScroll=" + mTotalScroll + ", ratio=" +
                                    ratio + ", elapsedTime=" + elapsedTime + ", dy=" + (mCurrScroll - mLastScroll)
                        )
                    }
                    return true
                }
                if (DEBUG) {
                    Log.d(TAG, "forwardMoveStep complete in advance")
                }
            } else {
                if (DEBUG) {
                    Log.d(TAG, "forwardMoveStep move to next stage")
                }
            }
            forwardMoveStage()
            return forwardMoveStep()
        }

        protected abstract class Interpolator {
            abstract fun interpolate(input: Float): Float
            abstract fun inverse(fraction: Float): Float
        }

        protected class DecelerateInterpolator : Interpolator() {
            private val mInertia = 0.1f

            override fun interpolate(@FloatRange(from = 0.0, to = 1.0) input: Float): Float {
                return 1.0f - (1.0f - input) * (1.0f - input)
            }

            override fun inverse(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Float {
                return 1.0f - sqrt(1.0 - mInertia * fraction).toFloat()
            }
        }

        protected class DecelerateInterpolator2 : Interpolator() {
            private val mInertia = 0.1f
            override fun interpolate(@FloatRange(from = 0.0, to = 1.0) input: Float): Float {
                return sin(input * Math.PI / 2.0).toFloat()
            }

            override fun inverse(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Float {
                return (2.0 * asin((mInertia * fraction).toDouble()) / Math.PI).toFloat()
            }
        }
    }

    companion object {
        private const val TAG = "DelayScrollLayout"
        private const val DEBUG = DebugUtils.DEBUG

        private const val SCROLL_STATE_IDLE = 0
        private const val SCROLL_STATE_DRAGGING = 1
        private const val SCROLL_STATE_SETTLING = 2
    }
}