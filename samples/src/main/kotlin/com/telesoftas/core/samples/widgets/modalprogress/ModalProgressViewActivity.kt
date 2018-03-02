package com.telesoftas.core.samples.widgets.modalprogress

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity

class ModalProgressViewActivity : AppCompatActivity() {
    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ModalProgressViewActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
