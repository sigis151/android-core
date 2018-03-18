package com.telesoftas.core.common.intent.phone

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.telesoftas.core.common.intent.IntentFactory

/**
 * Creates the [Intent] for the phone dial [android.app.Activity], using [PhoneDialData]
 * and subtracting it's suffix and prefix.
 *
 * * A simple usage example would be launching the phone dial [Intent] via a button action
 * that passes a phone number with no additional processing required, e.g. +00 00000000
 * * A more complex usage example would be launching the phone dial [Intent] via an HTML
 * `<a href="tel:00000000"/>` hyperlink click in a [android.webkit.WebView], in such
 * a case the parsed href link could be directly set as the `phone` field in [PhoneDialData]
 * and a `tel:` suffix added instead of the default empty one.
 *
 * @see PhoneDialData
 */
class PhoneDialIntentFactory : IntentFactory<PhoneDialData> {
    override fun createIntent(context: Context, data: PhoneDialData): Intent {
        val (rawPhone, prefix, suffix) = data
        val phone = rawPhone.removePrefix(prefix).removeSuffix(suffix)
        return Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
    }
}