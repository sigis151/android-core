package com.telesoftas.core.samples.sample

import android.support.v7.app.AppCompatActivity

interface Sample {
    fun showSampleScreen(activity: AppCompatActivity)

    companion object {
        private val EMPTY = object : Sample {
            override fun showSampleScreen(activity: AppCompatActivity) {
                // Do nothing
            }
        }

        fun empty() = EMPTY
    }
}