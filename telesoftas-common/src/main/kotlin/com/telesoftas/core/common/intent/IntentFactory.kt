package com.telesoftas.core.common.intent

import android.content.Context
import android.content.Intent

interface IntentFactory<in T> {
    fun createIntent(context: Context, data: T): Intent
}