package com.telesoftas.core.samples.widgets.modalprogress

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.telesoftas.core.samples.R
import kotlinx.android.synthetic.main.activity_modal_progress.*

class ModalProgressViewActivity : AppCompatActivity(), ColorPickerDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal_progress)
        launchButton.setOnClickListener {
            applyConfig()
            modalProgressView.showProgress()
        }
        colorView.setOnClickListener {
            ColorPickerDialog.newBuilder().setColor(modalProgressView.progressColor)
                    .show(this);
        }
        setUpInitialConfigState()
    }

    private fun setUpInitialConfigState() {
        delayCheckBox.isChecked = modalProgressView.showProgressDelay
        (delayTimeEditText as TextView).text = modalProgressView.progressDelay.toString()
        (animationTimeEditText as TextView).text =
                modalProgressView.progressAnimationDuration.toString()
        colorView.background = ColorDrawable(modalProgressView.progressColor)
    }

    private fun applyConfig() {
        modalProgressView.showProgressDelay = delayCheckBox.isChecked
        modalProgressView.progressDelay = delayTimeEditText.text.toString().toLong()
        modalProgressView.progressAnimationDuration = animationTimeEditText.text.toString().toLong()
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        colorView.background = ColorDrawable(color)
        modalProgressView.progressColor = color
    }

    override fun onDialogDismissed(dialogId: Int) {
        // Do nothing
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ModalProgressViewActivity::class.java)
            activity.startActivity(intent)
        }
    }
}