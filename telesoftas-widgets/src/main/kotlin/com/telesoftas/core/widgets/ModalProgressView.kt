package com.telesoftas.core.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.telesoftas.core.widgets.utils.animators.AlphaAnimator
import com.telesoftas.core.widgets.utils.delayer.ActionDelayer
import com.telesoftas.core.widgets.utils.obtainAttributes
import com.telesoftas.core.widgets.utils.progress.ProgressViewController
import kotlinx.android.synthetic.main.layout_progress_bar_modal.view.*

class ModalProgressView : FrameLayout, ProgressViewController {
    private val actionDelayer = ActionDelayer(Handler(), DEFAULT_DELAY_TIME)
    private val progressAnimator = AlphaAnimator()
    private var progressColor: Int = DEFAULT_PROGRESS_COLOR
    private var progressLayout: Int = DEFAULT_PROGRESS_LAYOUT
    private var showProgressDelay: Boolean = DEFAULT_SHOW_PROGRESS_DELAY
    private var progressAnimationDuration = DEFAULT_ANIMATION_DURATION
    private var progressDelay = DEFAULT_DELAY_TIME

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attributeSet: AttributeSet) {
        setUpValuesFromAttributeSet(attributeSet)
        setUpProgressDelay()
        setUpProgressAnimationDuration()
    }

    private fun setUpValuesFromAttributeSet(attributeSet: AttributeSet) {
        attributeSet.obtainAttributes(
                context,
                R.styleable.ModalProgressView
        ) { typedArray ->
            progressColor = retrieveProgressColor(typedArray)
            progressLayout = retrieveProgressLayout(typedArray)
            showProgressDelay = retrieveShowProgressDelay(typedArray)
            progressDelay = retrieveProgressDelay(typedArray)
            progressAnimationDuration = retrieveProgressAnimationDuration(typedArray)
        }
    }

    private fun retrieveProgressColor(typedArray: TypedArray): Int {
        val styleable = R.styleable.ModalProgressView_progressBarColor
        val defaultColor = ContextCompat.getColor(context, android.R.color.white)
        return typedArray.getColor(styleable, defaultColor)
    }

    private fun retrieveProgressLayout(typedArray: TypedArray): Int {
        val styleable = R.styleable.ModalProgressView_progressLayout
        return typedArray.getResourceId(styleable, DEFAULT_PROGRESS_LAYOUT)
    }

    private fun retrieveShowProgressDelay(typedArray: TypedArray): Boolean {
        val styleable = R.styleable.ModalProgressView_showProgressDelay
        return typedArray.getBoolean(styleable, DEFAULT_SHOW_PROGRESS_DELAY)
    }

    private fun retrieveProgressDelay(typedArray: TypedArray): Long {
        val styleable = R.styleable.ModalProgressView_progressDelay
        return typedArray.getInteger(styleable, DEFAULT_DELAY_TIME.toInt()).toLong()
    }

    private fun retrieveProgressAnimationDuration(typedArray: TypedArray): Long {
        val styleable = R.styleable.ModalProgressView_progressAnimationDuration
        return typedArray.getInteger(styleable, DEFAULT_ANIMATION_DURATION.toInt()).toLong()
    }

    private fun setUpProgressDelay() {
        if (showProgressDelay) {
            actionDelayer.setDelay(progressDelay)
        } else {
            actionDelayer.setDelay(0)
        }
    }

    private fun setUpProgressAnimationDuration() {
        progressAnimator.setDuration(progressAnimationDuration)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.layout_progress_bar_modal, this)
        View.inflate(context, progressLayout, modalProgressLayout)
        applyProgressBarTheme()
        progressAnimator.setTarget(modalProgressLayout)
    }

    private fun applyProgressBarTheme() {
        modalProgressLayout.findViewById<ProgressBar>(R.id.progressBar)
                .indeterminateDrawable.setColorFilter(progressColor, PorterDuff.Mode.SRC_ATOP)
    }

    private fun applyHighestElevationToModalProgress() {
        ViewCompat.setElevation(modalProgressLayout, findHighestElevation() + 1f)
    }

    private fun findHighestElevation(): Float =
            ViewCompat.getElevation((childCount - 1).downTo(0)
                    .map { index -> getChildAt(index) }
                    .maxBy { view -> ViewCompat.getElevation(view) })

    override fun showProgress() {
        applyHighestElevationToModalProgress()
        actionDelayer.delay { progressAnimator.showProgress() }
    }

    override fun hideProgress() {
        actionDelayer.cancelDelaysWithAction { progressAnimator.hideProgress() }
    }

    companion object {
        private const val DEFAULT_DELAY_TIME = 150L
        private const val DEFAULT_ANIMATION_DURATION = 150L
        private const val DEFAULT_SHOW_PROGRESS_DELAY = true
        private const val DEFAULT_PROGRESS_COLOR = Color.WHITE
        private val DEFAULT_PROGRESS_LAYOUT = R.layout.layout_default_progress
    }
}