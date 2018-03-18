package com.telesoftas.core.common.intent.email

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.telesoftas.core.common.intent.IntentFactory

/**
 * Creates the [Intent] for the email [android.app.Activity], using [EmailChooserData]
 * and subtracting it's suffix and prefix.
 *
 * * A simple usage example would be launching the email [Intent] via a button action
 * that passes an email with no additional processing required, e.g. `test@example.com`
 * * A more complex usage example would be launching the email [Intent] via an HTML
 * `<a href="mail:test@example.com"/>` hyperlink click in a [android.webkit.WebView], in such
 * a case the parsed href link could be directly set as the `email` field in [EmailChooserData]
 * and a `mail:` suffix added instead of the default empty one.
 *
 * @see EmailChooserData
 */
class EmailChooserIntentFactory : IntentFactory<EmailChooserData> {
    override fun createIntent(context: Context, data: EmailChooserData): Intent {
        val (rawEmail, title, prefix, suffix) = data
        val email = rawEmail.removePrefix(prefix).removeSuffix(suffix)
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        return Intent.createChooser(intent, title)
    }
}

fun Context.createEmailIntent(
        email: String,
        chooserTitle: String,
        emailPrefix: String = "",
        emailSuffix: String = ""
): Intent = EmailChooserIntentFactory().createIntent(
        context = this,
        data = EmailChooserData(
                email = email,
                chooserTitle = chooserTitle,
                emailPrefix = emailPrefix,
                emailSuffix = emailSuffix
        ))