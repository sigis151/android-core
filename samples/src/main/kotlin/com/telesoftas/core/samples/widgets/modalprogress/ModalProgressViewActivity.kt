package com.telesoftas.core.samples.widgets.modalprogress

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.telesoftas.core.samples.R

class ModalProgressViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal_progress)
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ModalProgressViewActivity::class.java)
            activity.startActivity(intent)
        }
    }
}