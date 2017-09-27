package com.telesoftas.core.samples.sample

import android.support.v7.app.AppCompatActivity
import com.telesoftas.core.samples.R

interface Sample {
    val nameText: String
    val descriptionText: String
    val imageDrawableRes: Int

    fun showSampleScreen(activity: AppCompatActivity)

    companion object {
        private val EMPTY = object : Sample {
            override val imageDrawableRes: Int = R.mipmap.ic_launcher
            override val nameText: String = ""
            override val descriptionText: String = ""

            override fun showSampleScreen(activity: AppCompatActivity) {
                // Do nothing
            }
        }

        fun empty() = EMPTY
    }
}